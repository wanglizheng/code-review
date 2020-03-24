package com.inspur.ihealth.codes.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池的实现(java.util.concurrent.Executor接口)
 * 降低了创建线程和销毁线程时间开销和资源浪费
 * 具体代码实现
 */
public class Demo6  {

    /**
     * 运行完毕，但程序并未停止，原因是线程池并未销毁，若想销毁调用threadPool.shutdown();    注意需要把我上面的
     * Executor threadPool = Executors.newFixedThreadPool(10);              改为  
     * ExecutorService threadPool = Executors.newFixedThreadPool(10);     否则无shutdown()方法
     *
     * 若创建的是CachedThreadPool则不需要指定线程数量，线程数量多少取决于线程任务，不够用则创建线程，够用则回收。
     */
    public static void main(String[] args) {
        //创建带有5个线程的线程池
        //返回的实际上是ExecutorService,而ExecutorService是Executor的子接口
        //Executor threadPool = Executors.newFixedThreadPool(5);
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for(int i = 0 ;i < 10 ; i++) {
            threadPool.execute(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread().getName()+" is running");
                }
            });
        }
        threadPool.shutdown();

    }
}
