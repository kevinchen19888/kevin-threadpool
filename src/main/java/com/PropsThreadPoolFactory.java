package com;

import com.executor.WorkThreadPoolExecutor;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * create all threadPool instances through properties file
 * and you can use all these threadPools
 * @author chenjun
 */
public class PropsThreadPoolFactory {

    private static final ThreadPoolRepository poolRepository = ThreadPoolRepository.getInstance();

    static {
        init();
    }

    public static ExecutorService getThreadPool(String poolname) {
        return poolRepository.getThreadPool(poolname);
    }

    /**
     * create all threadPolls through the configure of the threadPool.properties
     * and put them into ThreadPoolRepository
     */
    private static void init() {
        String path = PropsThreadPoolFactory.class.getClassLoader().getResource("threadPool.properties").getPath();
        try {
            Properties file = new Properties();
            file.load(new FileInputStream(new File(path)));
            Enumeration<?> names = file.propertyNames();
            if (names != null) {
                while (names.hasMoreElements()) {
                    String poolName = (String) names.nextElement();
                    if (poolName.startsWith("threadPool")) {
                        String poolVal = (String) file.get(poolName);
                        String[] vals = poolVal.split(",");
                        Map<String, Object> map = new HashMap<String,Object>();
                        for (String val : vals) {
                            String[] entry = val.trim().split("=");
                            // 取出值
                            map.put(entry[0].trim(), entry[1].trim());
                        }
                        // init all threadpool
                        createThreadPool(poolName, map);
                        System.out.println("create success");// TODO you can use log4j to improve it
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();// TODO
        }
    }

    private static void createThreadPool(String poolName, Map<String, Object> poolValueMap) {

        int maxPoolSize = Integer.valueOf(String.valueOf(poolValueMap.get("maximumPoolSize")));
        int corePoolSize = Integer.valueOf(String.valueOf(poolValueMap.get("corePoolSize")));
        int queueSize = Integer.valueOf(String.valueOf(poolValueMap.get("queueSize")));
        long keepAliveTime = Long.valueOf(String.valueOf(poolValueMap.get("keepAliveTime")));
        ArrayBlockingQueue queue;
        if (maxPoolSize > 0 && queueSize > 0) {
            queue = new ArrayBlockingQueue(queueSize);
        } else {
            throw new IllegalArgumentException("maxPoolSize or queueSize is illegal");
        }
        WorkThreadPoolExecutor executor = new WorkThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime,
                TimeUnit.SECONDS, queue);
        poolRepository.addThreadPool(poolName, executor);
    }

}
