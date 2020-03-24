package com.inspur.ihealth.codes.list;

import org.assertj.core.util.Lists;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ListRandom {

    //参考： https://www.jianshu.com/p/56067e334e0c

    public static void main(String[] args) {
        //赋初始值，方式一：
        List<String> list = Lists.newArrayList();
        list.add("150100");
        list.add("150200");
        list.add("150300");
        list.add("150400");
        list.add("150500");
        list.add("150600");
        list.add("150700");
        list.add("150800");
        list.add("150900");
        list.add("152200");
        list.add("152500");
        list.add("152900");
        //System.out.println(solution1(list));
        //System.out.println(solution2(list));
        //System.out.println(solution3(list));
        //System.out.println(solution4(list));

        new Thread() {
            public void run() {
                System.out.println(this.getName() + "： " + solution4(list));
            }
        }.start();

        new Thread() {
            public void run() {
                System.out.println(this.getName() + "： " + solution4(list));
            }
        }.start();

        new Thread() {
            public void run() {
                System.out.println(this.getName() + "： " + solution4(list));
            }
        }.start();

        new Thread() {
            public void run() {
                System.out.println(this.getName() + "： " + solution4(list));
            }
        }.start();

        new Thread() {
            public void run() {
                System.out.println(this.getName() + "： " + solution4(list));
            }
        }.start();

        new Thread() {
            public void run() {
                System.out.println(this.getName() + "： " + solution4(list));
            }
        }.start();


        //赋初始值，方式二：
/*        List<String> l = Arrays.asList("7","8","9","0");
        //调用Arrays.asList()生产的List的add、remove方法时报异常，
        // 这是由Arrays.asList() 返回的是Arrays的内部类ArrayList， 而不是java.util.ArrayList
        List list = new ArrayList(l);
        //shffle 取值也可
        Collections.shuffle(list);
        list.remove("0");
        Random random = new Random();
        int n = random.nextInt(list.size());
        System.out.println(list.get(0));*/
    }


    public static String solution1(List<String> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public static String solution2(List<String> list) {
        Collections.shuffle(list);
        return list.get(0);
    }

    public static String solution3(List<String> list) {
        Double d = Math.random();
        return list.get((int) (d * list.size()));
    }

    //当在多线程环境下使用Randome实例的时候，可能导致多个线程获取到相同值的情况，
    // 这时我们可以使用ThreadLocalRandom类
    public static String solution4(List<String> list) {
        int listSize = list.size();
        int randomElementIndex = ThreadLocalRandom.current().nextInt(listSize) % list.size();
        return list.get(randomElementIndex);
    }

}
