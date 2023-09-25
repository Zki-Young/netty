package cn.itcast.netty.echotest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/14 14:37
 * @company：CTTIC
 */
@Slf4j
public class EchoServer {
    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(final NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline
                                .addLast("handler01", new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf buf = (ByteBuf) msg;
                                        String msgString = buf.toString(Charset.defaultCharset());
                                        log.info(msgString);

                                        //向client返回消息
                                        ByteBuf response = ctx.alloc().buffer(20);
                                        response.writeBytes(msgString.getBytes());
                                        ctx.writeAndFlush(response);

                                        //不能重写response，会报错
//                                        response.writeBytes("02".getBytes());
//                                        ctx.writeAndFlush(response);
                                    }
                                })
                        ;
                    }
                })
                .bind(8080);
    }
}
