package com.bpms.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.utils.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 用户实体类
 */
@MappedSuperclass
public class User extends SysBaseEntity {
    private static final long serialVersionUID = -20160330184231056L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 部门UID
     */
    @Column(length = 32)
    private String deptUid;

    /**
     * 部门
     */
    @Transient
    private String deptName;

    /**
     * 职位UID
     */
    @Column(length = 32)
    private String positionUid;

    /**
     * 职位
     */
    @Transient
    private String positionName;

    /**
     * 用户名
     */
    @Length(max = 64, message = "请在用户名中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入用户名。")
    @Column(length = 64, nullable = false)
    private String userCd;

    /**
     * 密码
     */
    @Length(max = 256, message = "请在密码中输入[0-256]位以内的文字。")
    @Column(length = 256, nullable = false)
    private String password;

    /**
     * DES密码
     */
    @Length(max = 256, message = "请在DES密码中输入[0-256]位以内的文字。")
    @Column(length = 256, nullable = false)
    private String desPassword;

    /**
     * 工号
     */
    @Length(max = 32, message = "请在工号中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String userNumber;

    /**
     * 考勤号
     */
    @Length(max = 32, message = "请在考勤号中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String attendanceNo;

    /**
     * 用户名
     */
    @Length(max = 32, message = "请在姓名中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入姓名。")
    @Column(length = 32, nullable = false)
    private String userName;

    /**
     * 性别
     */
    @Length(max = 8, message = "请在性别中输入[0-8]位以内的文字。")
    @Column(length = 8)
    private String sexCd;

    /**
     * 性别
     */
    @Transient
    private String sexName;

    /**
     * 出生日期
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date birthday;

    /**
     * 英文名
     */
    @Length(max = 32, message = "请在英文名中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String userEnName;

    /**
     * 邮箱地址
     */
    @Length(max = 64, message = "请在邮件地址中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String userMail;

    /**
     * 手机号码
     */
    @Length(max = 32, message = "请在手机号码中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String userPhone;

    /**
     * 传真号码
     */
    @Length(max = 32, message = "请在传真号码中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String userFaxphone;

    /**
     * 分机号码
     */
    @Length(max = 32, message = "请在分机号码中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String userExtensionNumber;

    /**
     * 家庭地址
     */
    @Length(max = 128, message = "请在家庭地址中输入[0-128]位以内的文字。")
    @Column(length = 128)
    private String userAddress;

    /**
     * 邮政编码
     */
    @Length(max = 6, message = "请在邮政编码中输入[0-6]位以内的文字。")
    @Column(length = 6)
    private String userZipcode;

    /**
     * 强制密码变更
     */
    @Range(min = 0, max = 99999, message = "请在强制密码变更中输入[0-99999]范围内的数值。")
    @Column
    private Integer forceChangePswd;

    /**
     * 密码变更时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date changePswdDatetime;

    /**
     * 账号锁定标志
     */
    @Range(min = 0, max = 99999, message = "请在账号锁定标志中输入[0-99999]范围内的数值。")
    @Column
    private Integer accountLock = 0;

    /**
     * 登录许可开始时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date startLoginTime;

    /**
     * 登录许可结束时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date endLoginTime;

    /**
     * 入职日期
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date entryDate;

    /**
     * 退职日期
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date retireDate;

    /**
     * 系统注册
     */
    @Range(min = 0, max = 99999, message = "请在系统注册中输入[0-99999]范围内的数值。")
    @Column
    private Integer regSystem = 0;

    /**
     * 可访问IP
     */
    @Length(max = 512, message = "请在可访问IP中输入[0-512]位以内的文字。")
    @Column(length = 512)
    private String accessIp;

    /**
     * 登陆次数
     */
    @Range(min = 0, max = 99999, message = "请在登陆次数中输入[0-99999]范围内的数值。")
    @Column
    private Integer loginCount = 0;

    /**
     * 最近登录时间
     */
    @Column
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date lastLoginTime;

    /**
     * 个人主题
     */
    @Length(max = 128, message = "请在个人主题中输入[0-32]位以内的文字。")
    @Column(length = 128)
    private String themeName;

    /**
     * 用户坐席
     */
    @Length(max = 32, message = "请在用户坐席输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String userAgent;

    /**
     * 用户坐席密码
     */
    @Length(max = 32, message = "请在用户坐席密码中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String userAgentPassword;

    /**
     * 划入时间
     */
    @Column(name = "shift_date")
    @DateTimeFormat(pattern = CmnConsts.DATE_FORMAT)
    @JsonFormat(pattern = CmnConsts.DATE_FORMAT, timezone = "GMT+8")
    private Date shiftDate;

    public User() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getDeptUid() {
        return this.deptUid;
    }

    public void setDeptUid(String deptUid) {
        this.deptUid = deptUid;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPositionUid() {
        return this.positionUid;
    }

    public void setPositionUid(String positionUid) {
        this.positionUid = positionUid;
    }

    public String getPositionName() {
        return this.positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getUserCd() {
        return this.userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesPassword() {
        return desPassword;
    }

    public void setDesPassword(String desPassword) {
        this.desPassword = desPassword;
    }

    public String getUserNumber() {
        return this.userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getAttendanceNo() {
        return attendanceNo;
    }

    public void setAttendanceNo(String attendanceNo) {
        this.attendanceNo = attendanceNo;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSexCd() {
        return this.sexCd;
    }

    public void setSexCd(String sexCd) {
        this.sexCd = sexCd;
    }

    public String getSexName() {
        return this.sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getUserEnName() {
        return this.userEnName;
    }

    public void setUserEnName(String userEnName) {
        this.userEnName = userEnName;
    }

    public String getUserMail() {
        return this.userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPhone() {
        return this.userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserFaxphone() {
        return this.userFaxphone;
    }

    public void setUserFaxphone(String userFaxphone) {
        this.userFaxphone = userFaxphone;
    }

    public String getUserExtensionNumber() {
        return this.userExtensionNumber;
    }

    public void setUserExtensionNumber(String userExtensionNumber) {
        this.userExtensionNumber = userExtensionNumber;
    }

    public String getUserAddress() {
        return this.userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserZipcode() {
        return this.userZipcode;
    }

    public void setUserZipcode(String userZipcode) {
        this.userZipcode = userZipcode;
    }

    public Integer getForceChangePswd() {
        return this.forceChangePswd;
    }

    public void setForceChangePswd(Integer forceChangePswd) {
        this.forceChangePswd = forceChangePswd;
    }

    public Date getChangePswdDatetime() {
        return this.changePswdDatetime;
    }

    public void setChangePswdDatetime(Date changePswdDatetime) {
        this.changePswdDatetime = changePswdDatetime;
    }

    public Integer getAccountLock() {
        return this.accountLock;
    }

    public void setAccountLock(Integer accountLock) {
        this.accountLock = accountLock;
    }

    public Date getStartLoginTime() {
        return this.startLoginTime;
    }

    public void setStartLoginTime(Date startLoginTime) {
        this.startLoginTime = startLoginTime;
    }

    public Date getEndLoginTime() {
        return this.endLoginTime;
    }

    public void setEndLoginTime(Date endLoginTime) {
        this.endLoginTime = endLoginTime;
    }

    public Date getEntryDate() {
        return this.entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getRetireDate() {
        return this.retireDate;
    }

    public void setRetireDate(Date retireDate) {
        this.retireDate = retireDate;
    }

    public Integer getRegSystem() {
        return this.regSystem;
    }

    public void setRegSystem(Integer regSystem) {
        this.regSystem = regSystem;
    }

    public String getAccessIp() {
        return this.accessIp;
    }

    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getThemeName() {
        return StringUtils.isEmpty(themeName) ? "theme-black" : this.themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserAgentPassword() {
        return userAgentPassword;
    }

    public void setUserAgentPassword(String userAgentPassword) {
        this.userAgentPassword = userAgentPassword;
    }

    public Date getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(Date shiftDate) {
        this.shiftDate = shiftDate;
    }
}