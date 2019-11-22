package com.inspur.ihealth.utils.security;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class AESUtils {
	
	private static String ENCODING = "UTF-8";
	public static final String KEY_ALGORITHM = "AES";
	public static final String SIGN_ALGORITHMS = "SHA1PRNG";
	public static void main(String[] args) throws UnsupportedEncodingException {
	}
	
	/**
	 * 大小128bit，16字节
	 * @param str
	 * @return 密钥对应的字节数组
	 */
	 private static byte[] createKey(String str){
		   if(StringUtils.length(str) != 32){
			   return null;
		   }
		   byte[] keys = new byte[16];
		   int keyIndex = 0;
			for(int i=0; i<str.length(); i+=2){
				keys[keyIndex] = (byte)Integer.parseInt(StringUtils.substring(str, i,i+2),16);
				keyIndex++;
			}
		   return keys;
	 }
	 
	 public static String encrypt(String data,String key) throws UnsupportedEncodingException{
		 byte[] keyBytes = createKey(key);
		 try {
			 //KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
			 //SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
			// random.setSeed(keyBytes);
			// kgen.init(128, random);
			 //SecretKey secretKey = kgen.generateKey();
			 //byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, KEY_ALGORITHM);        
			 Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");	    
			 cipher.init(Cipher.ENCRYPT_MODE, skeySpec);       
			 byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
			 return binary(encrypted, 16);
			// return byteArrayToHexString(encrypted);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return "";
	 }
	 
	 /**
	  * AES 128bit 解密[ECB/PKCS5Padding(PKCS5Padding)] 分组块大小128bit，16byte
	  * @param data
	  * @param key
	  * @return
	  */
	 public static String decrypt(String data,String key){
		 try {
			byte[] keyBytes = createKey(key);
			byte[] dataBytes = hexStringToByteArray(data);
			// KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
			 //SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
			//random.setSeed(keyBytes);
			//kgen.init(128,random);
			// SecretKey secretKey = kgen.generateKey();
			// byte[] enCodeFormat = secretKey.getEncoded();
			 //Security.addProvider(new BouncyCastleProvider());
			 SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, KEY_ALGORITHM);        
			 Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			 cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			 byte[] original =cipher.doFinal(dataBytes);
			 return new String(original, "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}     
	 }
	 public static String binary(byte[] bytes,int radix){
		 return new BigInteger(1,bytes).toString(radix);
	 }
	 
	public static String byteArrayToHexString(byte[] data) {
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			String str1 = Integer.toHexString(data[i] & 0xFF);
			sBuilder.append(str1);
		}
		return sBuilder.toString();
	}
	
	/**
	 * * 16进制的字符串表示转成字节数组 * * 
	 **/
	public static byte[] toByteArray(String hexString) {
		if (hexString.isEmpty())
			throw new IllegalArgumentException("this hexString must not be empty");
		hexString = hexString.toLowerCase();
		final byte[] byteArray = new byte[hexString.length() / 2];
		int k = 0;
		for (int i = 0; i < byteArray.length; i++) {	//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
			byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
			byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
			byteArray[i] = (byte) (high << 4 | low);
			k += 2;
		}
		return byteArray;
	}
	
	/**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

	private static byte charToByte(char c) {
		return (byte) (Character.digit(c, 16) & 0xff);
	}
	
	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] bs = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			bs[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return bs;
	}
    
    //charToByte(hexChars[pos])
    //(byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
		
}
