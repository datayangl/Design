package com.ly.design.rpc.netty.transport.netty;

import com.ly.design.rpc.netty.transport.command.Command;
import com.ly.design.rpc.netty.transport.command.Header;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public abstract class CommandDecoder extends ByteToMessageDecoder {
    private static final int LENGTH_FIELD_LENGTH = Integer.BYTES;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.isReadable(LENGTH_FIELD_LENGTH)) {
            return;
        }

        byteBuf.markReaderIndex();
        int length = byteBuf.readByte() - LENGTH_FIELD_LENGTH;

        if(byteBuf.readableBytes() < length) {
            byteBuf.resetReaderIndex();
            return;
        }

        Header header = decodeHeader(channelHandlerContext, byteBuf);
        int payloadLength  = length - header.length();
        byte [] payload = new byte[payloadLength];
        byteBuf.readBytes(payload);
        list.add(new Command(header, payload));
    }

    protected abstract Header decodeHeader(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) ;
}
