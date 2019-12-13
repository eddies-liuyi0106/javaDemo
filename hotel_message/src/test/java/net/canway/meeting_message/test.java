package net.canway.meeting_message;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class test {
    public static String timer() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(100);
        long initialDelay = 100;
        final int[] i = {0};
        service.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("开始");
            }
        }, initialDelay, TimeUnit.MILLISECONDS);
        service.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("结束");
            }
        }, 3000, TimeUnit.MILLISECONDS);
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("循环方法");
            }
        }, initialDelay, 1000, TimeUnit.MILLISECONDS);
        System.out.println("1111111");
        service.shutdown();
        return "成功";
    }

    public static void main(String[] args) {
        String time = timer();
        System.out.println(time);
    }

}
