package com.bpms.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

/**
 * 支持SHA-1/MD5消息摘要的工具类.
 * <p>
 * 返回ByteSource，可进一步被编码为Hex, Base64或UrlSafeBase64
 *
 * @author calvin
 */
public class Digests {

    private static final String SHA1 = "SHA-1";

    /**
     * sha256加密
     *
     * @param password 明文密码
     * @return 加密后密码
     */
    public static String shaPassword256(String password) {
        return Encodes.encodeHex(Digests.digest(StringUtils.defaultString(password).getBytes(), SHA1, null, 256));
    }

    /**
     * 对字符串进行散列, 支持md5与sha1算法.
     */
    private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            if (salt != null) {
                digest.update(salt);
            }

            byte[] result = digest.digest(input);

            for (int i = 1; i < iterations; i++) {
                digest.reset();
                result = digest.digest(result);
            }
            return result;
        } catch (GeneralSecurityException e) {
            throw Exceptions.unchecked(e);
        }
    }
}
