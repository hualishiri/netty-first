package org.codehp.netty.example.ex05_08_07;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        // super.channelRead(ctx, msg);
        System.out.println("【服务端】服务器读取线程 " + Thread.currentThread().getName() + " channel = " + ctx.channel());
        System.out.println("【服务端】server ctx = " + ctx);
        System.out.println("【服务端】看看channel 和pipeline的关系");
        final Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();

        ByteBuf buf = (ByteBuf) msg;
        System.out.println("【服务端】客户端发送消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("【服务端】客户端地址：" + channel.remoteAddress());

        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                 try {
                     Thread.sleep(5000);
                     ctx.writeAndFlush(Unpooled.copiedBuffer("【服务器】异步任务返回结果1" + new Date(), CharsetUtil.UTF_8));
                     System.out.println("【服务器】异步任务1 channel code = " + channel.hashCode());
                 } catch (Exception ex) {
                     ex.printStackTrace();
                 }
            }
        });

        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("【服务器】异步任务返回结果2" + new Date(), CharsetUtil.UTF_8));
                    System.out.println("【服务器】异步任务2 channel code = " + channel.hashCode());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("【服务器】定时任务返回结果" + new Date(), CharsetUtil.UTF_8));
                    System.out.println("【服务器】定时任务 channel code = " + channel.hashCode());
                    ctx.fireChannelRead(msg);
                    ctx.fireChannelReadComplete();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, 5, TimeUnit.SECONDS);

        System.out.println("going on");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // super.channelReadComplete(ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("【服务端】hello, 客户端~", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
