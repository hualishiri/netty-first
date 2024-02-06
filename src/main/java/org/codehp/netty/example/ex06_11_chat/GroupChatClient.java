package org.codehp.netty.example.ex06_11_chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class GroupChatClient {
    public static void main(String[] args) {
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            Bootstrap bs = new Bootstrap();
            bs.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            sc.pipeline().addLast("encoder", new StringEncoder());
                            sc.pipeline().addLast("decoder", new StringDecoder());
                            sc.pipeline().addLast(new GroupChatClientHandler());
                        }
                    });

            ChannelFuture cf = bs.connect("127.0.0.1", 6888).sync();

            Scanner scan = new Scanner(System.in);
            while(scan.hasNextLine()) {
                String msg = scan.nextLine();
                cf.channel().writeAndFlush(msg);
            }

            cf.channel().closeFuture().sync();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
        }

    }
}
