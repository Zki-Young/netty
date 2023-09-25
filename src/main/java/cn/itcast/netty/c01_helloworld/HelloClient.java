package cn.itcast.netty.c01_helloworld;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/12 08:17
 * @company：CTTIC
 */
public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    //在建立连接后被调用
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        //字符串转为字节 编码器
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost", 8080))
                .sync()//同步方法/阻塞方法，连接简历后才继续向下执行
                .channel()
                //发送消息(收发数据都会走handler)
                .writeAndFlush("hello, world");
    }
}
