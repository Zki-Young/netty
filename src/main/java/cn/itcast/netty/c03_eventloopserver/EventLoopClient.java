package cn.itcast.netty.c03_eventloopserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/12 08:17
 * @company：CTTIC
 */
@Slf4j
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        final NioEventLoopGroup group = new NioEventLoopGroup();
        final ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    //在建立连接后被调用
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        //调试使用的handler
                        nioSocketChannel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        //字符串转为字节 编码器
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                    }
                })
                //异步非阻塞方法
                .connect(new InetSocketAddress("localhost", 8080));

        channelFuture.sync();//同步方法/阻塞方法，连接建立后才继续向下执行
        final Channel channel = channelFuture.channel();
        channel.writeAndFlush("hello, world!");

        new Thread(new Runnable(){
            public void run() {
                Scanner sc = new Scanner(System.in);
                while (true) {
                    String s = sc.nextLine();
                    if ("q".equals(s)){
                        channel.close();
                        log.info("错误的善后位置：错误的关闭提示，close()方法是异步调用，可能channel还没关闭就出现这段提示");
                        break;
                    }
                    channel.writeAndFlush(s);
                }
            }
        }, "inputThread").start();

        //获取ClosedFuture对象：同步处理关闭
//        ChannelFuture closeFuture = channel.closeFuture();
//        closeFuture.sync();
//        log.info("正确的善后位置：等待同步关闭channel后的正确善后位置");

        //获取ClosedFuture对象：异步处理关闭
        ChannelFuture closeFuture = channel.closeFuture();
        closeFuture.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                log.info("正确的善后位置：ClosedFuture异步处理关闭");
                //优雅终止，服务端不会报错：强制断连
                group.shutdownGracefully();
            }
        });


        /*
发起线程调用一个线程全权监听 回调对象 方法异步处理结果
        channelFuture.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                Channel channel = future.channel();
                log.info("{}", channel);
                channel.writeAndFlush("你是我的我是你的谁");
            }
        });
*/
    }
}
