package com.inspur.ihealth.codes.thread;

/**
 *  创建启动线程的第三种方式————匿名内部类
 */
public class Demo3 {

    public static void main(String[] args) {

        //方式1： new Thread（）{}.start
        // 相当于继承了Thread类，作为子类重写run()实现

        new Thread () {
            public void run() {
                System.out.println("匿名内部类创建线程方式1...");
            }
        }.start();

        //方式 2： new Thread( runable ).start();
        //实现Runnable,Runnable作为匿名内部类
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("匿名内部类创建线程方式2...");
                    }
                }
        ).start();

    }
}
