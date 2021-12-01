package com.ly.design.rpc.netty.transport;

import com.ly.design.rpc.netty.transport.command.Command;

import java.util.concurrent.CompletableFuture;

public interface Transport {
    /**
     * 发送请求命令
     * @param request 请求命令
     * @return 返回值是一个Future，Future
     */
    CompletableFuture<Command> send(Command request);
}
