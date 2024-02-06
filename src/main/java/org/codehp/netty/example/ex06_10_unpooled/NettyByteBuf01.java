package org.codehp.netty.example.ex06_10_unpooled;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuf01 {
    public static void main(String[] args) {
        ByteBuf buf = Unpooled.buffer(10);

        for(int i = 0; i < 10; ++i) {
            buf.writeByte(i);
        }

        for(int i = 0; i < buf.capacity(); ++i) {
            System.out.println(buf.readByte());
        }
    }
}
