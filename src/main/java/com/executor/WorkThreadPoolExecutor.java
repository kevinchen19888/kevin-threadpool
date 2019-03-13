package com.executor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * the customized threadPool from ThreadPoolExecutor
 *
 * @author chenjun
 */
public class WorkThreadPoolExecutor extends ThreadPoolExecutor {
    private int totalDoneTasks;
    private long totalTime;
    private final String poolName;
    private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    private final AtomicInteger submittedTasks = new AtomicInteger(0);

    public WorkThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue, String poolName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.poolName = poolName;
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        startTime.set(Long.valueOf(System.currentTimeMillis()));
        submittedTasks.incrementAndGet();
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        long time = System.currentTimeMillis() - startTime.get();
        synchronized (this) {
            totalDoneTasks++;
            totalTime += time;
        }
    }

    public synchronized int getTotalDoneTasks() {
        return totalDoneTasks;
    }

    public synchronized int getQueueSize() {
        return getQueue().size();
    }

    public synchronized double getAverageExecuteTime() {
        return totalDoneTasks == 0 ? 0 : totalTime / (double) totalDoneTasks;
    }

    public String getPoolName() {
        return this.poolName;
    }

    public int getSubmittedTasks(){
        return submittedTasks.get();
    }
}
