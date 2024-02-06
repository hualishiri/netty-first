package org.codehp.netty.example.ex09_04_sticker;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MsgDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
        System.out.println("MsgDecoder被调用");
        MessageProtocol msg = new MessageProtocol();
        msg.setLen(buf.readInt());
        msg.setData(new byte[msg.getLen()]);
        buf.readBytes(msg.getData());

        list.add(msg);
    }
}
