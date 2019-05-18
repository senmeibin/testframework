package com.bpms.sys.service;

import com.bpms.core.AppContext;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.*;
import com.bpms.sys.dao.AttachmentImportDao;
import com.bpms.sys.entity.ext.AttachmentImportExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;

/**
 * 附件导入服务类
 */
@Service
public class AttachmentImportService extends SysBaseService<AttachmentImportExt> {
    @Autowired
    private AttachmentImportDao attachmentImportDao;

    @Override
    public AttachmentImportDao getDao() {
        return attachmentImportDao;
    }

    /**
     * 根据附件文件路径获取文件
     *
     * @param filePath 附件文件路径
     * @return 附件文件
     */
    public File getAttachmentFile(String filePath) {
        //系统中文件上传的根目录 取得
        String uploadPath = this.parameterService.getValue(AppCodeConsts.APP_COMMON, "FILE_UPLOAD_PATH", AppContext.getRequest().getServletContext().getRealPath("/"));
        //构建附件文件
        return new File(uploadPath + filePath);
    }

    /**
     * 保存附件文件
     *
     * @param file       附件文件
     * @param attachment 实体对象
     * @return 实体对象
     */
    public AttachmentImportExt save(MultipartFile file, AttachmentImportExt attachment) {
        if (StringUtils.isEmpty(attachment.getModuleName())) {
            throw new ServiceException("请设置附件导入模块名称（如：工资导入/社保导入等）。");
        }

        //应用编号
        String appCode = RequestUtils.getAppCode(RequestUtils.getRequestPathInfo(AppContext.getRequest()));

        //系统中文件上传的根目录 取得
        String uploadRootPath = this.parameterService.getValue(AppCodeConsts.APP_COMMON, "FILE_UPLOAD_PATH", AppContext.getAppPath());
        //上传相对路径
        String relationUploadPath = FileUtils.getUploadRelationPath(uploadRootPath);
        //设置文件地址
        String ctxPath = uploadRootPath + relationUploadPath;

        String fileName = file.getOriginalFilename();

        //构成新文件名
        String newFileName = UniqueUtils.getUID() + fileName.substring(fileName.lastIndexOf("."));

        //创建文件
        File uploadFile = new File(ctxPath + newFileName);

        try {
            //copy文件到 创建的文件中
            FileCopyUtils.copy(file.getBytes(), uploadFile);
        } catch (Exception ex) {
            String message = String.format("附件文件保存失败：%s", ex.getMessage());
            if (!StringUtils.isEmpty(attachment.getImportResultDetailMessage())) {
                message = attachment.getImportResultDetailMessage() + "\n" + message;
            }
            attachment.setImportResultDetailMessage(message);
        }

        //应用编号
        attachment.setAppCode(appCode);
        //文件名
        attachment.setFileName(fileName);
        //文件地址
        attachment.setFilePath(relationUploadPath + newFileName);
        //文件大小
        attachment.setFileSize(FileUtils.formatFileSize(uploadFile.length()));
        //MIME类型
        attachment.setMimeType(new MimetypesFileTypeMap().getContentType(fileName));

        //可用状态
        attachment.setRecordStatus(1);

        //数据保存处理
        AttachmentImportExt savedEntity = getDao().save(attachment);

        //主键值设定
        EntityUtils.setPkValue(savedEntity, attachment);

        return attachment;
    }
}