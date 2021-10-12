package io.github.kimmking.gateway.outbound.nettyclient;

import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.pool.SimpleChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
public class NettyClient {

    private final EventLoopGroup group = new NioEventLoopGroup(4 * 2);
    private final Bootstrap bootstrap = new Bootstrap();
    private ChannelPoolMap<NettyClientHttpRequest, SimpleChannelPool> channelPoolMap;

    //传入多个url，以便route
    private List<String> backendUrls;
    private static NettyClient instance = new NettyClient();
    public static NettyClient getInstance() {
        return instance;
    }

    public NettyClient() {
        this.bootstrap.group(this.group)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 500)
                .option(ChannelOption.TCP_NODELAY, true)
                .channel(NioSocketChannel.class);
        this.channelPoolMap = new AbstractChannelPoolMap<NettyClientHttpRequest, SimpleChannelPool>() {
            @Override
            protected SimpleChannelPool newPool(NettyClientHttpRequest key) {

                return new FixedChannelPool(bootstrap.remoteAddress(key.getSocketAddress()), new NettyClientPoolHandler(), 50);
            }
        };
    }


    public void request(final NettyClientHttpRequest httpRequest, final ChannelHandlerContext serverChannel, final URI uri, HttpRequestFilter filter) throws InterruptedException, URISyntaxException {

        SimpleChannelPool pool = channelPoolMap.get(httpRequest);
        Future<Channel> channelFuture = pool.acquire().sync();
        channelFuture.addListener((FutureListener<Channel>) future -> {
            if (future.isSuccess()) {
                Channel clientChannel = future.getNow();
                //clientChannel bind with serverChannel
                clientChannel.attr(Attributes.SERVER_CHANNEL).set(serverChannel);
                clientChannel.attr(Attributes.CLIENT_POOL).set(pool);
                log.info("服务器端请求："+httpRequest.toString());
                //组装http get请求并发送
                FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, uri.toASCIIString());
                request.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                request.headers().add(HttpHeaderNames.CONTENT_LENGTH,request.content().readableBytes());
                clientChannel.writeAndFlush(request);

                //write data
//                clientChannel.writeAndFlush(httpRequest.getHttpRequest());
            }
        });
    }
}
