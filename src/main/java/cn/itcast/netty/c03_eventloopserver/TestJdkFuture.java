package cn.itcast.netty.c03_eventloopserver;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/12 18:50
 * @company：CTTIC
 */
@Slf4j
public class TestJdkFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Integer> future = service.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                log.info("开始执行运算");
                Thread.sleep(1000);
                return 147;
            }
        });

        log.info("等待结果中");
        //get()是同步方法
        log.info("结果是{}", future.get());

    }
}
