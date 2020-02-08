package com.inspur.ihealth.codes.date;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class DateTests {


    public static void main(String[] args) {


        //获取昨日正确姿势如下：
        //注意不要用Calendar.HOUR，这是12小时制，Calendar.HOUR_OF_DAY是24小时制

        Date date = new Date();


        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,-24);
        String yesterdayDate=dateFormat.format(calendar.getTime());

        log.info(yesterdayDate);
        log.info(String.valueOf(date.after(calendar.getTime()))); //今天，在昨天之后。


        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:S");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String time0 = format0.format(System.currentTimeMillis());
        String time1 = format1.format(System.currentTimeMillis());
        log.info("发送时间:  " + time0);
        log.info("发送时间:  " + time1);

    }
}
