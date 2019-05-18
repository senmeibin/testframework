package com.bpms.core.service;

import com.bpms.core.entity.CoreEntity;
import com.bpms.core.security.ShiroUser;
import com.bpms.core.sql.SqlReader;
import com.bpms.sys.entity.ext.AttachmentImportExt;
import com.bpms.sys.service.AttachmentImportService;
import com.bpms.sys.service.DictionaryService;
import com.bpms.sys.service.ParameterService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.MappedSuperclass;

/**
 * Service处理核心类
 */
@SuppressWarnings("unchecked")
@MappedSuperclass
public abstract class CoreService<T extends CoreEntity> {
    /**
     * 日志输出对象
     */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * SqlReader对象实例
     */
    @Autowired
    protected SqlReader sqlReader;

    /**
     * 字典表Service实例
     */
    @Autowired(required = false)
    protected DictionaryService dictionaryService;

    @Autowired(required = false)
    protected ParameterService parameterService;

    @Autowired(required = false)
    protected AttachmentImportService attachmentImportService;

    /**
     * 取得SQL文 路径规范:/模块/功能/SQL节点
     *
     * @param path SQL文件路径
     * @return 查询SQL文
     */
    public String getSQL(String path) {
        return sqlReader.read(path);
    }

    /**
     * 取出SHIRO中的当前用户信息
     */
    public ShiroUser getCurrentUser() {
        return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 取出Shiro中的当前用户UID.
     *
     * @return 当前用户UID
     */
    public String getCurrentUserId() {
        return getCurrentUser().getUserUid();
    }

    /**
     * 保存附件文件
     *
     * @param file       附件文件
     * @param attachment 实体对象
     * @return 实体对象
     */
    public AttachmentImportExt saveAttachment(MultipartFile file, AttachmentImportExt attachment) {
        return this.attachmentImportService.save(file, attachment);
    }
}
