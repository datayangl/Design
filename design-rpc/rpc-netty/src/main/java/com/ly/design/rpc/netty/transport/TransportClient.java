package com.ly.design.rpc.netty.transport;

import java.io.Closeable;
import java.net.SocketAddress;
import java.util.concurrent.TimeoutException;

public interface TransportClient extends Closeable {
    Transport createTransport(SocketAddress address, long connectionTimeout) throws InterruptedException, TimeoutException;
    @Override
    void close();
}
