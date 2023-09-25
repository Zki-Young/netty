package cn.itcast;

import cn.itcast.message.LoginRequestMessage;
import cn.itcast.protocol.MessageCodec;
import cn.itcast.protocol.MessageCodecSharable;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import org.junit.Test;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/19 09:57
 * @company：CTTIC
 */
public class PotocolTest {
    @Test
    public void testEncodeAndDecode() throws Exception {
        MessageCodecSharable messageCodec = new MessageCodecSharable();
        EmbeddedChannel channel = new EmbeddedChannel(
                //加帧解码器，防止粘包半包问题
                new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
                new LoggingHandler(),
                messageCodec
        );

        //encode
        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123", "张三");
        channel.writeOutbound(message);

        //decode
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, message, buf);

//        buf.writeByte(3);
        //入站
//        channel.writeInbound(buf);

        //模拟半包
        ByteBuf slice01 = buf.slice(0, 100);
        ByteBuf slice02 = buf.slice(100, buf.readableBytes() - 100);

        //引用计数 加1   因为切片1是切出来的被写入之后，引用计数会减少，减少为0，原来的buf和相关切片就都被释放了
        slice01.retain();

        channel.writeInbound(slice01);
        channel.writeInbound(slice02);//如果引用计数没有手动加1，那么到这里切片02将被释放，会报错
    }
}
