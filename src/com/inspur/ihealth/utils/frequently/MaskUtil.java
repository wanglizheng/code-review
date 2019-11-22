package com.inspur.ihealth.utils.frequently;

import java.util.Objects;

/**
 * 脱敏工具
 */
public class MaskUtil {

    private static final String MASK_CODE="*";

    /**
     * 姓名脱敏
     * @param name 姓名
     * @param maskCode 脱敏符号
     * @return 脱敏后数据
     */
    public static String maskName(String name,String maskCode){
        String ret = null;
        StringBuilder sb = new StringBuilder();
        if(Objects.nonNull(name)){

            int length = name.length();

            if(length > 1){
                sb.append(name.substring(0,1));
                for(int i=0;i<length-1;i++){
                    sb.append("*");
                }
                ret = sb.toString();
            }
        }
        return ret;
    }

    /**
     * 用户名脱敏
     * @param name 用户名
     * @return 返回脱敏用户名称
     */
    public static String maskName(String name){
        return maskName(name,MASK_CODE);
    }

    /**
     * 身份证号虚化
     * @param personCode 身份证号
     * @return 返回脱敏后身份证
     */
    public static String maskPersonCode(String personCode){
        StringBuilder builder = new StringBuilder(personCode);
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<12;i++){
            sb.append(MASK_CODE);
        }
        builder.replace(2,13,sb.toString());
        return builder.toString();
    }

    /**
     * 电话号码脱敏
     * @param tel 电话号码
     * @return 脱敏后的电话号码
     */
    public static String maskTel(String tel){
        StringBuilder builder = new StringBuilder(tel);
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<4;i++){
            sb.append(MASK_CODE);
        }
        builder.replace(3,6,sb.toString());
        return builder.toString();
    }
}
