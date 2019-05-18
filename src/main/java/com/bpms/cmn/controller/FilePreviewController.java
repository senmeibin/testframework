package com.bpms.cmn.controller;

import com.bpms.core.entity.AjaxResult;
import com.bpms.core.utils.StringUtils;
import com.bpms.sys.controller.AttachmentController;
import com.bpms.sys.entity.ext.AttachmentExt;
import com.bpms.sys.service.AttachmentAccessLogService;
import com.bpms.sys.service.AttachmentImportService;
import com.bpms.sys.service.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;

/**
 * 附件预览控制器类 (无需登录查看附件）
 */
@Controller
@RequestMapping("/cmn/filepreview")
public class FilePreviewController extends AttachmentController {
    /**
     * 日志
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AttachmentAccessLogService attachmentAccessLogService;

    /**
     * 附件文件服务类
     */
    @Autowired
    private AttachmentService attachmentService;

    /**
     * 数据导入文件服务类
     */
    @Autowired
    private AttachmentImportService attachmentImportService;

    /**
     * 取得Service对象
     */
    @Override
    public AttachmentService getService() {
        return attachmentService;
    }

    /**
     * 数据一览画面初期化
     *
     * @param model Model对象
     * @return 数据一览JSP页面
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Override
    public String list(Model model) throws IllegalAccessException, IOException, InstantiationException {
        //初始化数据导入标志位
        return "cmn/filepreview/FilePreviewList";
    }

    /**
     * Office在线预览
     *
     * @param attachmentUid 附件UID
     * @param isImportFile  (1:是数据导入文件，  0：为普通附件）
     * @return AjaxResult
     * @throws Exception
     */
    @RequestMapping(value = "officeOnlinePreview", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "预览文件")
    @ResponseBody
    public AjaxResult officeOnlinePreview(@RequestParam("attachmentUid") String attachmentUid, @RequestParam("isImportFile") String isImportFile) throws Exception {
        String sourceFilePath = "";
        //数据导入附件，来自sys_attachment_import表
        if (StringUtils.equals("1", isImportFile)) {
            sourceFilePath = this.attachmentImportService.findOne(attachmentUid).getFilePath();
        }
        //普通附件，来自sys_attachment表
        else {
            AttachmentExt attachment = this.attachmentAccessLogService.writeAttachmentAccessLog(attachmentUid, 0);
            sourceFilePath = attachment.getFilePath();
        }
        File sourceFile = this.attachmentImportService.getAttachmentFile(sourceFilePath);
        if (sourceFile == null || !sourceFile.exists()) {
            return AjaxResult.createFailResult("文件不存在！");
        }
        return AjaxResult.createSuccessResult("获取文件成功", sourceFilePath);
    }
}