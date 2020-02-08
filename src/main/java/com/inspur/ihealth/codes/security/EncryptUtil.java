package com.inspur.ihealth.codes.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class EncryptUtil {

    public static final String ALGORITHM_SHA1 = "SHA-1";
    public static final String ALGORITHM_SHA = "SHA";
    public static final String ALGORITHM_MD5 = "MD5";
    private static final String CANDIDATE_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int RANDOM_STRING_LENGTH = 32;

    public EncryptUtil() {
    }

    public static String createRandomString() {
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for(int i = 0; i < 32; ++i) {
            int startIndex = Math.abs(random.nextInt()) % "abcdefghijklmnopqrstuvwxyz0123456789".length();
            randomString.append("abcdefghijklmnopqrstuvwxyz0123456789", startIndex, startIndex + 1);
        }

        return randomString.toString();
    }

    public static String encrypt(String value, String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(value.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuilder hexString = new StringBuilder();
            byte[] var5 = messageDigest;
            int var6 = messageDigest.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                byte aMessageDigest = var5[var7];
                String shaHex = Integer.toHexString(aMessageDigest & 255);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }

                hexString.append(shaHex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException var10) {
            var10.printStackTrace();
            return "";
        }
    }

}
