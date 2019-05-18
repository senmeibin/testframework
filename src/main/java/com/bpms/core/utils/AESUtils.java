package com.bpms.core.utils;

import org.apache.axis.encoding.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 通过AES算法对文本进行加密解密
 */
public class AESUtils {
    /**
     * 密钥
     */
    private static final byte[] KEY_VALUE = new byte[]{
            82, 69, 78, 82, 85, 73, 72, 82, 21, 25, -35, -45, 25, 98, -55, -45, 10, 35, -45, 25,
            26, -92, 25, -65, -78, -99, 85, 45, -62, 10, -10, 11, -35, 48, -98, 65, -32, 14, -78,
            25, 56, -56, -45, -45, 12, 15, -35, -75, 15, -14, 62, -25, 33, -45, 55, 78, -88, -99
    };

    /**
     * 加密密钥
     */
    private static SecretKey key;

    /**
     * 加密算法
     */
    private static Cipher cipher;

    static {
        //构造密钥生成器
        KeyGenerator keygen;
        try {
            //指定为AES算法
            keygen = KeyGenerator.getInstance("AES");
            //根据秘钥初始化密钥生成器(生成一个128位的随机源,根据传入的字节数组)
            keygen.init(128, new SecureRandom(KEY_VALUE));
            //产生原始对称密钥
            SecretKey secretKey = keygen.generateKey();
            //获得原始对称密钥的字节数组
            byte[] enCodeFormat = secretKey.getEncoded();
            //根据字节数组生成AES密钥
            key = new SecretKeySpec(enCodeFormat, "AES");
            //根据指定算法AES自成密码器
            cipher = Cipher.getInstance("AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @return
     */
    public static String encrypt(String content) {
        try {
            //初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //获取加密内容的字节数组
            byte[] byteContent = content.getBytes("utf-8");
            //根据密码器的初始化方式--加密：将数据加密
            byte[] result = cipher.doFinal(byteContent);
            //用Base64将加密后的数据转换为字符串
            return Base64.encode(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content 待解密内容
     * @return
     */
    public static String decrypt(String content) {
        try {
            //初始化密码器
            cipher.init(Cipher.DECRYPT_MODE, key);
            //解密
            byte[] result = cipher.doFinal(Base64.decode(content));
            return new String(result, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用公钥加密
     *
     * @param publicKeyByte 公钥
     * @param encryptedText 待加密加密的信息
     * @return 加密后的对象
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeySpecException
     * @throws UnsupportedEncodingException
     */
    public static Params encrypt(byte[] publicKeyByte, String encryptedText) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, UnsupportedEncodingException {
        byte[] encryptedByte = encryptedText.getBytes("UTF-8");
        //生成会话密钥
        // 使用AES算法
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        // 使用128位长度的密钥
        keyGenerator.init(128);
        // 生成密钥
        SecretKey secretKey = keyGenerator.generateKey();

        // 加密报文
        // 使用AES算法
        Cipher secretKeyCipher = Cipher.getInstance("AES");
        // 加密模式
        secretKeyCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherText = secretKeyCipher.doFinal(encryptedByte);

        // 读取公钥 PUBLIC_KEY; 公钥（PEM格式）
        // 使用RSA算法
        KeyFactory kf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509ks = new X509EncodedKeySpec(publicKeyByte);
        PublicKey publicKey = kf.generatePublic(x509ks);

        // 用公钥对密钥进行加密
        // 使用RSA算法
        Cipher publicKeyCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        // 加密模式
        publicKeyCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 密钥明文
        byte[] plainkey = secretKey.getEncoded();
        // 密钥密文
        byte[] cipherKey = publicKeyCipher.doFinal(plainkey);

        Params result = new Params();
        result.setCipher(org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(cipherKey));
        result.setText(org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(cipherText));
        return result;
    }

    /**
     * 用私钥解密
     *
     * @param privateKeyByte 私钥
     * @param cipher         密文密钥
     * @param text           密文
     * @return 解密的信息
     */
    public static byte[] decrypt(byte[] privateKeyByte, String cipher, String text) throws NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] cipherKey = org.apache.commons.codec.binary.Base64.decodeBase64(cipher);
        byte[] cipherText = org.apache.commons.codec.binary.Base64.decodeBase64(text);

        // 读取私钥
        PKCS8EncodedKeySpec p8ks = new PKCS8EncodedKeySpec(privateKeyByte);
        // 使用RSA算法
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(p8ks);

        // 用私钥解密出密钥
        // 使用RSA算法
        Cipher publicKeyCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        // 解密模式
        publicKeyCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedKey = publicKeyCipher.doFinal(cipherKey);

        // 用密钥解密出报文
        // 使用AES算法
        Cipher secretKeyCipher = Cipher.getInstance("AES");
        SecretKeySpec secretKeySpec = new SecretKeySpec(decryptedKey, "AES");
        // 解密模式
        secretKeyCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        return secretKeyCipher.doFinal(cipherText);
    }

    public static class Params implements Serializable {

        /**
         *
         */
        private static final long serialVersionUID = -5495878594258863225L;

        /**
         * cipherText：用AES密钥加密得到的密文
         */
        private java.lang.String text;
        /**
         * cipherKey：用RSA私钥对AES密钥加密得到的密文
         */
        private java.lang.String cipher;

        public java.lang.String getText() {
            return text;
        }

        public void setText(java.lang.String text) {
            this.text = text;
        }

        public java.lang.String getCipher() {
            return cipher;
        }

        void setCipher(java.lang.String cipher) {
            this.cipher = cipher;
        }

        @Override
        public java.lang.String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Params [");
            if (text != null) {
                builder.append("text=").append(text).append(", ");
            }
            if (cipher != null) {
                builder.append("cipher=").append(cipher);
            }
            builder.append("]");
            return builder.toString();
        }
    }
}