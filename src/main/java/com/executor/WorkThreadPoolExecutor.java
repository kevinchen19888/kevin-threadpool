package com.executor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * the customized threadPool from ThreadPoolExecutor
 *
 * @author chenjun
 */
public class WorkThreadPoolExecutor extends ThreadPoolExecutor {
    private int totalTask;
    private long totalTime;
    private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    public WorkThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        startTime.set(Long.valueOf(System.currentTimeMillis()));
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        long time = System.currentTimeMillis() - startTime.get();
        synchronized (this) {
            totalTask++;
            totalTime += time;
        }
    }

    public synchronized int getTotalTask() {
        return totalTask;
    }

    public synchronized int getQueueSize() {
        return getQueue().size();
    }

    public synchronized double getAverageExecuteTime() {
        return totalTask == 0 ? 0 : totalTime / (double) totalTask;
    }

}
