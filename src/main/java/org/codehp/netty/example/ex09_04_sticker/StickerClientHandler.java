package org.codehp.netty.example.ex09_04_sticker;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class StickerClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count = 0;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // super.channelActive(ctx);
        for(int i = 0; i < 10; ++i) {
            MessageProtocol msg = new MessageProtocol();
            msg.setData(("hello, server" + i).getBytes(Charset.forName("utf-8")));
            msg.setLen(msg.getData().length);
            ctx.writeAndFlush(msg);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol msg) throws Exception {
        System.out.println("【客户端】接收到消息：" + new String(msg.getData(), Charset.forName("utf-8")));
        System.out.println("【客户端】接收到消息数据量：" + (++count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
