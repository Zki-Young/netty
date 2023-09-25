package cn.itcast.netty.c03_eventloopserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/12 11:05
 * @company：CTTIC
 */
@Slf4j
public class EventLoopServer {
    public static void main(String[] args) {

        //细化职责02 新建一个组
        final DefaultEventLoopGroup group = new DefaultEventLoopGroup();

        new ServerBootstrap()
                //未划分职责
//                .group(new NioEventLoopGroup())
                //职责细化01 第一个是boss：只负责accept 第二个是worker：只负责读写
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline()
                                .addLast("handler01", new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf buf = (ByteBuf) msg;
                                        log.debug(buf.toString(Charset.defaultCharset()));
                                        //给handler02传递消息
                                        ctx.fireChannelRead(msg);
                                    }
                                })
                                .addLast(group, "handler02", new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf buf = (ByteBuf) msg;
                                        log.debug(buf.toString(Charset.defaultCharset()));
                                    }
                                })
                        ;
                    }
                })
                .bind(8080);
    }
}
