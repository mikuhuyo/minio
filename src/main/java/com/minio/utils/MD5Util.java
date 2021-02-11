package com.minio.utils;

import java.security.MessageDigest;

/**
 * @author yuelimin
 * @since 1.8
 */
public class MD5Util {
    /**
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * 加密解密算法, 执行一次加密, 两次解密
     */
    public static String convertMD5(String inStr) {
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }

        return new String(a);

    }

    /**
     * 判断输入的密码和数据库中保存的MD5密码是否一致
     *
     * @param inputPassword 输入的密码
     * @param md5DB         数据库保存的密码
     * @return
     */
    public static boolean passwordIsTrue(String inputPassword, String md5DB) {
        String md5 = string2MD5(inputPassword);
        return md5DB.equals(md5);
    }
}
