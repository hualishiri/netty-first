package org.codehp.netty.example.ex05_10_http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println("对应的channel = " + ctx.channel() + " pipeline = " + ctx.pipeline() +
                " 通过pipeline获取的channel" + ctx.pipeline().channel());
        System.out.println("当前ctx的handler = " + ctx.handler());

        if (msg instanceof HttpRequest) {
            System.out.println("ctx 类型=" + ctx.getClass());

            System.out.println("pipeline hashcode = " + ctx.pipeline().hashCode() +
                    " TestServerHandler hash = " + this.hashCode());
            System.out.println("msg 类型= " + msg.getClass());
            System.out.println("客户端地址：" + ctx.channel().remoteAddress());

            HttpRequest httpRequest = (HttpRequest) msg;

            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("favicon.ico 不作响应");
                return;
            }

            ByteBuf buf = Unpooled.copiedBuffer("我是服务器，hello", CharsetUtil.UTF_8);

            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());

            ctx.writeAndFlush(response);
        }

    }


}
