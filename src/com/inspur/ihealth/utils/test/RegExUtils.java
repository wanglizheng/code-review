package com.inspur.ihealth.utils.test;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 传输List<String> 返回数组
 */
public class RegExUtils {

    public static Set<String> getTeleCode(Set strsSet, String originalStr){

        //查询符合的固定电话
        Pattern p1 = Pattern.compile("(0\\d{2}-\\d{3,8}(-\\d{1,4})?)|(0\\d{3}-\\d{3,8}(-\\d{1,4})?)|(0\\d{9,11})");  // 将给定的正则表达式编译到模式中
        Matcher m1 = p1.matcher(originalStr); // 创建匹配给定输入与此模式的匹配器。
        while(m1.find()){  //查找字符串中是否有符合的子字符串
            strsSet.add(m1.group());
        }

        //查询符合的手机号码
        Pattern p2 = Pattern.compile("((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}");
        Matcher m2 = p2.matcher(originalStr);
        while (m2.find()){
            strsSet.add(m2.group());
        }

        //查询符合的400或者800电话
        Pattern p3 = Pattern.compile("[4|8]00\\d{7}|[4|8]00-\\d{3}-\\d{4}|[4|8]00\\d{1}-\\d{3}-\\d{3}");
        Matcher m3 = p3.matcher(originalStr);
        while (m3.find()){
            strsSet.add(m3.group());
        }

        return strsSet;
    }

}
