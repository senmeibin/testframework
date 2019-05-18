package com.bpms.cmn.utility;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.bpms.core.exception.ServiceException;
import org.springframework.util.Base64Utils;

import java.io.ByteArrayOutputStream;

/**
 * 二维码生成类
 */
public final class QRCodeUtils {
    /**
     * 生成二维码base64字符串
     *
     * @param content 要生成的内容
     * @return base64字符串
     */
    public static String getBase64ImageString(String content) {
        //默认500*500 png
        return getBase64ImageString(content, 500, 500, "png");
    }

    /**
     * 生成二维码base64字符串
     *
     * @param content       要生成的内容
     * @param width         图片宽度
     * @param height        图片高度
     * @param extensionName 图片扩展名
     * @return base64字符串
     */
    public static String getBase64ImageString(String content, int width, int height, String extensionName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height);
            MatrixToImageWriter.writeToStream(bitMatrix, extensionName, outputStream);
        } catch (Exception e) {
            throw new ServiceException("二维码生成失败：" + e.getMessage());
        }
        return Base64Utils.encodeToString(outputStream.toByteArray());
    }
}
