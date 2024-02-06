package org.codehp.netty.example.ex09_04_sticker;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.UUID;

public class StickerServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count = 0;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println("【服务器】接收到消息：" + new String(msg.getData(), Charset.forName("utf-8")));
        System.out.println("【服务器】接收到消息数据量：" + (++count));
        ctx.writeAndFlush(Unpooled.copiedBuffer("回复的消息未：" + UUID.randomUUID(), CharsetUtil.UTF_8));

        MessageProtocol result = new MessageProtocol();
        String uuid = UUID.randomUUID().toString();
        result.setData(uuid.getBytes(Charset.forName("utf-8")));
        result.setLen(result.getData().length);
        ctx.writeAndFlush(result);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
