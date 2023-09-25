package cn.itcast.netty.echotest;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.Charset;

/**
 * @Description: 使用Redis协议，向redis发送指令，完成数据的插入
 * @Auther: Zki Young
 * @Date: 2023/9/18 15:13
 * @company：CTTIC
 */
public class TestRedis {
    public static void main(String[] args) {
        //代表回车+换行
        final byte[] LINE = {13, 10};
        ChannelFuture channel = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new LoggingHandler())
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        ByteBuf buffer = ctx.alloc().buffer();
                                        buffer.writeBytes("*3".getBytes());
                                        buffer.writeBytes(LINE);
                                        buffer.writeBytes("$3".getBytes());
                                        buffer.writeBytes(LINE);
                                        buffer.writeBytes("set".getBytes());
                                        buffer.writeBytes(LINE);
                                        buffer.writeBytes("$4".getBytes());
                                        buffer.writeBytes(LINE);
                                        buffer.writeBytes("name".getBytes());
                                        buffer.writeBytes(LINE);
                                        buffer.writeBytes("$3".getBytes());
                                        buffer.writeBytes(LINE);
                                        buffer.writeBytes("zki".getBytes());
                                        buffer.writeBytes(LINE);
                                        ctx.writeAndFlush(buffer);
                                    }

                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf buf = (ByteBuf) msg;
                                        System.out.println(buf.toString(Charset.defaultCharset()));
                                    }

                                });
                    }
                })
                .connect("127.0.0.1", 6379);
        try {
            channel.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
