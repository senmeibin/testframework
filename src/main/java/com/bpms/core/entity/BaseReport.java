package com.bpms.core.entity;

import java.math.BigDecimal;

/**
 * 报表基础Entity
 */
public class BaseReport extends BaseEntity {
    /**
     * 所属大区UID
     */
    private String areaUid;

    /**
     * 所属大区名称
     */
    private String areaName;

    /**
     * 所属分公司UID
     */
    private String companyUid;

    /**
     * 所属分公司名称
     */
    private String companyName;

    /**
     * 所属部门UID
     */
    private String deptUid;

    /**
     * 所属部门名称
     */
    private String deptName;


    /**
     * 执行所属大区UID
     */
    private String executeAreaUid;

    /**
     * 执行所属大区名称
     */
    private String executeAreaName;

    /**
     * 执行所属分公司UID
     */
    private String executeCompanyUid;

    /**
     * 执行所属分公司名称
     */
    private String executeCompanyName;

    /**
     * 执行所属部门UID
     */
    private String executeDeptUid;

    /**
     * 执行所属部门名称
     */
    private String executeDeptName;

    /**
     * 分类名称[通用ECharts图表专用属性]
     */
    private String category;

    /**
     * 系列值1[通用ECharts图表专用属性]
     */
    private BigDecimal seriesValue1;

    /**
     * 系列值2[通用ECharts图表专用属性]
     */
    private BigDecimal seriesValue2;

    /**
     * 系列值3[通用ECharts图表专用属性]
     */
    private BigDecimal seriesValue3;

    /**
     * 系列值4[通用ECharts图表专用属性]
     */
    private BigDecimal seriesValue4;

    /**
     * 系列值5[通用ECharts图表专用属性]
     */
    private BigDecimal seriesValue5;

    /**
     * 系列值6[通用ECharts图表专用属性]
     */
    private BigDecimal seriesValue6;

    /**
     * 系列值7[通用ECharts图表专用属性]
     */
    private BigDecimal seriesValue7;

    /**
     * 系列值8[通用ECharts图表专用属性]
     */
    private BigDecimal seriesValue8;

    /**
     * 系列值9[通用ECharts图表专用属性]
     */
    private BigDecimal seriesValue9;

    /**
     * 系列值10[通用ECharts图表专用属性]
     */
    private BigDecimal seriesValue10;

    /**
     * 系列值1占比[通用ECharts图表专用属性]
     */
    private Double seriesRate1 = 0.0;

    /**
     * 系列值2占比[通用ECharts图表专用属性]
     */
    private Double seriesRate2 = 0.0;

    /**
     * 系列值3占比[通用ECharts图表专用属性]
     */
    private Double seriesRate3 = 0.0;

    /**
     * 系列值4占比[通用ECharts图表专用属性]
     */
    private Double seriesRate4 = 0.0;

    /**
     * 系列值5占比[通用ECharts图表专用属性]
     */
    private Double seriesRate5 = 0.0;

    /**
     * 系列值6占比[通用ECharts图表专用属性]
     */
    private Double seriesRate6 = 0.0;

    /**
     * 系列值7占比[通用ECharts图表专用属性]
     */
    private Double seriesRate7 = 0.0;

    /**
     * 系列值8占比[通用ECharts图表专用属性]
     */
    private Double seriesRate8 = 0.0;

    /**
     * 系列值9占比[通用ECharts图表专用属性]
     */
    private Double seriesRate9 = 0.0;

    /**
     * 系列值10占比[通用ECharts图表专用属性]
     */
    private Double seriesRate10 = 0.0;

    public String getAreaUid() {
        return areaUid;
    }

