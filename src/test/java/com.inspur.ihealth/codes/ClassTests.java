package com.inspur.ihealth.codes;

public class ClassTests {

        class BBB {
        }

        public static void main(String[] args) {
            // 数组
            System.out.println(String[].class.getName()); // [Ljava.lang.String;
            System.out.println(String[].class.getCanonicalName()); // java.lang.String[]
            System.out.println(String[].class.getSimpleName()); // String[]
            System.out.println(String[].class.getTypeName()); // java.lang.String[]

            // 成员内部类
            System.out.println(BBB.class.getName()); // lang.reflect.AAA$BBB
            System.out.println(BBB.class.getCanonicalName()); // lang.reflect.AAA.BBB
            System.out.println(BBB.class.getSimpleName()); // BBB
            System.out.println(BBB.class.getTypeName()); // lang.reflect.AAA$BBB

            // 匿名内部类
            System.out.println(new Object(){}.getClass().getName()); // lang.reflect.AAA$1
            System.out.println(new Object(){}.getClass().getCanonicalName()); // null
            System.out.println(new Object(){}.getClass().getSimpleName()); // ""
            System.out.println(new Object(){}.getClass().getTypeName()); // lang.reflect.AAA$4

            // 普通类
            System.out.println(ClassTests.class.getName()); // lang.reflect.AAA
            System.out.println(ClassTests.class.getCanonicalName()); // lang.reflect.AAA
            System.out.println(ClassTests.class.getSimpleName()); // AAA
            System.out.println(ClassTests.class.getTypeName()); // lang.reflect.AAA

            // 基本数据类型
            System.out.println(int.class.getName()); // int
            System.out.println(int.class.getCanonicalName()); // int
            System.out.println(int.class.getSimpleName()); // int
            System.out.println(int.class.getTypeName()); // int
        }

}
