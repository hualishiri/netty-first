package org.codehp.netty.example.ex05_08_07;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // super.channelActive(ctx);
        System.out.println("【客户端】client " + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("【客户端】hello server", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // super.channelRead(ctx, msg);
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("【客户端】服务器回复的消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("【客户端】服务器的地址是：" + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
