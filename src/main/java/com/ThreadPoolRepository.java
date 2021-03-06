package com;

import org.apache.commons.collections.CollectionUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * the repository maintains all threadPools,you can use different ways
 * to init threadPool besides properties file
 * @author chenjun
 */
public class ThreadPoolRepository {
    /**
     * contain all threadPools
     */
    private final ConcurrentHashMap<String, ExecutorService> poolRepository;

    /**
     * get a ThreadPoolRepository instance and then
     * you can use the threadPools
     * @return a singleton ThreadPoolRepository
     */
    public static ThreadPoolRepository getInstance() {
        return SingleHandle.INSTANCE;
    }

    /**
     * delay initial single model
     */
    private static class SingleHandle {
        final static ThreadPoolRepository INSTANCE = new ThreadPoolRepository();
    }
    public ExecutorService getThreadPool(String poolname) {
        return poolRepository.get(poolname);
    }

    public ExecutorService addThreadPool(String poolName, ThreadPoolExecutor pool) {
        return poolRepository.putIfAbsent(poolName, pool);
    }

    public void removeThreadPool(String poolName) {
        ExecutorService removedPool = poolRepository.remove(poolName);
        removedPool.shutdown();
    }

    public void shutDownAllThreadPool() {
        Set<Map.Entry<String, ExecutorService>> entries = poolRepository.entrySet();
        if (CollectionUtils.isEmpty(entries))
            throw new NullPointerException("the poolRepository is null");

        for (Map.Entry<String, ExecutorService> entry : entries) {
            synchronized (entry) {
                entry.getValue().shutdown();
            }
        }
    }

    public boolean isShutDownThreadPool(String poolName) {
        Set<Map.Entry<String, ExecutorService>> entries = poolRepository.entrySet();
        if (CollectionUtils.isEmpty(entries))
            throw new NullPointerException("the poolRepository is null");

        for (Map.Entry<String, ExecutorService> entry : entries) {
            if (entry.getKey().equals(poolName)) {
                return entry.getValue().isShutdown();
            }
        }
        return false;
    }

    private ThreadPoolRepository() {
        poolRepository = new ConcurrentHashMap<String, ExecutorService>();
    }

}
