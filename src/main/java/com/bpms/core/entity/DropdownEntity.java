package com.bpms.core.entity;

import java.io.Serializable;

public class DropdownEntity implements Serializable {
    private static final long serialVersionUID = -20160830111111119L;
    /**
     * 上级CD
     */
    private String mainCd;

    /**
     * 上级名称
     */
    private String mainName;

    /**
     * 上级CD:待删除
     */
    private String parentSubCd;

    /**
     * 上级名称:待删除
     */
    private String parentSubName;

    /**
     * 小区分CD
     */
    private String subCd;

    /**
     * 小区分名称
     */
    private String subName;


    public String getParentSubCd() {
        return parentSubCd;
    }

    public void setParentSubCd(String parentSubCd) {
        this.parentSubCd = parentSubCd;
    }

    public String getParentSubName() {
        return parentSubName;
    }

    public void setParentSubName(String parentSubName) {
        this.parentSubName = parentSubName;
    }

    public String getMainCd() {
        return mainCd;
    }

    public void setMainCd(String mainCd) {
        this.mainCd = mainCd;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getSubCd() {
        return subCd;
    }

    public void setSubCd(String subCd) {
        this.subCd = subCd;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }
}
