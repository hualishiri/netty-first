package org.codehp.netty.example.ex06_12_heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.codehp.netty.example.ex06_11_chat.GroupChatServer;
import org.codehp.netty.example.ex06_11_chat.GroupChatServerHandler;

public class HeartBeatServer {
    private int port;

    HeartBeatServer(int port) {
        this.port = port;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bs = new ServerBootstrap();
            bs.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            socketChannel.pipeline().addLast(new IdleStateHandler(
                                    7000, 7000, 10));
                            socketChannel.pipeline().addLast(new HeartBeatServerHandler());
                            System.out.println("【服务器】接收socketChannel");
                        }
                    });
            ChannelFuture cf = bs.bind(port).sync();

            cf.addListener((ChannelFutureListener) channelFuture -> {
                if (channelFuture.isSuccess()) {
                    System.out.println("【服务器】启动6888端口成功");
                } else {
                    System.out.println("【服务器】启动6888端口失败");
                }
            });

            cf.channel().closeFuture().sync();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new HeartBeatServer(6888).run();;
    }
}
