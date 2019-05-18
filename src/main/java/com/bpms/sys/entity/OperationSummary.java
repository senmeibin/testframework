package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 操作日志汇总实体类
 */
@MappedSuperclass
public class OperationSummary extends SysBaseEntity {
    private static final long serialVersionUID = -20180424145158835L;

    /**
     * 企业UID
     */
    @Column(name = "enterprise_uid", length = 8)
    private Integer enterpriseUid;

    /**
     * 企业
     */
    @Transient
    private String enterpriseName;

    /**
     * 路径
     */
    @Length(max = 128, message = "请在路径中输入[0-128]位以内的文字。")
    @NotEmpty(message = "请输入路径。")
    @Column(name = "url", length = 128, nullable = false)
    private String url;

    /**
     * 访问次数
     */
    @Range(min = 0L, max = 99999999L, message = "请在访问次数中输入[0-99999999]范围内的数值。")
    @Column(name = "count")
    private Integer count;

    public OperationSummary() {
    }

    public Integer getEnterpriseUid() {
        return this.enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getEnterpriseName() {
        return this.enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}