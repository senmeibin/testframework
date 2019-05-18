package com.bpms.crm.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName: SignUtil
 * @Description: 签名认证工具类
 */

public class SignUtils {
    //XXXXXX接口KEY
    private static String XC_INTERFACE_KEY = "HPjOz9o&#cD0Kb3X";

    /**
     * 生成XXXXXX接口密钥
     * <p>
     * 用当前时间戳和关键字进行med5加密并返回加密结果
     *
     * @return 密钥
     */
    public static String createSign(long currentTimeMillis) {
        return createMD5Sign(XC_INTERFACE_KEY, currentTimeMillis);
    }

    private static String createMD5Sign(String key, long currentTimeMillis) {
        if (currentTimeMillis == 0) {
            return null;
        }
        try {
            return MD5(key + currentTimeMillis);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * XXXXXX验证密钥
     *
     * @param currentTimeMillis 加密时的时间戳
     * @param sign              密钥
     * @return 验证结果(true / false)
     */
    public static boolean checkSign(String currentTimeMillis, String sign) {
        return checkMD5Sign(XC_INTERFACE_KEY, currentTimeMillis, sign);
    }

    /**
     * MD5验证密钥
     *
     * @param key               第三方接口KEY
     * @param currentTimeMillis 加密时的时间戳
     * @param sign              密钥
     * @return 验证结果(true / false)
     */
    private static boolean checkMD5Sign(String key, String currentTimeMillis, String sign) {
        if (currentTimeMillis == null || currentTimeMillis.isEmpty()) {
            return false;
        }
        if (sign == null || sign.isEmpty()) {
            return false;
        }
        try {
            String tempSign = MD5(key + currentTimeMillis);
            if (tempSign.equals(sign)) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 进行md5加密,需要处理异常
     *
     * @param str 要加密的字符串
     * @return 加密结果
     * @throws NoSuchAlgorithmException
     */
    public static String MD5(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        md.update(str.getBytes());
        byte b[] = md.digest();
        int i;
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }
        return buf.toString();
    }

    public static void main(String args[]) {
        long t = System.currentTimeMillis();
        System.out.println(createSign(t));
        System.out.println(t);
    }
}
