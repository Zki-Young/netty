package cn.itcast.netty.echotest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/18 15:59
 * @company：CTTIC
 */
@Slf4j
public class TestHttp {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new LoggingHandler())
                                    .addLast(new HttpServerCodec())//
                                    .addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                                            //获取请求
                                            log.info(msg.uri());

                                            //返回响应
                                            DefaultFullHttpResponse response =
                                                    new DefaultFullHttpResponse(msg.protocolVersion(), HttpResponseStatus.OK);
                                            byte[] bytes = "<h1>Hello, world!</h1>".getBytes();
                                            response.headers().setInt(CONTENT_LENGTH, bytes.length);
                                            response.content().writeBytes(bytes);
                                            ctx.writeAndFlush(response);
                                        }
                                    })
//                                    .addLast(new ChannelInboundHandlerAdapter(){
//                                        @Override
//                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                            log.info("{}", msg.getClass());
//                                        }
//                                    })
                            ;
                        }
                    })
                    .bind(8080).sync().channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
