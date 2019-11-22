package com.inspur.ihealth.utils.security;

public class SignUtils {

    /**
     * 生成签名
     * @param appid  appid
     * @param appsecret appsecret
     * @param time time
     * @param nonceStr nonceStr
     * @return 生成签名值
     */
    public static String makeSign(String appid, String appsecret, long time, String nonceStr) {
        String str = appid + "&" + time + "&" + nonceStr;
        return HmacSHA1Util.genHMAC(str, appsecret);
    }

}
