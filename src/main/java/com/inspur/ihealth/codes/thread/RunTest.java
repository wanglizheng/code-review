package com.inspur.ihealth.codes.thread;

import com.inspur.ihealth.codes.config.HhreadConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RunTest {

    public static void main(String[] args) {
        //构造方法传递Java配置类Config.class
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(HhreadConfig.class);
        AsyncService bean = ac.getBean(AsyncService.class);
        bean.Async_A();
        bean.Async_B();
    }


}
