package com.inspur.ihealth.codes.thread;

import java.util.Arrays;
import java.util.List;

/**
 * Lambda表达式的实现(parallelStream)
 */
public class Demo7 {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6);
        Demo7 demo = new Demo7();
        int result = demo.add(list);
        System.out.println("计算后的结果为"+result);
    }

    public int add(List<Integer> list) {
        //若Lambda是串行执行,则应顺序打印
        list.stream().forEach(System.out :: println);   //串行
        list.parallelStream().forEach(System.out :: println);  //并行
        //Lambda有stream和parallelSteam(并行)
        return list.parallelStream().mapToInt(i -> i).sum();
    }

}
