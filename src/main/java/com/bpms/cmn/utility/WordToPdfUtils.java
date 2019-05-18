package com.bpms.cmn.utility;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * word转换pdf
 */
public class WordToPdfUtils {
    /**
     * 日志输出对象
     */
    protected final static Logger log = LoggerFactory.getLogger(WordToPdfUtils.class);

    /**
     * 获取license
     *
     * @return
     */
    static {
        try {
            InputStream is = WordToPdfUtils.class.getClassLoader().getResourceAsStream("\\license.xml");
            License asposeLicense = new License();
            asposeLicense.setLicense(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * word转pdf
     *
     * @param sourceFileInputStream 源文件的输入流
     * @param targetFile            转换后的文件
     */
    public static void convertWordToPdf(InputStream sourceFileInputStream, File targetFile) {
        FileOutputStream outputStream = null;
        try {
            long begin = System.currentTimeMillis();
            outputStream = new FileOutputStream(targetFile);
            //生成document
            Document doc = new Document(sourceFileInputStream);
            //进行转换
            doc.save(outputStream, SaveFormat.PDF);
            long now = System.currentTimeMillis();
            //转化用时
            log.info("共耗时：" + ((now - begin) / 1000.0) + "秒");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }
}
