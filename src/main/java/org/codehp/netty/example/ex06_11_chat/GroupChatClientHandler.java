package org.codehp.netty.example.ex06_11_chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class GroupChatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        System.out.println("【客户端】" + ctx.channel().remoteAddress() + " 发送了：" + s);
    }
}
