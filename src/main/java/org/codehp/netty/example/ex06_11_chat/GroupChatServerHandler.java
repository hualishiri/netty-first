package org.codehp.netty.example.ex06_11_chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // super.handlerAdded(ctx);
        // System.out.println("客户端上线了");
        channelGroup.writeAndFlush("客户端：" + ctx.channel().remoteAddress() + " 加入聊天 "
                + sdf.format(new Date()) + " \n");
        channelGroup.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // super.handlerRemoved(ctx);
        // System.out.println("")
        channelGroup.writeAndFlush("客户端：" + ctx.channel().remoteAddress() + " 离开了 "
                + sdf.format(new Date()) + " \n");
        System.out.println("channelGroup size = " + channelGroup.size());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // super.channelActive(ctx);
        System.out.println(ctx.channel().remoteAddress() + " 上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // super.channelInactive(ctx);
        System.out.println(ctx.channel().remoteAddress() + " 离线了");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        for(Channel channel : channelGroup) {
            if (channel != ctx.channel()) {
                channel.writeAndFlush("【客户】" + ctx.channel().remoteAddress() + " 发送了消息：" + s + "\n");
            } else {
                ctx.writeAndFlush("【自己】发送了消息 " + s + "\n");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
