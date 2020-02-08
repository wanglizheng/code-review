package com.inspur.ihealth.codes.redislock.sample2;

import javax.annotation.Resource;

public class LockTest {

    @Resource
    private RedisGlobalLock redisGlobalLock;

    String memberId = "001";

    public void lockTest(String memberId) {

        // 1、获取分布式锁防止重复调用 =====================================================
        String key = "redisLock_" + memberId;

        if (redisGlobalLock.lock(key)) {
            try {
                System.out.println("--处理业务---");
            } catch (Exception e) {
                throw e;
            } finally {
                // 4、释放分布式锁 ================================================================
                redisGlobalLock.unlock(key);
            }
        } else {
            // 如果没有获取锁
            //Ensure.that(true).isTrue("17000706");
        }
    }


    /**
     *
     * 所有锁业务必须释放锁，防止死锁
     * 但是以下业务可以不释放锁：
     * 1.定时任务：每日执行一次，或者每个月执行一次，就不需要释放锁，我们要对锁的时间加长。
     * 2.支付场景：接通易宝支付，首先用户要绑定银行卡，但是绑卡过程中，我们这边要调用易宝支付绑卡接口，如果因网络等原因APP对重复点击没有得到控制，那么会调用后台多次接口，那么直接的结果是：后台调用易宝支付第一次是成功，后台第二次是返回系统异常，但是易宝支付平台照样收取费用，是我们平台没有控制好，易宝支付调用绑卡接口两次时间间隔在40秒以上。
     *
     * 原文链接：https://blog.csdn.net/qq_39291929/article/details/79678593
     *
     */

}
