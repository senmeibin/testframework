package com.bpms.sys.service;

import com.bpms.core.entity.DropdownEntity;
import com.bpms.core.exception.ServiceValidationException;
import com.bpms.sys.dao.DictionaryDao;
import com.bpms.sys.entity.ext.DictionaryExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典服务类
 */
@Service
public class DictionaryService extends SysBaseService<DictionaryExt> {
    /**
     * 缓存KEY前缀
     */
    public static final String CACHE_PREFIX_KEY = "DICTIONARY_CACHE";

    @Autowired
    private DictionaryDao dictionaryDao;

    @Override
    public DictionaryDao getDao() {
        return dictionaryDao;
    }

    /**
     * 根据大区分CD与小区分CD查询
     *
     * @param dictionary 实体对象
     * @return 实体对象[未查询到数据的场合，NULL返回]
     */
    public DictionaryExt findByMainCdAndSubCd(DictionaryExt dictionary) {
        return dictionaryDao.findByMainCdAndSubCd(dictionary.getMainCd(), dictionary.getSubCd());
    }

    /**
     * 根据大区分CD取得字典列表数据
     *
     * @param mainCd 大区分CD
     * @return 字典列表数据
     */
    @Transactional(readOnly = true)
    public List<DropdownEntity> getDictionaryList(String mainCd) {
        String cacheKey = String.format("%s_%s", CACHE_PREFIX_KEY, mainCd);
        List<DropdownEntity> value = redisCacheManager.getList(DropdownEntity.class, cacheKey);

        if (value == null) {
            Map<String, Object> condition = new HashMap<>();

            //大区分CD
            condition.put("main.main_cd", mainCd);
            PageRequest pageRequest = new PageRequest(0, Integer.MAX_VALUE, new Sort(Sort.Direction.ASC, "dispSeq"));
            //查询后返回字典列表数据
            value = this.getDao().search(DropdownEntity.class, this.getSearchSQL(condition), condition, pageRequest).getContent();

            redisCacheManager.set(cacheKey, value);
        }
        return value;
    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected DictionaryExt saveBefore(DictionaryExt entity) {
        //小区分CD重复性校验(有效的)
        if (this.isDuplication("uid", entity.getUid(), "mainCd", entity.getMainCd(), "subCd", entity.getSubCd(), true)) {
            throw new ServiceValidationException(String.format("指定大区分CD(%s)中已存在小区分CD(%s)，请重新输入。", entity.getMainCd(), entity.getSubCd()));
        }

        //小区分CD重复性校验(删除的)
        if (this.isDuplication("uid", entity.getUid(), "mainCd", entity.getMainCd(), "subCd", entity.getSubCd(), false)) {
            throw new ServiceValidationException(String.format("指定大区分CD(%s)中已存在被删除的小区分CD(%s)，<br/>请与系统管理员联系。", entity.getMainCd(), entity.getSubCd()));
        }

        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected DictionaryExt saveAfter(DictionaryExt entity) {
        //删除缓存数据
        this.deleteCache(entity);

        return entity;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param ids ID集合
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(String ids) {
        String[] uids = ids.split(",");

        // 循环删除缓存
        for (String uid : uids) {
            this.deleteCache(this.findOne(ids));
        }

        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(DictionaryExt entity) {
        //删除缓存数据
        this.deleteCache(entity);

        return true;
    }

    /**
     * 记录状态更新后处理[子类覆盖]
     *
     * @param uid          主键UID
     * @param recordStatus 记录状态
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusAfter(String uid, String recordStatus) {
        //删除缓存数据
        this.deleteCache(this.findOne(uid));

        return true;
    }

    /**
     * 删除缓存数据
     *
     * @param entity 实体对象
     */
    private void deleteCache(DictionaryExt entity) {
        String cacheKey = String.format("%s_%s", CACHE_PREFIX_KEY, entity.getMainCd());

        //删除redis数据
        redisCacheManager.delete(cacheKey);
    }

    /**
     * 根据mainCd和subCd取得subName
     */
    public String getSubNameByMainCdAndSubCd(String mainCd, String subCd) {
        DictionaryExt dictionary = dictionaryDao.findByMainCdAndSubCd(mainCd, subCd);
        return dictionary != null ? dictionary.getSubName() : null;
    }
}