    public void setAreaUid(String areaUid) {
        this.areaUid = areaUid;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCompanyUid() {
        return companyUid;
    }

    public void setCompanyUid(String companyUid) {
        this.companyUid = companyUid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDeptUid() {
        return deptUid;
    }

    public void setDeptUid(String deptUid) {
        this.deptUid = deptUid;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getExecuteAreaUid() {
        return executeAreaUid;
    }

    public void setExecuteAreaUid(String executeAreaUid) {
        this.executeAreaUid = executeAreaUid;
    }

    public String getExecuteAreaName() {
        return executeAreaName;
    }

    public void setExecuteAreaName(String executeAreaName) {
        this.executeAreaName = executeAreaName;
    }

    public String getExecuteCompanyUid() {
        return executeCompanyUid;
    }

    public void setExecuteCompanyUid(String executeCompanyUid) {
        this.executeCompanyUid = executeCompanyUid;
    }

    public String getExecuteCompanyName() {
        return executeCompanyName;
    }

    public void setExecuteCompanyName(String executeCompanyName) {
        this.executeCompanyName = executeCompanyName;
    }

    public String getExecuteDeptUid() {
        return executeDeptUid;
    }

    public void setExecuteDeptUid(String executeDeptUid) {
        this.executeDeptUid = executeDeptUid;
    }

    public String getExecuteDeptName() {
        return executeDeptName;
    }

    public void setExecuteDeptName(String executeDeptName) {
        this.executeDeptName = executeDeptName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getSeriesValue1() {
        return seriesValue1;
    }

    public void setSeriesValue1(BigDecimal seriesValue1) {
        this.seriesValue1 = seriesValue1;
    }

    public BigDecimal getSeriesValue2() {
        return seriesValue2;
    }

    public void setSeriesValue2(BigDecimal seriesValue2) {
        this.seriesValue2 = seriesValue2;
    }

    public BigDecimal getSeriesValue3() {
        return seriesValue3;
    }

    public void setSeriesValue3(BigDecimal seriesValue3) {
        this.seriesValue3 = seriesValue3;
    }

    public BigDecimal getSeriesValue4() {
        return seriesValue4;
    }

    public void setSeriesValue4(BigDecimal seriesValue4) {
        this.seriesValue4 = seriesValue4;
    }

    public BigDecimal getSeriesValue5() {
        return seriesValue5;
    }

    public void setSeriesValue5(BigDecimal seriesValue5) {
        this.seriesValue5 = seriesValue5;
    }

    public Double getSeriesRate1() {
        return seriesRate1;
    }

    public void setSeriesRate1(Double seriesRate1) {
        this.seriesRate1 = seriesRate1;
    }

    public Double getSeriesRate2() {
        return seriesRate2;
    }

    public void setSeriesRate2(Double seriesRate2) {
        this.seriesRate2 = seriesRate2;
    }

    public Double getSeriesRate3() {
        return seriesRate3;
    }

    public void setSeriesRate3(Double seriesRate3) {
        this.seriesRate3 = seriesRate3;
    }

    public Double getSeriesRate4() {
        return seriesRate4;
    }

    public void setSeriesRate4(Double seriesRate4) {
        this.seriesRate4 = seriesRate4;
    }

    public Double getSeriesRate5() {
        return seriesRate5;
    }

    public void setSeriesRate5(Double seriesRate5) {
        this.seriesRate5 = seriesRate5;
    }

    public BigDecimal getSeriesValue6() {
        return seriesValue6;
    }

    public void setSeriesValue6(BigDecimal seriesValue6) {
        this.seriesValue6 = seriesValue6;
    }

    public BigDecimal getSeriesValue7() {
        return seriesValue7;
    }

    public void setSeriesValue7(BigDecimal seriesValue7) {
        this.seriesValue7 = seriesValue7;
    }

    public BigDecimal getSeriesValue8() {
        return seriesValue8;
    }

    public void setSeriesValue8(BigDecimal seriesValue8) {
        this.seriesValue8 = seriesValue8;
    }

    public BigDecimal getSeriesValue9() {
        return seriesValue9;
    }

    public void setSeriesValue9(BigDecimal seriesValue9) {
        this.seriesValue9 = seriesValue9;
    }

    public BigDecimal getSeriesValue10() {
        return seriesValue10;
    }

    public void setSeriesValue10(BigDecimal seriesValue10) {
        this.seriesValue10 = seriesValue10;
    }

    public Double getSeriesRate6() {
        return seriesRate6;
    }

    public void setSeriesRate6(Double seriesRate6) {
        this.seriesRate6 = seriesRate6;
    }

    public Double getSeriesRate7() {
        return seriesRate7;
    }

    public void setSeriesRate7(Double seriesRate7) {
        this.seriesRate7 = seriesRate7;
    }

    public Double getSeriesRate8() {
        return seriesRate8;
    }

    public void setSeriesRate8(Double seriesRate8) {
        this.seriesRate8 = seriesRate8;
    }

    public Double getSeriesRate9() {
        return seriesRate9;
    }

    public void setSeriesRate9(Double seriesRate9) {
        this.seriesRate9 = seriesRate9;
    }

    public Double getSeriesRate10() {
        return seriesRate10;
    }

    public void setSeriesRate10(Double seriesRate10) {
        this.seriesRate10 = seriesRate10;
    }
}
