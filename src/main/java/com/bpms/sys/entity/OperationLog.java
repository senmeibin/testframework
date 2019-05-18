package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 日志实体类
 */
@MappedSuperclass
public class OperationLog extends SysBaseEntity {
    private static final long serialVersionUID = -20160414152527168L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 用户UID
     */
    @Column(length = 64, nullable = false)
    @NotEmpty(message = "请选择用户。")
    private String userUid;

    /**
     * 用户名
     */
    @Length(max = 64, message = "请在用户名中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入用户名。")
    @Column(length = 64, nullable = false)
    private String userCd;

    /**
     * 用户名称
     */
    @Length(max = 32, message = "请在姓名中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入姓名。")
    @Column(length = 32, nullable = false)
    private String userName;

    /**
     * 应用编号
     */
    @Length(max = 16, message = "请在应用编号中输入[0-16]位以内的文字。")
    @NotEmpty(message = "请输入应用编号。")
    @Column(length = 16, nullable = false)
    private String appCode;

    /**
     * 模块名
     */
    @Length(max = 32, message = "请在模块名中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String moduleName;

    /**
     * 方法名
     */
    @Length(max = 64, message = "请在方法名中输入[0-64]位以内的文字。")
    @Column(length = 64)
    private String functionName;

    /**
     * 参数
     */
    @Column
    private String parameters;

    /**
     * 路径
     */
    @Length(max = 128, message = "请在路径中输入[0-128]位以内的文字。")
    @Column(length = 128)
    private String url;

    /**
     * 日志来源
     */
    @Length(max = 32, message = "请在日志来源中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String logSource;

    /**
     * 日志类型
     */
    @Length(max = 32, message = "请在日志类型中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String logType;

    public OperationLog() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAppCode() {
        return this.appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getParameters() {
        return this.parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogSource() {
        return this.logSource;
    }

    public void setLogSource(String logSource) {
        this.logSource = logSource;
    }

    public String getLogType() {
        return this.logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

}