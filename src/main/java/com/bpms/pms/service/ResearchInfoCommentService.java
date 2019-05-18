package com.bpms.pms.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.bpms.crm.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.bpms.core.service.BaseService;
import com.bpms.pms.dao.ResearchInfoCommentDao;
import com.bpms.pms.entity.ext.ResearchInfoCommentExt;
import com.bpms.pms.dao.ResearchInfoDao;
import com.bpms.pms.entity.ext.ResearchInfoExt;
import com.bpms.pms.service.PmsBaseService;

/**
 * 市场调研评论服务类
 */
@Service
public class ResearchInfoCommentService extends PmsBaseService<ResearchInfoCommentExt> {
    @Autowired
    private ResearchInfoCommentDao researchInfoCommentDao;

    @Autowired
    private ResearchInfoDao researchInfoDao;

    @Override
    public ResearchInfoCommentDao getDao() {
        return researchInfoCommentDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     * 
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(ResearchInfoCommentExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     * 
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(ResearchInfoCommentExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     * 
     * @param entity 实体对象
     */
    @Override
    protected ResearchInfoCommentExt saveBefore(ResearchInfoCommentExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     * 
     * @param entity 实体对象
     */
    @Override
    protected ResearchInfoCommentExt saveAfter(ResearchInfoCommentExt entity) {
        if (entity.isAddMode()) {
            ResearchInfoExt researchInfo = this.researchInfoDao.findOne(entity.getResearchInfoUid());
            //跟进次数
            researchInfo.setCommentCount(researchInfo.getCommentCount() + 1);
            //最近跟进UID
//            researchInfo.setLastFollowupUid(entity.getUid());
            this.researchInfoDao.save(researchInfo);
        }
        return entity;
    }

    /**
     * 数据删除前处理[子类覆盖]
     *
     * @param ids ID集合
     * @return 处理结果
     */
    @Override
    protected Boolean deleteBefore(String ids) {
        ResearchInfoExt researchInfo = this.researchInfoDao.findOne(this.findOne(ids).getResearchInfoUid());
        researchInfo.setCommentCount(researchInfo.getCommentCount() - 1);
        this.researchInfoDao.save(researchInfo);
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     * 
     * @param ids ID集合
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(String ids) {
        return true;
    }

    /**
     * 数据删除前处理[子类覆盖]
     * 
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteBefore(ResearchInfoCommentExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     * 
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(ResearchInfoCommentExt entity) {
        return true;
    }

    /**
     * 记录状态更新前处理[子类覆盖]
     * 
     * @param uid 主键UID
     * @param recordStatus 记录状态
     * 
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusBefore(String uid, String recordStatus) {
        return true;
    }

    /**
     * 记录状态更新后处理[子类覆盖]
     * 
     * @param uid 主键UID
     * @param recordStatus 记录状态
     * 
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusAfter(String uid, String recordStatus) {
        return true;
    }

    /**
     * 初始化下拉框选项内容
     *
     * @param model Model对象
     * @throws IllegalAccessException
     * @throws IOException
     */
    @Override
    public void initOptionList(Model model) throws IllegalAccessException, IOException {
        Map<String, Object> dicMap = new HashMap<>();

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }
}