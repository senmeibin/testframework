package com.bpms.core.utils;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * DES加密和解密工具,可以对字符串进行加密和解密操作
 */
public class DesUtils {
    /**
     * 机密KEY
     */
    private static String KEY = "BPMS_DES";

    /**
     * 加密处理
     *
     * @param value 明文字符串
     * @return DES加密后的字符串
     */
    public static String encrypt(String value) {
        return encrypt(value, KEY);
    }

    /**
     * 加密处理
     *
     * @param value 明文字符串
     * @param key   机密key
     * @return DES加密后的字符串
     */
    public static String encrypt(String value, String key) {
        try {
            if (StringUtils.isEmpty(value)) {
                return value;
            }

            //获取密钥
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            DESKeySpec keySpec = new DESKeySpec(key.getBytes());
            SecretKey desKey = factory.generateSecret(keySpec);

            // Cipher负责完成加密或解密工作
            Cipher c = Cipher.getInstance("DES");

            // 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
            c.init(Cipher.ENCRYPT_MODE, desKey);
            byte[] src = value.getBytes();

            // 该字节数组负责保存加密的结果
            byte[] cipherByte = c.doFinal(src);
            return new String(Base64.encodeBase64(cipherByte));
        } catch (Exception ex) {
            return value;
        }
    }

    /**
     * 解密处理
     *
     * @param data 机密字符串
     * @return 明文字符串
     */
    public static String decrypt(String data) {
        return decrypt(data, KEY);
    }

    /**
     * 解密处理
     *
     * @param data 机密字符串
     * @param key  机密key
     * @return 明文字符串
     */
    public static String decrypt(String data, String key) {
        try {
            if (StringUtils.isEmpty(data)) {
                return data;
            }

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] buf = decoder.decodeBuffer(data);
            byte[] bt = decrypt(buf, key.getBytes());
            return new String(bt);
        } catch (Exception ex) {
            return data;
        }
    }

    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
        return cipher.doFinal(data);
    }

//    public static void main(String[] args) {
//        String password = DesUtils.encrypt("abc123");
//
//        password = DesUtils.decrypt(password);
//    }
}
