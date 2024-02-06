package org.codehp.netty.example.ex09_04_sticker;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class StickerClient {
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
                            ChannelPipeline pipeline = sc.pipeline();
                            pipeline.addLast("encoder", new MsgEncoder());
                            pipeline.addLast("decoder", new MsgDecoder());
                            pipeline.addLast(new StickerClientHandler());
                        }
                    });
            ChannelFuture cf = bs.connect("127.0.0.1", 6888).sync();
            cf.channel().closeFuture().sync();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
        }
    }
}
