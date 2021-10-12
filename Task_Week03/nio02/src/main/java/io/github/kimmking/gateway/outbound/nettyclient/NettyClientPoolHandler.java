package io.github.kimmking.gateway.outbound.nettyclient;

import io.github.kimmking.gateway.outbound.nettyclient.NettyClientHandler;
import io.netty.channel.Channel;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import lombok.extern.slf4j.Slf4j;


    @Slf4j
    public class NettyClientPoolHandler implements ChannelPoolHandler {
        @Override
        public void channelReleased(Channel ch) throws Exception {
            log.info("channelCreated. Channel_ID is {}", ch.id());
        }

        @Override
        public void channelAcquired(Channel ch) throws Exception {
            log.info("channelCreated. Channel_ID is {}", ch.id());
        }

        @Override
        public void channelCreated(Channel ch) throws Exception {
            log.info("channelCreated. Channel_ID is {}", ch.id());
            NioSocketChannel socketChannel = (NioSocketChannel) ch;
            socketChannel.config().setTcpNoDelay(true);
            socketChannel.config().setKeepAlive(true);
            socketChannel.pipeline()
                    .addLast(new HttpClientCodec())
                    .addLast(new HttpObjectAggregator(1024 * 1024))
                    .addLast(new NettyClientHandler());
        }
    }
