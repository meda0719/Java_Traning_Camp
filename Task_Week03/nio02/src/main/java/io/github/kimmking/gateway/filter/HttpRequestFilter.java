package io.github.kimmking.gateway.filter;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public interface HttpRequestFilter {
    
    void filter(FullHttpRequest fullRequest, Channel ctx);
    
}
