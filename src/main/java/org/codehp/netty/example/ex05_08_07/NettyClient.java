package org.codehp.netty.example.ex05_08_07;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    static public void main(String[] args) {
        EventLoopGroup workLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("【客户端】客户端 ok...");
            ChannelFuture cf = bootstrap.connect("127.0.0.1", 6888).sync();
            cf.channel().closeFuture().sync();
        } catch (Exception ex) {
            workLoopGroup.shutdownGracefully();
        }
    }
}
