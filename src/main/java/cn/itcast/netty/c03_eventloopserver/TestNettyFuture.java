package cn.itcast.netty.c03_eventloopserver;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/12 19:48
 * @company：CTTIC
 */
@Slf4j
public class TestNettyFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop next = group.next();
        Future<Integer> future = next.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                log.info("开始计算");
                Thread.sleep(1000);
                return 147;
            }
        });

        //同步方法
//        log.info("等待结果");
//        log.info("结果为：{}", future.get());

        //异步方法
        future.addListener(new GenericFutureListener<Future<? super Integer>>() {
            public void operationComplete(Future<? super Integer> future) throws Exception {
                //getNow立刻获取结果，不阻塞。
                log.info("异步获取结果：{}", future.getNow());
            }
        });
    }
}
