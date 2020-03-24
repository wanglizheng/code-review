package com.inspur.ihealth.codes;

import org.apache.commons.lang3.RandomUtils;

public class RadomTests {

    public static void main(String[] args) {
        System.out.println(String.valueOf(Math.random()));
        System.out.println(String.valueOf(Math.random() * 100000000 +1L));
        System.out.println(RandomUtils.nextLong(0,4294967295L));
        System.out.println(Integer.MAX_VALUE);
    }
}
