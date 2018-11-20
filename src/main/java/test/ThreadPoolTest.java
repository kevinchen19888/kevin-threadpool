package test;

import com.PropsThreadPoolFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;

public class ThreadPoolTest {

    @Test
    public void testPoolRepository() {
        ExecutorService threadPool1 = PropsThreadPoolFactory.getThreadPool("threadPool1");
        ExecutorService threadPool2 = PropsThreadPoolFactory.getThreadPool("threadPool2");
        Assert.assertNotNull(threadPool1);
        Assert.assertNotNull(threadPool2);
    }

}
