package com.bpms.cmn.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 附件预览服务类
 */
@Service
public class FilePreviewService {
    /**
     * 启用附件预览状态
     */
    @Value("${attachmentPreview.enable:false}")
    private boolean attachmentPreviewEnable;

    /**
     * 是否启用附件预览
     *
     * @return true：启用附件预览
     */
    public boolean getAttachmentPreviewEnable() {
        return this.attachmentPreviewEnable;
    }
}