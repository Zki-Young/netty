package cn.itcast.netty.c01_helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/12 07:45
 * @company：CTTIC
 */
public class HelloServer {
    public static void main(String[] args) {
        //服务器端启动器，组装netty组件
        new ServerBootstrap()
                //group包含线程和选择器
                .group(new NioEventLoopGroup())
                //选择服务器以哪种方式实现：当前是Nio方式
                .channel(NioServerSocketChannel.class)//接收accept连接时间 和 read读事件
                //boss 负责处理连接worker（child）负责处理读写，决定了worker能执行哪些操作
                .childHandler(
                        //负责添加别的handler；channel代表和客户端进行数据读写的通道 initializer初始化
                        new ChannelInitializer<NioSocketChannel>() {
                            //添加具体的handler 连接建立后才会调用初始化方法
                            protected void initChannel(NioSocketChannel ch) throws Exception {//pipeline：工作流水线
                                //将 ByteBuf 转换为字符串
                                ch.pipeline().addLast(new StringDecoder());
                                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {//自定义处理器
                                    //读事件
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) {
                                        System.out.println(msg);
                                    }
                                });
                            }
                            //绑定监听端口
                        }).bind(8080);
    }
}
