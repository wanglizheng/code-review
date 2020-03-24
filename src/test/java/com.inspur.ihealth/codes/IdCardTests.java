package com.inspur.ihealth.codes;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IdCardTests {

    public static void main(String[] args) {
        System.out.println(generateBirthdayStr("37232119790413111X"));
    }

    public static String generateBirthdayStr(String code){
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMdd");

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        return formater.format(DateTime.parse(code.substring(6,14),format).toDate());
    }

    public static Date generateBirthdayDate(String code){
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
        return DateTime.parse(code.substring(6,14),format).toDate();
    }
}
