package com.inspur.ihealth.codes.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *  Spring实现多线程
 * 新建一个java配置类(注意需要开启@EnableAsync注解——支持异步任务)
 */
@Configuration
@ComponentScan("com.inspur.ihealth.codes.thread")
@EnableAsync
public class HhreadConfig {
}
