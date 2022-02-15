package com.ly.design.rpc.netty.client;

import com.ly.design.rpc.netty.transport.Transport;

public interface ServiceStub {
    void setTransport(Transport transport);
}
