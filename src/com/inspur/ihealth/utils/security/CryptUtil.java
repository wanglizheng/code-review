package com.inspur.ihealth.utils.security;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;


public class CryptUtil {
    // 加密
    public static String encrypt(String originData, String secret) {
        try {
            DESedeKeySpec dks = new DESedeKeySpec(secret.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey securekey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, securekey);
            byte[] b = cipher.doFinal(originData.getBytes());
            return new String(Base64.encodeBase64(b), "UTF-8").replaceAll("\r",
                    "").replaceAll("\n", "");
        } catch (Exception e) {
            return "";
        }
    }

    // 解密
    public static String decrypt(String encryptData, String secret) {
        try {
            // --􏳑􏳒 base64,􏰠􏱪􏳘􏲮􏳂􏲀 byte 􏱓􏷛
            byte[] bytesrc = Base64.decodeBase64(encryptData.getBytes());
            // --􏳔􏱡􏳌 key
            DESedeKeySpec dks = new DESedeKeySpec(secret.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey securekey = keyFactory.generateSecret(dks);
            // --Chipher 􏰽􏴛􏳔􏱡
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, securekey);
            byte[] retByte = cipher.doFinal(bytesrc);
            return new String(retByte, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    public static void main(String args[]){

        String key = "0bxu3mqgz88b88bl8vilgjsa4c11t2w6";
        String idCard= "110101198008150075";
        String encryptCode =encrypt(idCard,key);
        System.out.println(encryptCode);
        //System.out.println(decrypt("jXG92bF0zyFbsSczsfQeBpooJ2+jUug5yUrrxzWZv12/OUj4z+cNzfdCVCKGo/81KTmTS8Yi49jY0DJAPi8V+54ZwgwRwYzQeH/ah2Fp0IL19dq4NsbbLGQzPt1Lq2fcTS4M8xRZXjH61erNs5BES1Bj4/4T/UOg1UzoQ1pbmCvLsTD+lIDL4k1cMu9rRXcsnLMt/5cBZruQLl5M9vggETCn0mkz1FB20BxwBBGId8IfAZALKenp7jhQKB2oyeQeln6XdIVm/F168qo/RboMQdjhM9EbUHu1fMwxakp52BRRTvOcAR9nzuBvHya+eWB+Fn+XHIFhEwCrKQx2uuYMQfm8qgGJiMd3AD+5WAVMKT2cmn7pDjZnlrpPX97yiZ6/jmdCHXWu9kDOCIzUpjUNNItDItsTC5Bu9PZgINrt2FNlySo6gSJjSi8emG3i8qS+EwbL/+0Vyc048nE2TrHovKKkuTbORGfdkzaaWW9pGKeP8WJIdC1NOoA51rL+PIw6",key));
    }



}

