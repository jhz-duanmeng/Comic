package com.meng.wether.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 *  线程工具类
 * @author Dmeng
 */

public class ThreadUtils {
    /**
     * 创建可以生成线程名的线程工厂
     *
     * @param name
     * @return
     */
    private static ThreadFactory getThreadFactory(String name) {
        return new NameThreadFactory(name);
    }

    public static ExecutorService getSimpleExecutor(String threadName) {
        return new ThreadPoolExecutor(5, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(24), getThreadFactory(threadName));
    }

    private static class NameThreadFactory implements ThreadFactory {
        private String mThreadName;

        private NameThreadFactory(String name) {
            this.mThreadName = name;
        }

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, mThreadName + "-Thread");
        }
    }
}
