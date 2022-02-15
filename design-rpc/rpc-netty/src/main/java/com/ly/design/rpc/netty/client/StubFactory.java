package com.ly.design.rpc.netty.client;

import com.ly.design.rpc.netty.transport.Transport;

public interface StubFactory {
    <T> T createStub(Transport transport, Class<T> serviceClass);
}
