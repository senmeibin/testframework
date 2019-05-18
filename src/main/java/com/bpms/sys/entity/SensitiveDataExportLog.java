package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 敏感数据导出日志实体类
 */
@MappedSuperclass
public class SensitiveDataExportLog extends SysBaseEntity {
    private static final long serialVersionUID = -20180313084115701L;

    /**
     * 手机号码
     */
    @Length(max = 16, message = "请在手机号码中输入[0-16]位以内的文字。")
    @Column(name = "mobile", length = 16)
    private String mobile;

    /**
     * 邮箱地址
     */
    @Length(max = 64, message = "请在邮箱地址中输入[0-64]位以内的文字。")
    @Column(name = "email", length = 64)
    private String email;

    /**
     * 敏感数据1
     */
    @Length(max = 32, message = "请在敏感数据1中输入[0-32]位以内的文字。")
    @Column(name = "sensitive_data1", length = 32)
    private String sensitiveData1;

    /**
     * 敏感数据2
     */
    @Length(max = 32, message = "请在敏感数据2中输入[0-32]位以内的文字。")
    @Column(name = "sensitive_data2", length = 32)
    private String sensitiveData2;

    /**
     * 敏感数据3
     */
    @Length(max = 32, message = "请在敏感数据3中输入[0-32]位以内的文字。")
    @Column(name = "sensitive_data3", length = 32)
    private String sensitiveData3;

    /**
     * 敏感数据4
     */
    @Length(max = 32, message = "请在敏感数据4中输入[0-32]位以内的文字。")
    @Column(name = "sensitive_data4", length = 32)
    private String sensitiveData4;

    /**
     * 敏感数据5
     */
    @Length(max = 32, message = "请在敏感数据5中输入[0-32]位以内的文字。")
    @Column(name = "sensitive_data5", length = 32)
    private String sensitiveData5;

    /**
     * 敏感数据6
     */
    @Length(max = 32, message = "请在敏感数据6中输入[0-32]位以内的文字。")
    @Column(name = "sensitive_data6", length = 32)
    private String sensitiveData6;

    /**
     * 敏感数据7
     */
    @Length(max = 32, message = "请在敏感数据7中输入[0-32]位以内的文字。")
    @Column(name = "sensitive_data7", length = 32)
    private String sensitiveData7;

    /**
     * 敏感数据8
     */
    @Length(max = 32, message = "请在敏感数据8中输入[0-32]位以内的文字。")
    @Column(name = "sensitive_data8", length = 32)
    private String sensitiveData8;

    /**
     * 敏感数据9
     */
    @Length(max = 32, message = "请在敏感数据9中输入[0-32]位以内的文字。")
    @Column(name = "sensitive_data9", length = 32)
    private String sensitiveData9;

    /**
     * 敏感数据10
     */
    @Length(max = 32, message = "请在敏感数据10中输入[0-32]位以内的文字。")
    @Column(name = "sensitive_data10", length = 32)
    private String sensitiveData10;

    /**
     * 路径
     */
    @Length(max = 512, message = "请在路径中输入[0-512]位以内的文字。")
    @Column(name = "url", length = 512)
    private String url;

    /**
     * 远程IP
     */
    @Length(max = 32, message = "请在远程IP中输入[0-32]位以内的文字。")
    @Column(name = "remote_ip", length = 32)
    private String remoteIp;

    public SensitiveDataExportLog() {
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSensitiveData1() {
        return this.sensitiveData1;
    }

    public void setSensitiveData1(String sensitiveData1) {
        this.sensitiveData1 = sensitiveData1;
    }

    public String getSensitiveData2() {
        return this.sensitiveData2;
    }

    public void setSensitiveData2(String sensitiveData2) {
        this.sensitiveData2 = sensitiveData2;
    }

    public String getSensitiveData3() {
        return this.sensitiveData3;
    }

    public void setSensitiveData3(String sensitiveData3) {
        this.sensitiveData3 = sensitiveData3;
    }

    public String getSensitiveData4() {
        return this.sensitiveData4;
    }

    public void setSensitiveData4(String sensitiveData4) {
        this.sensitiveData4 = sensitiveData4;
    }

    public String getSensitiveData5() {
        return this.sensitiveData5;
    }

    public void setSensitiveData5(String sensitiveData5) {
        this.sensitiveData5 = sensitiveData5;
    }

    public String getSensitiveData6() {
        return this.sensitiveData6;
    }

    public void setSensitiveData6(String sensitiveData6) {
        this.sensitiveData6 = sensitiveData6;
    }

    public String getSensitiveData7() {
        return this.sensitiveData7;
    }

    public void setSensitiveData7(String sensitiveData7) {
        this.sensitiveData7 = sensitiveData7;
    }

    public String getSensitiveData8() {
        return this.sensitiveData8;
    }

    public void setSensitiveData8(String sensitiveData8) {
        this.sensitiveData8 = sensitiveData8;
    }

    public String getSensitiveData9() {
        return this.sensitiveData9;
    }

    public void setSensitiveData9(String sensitiveData9) {
        this.sensitiveData9 = sensitiveData9;
    }

    public String getSensitiveData10() {
        return this.sensitiveData10;
    }

    public void setSensitiveData10(String sensitiveData10) {
        this.sensitiveData10 = sensitiveData10;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemoteIp() {
        return this.remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

}