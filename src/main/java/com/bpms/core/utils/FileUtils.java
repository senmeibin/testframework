package com.bpms.core.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.text.DecimalFormat;

/**
 * 文件操作工具类
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
    /**
     * 取得OS环境匹配的实际路径地址
     *
     * @param path 路径地址
     * @return 与OS相关的实际路径地址
     */
    public static String getRealPath(String path) {
        //Linux系统的场合
        if (StringUtils.equals(File.separator, "/")) {
            return path.replaceAll("\\\\", "/");
        }
        //Windows系统的场合
        else {
            return path.replaceAll("/", "\\\\");
        }
    }

    /**
     * 把in流中的内容存入指定目录下的指定文件中
     *
     * @param path     文件绝对路径
     * @param fileName 文件名
     * @param in       文件流
     */
    public static boolean createFile(String path, String fileName, byte[] in) {
        //创建文件
        File folder = new File(path);
        //如果不存在
        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (!path.endsWith("/") && !path.endsWith("\\")) {
            //linux 和windows都可以的分割伏
            path = path + File.separator;
        }
        File uploadFile = new File(path + fileName);
        //copy文件到 创建的文件中 (有则覆盖）
        try {
            FileCopyUtils.copy(in, uploadFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取文件扩展名称
     *
     * @param fileName 文件名
     * @return 文件扩展名称
     */
    public static String getExtensionName(String fileName) {
        if (StringUtils.isNotEmpty(fileName)) {
            int dot = fileName.lastIndexOf(".");
            if (dot > -1 && dot < fileName.length() - 1) {
                return fileName.substring(dot + 1).toLowerCase();
            }
        }

        return fileName;
    }

    /**
     * 获取无扩展名的文件名
     *
     * @param fileName 文件名
     * @return 文件名
     */
    public static String getFileNameWithoutExtension(String fileName) {
        if (StringUtils.isNotEmpty(fileName)) {
            int dot = fileName.lastIndexOf(".");
            if (dot > -1 && dot < fileName.length()) {
                return fileName.substring(0, dot);
            }
        }

        return fileName;
    }

    /**
     * 获取文件编码格式
     *
     * @param file 文件
     * @return 文件编码格式
     * @throws Exception 读取异常
     */
    public static String getFileCharsetName(File file) throws Exception {
        String code = null;
        FileInputStream in = new FileInputStream(file);
        BufferedInputStream bin = new BufferedInputStream(in);
        int p = (bin.read() << 8) + bin.read();
        bin.close();
        in.close();
        switch (p) {
            case 0x5c75:
                code = "ANSI|ASCII";
                break;
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            default:
                code = "GBK";
        }

        return code;
    }

    /**
     * 将文件转换为指定的编码格式
     *
     * @param file            原文件
     * @param destFile        目标文件
     * @param fromCharsetName 原文件编码格式
     * @param toCharsetName   目标文件编码格式
     * @throws Exception 转换异常
     */
    public static void convert(File file, File destFile, String fromCharsetName, String toCharsetName) throws Exception {
        if (!file.isDirectory()) {
            String fileContent = getFileContentFromCharset(file, fromCharsetName);
            if (destFile == null) {
                saveFile2Charset(file, toCharsetName, fileContent);
            }
            else {
                saveFile2Charset(destFile, toCharsetName, fileContent);
            }
        }
    }

    /**
     * 以指定编码格式读取文件
     *
     * @param file        读取文件
     * @param charsetName 编码格式
     * @return 文件内容
     * @throws Exception 读取异常
     */
    public static String getFileContentFromCharset(File file, String charsetName) throws Exception {
        //判断指定的字符集是否受支持
        if (!Charset.isSupported(charsetName)) {
            return null;
        }
        else {
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(inputStream, charsetName);
            char[] chs = new char[(int) file.length()];
            reader.read(chs);
            String str = (new String(chs)).trim();

            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(inputStream);
            return str;
        }
    }

    /**
     * 以指定编码格式写文件
     *
     * @param file        写入文件
     * @param charsetName 编码格式
     * @param content     文件内容
     * @throws Exception 异常信息
     */
    public static void saveFile2Charset(File file, String charsetName, String content) throws Exception {
        //判断指定的字符集是否受支持
        if (Charset.isSupported(charsetName)) {
            FileOutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter outWrite = new OutputStreamWriter(outputStream, charsetName);
            outWrite.write(content);

            IOUtils.closeQuietly(outWrite);
            IOUtils.closeQuietly(outputStream);
        }
    }

    /**
     * 格式化文件大小
     *
     * @param fileSize 文件大小
     * @return 格式化后的文件大小
     */
    public static String formatFileSize(long fileSize) {
        //设置小数点位数
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = org.apache.commons.lang3.StringUtils.EMPTY;

        //根据文件大小 计算文件后缀  进制1024
        if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        }
        else if (fileSize < (1024 * 1024)) {
            fileSizeString = df.format((double) fileSize / 1024) + "K";
        }
        else if (fileSize < (1024 * 1024 * 1024)) {
            fileSizeString = df.format((double) fileSize / (1024 * 1024)) + "M";
        }
        else {
            fileSizeString = df.format((double) fileSize / (1024 * 1024 * 1024)) + "G";
        }

        return fileSizeString;
    }

    /**
     * 获取以当前月份为上传单位的相对上传路径（如果不存在该目录，则新增该目录）
     *
     * @param uploadRootPath 上传根目录(以/结尾）
     * @return 以当前月份为上传单位的相对上传路径 (如： upload/201801/
     */
    public static String getUploadRelationPath(String uploadRootPath) {
        //取当前年月
        String currentMonth = DateUtils.format(DateUtils.getNowDate(), "yyyyMM");
        //图片相对路径
        String uploadRelationPath = "upload/" + currentMonth + "/";
        //设置文件地址
        String uploadFullPath = uploadRootPath + uploadRelationPath;

        //创建文件夹
        File file = new File(uploadFullPath);
        //如果不存在文件夹创建
        if (!file.exists()) {
            //创建文件夹
            file.mkdirs();
        }
        return uploadRelationPath;
    }
}