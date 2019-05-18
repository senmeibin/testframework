package com.bpms.sys.service;

import com.bpms.core.AppContext;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.sys.dao.AttachmentDao;
import com.bpms.sys.entity.ext.AttachmentExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 附件文件服务类
 */
@Service
public class AttachmentService extends SysBaseService<AttachmentExt> {
    @Autowired
    private AttachmentDao attachmentDao;


    @Override
    public AttachmentDao getDao() {
        return attachmentDao;
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
     * 根据关联主键查询附件信息
     *
     * @param relationUid 关联主键UID
     * @return 附件列表
     */
    public List<AttachmentExt> findByRelationUid(String relationUid) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.relation_uid", relationUid);
        //取得附件文件实体
        return search(condition);
    }
}