package org.codehp.netty.example.ex11_04_rpc;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class RpcClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private String param;
    private String result;
    private ChannelHandlerContext content;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // super.channelActive(ctx);
        System.out.println("channelActive被调用 ");
        content = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object s) throws Exception {
        System.out.println("channelRead0被调用 ");
        result = s.toString();
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // super.exceptionCaught(ctx, cause);
        ctx.close();
    }

    @Override
    public synchronized Object call() throws Exception {
        System.out.println("call 被调用， para = " + param);
        ChannelFuture cf = content.writeAndFlush(param);
        cf.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("发送成功");
                } else {
                    System.out.println(channelFuture.cause().getMessage());
                    channelFuture.cause().getMessage();
                    System.out.println("发送失败");
                }
            }
        });
        wait();
        System.out.println("call 的结果为，result = " + result);
        return result;
    }

    public void setParam(String param) {
        System.out.println("setParam = " + param);
        this.param = param;
    }
}
