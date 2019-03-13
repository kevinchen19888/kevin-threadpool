package test;

import com.PropsThreadPoolFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolTest {

    private AtomicInteger atomicInteger = new AtomicInteger();

    @Test
    public void testPoolRepository() {
        ExecutorService threadPool1 = PropsThreadPoolFactory.getThreadPool("threadPool1");
        ExecutorService threadPool2 = PropsThreadPoolFactory.getThreadPool("threadPool2");
        Assert.assertNotNull(threadPool1);
        Assert.assertNotNull(threadPool2);
        threadPool1.shutdown();
        threadPool2.shutdown();

    }

    @Test
    public void test2() {
        ExecutorService threadPool1 = null;
        try {
            threadPool1 = PropsThreadPoolFactory.getThreadPool("threadPool1");
            threadPool1.execute(new ThreadA(atomicInteger.incrementAndGet()));
            System.out.println("done");
        } finally {
            if (threadPool1 != null) {
                threadPool1.shutdown();
            }
        }

    }

    public boolean isNullStr(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        }
        return false;
    }

}

class ThreadA implements Runnable {
    private int a;

    ThreadA(int a) {
        this.a = a;
    }

    public void run() {
        System.out.println("this ThreadA is: " + a);
    }
}


