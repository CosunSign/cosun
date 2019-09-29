package com.cosun.cosunp.tool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author:homey Wong
 * @date:2019/6/11 0011 上午 9:44
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ThreadUtil {

    /**
     * 创建批量下载线程池
     *
     * @param threadSize 下载线程数
     * @return ExecutorService
     */
    public static ExecutorService buildDownloadBatchThreadPool(int threadSize) {
        int keepAlive = 0;
        String prefix = "download-batch";
        ThreadFactory factory = ThreadUtil.buildThreadFactory(prefix);
        return new ThreadPoolExecutor(threadSize, threadSize, keepAlive, TimeUnit.SECONDS, new ArrayBlockingQueue<>(threadSize), factory);
    }

    /**
     * 创建自定义线程工厂
     *
     * @param prefix 名称前缀
     * @return ThreadFactory
     */
    public static ThreadFactory buildThreadFactory(String prefix) {
        return new CustomThreadFactory(prefix);
    }

    /**
     * 自定义线程工厂
     */
    public static class CustomThreadFactory implements ThreadFactory {
        private String threadNamePrefix;
        private AtomicInteger counter = new AtomicInteger(1);

        /**
         * 自定义线程工厂
         *
         * @param threadNamePrefix 工厂名称前缀
         */
        CustomThreadFactory(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            String threadName = threadNamePrefix + "-t" + counter.getAndIncrement();
            return new Thread(r, threadName);
        }
    }




}
