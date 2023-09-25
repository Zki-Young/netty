package cn.itcast.netty.c04_bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/13 16:28
 * @company：CTTIC
 */
@Slf4j
public class TestByteBuf {
    public static void main(String[] args) {

        System.setProperty("io.netty.allocator.type", "unpooled");
        ByteBuf byteBuf;
        byteBuf = ByteBufAllocator.DEFAULT.buffer();
        log.info(byteBuf.getClass().toString());//class io.netty.buffer.PooledUnsafeDirectByteBuf
        byteBuf = ByteBufAllocator.DEFAULT.heapBuffer();
        log.info(byteBuf.getClass().toString());//class io.netty.buffer.PooledUnsafeHeapByteBuf

//        System.out.println(byteBuf);
//        log.info(byteBuf.toString());
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < 300; i++) {
//            sb.append("a");
//        }
//        byteBuf.writeBytes(sb.toString().getBytes());
//        log.info(byteBuf.toString());
    }
}
