package org.codehp.netty.example.ex11_04_rpc;

public class HelloServiceImpl implements HelloService{
    private int count = 0;

    @Override
    public String hello(String msg) {
        count++;
        if (msg == null) {
            return "【服务器】我收到消息了";
        } else {
            return "【服务器】收到客户端消息：" + msg + ", 次数：" + count;
        }
    }
}
