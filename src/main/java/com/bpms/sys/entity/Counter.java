package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * 计数器管理实体类
 */
@MappedSuperclass
public class Counter extends SysBaseEntity {
    private static final long serialVersionUID = -20160923140753507L;

    /**
     * 企业UID
     */
    @Column(length = 8)
    private Integer enterpriseUid;

    /**
     * 计数器类型
     */
    @Length(max = 8, message = "请在计数器类型中输入[0-8]位以内的文字。")
    @NotEmpty(message = "请输入计数器类型。")
    @Column(length = 8, nullable = false)
    private String typeCd;

    /**
     * 计数器类型
     */
    @Transient
    private String typeName;

    /**
     * 分组条件1
     */
    @Length(max = 32, message = "请在分组条件1中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String groupCounter1;

    /**
     * 分组条件2
     */
    @Length(max = 32, message = "请在分组条件2中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String groupCounter2;

    /**
     * 分组条件3
     */
    @Length(max = 32, message = "请在分组条件3中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String groupCounter3;

    /**
     * 分组条件4
     */
    @Length(max = 32, message = "请在分组条件4中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String groupCounter4;

    /**
     * 分组条件5
     */
    @Length(max = 32, message = "请在分组条件5中输入[0-32]位以内的文字。")
    @Column(length = 32)
    private String groupCounter5;

    /**
     * 当前计数值
     */
    @Range(min = 0, max = 99999999, message = "请在当前计数值中输入[0-99999999]范围内的数值。")
    @NotNull(message = "请输入当前计数值。")
    @Column(nullable = false)
    private Integer counterNumber;

    public Counter() {
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    public String getTypeCd() {
        return this.typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getGroupCounter1() {
        return this.groupCounter1;
    }

    public void setGroupCounter1(String groupCounter1) {
        this.groupCounter1 = groupCounter1;
    }

    public String getGroupCounter2() {
        return this.groupCounter2;
    }

    public void setGroupCounter2(String groupCounter2) {
        this.groupCounter2 = groupCounter2;
    }

    public String getGroupCounter3() {
        return this.groupCounter3;
    }

    public void setGroupCounter3(String groupCounter3) {
        this.groupCounter3 = groupCounter3;
    }

    public String getGroupCounter4() {
        return this.groupCounter4;
    }

    public void setGroupCounter4(String groupCounter4) {
        this.groupCounter4 = groupCounter4;
    }

    public String getGroupCounter5() {
        return this.groupCounter5;
    }

    public void setGroupCounter5(String groupCounter5) {
        this.groupCounter5 = groupCounter5;
    }

    public Integer getCounterNumber() {
        return this.counterNumber;
    }

    public void setCounterNumber(Integer counterNumber) {
        this.counterNumber = counterNumber;
    }

}