package com.bpms.core.utils;


import com.sun.imageio.plugins.jpeg.JPEGImageWriter;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片工具类
 */
public class ImageUtils {
    /**
     * 等比例压缩图片 指定宽度、高度压缩图片
     *
     * @param inputImagePath 压缩图片原路径
     * @param outImagePath   输出压缩图片路径
     * @param imageName      图片名称
     * @param imageWidth     图片宽度
     * @param imageHeight    图片高度
     * @return 压缩后文件名称（不带路径）
     */
    public static String compressImage(String inputImagePath, String outImagePath, String imageName, int imageWidth, int imageHeight) {
        String result = StringUtils.EMPTY;
        try {
            //获得源文件
            File file = new File(inputImagePath + imageName);

            //判断源文件是否存在
            if (!file.exists()) {
                return result;
            }
            Image img = ImageIO.read(file);

            // 判断图片格式是否正确
            if (img == null || img.getWidth(null) == -1) {
                return result;
            }
            else {

                //默认高度、宽度等比例缩放
                double widthRate = ((double) img.getWidth(null)) / (double) imageWidth;
                double heightRate = ((double) img.getHeight(null)) / (double) imageHeight;

                // 根据缩放比率大的进行缩放控制
                double rate = widthRate > heightRate ? widthRate : heightRate;

                //获取缩放比例之后的宽度与高度
                imageWidth = (int) (((double) img.getWidth(null)) / rate);
                imageHeight = (int) (((double) img.getHeight(null)) / rate);

                BufferedImage bufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);

                //Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
                bufferedImage.getGraphics().drawImage(img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH), 0, 0, null);

                //创建输出图片的路径及名称
                FileOutputStream outStream = new FileOutputStream(outImagePath + getNewImageName(imageName, imageWidth, imageHeight));

                //以JPEG编码保存图片
                saveAsJPEG(bufferedImage, 0.7f, outStream);

                //获取压缩后的文件名称
                result = getNewImageName(imageName, imageWidth, imageHeight);

                //关闭流
                IOUtils.closeQuietly(outStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    /**
     * 根据文件名获取压缩后的文件名
     *
     * @param fileName    文件名称
     * @param imageWidth  图片宽度
     * @param imageHeight 图片高度
     * @return 压缩后文件名称（不带路径）
     */
    private static String getNewImageName(String fileName, int imageWidth, int imageHeight) {
        int index = fileName.lastIndexOf(".");

        //newImageName = 原文件名_宽_高.xxx
        return fileName.substring(0, index) + "_" + imageWidth + "_" + imageHeight + fileName.substring(index);
    }

    /**
     * 根据文件名判断文件是否为图像
     *
     * @param fileName 文件名称
     * @return true：图像文件
     */
    public static Boolean isImage(String fileName) {
        Boolean result = false;

        //如果为图像格式返回true
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".dmp") || fileName.endsWith(".gif")) {
            result = true;
        }

        return result;
    }

    /**
     * 以JPEG编码保存图片
     *
     * @param imageToSave     要处理的图像图片
     * @param compressionRate 压缩比
     * @param fos             文件输出流
     * @throws IOException
     */
    private static void saveAsJPEG(BufferedImage imageToSave,
                                   float compressionRate, FileOutputStream fos) throws IOException {
        // Image writer
        JPEGImageWriter imageWriter = (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpg").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
        imageWriter.setOutput(ios);
        // and metadata
        IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(imageToSave), null);

        if (compressionRate >= 0 && compressionRate <= 1f) {
            JPEGImageWriteParam jpegParams = (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(compressionRate);
        }
        // new Write and clean up
        imageWriter.write(imageMetaData, new IIOImage(imageToSave, null, null), null);
        IOUtils.closeQuietly(ios);
        imageWriter.dispose();
    }

    /**
     * 图片旋转
     *
     * @param degree     旋转角度
     * @param sourcePath 原图片路径
     * @param targetPath 目标图片路径
     */
    public static void rotate(int degree, String sourcePath, String targetPath) {
        try {
            Thumbnails.of(sourcePath).scale(1).rotate(degree).toFile(targetPath);
        } catch (Exception ex) {

        }
    }
}
