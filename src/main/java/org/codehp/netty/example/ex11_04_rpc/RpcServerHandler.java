package org.codehp.netty.example.ex11_04_rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class RpcServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // super.channelActive(ctx);
        System.out.println("通道激活");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // super.channelInactive(ctx);
        System.out.println("通道已断开");
    }



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object s) throws Exception {

        if (s != null && s.toString().startsWith(RpcClient.providerName)) {
            System.out.println("【服务器】接收到了请求，请求参数为：" + s);
            String result = new HelloServiceImpl().hello(
                    s.toString().substring(s.toString().lastIndexOf('#') + 1));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
