package cn.itcast.netty.c02_eventloop;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/12 10:43
 * @company：CTTIC
 */
public class TextEventLoop {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup(2);

        //执行普通任务
        group.next().submit(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ok");
            }
        });
        System.out.println("main");

        //定时任务
        group.next().scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println("ok");
            }
        }, 0, 1, TimeUnit.SECONDS);//从0秒开始，1秒执行一次run方法中的内容

    }


}
