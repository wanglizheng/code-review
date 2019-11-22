package com.inspur.ihealth.utils.security;

/* 
字符串 DESede(3DES) 加密 
*/  

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class D3DESUtil {  
 
   private static final String Algorithm = "DESede"; // 定义 加密算法,可用  
                                                       // DES,DESede,Blowfish
   // 24字节的密钥  

   // keybyte为加密密钥，长度为24字节  
   // src为被加密的数据缓冲区（源）  
   public static byte[] encryptMode(byte[] src, byte[] key) {  
       try {  
           // 生成密钥  
           SecretKey deskey = new SecretKeySpec(key, Algorithm);  
 
           // 加密  
           Cipher c1 = Cipher.getInstance("DESede/ECB/Pkcs5Padding");
           c1.init(Cipher.ENCRYPT_MODE, deskey);  
           return c1.doFinal(src);  
       } catch (java.security.NoSuchAlgorithmException e1) {  
           e1.printStackTrace();  
       } catch (javax.crypto.NoSuchPaddingException e2) {  
           e2.printStackTrace();  
       } catch (Exception e3) {
           e3.printStackTrace();  
       }  
       return null;  
   }  
 
   // keybyte为加密密钥，长度为24字节  
   // src为加密后的缓冲区  
   public static byte[] decryptMode(byte[] src, byte[] key) {  
       try {  
           // 生成密钥  
           SecretKey deskey = new SecretKeySpec(key, Algorithm);  
 
           // 解密  
           Cipher c1 = Cipher.getInstance("DESede/ECB/Pkcs5Padding");
           c1.init(Cipher.DECRYPT_MODE, deskey);  
           return c1.doFinal(src);  
       } catch (java.security.NoSuchAlgorithmException e1) {  
           e1.printStackTrace();  
       } catch (javax.crypto.NoSuchPaddingException e2) {  
           e2.printStackTrace();  
       } catch (Exception e3) {
           e3.printStackTrace();  
       }  
       return null;  
   }  
 
   // 转换成十六进制字符串  
   public static String byte2hex(byte[] b) {  
       String hs = "";  
       String stmp = "";  
 
       for (int n = 0; n < b.length; n++) {  
           stmp = (Integer.toHexString(b[n] & 0XFF));
           if (stmp.length() == 1)  
               hs = hs + "0" + stmp;  
           else  
               hs = hs + stmp;  
           if (n < b.length - 1)  
               hs = hs + ":";  
       }  
       return hs.toUpperCase();  
   }  
   
   
   public static String byte2hexString(byte[] b) {  
       String hs = "";  
       String stmp = "";  
 
       for (int n = 0; n < b.length; n++) {  
           stmp = (Integer.toHexString(b[n] & 0XFF));
           if (stmp.length() == 1)  
               hs = hs + "0" + stmp;  
           else  
               hs = hs + stmp;  
       }  
       return hs.toUpperCase();  
   }  
 
   /** 
    * 字符串3DES加密 
    *
    * @return 加密的字符串 
    */  
   public static String  encrypt(String preStr,String keyStr) {  
	   byte[] key  = createKey(keyStr);
	   
	   String str = format(stringToHex(preStr));
       // 获取字符串  
       List<String> strs = new ArrayList<String>();  
       for (int i = 1; i <= str.length() / 2; i++) {  
           strs.add(str.substring((i - 1) * 2, i * 2));  
       }  
       byte[] l = new byte[str.length() / 2];  
       for (int i = 0; i < strs.size(); i++) {  
           int aaa = Integer.parseInt(strs.get(i).toUpperCase(),16);  
           l[i] = (byte) aaa;  
       }  
       byte[] encodedl = encryptMode(l,key);  
       String encodedS = byte2hex(encodedl);  
       encodedS = StringUtils.replace(encodedS, ":", "");
       return encodedS;  
   }  
 
   /** 
    * 字符串3DES解密 
    *  
    * @param str 
    * @return 解密字符串 
    */  
   
   public static String deciphering(String str,String keyStr) {  
	   byte[] key = createKey(keyStr);
	   // 获取字符串  
       List<String> strs = new ArrayList<String>();  
       for (int i = 1; i <= str.length() / 2; i++) {  
           strs.add(str.substring((i - 1) * 2, i * 2));  
       }  
       byte[] b1 = new byte[str.length() / 2];  
       for (int i = 0; i < strs.size(); i++) {  
           int aaa = Integer.parseInt(strs.get(i).toUpperCase(),16);  
           b1[i] = (byte) aaa;  
       }  
       byte[] encodedl = decryptMode(b1,key); 
       if(encodedl == null){
    	  return "";
       }
       String encodedS = byte2hex(encodedl);  
       encodedS = StringUtils.replace(encodedS, ":", ""); 
       String s = hexToString(encodedS);
      return s;  
   }
 
   public static String format(String s ){
	   int c = s.length() ;
	   if(c==32){
		   return s;
	   }
	   if(c>32){
		   return StringUtils.rightPad(s, c+32-(c%32), "0");
	   }
	   if(c<32){
		   return StringUtils.rightPad(s, 32, "0");
	   }
	   return s;
   }

    public static void main(String[] args) {
        String m = encrypt("130502198610220314", "8E627BDA54114732A202CD5DCA693E70");
        System.out.println(m);
        m = deciphering(m, "8E627BDA54114732A202CD5DCA693E70");
        System.out.println(m);
    }

	   

   
   private static byte[] createKey(String str){
	   if(StringUtils.length(str) != 32){
		   return null;
	   }
	    byte[] keys = new byte[24];
		int keyIndex = 0;
		for(int i=0; i<str.length(); i+=2){
			keys[keyIndex] = (byte)Integer.parseInt(StringUtils.substring(str, i,i+2),16);
			keyIndex++;
		}
		for(int j =0;j<8;j++){
			keys[j+16] = keys[j]; 
		}
		return keys;
   }
   
   public static String hexToString(String s){  
	      byte[] baKeyword = new byte[s.length()/2];  
	      for(int i = 0; i < baKeyword.length; i++){  
	       try{  
	        baKeyword[i] = (byte)(0xff & Integer.parseInt(s.substring(i*2, i*2+2),16));  
	       }catch(Exception e){  
	        e.printStackTrace();  
	       }  
	      }  
	      try{  
	       s = new String(baKeyword, "UTF-8");  
	      }catch (Exception e1){  
	       e1.printStackTrace();  
	      }  
	      return StringUtils.trim(s);  
	     }  
	 
	 
    	 public static String stringToHex(String s)
    	 {
    	         String ss = s;
    	         byte[] bt = ss.getBytes(Charset.forName("UTF-8"));
    	         String s1 = "";
    	         for (int i = 0; i < bt.length; i++)
    	         {
    	             String tempStr = Integer.toHexString(bt[i]);
    	             if (tempStr.length() > 2)
    	                 tempStr = tempStr.substring(tempStr.length() - 2);
    	             s1 = s1 + tempStr;
    	         }
    	         return s1.toUpperCase();
    	 }
   
}  
