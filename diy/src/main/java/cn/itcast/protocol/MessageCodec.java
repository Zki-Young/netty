package cn.itcast.protocol;

import cn.itcast.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @Description: 自定义编解码器 入站解码，出站编码
 * @Auther: Zki Young
 * @Date: 2023/9/19 09:22
 * @company：CTTIC
 */
@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {
    @Override
    public void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        //字节的魔术
        out.writeBytes(new byte[]{1, 2, 3, 4});
        //字节的版本
        out.writeByte(1);
        //字节的序列化方式 0：jdk 1：json
        out.writeByte(0);
        //字节的指令类型
        out.writeByte(msg.getMessageType());
        //请求序号
        out.writeInt(msg.getSequenceId());
        //无意义字节，填充对齐至16字节
        out.writeByte(0xff);

        //获取内容的字节数组 将msg序列化为byte数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        byte[] bytes = bos.toByteArray();

        //写入长度
        out.writeInt(bytes.length);
        //写入内容
        out.writeBytes(bytes);

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int magicNum = in.readInt();
        byte version = in.readByte();
        byte serializerType = in.readByte();
        byte messageType = in.readByte();
        int sequenceId = in.readInt();
        in.readByte();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Message message = (Message) ois.readObject();
        log.debug("{}, {}, {}, {}, {}, {}", magicNum, version, serializerType, messageType, sequenceId, length);
        log.debug("{}", message);
        out.add(message);
    }
}
