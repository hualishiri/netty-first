package org.codehp.netty.example.ex587;

import com.sun.security.ntlm.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main( String[] args )
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("【服务端】客户SocketChannel hashcode=" + ch.hashCode());
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture cf = bootstrap.bind(6888).sync();

            cf.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("【服务端】监听端口 6888 成功");
                    } else {
                        System.out.println("【服务端】监听端口 6888 失败");
                    }
                }
            });
            cf.channel().closeFuture().sync();
        } catch (Exception ex) {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}
