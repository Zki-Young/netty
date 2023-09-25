package cn.itcast.netty.echotest;

import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/18 14:35
 * @company：CTTIC
 */
public class EmbededChannelTest {
    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024, 0, 4, 1,4 ),
                new LoggingHandler()
        );

        sentMessage(channel, "hello, world1");
        sentMessage(channel, "hi");
    }

    private static void sentMessage(EmbeddedChannel channel, String str) {
        ByteBuf byteBuf = channel.alloc().buffer();
        byteBuf.writeInt(str.length());
        byteBuf.writeByte(1);
        byteBuf.writeBytes(str.getBytes());
        channel.writeInbound(byteBuf);
    }
}
