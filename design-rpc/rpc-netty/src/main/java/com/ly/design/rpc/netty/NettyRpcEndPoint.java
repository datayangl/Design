package com.ly.design.rpc.netty;


import com.ly.design.rpc.api.RpcEndPoint;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;

public class NettyRpcEndPoint implements RpcEndPoint {
    @Override
    public <T> T getRemoteService(URI uri, Class<T> serviceClass) {
        return null;
    }

    @Override
    public <T> URI addServiceProvider(T service, Class<T> serviceClass) {
        return null;
    }

    @Override
    public Closeable startServer() throws Exception {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
