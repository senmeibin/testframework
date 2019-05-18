package com.bpms.sys.service;

import com.bpms.core.utils.StringUtils;
import com.bpms.sys.dao.UselessDataTableDao;
import com.bpms.sys.entity.ext.UselessDataTableExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

/**
 * 无用数据表服务类
 */
@Service
public class UselessDataTableService extends SysBaseService<UselessDataTableExt> {
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UselessDataTableDao uselessDataTableDao;

    @Override
    public UselessDataTableDao getDao() {
        return uselessDataTableDao;
    }

    /**
     * 分页查询
     *
     * @param cls         实体类
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param pageRequest 分页排序对象
     * @return 分页实体对象列表
     */
    @Override
    public Page<UselessDataTableExt> search(Class<UselessDataTableExt> cls, String sql, Map<String, Object> condition, PageRequest pageRequest) {
        return this.initUselessDataTable(super.search(cls, sql, condition, pageRequest), pageRequest);
    }

    /**
     * 初始数据
     *
     * @param list        实体类列表
     * @param pageRequest 分页排序对象
     * @return 分页实体对象列表
     */
    private Page<UselessDataTableExt> initUselessDataTable(Page<UselessDataTableExt> list, PageRequest pageRequest) {
        for (UselessDataTableExt entity : list) {
            //没有record_status字段的场合
            if (!this.uselessDataTableDao.fieldExistsInTable(entity.getTableName(), "record_status")) {
                entity.setLogicDeleteRecordCount(0);
                continue;
            }

            BigInteger logicDelRecordCount = new BigInteger("0");
            //获取逻辑删除的条数
            List resultList = this.uselessDataTableDao.getResultList(String.format("SELECT COUNT(*) as recordCount FROM %s WHERE record_status = %s", entity.getTableName(), 9));
            if (resultList.size() > 0) {
                logicDelRecordCount = (BigInteger) ((Map) resultList.get(0)).get("recordCount");
            }
            //逻辑删除记录数
            entity.setLogicDeleteRecordCount(logicDelRecordCount.intValue());
        }

        //逻辑删除记录数排序功能
        for (Sort.Order order : pageRequest.getSort()) {
            if (StringUtils.equals(order.getProperty(), "logicDeleteRecordCount") || StringUtils.equals(order.getProperty(), "uid")) {
                boolean desc = Sort.Direction.DESC.equals(order.getDirection());
                //转换一个新的List排序
                List<UselessDataTableExt> sortList = new ArrayList<>();
                for (UselessDataTableExt entity : list) {
                    sortList.add(entity);
                }
                //按照逻辑删除记录数排序
                Collections.sort(sortList, new Comparator<UselessDataTableExt>() {
                    @Override
                    public int compare(UselessDataTableExt uselessDataTableExt1, UselessDataTableExt uselessDataTableExt2) {
                        if (desc) {
                            return uselessDataTableExt2.getLogicDeleteRecordCount() - uselessDataTableExt1.getLogicDeleteRecordCount();
                        }
                        return uselessDataTableExt1.getLogicDeleteRecordCount() - uselessDataTableExt2.getLogicDeleteRecordCount();
                    }
                });
                //替换原list
                return new PageImpl<>(sortList, pageRequest, list.getTotalElements());
            }
        }

        return list;
    }

    /**
     * 删除已逻辑删除的数据
     *
     * @param tableName 表名
     * @return 删除记录数
     */
    public int uselessDataDelete(String tableName) {
        int result = 0;
        String sql = String.format("DELETE FROM %s WHERE record_status = '9'", tableName);
        try {
            result = this.uselessDataTableDao.executeUpdate(sql, null);
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("忽略表{}，表不存在或者record_status字段不存在", tableName);
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("删除已逻辑删除的数据，表名：{}记录数：{}", tableName, result);
        }
        return result;
    }

    /**
     * 删除已逻辑删除的数据
     */
    public void uselessDataDelete() {
        //获取数据库下所有的表
        List<String> tables = this.uselessDataTableDao.getAllTableNames();
        for (String table : tables) {
            this.uselessDataDelete(table);
        }
    }
}