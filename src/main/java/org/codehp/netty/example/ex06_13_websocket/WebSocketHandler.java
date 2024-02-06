package org.codehp.netty.example.ex06_13_websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Date;

public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // super.handlerAdded(ctx);
        System.out.println("服务器被调用：" + ctx.channel().id().asLongText());
        System.out.println("服务器被调用：" + ctx.channel().id().asShortText());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器接收到消息：" + msg.text());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器的时间为：" + new Date() + " " + msg.text()));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // super.handlerRemoved(ctx);
        System.out.println("服务器下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // super.exceptionCaught(ctx, cause);
        System.out.println("服务器异常终止");
    }
}
