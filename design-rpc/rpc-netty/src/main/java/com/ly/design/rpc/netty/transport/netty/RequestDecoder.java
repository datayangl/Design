package com.ly.design.rpc.netty.transport.netty;

import com.ly.design.rpc.netty.transport.command.Header;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class RequestDecoder extends CommandDecoder{
    @Override
    protected Header decodeHeader(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        return null;
    }
}
