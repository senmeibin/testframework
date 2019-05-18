package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * 数据字典实体类
 */
@MappedSuperclass
public class Dictionary extends SysBaseEntity {
    private static final long serialVersionUID = -20160331132445250L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 大区分CD
     */
    @Length(max = 8, message = "请在大区分CD中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入大区分CD。")
    @Column(length = 8, nullable = false)
    private String mainCd;

    /**
     * 大区分名称
     */
    @Length(max = 64, message = "请在大区分名称中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入大区分名称。")
    @Column(length = 64, nullable = false)
    private String mainName;

    /**
     * 小区分CD
     */
    @Length(max = 32, message = "请在小区分CD中输入[0-32]位以内的文字。")
    @NotEmpty(message = "请输入小区分CD。")
    @Column(length = 32, nullable = false)
    private String subCd;

    /**
     * 小区分名称
     */
    @Length(max = 64, message = "请在小区分名称中输入[0-64]位以内的文字。")
    @NotEmpty(message = "请输入小区分名称。")
    @Column(length = 64, nullable = false)
    private String subName;

    /**
     * 表示顺序
     */
    @Range(min = 0, max = 99999, message = "请在表示顺序中输入[0-99999]范围内的数值。")
    @NotNull(message = "请输入表示顺序。")
    @Column(nullable = false)
    private Integer dispSeq;

    public Dictionary() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getMainCd() {
        return this.mainCd;
    }

    public void setMainCd(String mainCd) {
        this.mainCd = mainCd;
    }

    public String getMainName() {
        return this.mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getSubCd() {
        return this.subCd;
    }

    public void setSubCd(String subCd) {
        this.subCd = subCd;
    }

    public String getSubName() {
        return this.subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Integer getDispSeq() {
        return this.dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
    }

}