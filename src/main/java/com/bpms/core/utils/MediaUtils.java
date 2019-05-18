package com.bpms.core.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 上传下载多媒体文件工具类
 */
public class MediaUtils {
    static Logger log = LoggerFactory.getLogger(MediaUtils.class);

    /**
     * 从指定的媒体路径下载媒体文件到本地服务器
     *
     * @param mediaUrl 媒体URL路径
     * @param file     存储文件
     * @return 媒体文件类型
     */
    public static String downloadMedia(String mediaUrl, File file) {
        HttpURLConnection conn = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(mediaUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");

            //类型
            String contentType = conn.getHeaderField("Content-Type");

            bis = new BufferedInputStream(conn.getInputStream());
            fos = new FileOutputStream(file);
            byte[] buf = new byte[8096];
            int size = 0;
            while ((size = bis.read(buf)) != -1) {
                fos.write(buf, 0, size);
            }
            return contentType;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(bis);
            try {
                conn.disconnect();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return StringUtils.EMPTY;
    }
}
