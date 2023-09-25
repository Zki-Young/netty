package cn.itcast.netty.c03_eventloopserver;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/12 20:01
 * @company：CTTIC
 */
@Slf4j
public class TestNettyPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop next = group.next();
        final DefaultPromise<Integer> promise = new DefaultPromise<Integer>(next);
        new Thread(new Runnable(){
            public void run() {
                log.info("promise_kaishi");
                try {
                    int i = 1/0;
                    promise.setSuccess(147);
                } catch (Exception e) {
                    promise.setFailure(e);
                }
            }
        }).start();

        //同步
//        log.info("waiting result");
//        log.info("sync result:{}", promise.get());

        //异步
        promise.addListener(new GenericFutureListener<Future<? super Integer>>() {
            public void operationComplete(Future<? super Integer> future) throws Exception {
                log.info("async result:{}", future.get());
                log.info("async result:{}", promise.get());
            }
        });

    }
}
