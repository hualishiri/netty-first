package org.codehp.netty.example.ex09_04_sticker;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MsgEncoder extends MessageToByteEncoder<MessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf buf) throws Exception {
        System.out.println("MsgEncoder消息被调用");
        buf.writeInt(msg.len);
        buf.writeBytes(msg.getData());
    }
}
