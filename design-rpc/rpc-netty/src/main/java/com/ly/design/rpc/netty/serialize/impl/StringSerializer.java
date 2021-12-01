package com.ly.design.rpc.netty.serialize.impl;

import com.ly.design.rpc.netty.serialize.Serializer;

import java.nio.charset.StandardCharsets;

public class StringSerializer implements Serializer<String> {
    @Override
    public int size(String entry) {
        return entry.getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    public void serialize(String entry, byte[] bytes, int offset, int length) {
        byte [] strBytes = entry.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(strBytes, 0, bytes, offset, strBytes.length);
    }

    @Override
    public String parse(byte[] bytes, int offset, int length) {
        return new String(bytes, offset, length, StandardCharsets.UTF_8);
    }

    @Override
    public byte type() {
        return Types.TYPE_STRING;
    }

    @Override
    public Class<String> getSerializeClass() {
        return String.class;
    }
}
