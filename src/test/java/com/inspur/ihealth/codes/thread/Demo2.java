package com.inspur.ihealth.codes.thread;

/**
 * 实现Runnale接口
 * 实现Runnable接口，重写run()——实现Runnable接口只是完成了线程任务的编写
 * 若要启动线程，需要new Thread(Runnable target)，再有thread对象调用start()方法启动线程
 */
public class Demo2 implements Runnable {

    @Override
    public void run() {
        System.out.println("implements Runnable is running");
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Demo2());
        Thread thread2 = new Thread(new Demo2());
        thread1.start();
        thread2.start();
    }
}
