package cn.itcast.netty.c05;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.util.HashMap;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/18 10:25
 * @company：CTTIC
 */
public class HelloWorldClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            for (int i = 0; i < 10; i++) {
                Channel channel = creatChannelAndSentMessage(group);
                channel.close();
            }
        } catch (InterruptedException e) {
        } finally {
            group.shutdownGracefully();
        }
    }

    private static Channel creatChannelAndSentMessage(NioEventLoopGroup group) throws InterruptedException {
        Channel channel = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new StringEncoder())
                                .addLast(new LoggingHandler(LogLevel.DEBUG));
                    }
                })
                .connect(new InetSocketAddress("localhost", 8080))
                .sync().channel();
        sentMessage(channel);
        return channel;
    }

    private static void sentMessage(Channel channel) {
        ByteBuf byteBuf = channel.alloc().buffer(16);
        byte[] bytes = new byte[]{'0', 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};
        byteBuf.writeBytes(bytes);
        channel.writeAndFlush(byteBuf);
    }
}
