package com;

import com.exception.WorkThredPoolException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * relevant methods to use threadPool for executing the task/tasks
 * @author chenjun
 */
public final class ThreadPoolUtil {

    public static void submitTask(String poolName, Runnable task) throws WorkThredPoolException {
        ExecutorService pool = getThreadPool(poolName);
        pool.execute(task);
    }

    public static void submitTasks(String poolName, List<Runnable> tasks) throws WorkThredPoolException {
        ExecutorService pool = getThreadPool(poolName);
        for (Runnable task : tasks) {
            pool.submit(task);
        }
    }

    public static Future submitFutureTask(String poolName, Callable futureTask) throws WorkThredPoolException {
        ExecutorService pool = getThreadPool(poolName);
        return pool.submit(futureTask);
    }

    public static List<Future> submitFutureTasks(String poolName, List<Callable> futureTasks) throws WorkThredPoolException {
        ExecutorService pool = getThreadPool(poolName);
        // use CompletionService to improve the efficiency of acquiring results
        CompletionService service = new ExecutorCompletionService(pool);
        List<Future> results = new ArrayList<Future>();
        for (Callable task : futureTasks) {
            Future result = service.submit(task);
            results.add(result);
        }
        return results;
    }

    private ThreadPoolUtil() {
    }

    private static ExecutorService getThreadPool(String poolName) throws WorkThredPoolException {
        ExecutorService pool = PropsThreadPoolFactory.getThreadPool(poolName);
        if (pool == null) {
            throw new WorkThredPoolException("the specified pool is not exist");
        }
        return pool;
    }
}
