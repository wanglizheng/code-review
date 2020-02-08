package com.inspur.ihealth.codes;

public class AssertTest {

    public static void main(String[] args) {

        /**
         * IDEA中默认assert(断言)是关闭，开启方式如下：
         *  就是设置一下jvm的参数，参数是-enableassertions或者-ea（推荐）
         *
         * （1）assert [boolean 表达式]
         *      如果[boolean表达式]为true，则程序继续执行。
         *      如果为false，则程序抛出AssertionError，并终止执行。
         * （2）assert[boolean 表达式 : 错误表达式 （日志）]
         *      如果[boolean表达式]为true，则程序继续执行。
         *      如果为false，则程序抛出java.lang.AssertionError，输出[错误信息]。
         *
         *      断言只是为了用来调试程序，切勿将断言写入业务逻辑中。比如考虑下面这个简单的例子：
         */

        //String s = null;
        String s = "haha";
        assert s != null ? true : false;
        assert false;
        System.out.println("end");

        boolean isSafe = false;
        assert isSafe;
        System.out.println("断言通过!");

    }
}
