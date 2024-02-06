package org.codehp.netty.example.ex06_10_unpooled;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class NettyByteBuf02 {
    public static void main(String[] args) {
        ByteBuf buf = Unpooled.copiedBuffer("hello world!", Charset.forName("utf-8"));

        if (buf.hasArray()) {
            byte[] content = buf.array();

            System.out.println(new String(content, Charset.forName("utf-8")));
            System.out.println("byteBuf = " + buf);

            System.out.println(buf.arrayOffset());
            System.out.println(buf.readerIndex());
            System.out.println(buf.writerIndex());
            System.out.println(buf.capacity());

            System.out.println(buf.getByte(0));

            int len = buf.readableBytes();
            System.out.println("len=" + len);

            for(int i = 0; i < len; ++i) {
                System.out.println((char)buf.getByte(i));
            }

            System.out.println(buf.getCharSequence(0, 4, Charset.forName("utf-8")));
            System.out.println(buf.getCharSequence(4, 6, Charset.forName("utf-8")));
        }
    }
}
