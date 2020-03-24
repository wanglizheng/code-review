package com.inspur.ihealth.codes.thread;

/**
 * 继承Thread类，重写run()方法
 */
public class Demo1 extends Thread {

    //重写
    public void run() {
        System.out.println(this.getName() + "is running...");
    }


    public static void main(String[] args) {
        Demo1 demo1 = new Demo1();
        Demo1 demo2 = new Demo1();
        demo1.start();
        demo2.start();
    }
}
