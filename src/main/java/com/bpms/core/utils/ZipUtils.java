package com.bpms.core.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.*;

/**
 * 字符串压缩&解压缩处理
 */
public class ZipUtils {
    private static Logger log = LoggerFactory.getLogger(ZipUtils.class);

    /**
     * 将多个文件压缩Zip输出流中
     *
     * @param files    需要压缩的文件集合
     * @param baseName 基路径
     * @param zos      Zip文件输出流
     * @throws IOException
     */
    public static void zipFiles(File[] files, String baseName, ZipOutputStream zos) throws IOException {
        for (File file : files) {
            FileInputStream fis = null;
            try {
                zos.putNextEntry(new ZipEntry(baseName + file.getName()));
                fis = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int r;
                while ((r = fis.read(buffer)) != -1) {
                    zos.write(buffer, 0, r);
                }
            } finally {
                IOUtils.closeQuietly(fis);
            }
        }
    }

    /**
     * 压缩指定路径的文件
     *
     * @param filePath 待压缩的文件路径
     * @return 压缩后的文件
     */
    public static File zipFile(String filePath) {
        File target = null;
        File source = new File(filePath);
        if (source.exists()) {
            // 压缩文件名=源文件名.zip
            String zipName = source.getName() + ".zip";
            target = new File(source.getParent(), zipName);

            // 删除旧的文件
            if (target.exists()) {
                target.delete();
            }

            FileOutputStream fos = null;
            ZipOutputStream zos = null;
            try {
                fos = new FileOutputStream(target);
                zos = new ZipOutputStream(new BufferedOutputStream(fos));
                // 添加对应的文件Entry
                addEntry("/", source, zos);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                IOUtils.closeQuietly(zos);
                IOUtils.closeQuietly(fos);
            }
        }
        return target;
    }

    /**
     * 扫描添加文件Entry
     *
     * @param baseName 基路径
     * @param source   源文件
     * @param zos      Zip文件输出流
     * @throws IOException
     */
    private static void addEntry(String baseName, File source, ZipOutputStream zos) throws IOException {
        // 按目录分级，形如：/aaa/bbb.txt
        String entry = baseName + source.getName();
        if (source.isDirectory()) {
            for (File file : source.listFiles()) {
                // 递归列出目录下的所有文件，添加文件Entry
                addEntry(entry + "/", file, zos);
            }
        }
        else {
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                byte[] buffer = new byte[1024];
                fis = new FileInputStream(source);
                bis = new BufferedInputStream(fis, buffer.length);
                int read;
                zos.putNextEntry(new ZipEntry(entry));
                while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
                    zos.write(buffer, 0, read);
                }
                zos.closeEntry();
            } finally {
                IOUtils.closeQuietly(bis);
                IOUtils.closeQuietly(fis);
            }
        }
    }

    /**
     * 解压文件
     *
     * @param filePath 压缩文件路径
     */
    public static void unzipFile(String filePath) {
        File source = new File(filePath);
        if (source.exists()) {
            ZipInputStream zis = null;
            BufferedOutputStream bos = null;
            try {
                zis = new ZipInputStream(new FileInputStream(source));
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null
                        && !entry.isDirectory()) {
                    File target = new File(source.getParent(), entry.getName());
                    if (!target.getParentFile().exists()) {
                        // 创建文件父目录
                        target.getParentFile().mkdirs();
                    }
                    // 写入文件
                    bos = new BufferedOutputStream(new FileOutputStream(target));
                    int read;
                    byte[] buffer = new byte[1024];
                    while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
                        bos.write(buffer, 0, read);
                    }
                    bos.flush();
                }
                zis.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                IOUtils.closeQuietly(zis);
                IOUtils.closeQuietly(bos);
            }
        }
    }

    /**
     * 使用gzip进行压缩
     *
     * @param str 压缩前的文本
     * @return 返回压缩后的文本
     */
    public static String gzipString(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;

        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes());
            gzip.finish();
            return Base64.encodeBase64String(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(gzip);
            IOUtils.closeQuietly(out);
        }

        return str;
    }

    /**
     * 使用gzip进行解压缩
     *
     * @param str 压缩前的文本
     * @return 返回压缩后的文本
     */
    public static String gunzipString(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream ginzip = null;
        try {
            in = new ByteArrayInputStream(Base64.decodeBase64(str.getBytes()));
            ginzip = new GZIPInputStream(in);

            byte[] buffer = new byte[1024];
            int offset;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(ginzip);
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }

        return str;
    }

    /**
     * 使用zip进行压缩
     *
     * @param str 压缩前的文本
     * @return 返回压缩后的文本
     */
    public static String zipString(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ZipOutputStream zout = null;
        try {
            zout = new ZipOutputStream(out);
            zout.putNextEntry(new ZipEntry("0"));
            zout.write(str.getBytes());
            zout.closeEntry();
            return Base64.encodeBase64String(out.toByteArray());
        } catch (IOException e) {
            log.error("压缩IO错误(zip)：" + e);
        } finally {
            IOUtils.closeQuietly(zout);
            IOUtils.closeQuietly(out);
        }
        return str;
    }

    /**
     * 使用zip进行解压缩
     *
     * @param str 压缩后的文本
     * @return 解压后的字符串
     */
    public static String unzipString(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        ZipInputStream zin = null;
        try {
            in = new ByteArrayInputStream(Base64.decodeBase64(str.getBytes()));
            zin = new ZipInputStream(in);
            zin.getNextEntry();
            byte[] buffer = new byte[1024];
            int offset;
            while ((offset = zin.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            return out.toString();
        } catch (IOException e) {
            log.error("解压缩IO错误(zip)：" + e);
        } finally {
            IOUtils.closeQuietly(zin);
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
        return str;
    }
}
