package com.inspur.ihealth.utils.security;

import java.security.MessageDigest;

public class Md5Utils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String content = "eyJ1aWQiOiJDU0xZUyIsInVuYW1lIjoiQ1NMWVMiLCJvcmdpZCI6IjE1MDEwME1CMTUwMjEyNSIsIm9yZ25hbWUiOiLlhoXokpnlj6Toh6rmsrvljLrljavnlJ%2flgaXlurflp5TlkZjkvJoiLCJmcm9tc3lzdGVtIjoiaGlzMDEiLCJwbmFtZSI6IumZiOS4reWSjCIsInVoa2V5Ijoi5bGF5rCR6Lqr5Lu96K%2bB5Y%2b356CBIiwidWh2YWwiOiIxMjAxMDYxOTUwMDUxMzUwMTMiLCJtaG5hbWUiOiLlsYXmsJHouqvku73or4Hku7YiLCJ0aW1lc3RhbXAiOiIyMDE5MDgxMzIzNTk1OSIsImFwcG9pbnRrZXkiOiJtYW5kYWxhYSJ9ICAgIAo%3d";
		System.out.println(Md5Utils.md5(content));
	}
	
	
	public static String md5(String content) {
	    if(content == null || "".equals(content)){
	    	return null;
	    }
	    try {
	        byte[] b = content.getBytes("UTF-8");
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.reset();
	        md.update(b);
	        byte[] hash = md.digest();
	        StringBuffer outStrBuf = new StringBuffer(32);
	        for (int i = 0; i < hash.length; i++) {
	            int v = hash[i] & 0xFF;
	            if (v < 16) {
	                outStrBuf.append('0');
	            }
	            outStrBuf.append(Integer.toString(v, 16).toLowerCase());
	        }
	        return outStrBuf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
