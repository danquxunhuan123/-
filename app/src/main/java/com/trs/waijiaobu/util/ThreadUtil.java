package com.trs.waijiaobu.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * creator：liufan
 * data: 2019/7/25
 */
public class ThreadUtil {
    private static Executor executor = Executors.newCachedThreadPool(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncExecuter");
        }
    });

    public static void submit(final Runnable runnable) {
        executor.execute(runnable);
    }
}
