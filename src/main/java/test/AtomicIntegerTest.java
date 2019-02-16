package test;

import java.time.LocalDateTime;

public class AtomicIntegerTest {
    private static final int THREADS_CONUT = 20;
    //    public static AtomicInteger count = new AtomicInteger(0);
    // 使用此类型导致非预期错误结果
    public static volatile int count;
    public static LocalDateTime time = LocalDateTime.now();


    public static void increase() {
//        count.incrementAndGet();
        count++;
        time = time.plusSeconds(1);
    }

    public static void main(String[] args) {
        System.out.println(time.getSecond());
        Thread[] threads = new Thread[THREADS_CONUT];
        for (int i = 0; i < threads.length; i++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        increase();
                    }
                }
            });
            thread.start();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(time.getSecond());
//        System.out.println(count);
    }

}
