package com.inspur.ihealth.utils.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularTest {

    public static void main(String[] args) {

        String check = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{6,20}$";
        Pattern p = Pattern.compile(check);
        Matcher m = p.matcher("A11112");
        System.out.println(m.find());
/*        Pattern p = Pattern.compile("\\d{6}");
        Matcher m = p.matcher("111116");
        System.out.println(m.find());*/
    }
}
