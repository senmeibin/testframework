package com.bpms.cmn.service;

import com.bpms.core.AppContext;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.utils.ImageUtils;
import com.bpms.core.utils.MediaUtils;
import com.bpms.sys.entity.ext.AttachmentExt;
import com.bpms.sys.service.AttachmentService;
import com.bpms.sys.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * 媒体下载服务类
 */
@Service
public class MediaService {
    /**
     * ParameterService对象
     */
    @Autowired
    protected ParameterService parameterService;
    @Autowired
    private AttachmentService attachmentService;

    /**
     * 根据URL路径取得实际文件名称
     *
     * @param url URL路径
     * @return 实际文件名称
     */
    private String getRealFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 从指定的URL地址下载媒体文件到本地服务器并作为附件文件记录
     *
     * @param mediaUrl        媒体URL路径
     * @param displayFileName 文件表示名称
     * @param appCode         应用编号
     * @param moduleName      模块名称
     * @param relationUid     请求消息ID
     */
    public void downloadMedia(String mediaUrl, String displayFileName, String appCode, String moduleName, String relationUid) {
        //取得系统中文件上传的根目录
        String uploadPath = this.parameterService.getValue(AppCodeConsts.APP_COMMON, "FILE_UPLOAD_PATH", AppContext.getRequest().getServletContext().getRealPath("/"));

        //实际文件名称
        String realFileName = this.getRealFileName(mediaUrl);

        //文件存储路径
        String filePath = uploadPath + "weixin" + File.separator;

        File fileDirectory = new File(filePath);
        //如果文件夹不存在则创建
        if (!fileDirectory.exists() && !fileDirectory.isDirectory()) {
            //创建
            fileDirectory.mkdirs();
        }

        //定义存储文件
        File file = new File(filePath + realFileName);

        //下载保存文件
        String contentType = MediaUtils.downloadMedia(mediaUrl, file);

        //保存进附件表中
        AttachmentExt attachment = new AttachmentExt();

        //应用编号
        attachment.setAppCode(appCode);

        //MIME类型
        attachment.setMimeType(contentType);

        //模块名称
        attachment.setModuleName(moduleName);

        //请求消息ID
        attachment.setRelationUid(relationUid);

        //文件名称
        attachment.setFileName(displayFileName);

        //文件存储相对路径
        attachment.setFilePath("weixin" + File.separator + realFileName);

        //文件大小
        attachment.setFileSize(file.length() / 1024 + "K");

        //图片压缩
        attachment.setSmallImagePath("weixin" + File.separator + ImageUtils.compressImage(filePath, filePath, realFileName, 100, 100));

        attachmentService.save(attachment);
    }
}
