package org.codehp.netty.example.ex06_12_heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent ise = (IdleStateEvent) evt;
            String eventType = null;
            switch (ise.state()) {
                case READER_IDLE: {
                    eventType = "读空闲";
                    break;
                }
                case WRITER_IDLE: {
                    eventType = "写空闲";
                    break;
                }
                case ALL_IDLE: {
                    eventType = "读写空闲";
                    break;
                }
            }

            System.out.println("服务器：" + ctx.channel().remoteAddress() + " 超时类型：" + eventType);
        }
    }
}
