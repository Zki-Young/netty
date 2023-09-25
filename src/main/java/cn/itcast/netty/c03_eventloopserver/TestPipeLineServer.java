package cn.itcast.netty.c03_eventloopserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/12 20:40
 * @company：CTTIC
 */
@Slf4j
public class TestPipeLineServer {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        new ServerBootstrap()
                .group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(final NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ChannelInboundHandlerAdapter(){
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("写入handler01");
                                System.out.println(msg);
                                super.channelRead(ctx, msg);
                            }
                        });
                        pipeline.addLast(new ChannelInboundHandlerAdapter(){
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("写入handler02");
                                System.out.println(msg);
                                super.channelRead(ctx, msg);
                            }
                        });
                        pipeline.addLast(new ChannelOutboundHandlerAdapter(){
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.info("写出handler01");
                                System.out.println(msg);
                                super.write(ctx, msg, promise);
                            }
                        });
                        pipeline.addLast(new ChannelInboundHandlerAdapter(){
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("写入handler03");
                                System.out.println(msg);
                                ch.writeAndFlush("写出一些信息，反向走handler链");
                                ctx.writeAndFlush("写出一些信息，反向走handler链");
                                super.channelRead(ctx, msg);
                            }
                        });
                        pipeline.addLast(new ChannelOutboundHandlerAdapter(){
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.info("写出handler02");
                                System.out.println(msg);
                                super.write(ctx, msg, promise);
                            }
                        });
                        pipeline.addLast(new ChannelOutboundHandlerAdapter(){
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.info("写出handler03");
                                System.out.println(msg);
                                super.write(ctx, msg, promise);
                            }
                        });
                    }
                })
                .bind(8080);
    }
}
