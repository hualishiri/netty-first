package org.codehp.netty.example.ex05_10_http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyHttpServer {
    static public void main(String[] args) {
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
        EventLoopGroup workLoopGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossLoopGroup, workLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childHandler(new HttpServerInitializer());

            ChannelFuture cf = bootstrap.bind(6888).sync();

            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("服务器启动了");
                    } else {
                        System.out.println("服务器失败了");
                    }
                }
            });

            cf.channel().closeFuture().sync();
        } catch (Exception ex) {
            ex.printStackTrace();;
        } finally {
            bossLoopGroup.shutdownGracefully();
            workLoopGroup.shutdownGracefully();
        }
    }
}
