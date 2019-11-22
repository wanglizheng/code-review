package com.inspur.ihealth.utils.security;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数工具类
 *
 * @author 孙承旭
 * @since 2019-05-01
 */
public class RandomUtil {
    /**
     * 用于随机选的数字
     */
    private static final String BASE_NUMBER = "0123456789";
    /**
     * 用于随机选的字符
     */
    private static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";
    /**
     * 用于随机选的字符和数字
     */
    private static final String BASE_CHAR_NUMBER = BASE_CHAR + BASE_NUMBER;

    /**
     * 获取随机数生成器对象<br>
     * ThreadLocalRandom是JDK 7之后提供并发产生随机数，能够解决多个线程发生的竞争争夺。
     *
     * @return {@link ThreadLocalRandom}
     * @since 3.1.2
     */
    private static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }

    /**
     * 获得一个随机的字符串（只包含数字和字符）
     *
     * @param length 字符串的长度
     * @return 随机字符串
     */
    public static String randomString(int length) {
        return randomString(BASE_CHAR_NUMBER, length);
    }

    /**
     * 获得一个随机的字符串
     *
     * @param baseString 随机字符选取的样本
     * @param length     字符串的长度
     * @return 随机字符串
     */
    private static String randomString(String baseString, int length) {
        final StringBuilder sb = new StringBuilder();

        if (length < 1) {
            length = 1;
        }
        int baseLength = baseString.length();
        for (int i = 0; i < length; i++) {
            int number = getRandom().nextInt(baseLength);
            sb.append(baseString.charAt(number));
        }
        return sb.toString();
    }


}
