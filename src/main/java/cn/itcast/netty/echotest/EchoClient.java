package cn.itcast.netty.echotest;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/14 15:07
 * @company：CTTIC
 */
@Slf4j
public class EchoClient {
    public static void main(String[] args) throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline
                                .addLast(new StringEncoder())
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf buf = (ByteBuf) msg;
                                        log.info("收到server回声：{}", buf.toString(Charset.defaultCharset()));

                                    }
                                })
                        ;
                    }
                })
                .connect(new InetSocketAddress("localhost", 8080));
        channelFuture.sync();
        Channel channel = channelFuture.channel();
        channel.writeAndFlush("hello, I'm echoClient");
        while (true) {
            Scanner sc = new Scanner(System.in);
            String s = sc.nextLine();
            channel.writeAndFlush(s);
        }
    }
}
