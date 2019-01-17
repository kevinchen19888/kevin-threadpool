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
    }

    @Test
    public void test2() {
        ExecutorService threadPool1 = PropsThreadPoolFactory.getThreadPool("threadPool1");
        threadPool1.execute(new ThreadA(atomicInteger.incrementAndGet()));

    }

}

class ThreadA implements Runnable {
    private int a;

    ThreadA(int a) {
        this.a = a;
    }

    public void run() {
        System.out.println("this ThreadA is" + a);
    }
}


