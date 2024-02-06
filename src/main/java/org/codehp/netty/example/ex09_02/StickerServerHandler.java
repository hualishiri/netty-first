package org.codehp.netty.example.ex09_02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.UUID;

public class StickerServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count = 0;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        byte[] buffer = new byte[byteBuf.readableBytes()];

        byteBuf.readBytes(buffer);

        System.out.println("【服务器】接收到消息：" + new String(buffer, Charset.forName("utf-8")));
        System.out.println("【服务器】接收到消息数据量：" + (++count));

        ctx.writeAndFlush(Unpooled.copiedBuffer("回复的消息未：" + UUID.randomUUID().toString(), CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
