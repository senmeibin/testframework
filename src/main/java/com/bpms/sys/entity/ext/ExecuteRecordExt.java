package com.bpms.sys.entity.ext;

import com.bpms.sys.consts.SysConsts;
import com.bpms.sys.entity.ExecuteRecord;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name = "sys_execute_record")
public class ExecuteRecordExt extends ExecuteRecord {
    private static final long serialVersionUID = -20161114130550613L;
    /**
     * 锁定成功标志位（true：任务锁定成功）
     */
    @Transient
    private boolean lockSuccess = false;
    /**
     * 上次任务执行开始日期
     * （注：【最后执行开始时间】被改写前的备份时间）
     */
    @Transient
    private Date previousExecuteStartDate;

    /**
     * 设置业务数据最后新增时间/更新时间/更新UID相关信息
     *
     * @param uid                 手动执行时的唯一UID
     * @param insertDate          业务数据最后新增时间
     * @param updateDate          业务数据最后更新时间
     * @param lastRecordUpdateUid 业务数据最后更新UID
     */
    public void setLastExecuteRecordInfo(String uid, Date insertDate, Date updateDate, String lastRecordUpdateUid) {
        if (StringUtils.isEmpty(uid)) {
            //自动任务时需要更新字段信息
            this.setLastRecordInsertDate(insertDate);
            this.setLastRecordUpdateDate(updateDate);
            this.setLastRecordUpdateUid(lastRecordUpdateUid);
        }
    }

    public boolean isLockSuccess() {
        return lockSuccess;
    }

    public void setLockSuccess(boolean lockSuccess) {
        this.lockSuccess = lockSuccess;
    }

    public Date getPreviousExecuteStartDate() {
        return previousExecuteStartDate;
    }

    public void setPreviousExecuteStartDate(Date previousExecuteStartDate) {
        this.previousExecuteStartDate = previousExecuteStartDate;
    }

    /**
     * 是否执行中？
     *
     * @return true:执行中
     */
    public boolean isRunning() {
        if (StringUtils.equals(SysConsts.TASK_STATUS_RUNNING, this.getFunctionStatusCd())) {
            return true;
        }
        return false;
    }

    /**
     * 是否已停止？
     *
     * @return true:已停止
     */
    public boolean isStopped() {
        if (StringUtils.equals(SysConsts.TASK_STATUS_STOPPED, this.getFunctionStatusCd())) {
            return true;
        }
        return false;
    }

    /**
     * 是否待执行？
     *
     * @return true:待执行
     */
    public boolean isWaiting() {
        if (StringUtils.equals(SysConsts.TASK_STATUS_WAITING, this.getFunctionStatusCd())) {
            return true;
        }
        return false;
    }
}