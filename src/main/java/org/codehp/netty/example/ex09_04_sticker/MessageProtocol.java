package org.codehp.netty.example.ex09_04_sticker;

import lombok.Data;

@Data
public class MessageProtocol {
    public int len;
    public byte[] data;
}
