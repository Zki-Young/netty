package cn.itcast.netty.util;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.AsciiHeadersEncoder;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/14 10:23
 * @company：CTTIC
 */
public class ByteBufDebug {
//    public static void log(ByteBuf byteBuf) {
//        int length = byteBuf.readableBytes();
//        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
//        StringBuilder stringBuilder = new StringBuilder(rows * 80 * 2)
//                .append("read index:").append(byteBuf.readerIndex())
//                .append(" write index:").append(byteBuf.writerIndex())
//                .append(" capacity:").append(byteBuf.capacity())
//                .append();
//        appendPrettyHexDump();
//    }
}
