package com.inspur.ihealth.codes.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 实现Callable<T> 接口
 * 含返回值且可抛出异常的线程创建启动方式
 */
public class Demo4 implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("正在执行新建线程任务");
        Thread.sleep(2000);
        return "新建线程睡了2s后返回执行结果";
    }


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Demo4 d = new Demo4();
		/*	call()只是线程任务,对线程任务进行封装
			class FutureTask<V> implements RunnableFuture<V>
			interface RunnableFuture<V> extends Runnable, Future<V>
		*/
        FutureTask<String> task = new FutureTask<>(d);
        Thread t = new Thread(task);
        t.start();
        System.out.println("提前完成任务...");
        //获取任务执行后返回的结果
        String result = task.get();
        System.out.println("线程执行结果为"+result);
    }
}
