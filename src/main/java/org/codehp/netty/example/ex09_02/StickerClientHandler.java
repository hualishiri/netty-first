package org.codehp.netty.example.ex09_02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class StickerClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count = 0;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // super.channelActive(ctx);
        for(int i = 0; i < 10; ++i) {
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello, server " + i, Charset.forName("utf-8")));
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        byte[] buffer = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(buffer);

        System.out.println("【客户端】接收到消息：" + new String(buffer, Charset.forName("utf-8")));
        System.out.println("【客户端】接收到消息数据量：" + (++count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
