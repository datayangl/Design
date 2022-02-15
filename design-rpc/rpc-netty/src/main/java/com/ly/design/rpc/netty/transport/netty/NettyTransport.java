package com.ly.design.rpc.netty.transport.netty;

import com.ly.design.rpc.netty.transport.InFlightRequests;
import com.ly.design.rpc.netty.transport.ResponseFuture;
import com.ly.design.rpc.netty.transport.Transport;
import com.ly.design.rpc.netty.transport.command.Command;
import io.netty.channel.Channel;

import java.util.concurrent.CompletableFuture;

public class NettyTransport implements Transport {
    private final Channel channel;
    private InFlightRequests inFlightRequests;

    public NettyTransport(Channel channel, InFlightRequests inFlightRequests) {
        this.channel = channel;
        this.inFlightRequests = inFlightRequests;
    }

    @Override
    public CompletableFuture<Command> send(Command request) {
        // 创建新的 future
        CompletableFuture<Command> future = new CompletableFuture<>();
        try {
            // 封装为 ResponseFuture
            inFlightRequests.put(new ResponseFuture(request.getHeader().getRequestId(), future));
            channel.writeAndFlush(request).addListener(channelFuture -> {
                // 处理发送失败的情况
                if (!channelFuture.isSuccess()) {
                    future.completeExceptionally(channelFuture.cause());
                    channel.close();
                }
            });
        } catch (Exception e) {
            // 移除异常 future
            inFlightRequests.remove(request.getHeader().getRequestId());
            future.completeExceptionally(e);
        }

        return future;
    }
}
