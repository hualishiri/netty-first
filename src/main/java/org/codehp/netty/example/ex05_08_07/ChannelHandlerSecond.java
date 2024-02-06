package org.codehp.netty.example.ex05_08_07;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

public class ChannelHandlerSecond extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        // super.channelRead(ctx, msg);
        System.out.println("【ChannelHandlerSecond】服务器读取线程 " + Thread.currentThread().getName() + " channel = " + ctx.channel());
        System.out.println("【ChannelHandlerSecond】server ctx = " + ctx);
        System.out.println("【ChannelHandlerSecond】看看channel 和pipeline的关系");
        final Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();

        ByteBuf buf = (ByteBuf) msg;
        System.out.println("【ChannelHandlerSecond】客户端发送消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("【ChannelHandlerSecond】客户端地址：" + channel.remoteAddress());
        System.out.println("【ChannelHandlerSecond】going on");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // super.channelReadComplete(ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("【ChannelHandlerSecond】hello, 客户端~", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
