package io.github.kimmking.gateway.outbound.nettyclient;

import io.netty.handler.codec.http.HttpRequest;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class NettyClientHttpRequest {
    public static final String HTTP = "HTTP";
    public static final String HTTPS = "HTTPS";
//    private Route route;
    private URI uri;
    private HttpRequest httpRequest;

    public NettyClientHttpRequest(URI uri, HttpRequest httpRequest) {
//        this.route = route;
        this.uri = uri;
        this.httpRequest = httpRequest;
    }

    /**
     * 获取转发请求的Host
     *
     * @return
     */
    public String getHost() {
        if (uri.getHost() == null) {
            throw new RuntimeException("no host found");
        }
        return uri.getHost();
    }

    /**
     * 5
     * 获取端口号
     *
     * @return
     */
    public int getPort() {
        URL url = null;
        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String protocol = url.getProtocol() == null ? HTTP : url.getProtocol();
        int port = url.getPort();
        if (port == -1) {
            if (HTTP.equalsIgnoreCase(protocol)) {
                port = 80;
            } else if (HTTPS.equalsIgnoreCase(protocol)) {
                port = 443;
            }
        }
        return port;
    }


    public InetSocketAddress getSocketAddress() {
        return new InetSocketAddress(getHost(), getPort());
    }

    public HttpRequest getHttpRequest() {
        return this.httpRequest;
    }
}
