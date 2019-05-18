package com.bpms.demo.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * 专利版权实体类
 */
@MappedSuperclass
public class CompanyCopyright extends DemoBaseEntity {
    private static final long serialVersionUID = -20170511162516479L;

    /**
     * 企业UID
     */
    @Column(length = 32, nullable = false)
    @NotEmpty(message = "请选择企业。")
    private String companyUid;

    /**
     * 企业
     */
    @Transient
    private String companyName;

    /**
     * 当前承担国家级科技计划项目数
     */
    @Range(min = 0, max = 99999999, message = "请在当前承担国家级科技计划项目数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer nationalPlanningProjectNum;

    /**
     * 当前获得省级以上奖励
     */
    @Range(min = 0, max = 99999999, message = "请在当前获得省级以上奖励中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal provincialLevelNum = new BigDecimal(0);

    /**
     * 购买（被许可）国外专利
     */
    @Range(min = 0, max = 99999999, message = "请在购买（被许可）国外专利中输入[0-99999999]范围内的数值。")
    @Column
    private Integer foreignPatentNum;

    /**
     * 技术合同交易数量
     */
    @Range(min = 0, max = 99999999, message = "请在技术合同交易数量中输入[0-99999999]范围内的数值。")
    @Column
    private Integer technicalContractNum;

    /**
     * 技术合同交易额
     */
    @Range(min = 0, max = 99999999, message = "请在技术合同交易额中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal technicalContractAmount = new BigDecimal(0);

    /**
     * 研发加计备案数
     */
    @Range(min = 0, max = 99999999, message = "请在研发加计备案数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer rdCasesNum;

    /**
     * 累计工程中心数
     */
    @Range(min = 0, max = 99999999, message = "请在累计工程中心数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer totalEngineeringNum;

    /**
     * 国家级
     */
    @Range(min = 0, max = 99999999, message = "请在国家级中输入[0-99999999]范围内的数值。")
    @Column
    private Integer enNationalLevelNum;

    /**
     * 省级
     */
    @Range(min = 0, max = 99999999, message = "请在省级中输入[0-99999999]范围内的数值。")
    @Column
    private Integer enProvincialLevelNum;

    /**
     * 市级
     */
    @Range(min = 0, max = 99999999, message = "请在市级中输入[0-99999999]范围内的数值。")
    @Column
    private Integer enMunicipalLevelNum;

    /**
     * 累计项目数量
     */
    @Range(min = 0, max = 99999999, message = "请在累计项目数量中输入[0-99999999]范围内的数值。")
    @Column
    private Integer totalProjectNum;

    /**
     * 国家级
     */
    @Range(min = 0, max = 99999999, message = "请在国家级中输入[0-99999999]范围内的数值。")
    @Column
    private Integer projectNationalLevelNum;

    /**
     * 省级
     */
    @Range(min = 0, max = 99999999, message = "请在省级中输入[0-99999999]范围内的数值。")
    @Column
    private Integer projectProvincialLevelNum;

    /**
     * 市级
     */
    @Range(min = 0, max = 99999999, message = "请在市级中输入[0-99999999]范围内的数值。")
    @Column
    private Integer projectMunicipalLevelNum;

    /**
     * 累计获得国家资助经费金额
     */
    @Range(min = 0, max = 99999999, message = "请在累计获得国家资助经费金额中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal stateSubsidyAmount = new BigDecimal(0);

    /**
     * 累计获得省级资助经费金额
     */
    @Range(min = 0, max = 99999999, message = "请在累计获得省级资助经费金额中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal provincialSubsidyAmount = new BigDecimal(0);

    /**
     * 累计获得市级资助经费金额
     */
    @Range(min = 0, max = 99999999, message = "请在累计获得市级资助经费金额中输入[0-99999999]范围内的数值。")
    @Column
    private BigDecimal municipalSubsidyAmount = new BigDecimal(0);

    /**
     * 年份
     */
    @Length(max = 4, message = "请在年份中输入[0-4]位以内的文字。")
    @Column(length = 4)
    private String year;

    /**
     * 当年知识产权申请数
     */
    @Range(min = 0, max = 99999999, message = "请在当年知识产权申请数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer iprApplicationsNum;

    /**
     * 当年发明专利申请数
     */
    @Range(min = 0, max = 99999999, message = "请在当年发明专利申请数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer patentInventionNum;

    /**
     * 当前知识产权授权数
     */
    @Range(min = 0, max = 99999999, message = "请在当前知识产权授权数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer intellectualPropertyNum;

    /**
     * 发明专利授权数
     */
    @Range(min = 0, max = 99999999, message = "请在发明专利授权数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer inventionPatentsAuthNum;

    /**
     * 拥有有效知识产权数
     */
    @Range(min = 0, max = 99999999, message = "请在拥有有效知识产权数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer effectiveIntellectualNum;

    /**
     * 发明专利数
     */
    @Range(min = 0, max = 99999999, message = "请在发明专利数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer inventionPatentsNum;

    /**
     * 软件著作权登记数
     */
    @Range(min = 0, max = 99999999, message = "请在软件著作权登记数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer softwareCopyrightNum;

    /**
     * 植物新品种
     */
    @Range(min = 0, max = 99999999, message = "请在植物新品种中输入[0-99999999]范围内的数值。")
    @Column
    private Integer newPlantNum;

    /**
     * 集成电路布图设计登记数
     */
    @Range(min = 0, max = 99999999, message = "请在集成电路布图设计登记数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer icLayoutNum;

    /**
     * 其他知识产权登记数
     */
    @Range(min = 0, max = 99999999, message = "请在其他知识产权登记数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer otherIntellectualNum;

    public CompanyCopyright() {
    }

    public String getCompanyUid() {
        return this.companyUid;
    }

    public void setCompanyUid(String companyUid) {
        this.companyUid = companyUid;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getNationalPlanningProjectNum() {
        return this.nationalPlanningProjectNum;
    }

    public void setNationalPlanningProjectNum(Integer nationalPlanningProjectNum) {
        this.nationalPlanningProjectNum = nationalPlanningProjectNum;
    }

    public BigDecimal getProvincialLevelNum() {
        return this.provincialLevelNum;
    }

    public void setProvincialLevelNum(BigDecimal provincialLevelNum) {
        this.provincialLevelNum = provincialLevelNum;
    }

    public Integer getForeignPatentNum() {
        return this.foreignPatentNum;
    }

    public void setForeignPatentNum(Integer foreignPatentNum) {
        this.foreignPatentNum = foreignPatentNum;
    }

    public Integer getTechnicalContractNum() {
        return this.technicalContractNum;
    }

    public void setTechnicalContractNum(Integer technicalContractNum) {
        this.technicalContractNum = technicalContractNum;
    }

    public BigDecimal getTechnicalContractAmount() {
        return this.technicalContractAmount;
    }

    public void setTechnicalContractAmount(BigDecimal technicalContractAmount) {
        this.technicalContractAmount = technicalContractAmount;
    }

    public Integer getRdCasesNum() {
        return this.rdCasesNum;
    }

    public void setRdCasesNum(Integer rdCasesNum) {
        this.rdCasesNum = rdCasesNum;
    }

    public Integer getTotalEngineeringNum() {
        return this.totalEngineeringNum;
    }

    public void setTotalEngineeringNum(Integer totalEngineeringNum) {
        this.totalEngineeringNum = totalEngineeringNum;
    }

    public Integer getEnNationalLevelNum() {
        return this.enNationalLevelNum;
    }

    public void setEnNationalLevelNum(Integer enNationalLevelNum) {
        this.enNationalLevelNum = enNationalLevelNum;
    }

    public Integer getEnProvincialLevelNum() {
        return this.enProvincialLevelNum;
    }

    public void setEnProvincialLevelNum(Integer enProvincialLevelNum) {
        this.enProvincialLevelNum = enProvincialLevelNum;
    }

    public Integer getEnMunicipalLevelNum() {
        return this.enMunicipalLevelNum;
    }

    public void setEnMunicipalLevelNum(Integer enMunicipalLevelNum) {
        this.enMunicipalLevelNum = enMunicipalLevelNum;
    }

    public Integer getTotalProjectNum() {
        return this.totalProjectNum;
    }

    public void setTotalProjectNum(Integer totalProjectNum) {
        this.totalProjectNum = totalProjectNum;
    }

    public Integer getProjectNationalLevelNum() {
        return this.projectNationalLevelNum;
    }

    public void setProjectNationalLevelNum(Integer projectNationalLevelNum) {
        this.projectNationalLevelNum = projectNationalLevelNum;
    }

    public Integer getProjectProvincialLevelNum() {
        return this.projectProvincialLevelNum;
    }

    public void setProjectProvincialLevelNum(Integer projectProvincialLevelNum) {
        this.projectProvincialLevelNum = projectProvincialLevelNum;
    }

    public Integer getProjectMunicipalLevelNum() {
        return this.projectMunicipalLevelNum;
    }

    public void setProjectMunicipalLevelNum(Integer projectMunicipalLevelNum) {
        this.projectMunicipalLevelNum = projectMunicipalLevelNum;
    }

    public BigDecimal getStateSubsidyAmount() {
        return this.stateSubsidyAmount;
    }

    public void setStateSubsidyAmount(BigDecimal stateSubsidyAmount) {
        this.stateSubsidyAmount = stateSubsidyAmount;
    }

    public BigDecimal getProvincialSubsidyAmount() {
        return this.provincialSubsidyAmount;
    }

    public void setProvincialSubsidyAmount(BigDecimal provincialSubsidyAmount) {
        this.provincialSubsidyAmount = provincialSubsidyAmount;
    }

    public BigDecimal getMunicipalSubsidyAmount() {
        return this.municipalSubsidyAmount;
    }

    public void setMunicipalSubsidyAmount(BigDecimal municipalSubsidyAmount) {
        this.municipalSubsidyAmount = municipalSubsidyAmount;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getIprApplicationsNum() {
        return this.iprApplicationsNum;
    }

    public void setIprApplicationsNum(Integer iprApplicationsNum) {
        this.iprApplicationsNum = iprApplicationsNum;
    }

    public Integer getPatentInventionNum() {
        return this.patentInventionNum;
    }

    public void setPatentInventionNum(Integer patentInventionNum) {
        this.patentInventionNum = patentInventionNum;
    }

    public Integer getIntellectualPropertyNum() {
        return this.intellectualPropertyNum;
    }

    public void setIntellectualPropertyNum(Integer intellectualPropertyNum) {
        this.intellectualPropertyNum = intellectualPropertyNum;
    }

    public Integer getInventionPatentsAuthNum() {
        return this.inventionPatentsAuthNum;
    }

    public void setInventionPatentsAuthNum(Integer inventionPatentsAuthNum) {
        this.inventionPatentsAuthNum = inventionPatentsAuthNum;
    }

    public Integer getEffectiveIntellectualNum() {
        return this.effectiveIntellectualNum;
    }

    public void setEffectiveIntellectualNum(Integer effectiveIntellectualNum) {
        this.effectiveIntellectualNum = effectiveIntellectualNum;
    }

    public Integer getInventionPatentsNum() {
        return this.inventionPatentsNum;
    }

    public void setInventionPatentsNum(Integer inventionPatentsNum) {
        this.inventionPatentsNum = inventionPatentsNum;
    }

    public Integer getSoftwareCopyrightNum() {
        return this.softwareCopyrightNum;
    }

    public void setSoftwareCopyrightNum(Integer softwareCopyrightNum) {
        this.softwareCopyrightNum = softwareCopyrightNum;
    }

    public Integer getNewPlantNum() {
        return this.newPlantNum;
    }

    public void setNewPlantNum(Integer newPlantNum) {
        this.newPlantNum = newPlantNum;
    }

    public Integer getIcLayoutNum() {
        return this.icLayoutNum;
    }

    public void setIcLayoutNum(Integer icLayoutNum) {
        this.icLayoutNum = icLayoutNum;
    }

    public Integer getOtherIntellectualNum() {
        return this.otherIntellectualNum;
    }

    public void setOtherIntellectualNum(Integer otherIntellectualNum) {
        this.otherIntellectualNum = otherIntellectualNum;
    }

}