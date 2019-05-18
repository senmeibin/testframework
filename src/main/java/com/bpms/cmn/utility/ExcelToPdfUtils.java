package com.bpms.cmn.utility;

import com.aspose.cells.License;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Excel转换PDF
 */
public class ExcelToPdfUtils {
    /**
     * 日志输出对象
     */
    protected final static Logger log = LoggerFactory.getLogger(ExcelToPdfUtils.class);

    /**
     * license注册 否则打印有有水印
     *
     * @return
     */
    static {
        try {
            InputStream is = ExcelToPdfUtils.class.getClassLoader().getResourceAsStream("\\license.xml");
            License asposeLicense = new License();
            asposeLicense.setLicense(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * excel转pdf
     *
     * @param sourceFileInputStream 转换的源文件
     * @param targetFile            转换后的文件
     */
    public static void convertExcelToPdf(InputStream sourceFileInputStream, File targetFile) {
        FileOutputStream outputStream = null;
        try {
            long begin = System.currentTimeMillis();
            //源文件转换为workbook对象
            Workbook wb = new Workbook(sourceFileInputStream);
            //目标文件转换为输出流
            outputStream = new FileOutputStream(targetFile);
            //进行转换
            wb.save(outputStream, SaveFormat.PDF);
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
