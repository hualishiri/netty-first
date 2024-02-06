package org.codehp.netty.example.ex11_04_rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RpcClient {
    public static String providerName = "HelloService#hello#";
    public static RpcClientHandler handler;

    public static ExecutorService executor = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors() * 2,
            Runtime.getRuntime().availableProcessors() * 2,
            60 * 5,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );
    private int count = 0;
    public static void main(String[] args) throws InterruptedException {
        RpcClient client = new RpcClient();
        HelloService helloService = (HelloService) client.getBean(HelloService.class, providerName);

        for(; ;) {
            Thread.sleep(2 * 1000);
            String result = helloService.hello("你好，rpc");
            System.out.println("调用结果：" + result);
        }
    }

    public Object getBean(final Class<?> serviceClass, final String providerName) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{serviceClass}, ((proxy, method, args) -> {
                    System.out.println("(proxy, method, args) 进入...." + (++count) + "次");

                    if (handler == null) {
                        initClient();
                    }

                    handler.setParam(providerName + args[0]);

                    return executor.submit(handler).get();
                }));
    }

    public static void initClient() {
        EventLoopGroup workGroup = new NioEventLoopGroup();
        handler = new RpcClientHandler();
        try {
            Bootstrap bs = new Bootstrap();
            bs.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            ChannelPipeline pipeline = sc.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(handler);
                        }
                    });
            bs.connect("127.0.0.1", 6888).sync();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
