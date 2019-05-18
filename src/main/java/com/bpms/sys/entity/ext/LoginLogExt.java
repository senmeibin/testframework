package com.bpms.sys.entity.ext;

import com.bpms.core.consts.CmnConsts;
import com.bpms.sys.entity.LoginLog;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sys_login_log")
public class LoginLogExt extends LoginLog {
    private static final long serialVersionUID = -20160422113548836L;

    /**
     * 用户所属大区
     */
    @Transient
    private String areaName;

    /**
     * 用户所属分公司
     */
    @Transient
    private String companyName;

    /**
     * 用户所属部门
     */
    @Transient
    private String deptName;

    /**
     * 用户职位
     */
    @Transient
    private String positionName;

    /**
     * 联系方式
     */
    @Transient
    private String userPhone;
    /**
     * 登录类型
     */
    @Transient
    private String loginTypeName;

    /**
     * 登录结果
     */
    @Transient
    private String loginResultName;

    /**
     * 登录次数
     */
    @Transient
    private Integer loginTimes;

    /**
     * 登录天数
     */
    @Transient
    private Integer loginDays;


    public LoginLogExt() {
        this.setRecordStatus(Integer.valueOf(CmnConsts.RECORD_STATUS_ACTIVE));
    }

    public LoginLogExt(String userUid, String userCd, String userName, String ip, Integer type, Integer result) {
        this.setUserUid(userUid);
        this.setUserCd(userCd);
        this.setUserName(userName);
        this.setRemoteIp(ip);
        this.setLoginType(type);
        this.setLoginResult(result);
        this.setRecordStatus(Integer.valueOf(CmnConsts.RECORD_STATUS_ACTIVE));
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Integer getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(Integer loginTimes) {
        this.loginTimes = loginTimes;
    }

    public Integer getLoginDays() {
        return loginDays;
    }

    public void setLoginDays(Integer loginDays) {
        this.loginDays = loginDays;
    }

    public String getLoginTypeName() {
        return loginTypeName;
    }

    public void setLoginTypeName(String loginTypeName) {
        this.loginTypeName = loginTypeName;
    }

    public String getLoginResultName() {
        return loginResultName;
    }

    public void setLoginResultName(String loginResultName) {
        this.loginResultName = loginResultName;
    }
}