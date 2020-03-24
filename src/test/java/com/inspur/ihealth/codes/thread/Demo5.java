package com.inspur.ihealth.codes.thread;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 创建启动线程之Timer定时任务
 */
public class Demo5 {

    public static void main(String[] args) {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            int count = 0;
            @Override
            public void run() {
                //if(count > 5) Thread.interrupted();
                if(count > 5) timer.cancel();  //结束任务
                System.out.println("定时任务延迟0(即立刻执行),每隔1000ms执行一次" + count);
                count++;
            }
        }, 0, 1000);
    }

    //我们发现Timer有不可控的缺点，
    // 当任务未执行完毕或我们每次想执行不同任务时候，实现起来比较麻烦。
    // 这里推荐一个比较优秀的开源作业调度框架“quartz”

}
