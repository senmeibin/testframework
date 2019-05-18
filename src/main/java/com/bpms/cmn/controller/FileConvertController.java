package com.bpms.cmn.controller;

import com.bpms.cmn.utility.ExcelToPdfUtils;
import com.bpms.cmn.utility.WordToPdfUtils;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.utils.DateUtils;
import com.bpms.core.utils.DesUtils;
import com.bpms.core.utils.FileUtils;
import com.bpms.core.utils.StringUtils;
import com.bpms.sys.service.ParameterService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

/**
 * 文件转换(无需登录转换文件）
 */
@Controller
@RequestMapping("/cmn/fileconvert")
public class FileConvertController {

    /**
     * 日志输出对象
     */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ParameterService parameterService;

    /**
     * word，excel文件转换为pdf
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "convertPdf", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> convertPdf(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "token") String token, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //校验token是否正确
        String decryptToken = DesUtils.decrypt(token);
        if (!StringUtils.equals(decryptToken, "bpms")) {
            return new ResponseEntity<>(HttpStatus.HTTP_VERSION_NOT_SUPPORTED);
        }
        FileInputStream inputStream = null;
        ServletOutputStream outputStream = null;

        try {
            // 文件名称
            String sourceFileName = file.getOriginalFilename();

            //获取文件后缀
            String ext = FileUtils.getExtensionName(sourceFileName);

            //转换后的文件名
            String targetFileName = FileUtils.getFileNameWithoutExtension(sourceFileName) + ".pdf";

            //系统中文件上传的根目录 取得
            String uploadPath = this.parameterService.getValue(AppCodeConsts.APP_COMMON, "FILE_UPLOAD_PATH", request.getServletContext().getRealPath("/"));

            // 日期命名的文件夹
            String fileDirPath = "/upload/file/" + DateUtils.format(new Date(), "yyyy") + "/" + DateUtils.format(new Date(), "MM") + "/" + DateUtils.format(new Date(), "dd") + "/";

            //设置文件地址
            String ctxPath = uploadPath + fileDirPath;

            //创建文件夹
            File fileDir = new File(ctxPath);

            //如果不存在文件夹创建
            if (!fileDir.exists()) {
                //创建文件夹
                fileDir.mkdirs();
            }

            File targetFile = new File(ctxPath + targetFileName);

            //word文档
            if (StringUtils.equals(ext, "doc") || StringUtils.equals(ext, "docx")) {
                //word转pdf转换
                WordToPdfUtils.convertWordToPdf(file.getInputStream(), targetFile);
            }
            //excel文档
            else if (StringUtils.equals(ext, "xls") || StringUtils.equals(ext, "xlsx")) {
                //excel转换pdf
                ExcelToPdfUtils.convertExcelToPdf(file.getInputStream(), targetFile);
            }
            //其他文档暂不支持
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            //创建文件流
            inputStream = new FileInputStream(targetFile);
            outputStream = response.getOutputStream();

            //设置输出的格式
            response.reset();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + targetFileName + "\"");

            //循环取出流中的数据
            byte[] buffer = new byte[1024];
            int len;
            //读取数据流
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } finally {
            //关闭流
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
