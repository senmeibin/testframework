package com.bpms.sys.dao;

import com.bpms.core.consts.CmnConsts;
import com.bpms.core.dao.BaseDao;
import com.bpms.sys.entity.ext.DeptExt;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 部门数据访问类
 */
public interface DeptDao extends BaseDao<DeptExt, String> {
    /**
     * 查找所有子部门
     *
     * @param uid 部门UID
     * @return 部门列表
     */
    @Query("SELECT e FROM DeptExt e WHERE e.parentDeptUid = ?1 AND e.recordStatus = " + CmnConsts.RECORD_STATUS_ACTIVE + " ORDER BY e.dispSeq ASC ")
    List<DeptExt> findByParentDeptUid(String uid);
}