package com.bpms.demo.entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 人员结构实体类
 */
@MappedSuperclass
public class CompanyPersonnel extends DemoBaseEntity {
    private static final long serialVersionUID = -20170511161940228L;

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
     * 上年年末从业人员总数
     */
    @Range(min = 0, max = 99999999, message = "请在上年年末从业人员总数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer lastYearTotalEmployees;

    /**
     * 博士毕业人数
     */
    @Range(min = 0, max = 99999999, message = "请在博士毕业人数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfDoctor;

    /**
     * 留学归国人员数
     */
    @Range(min = 0, max = 99999999, message = "请在留学归国人员数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfReturnedStudents;

    /**
     * 千人计划数
     */
    @Range(min = 0, max = 99999999, message = "请在千人计划数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfThousandsPlans;

    /**
     * 应届毕业生
     */
    @Range(min = 0, max = 99999999, message = "请在应届毕业生中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfGraduates;

    /**
     * 大专以上毕业人数
     */
    @Range(min = 0, max = 99999999, message = "请在大专以上毕业人数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfCollegeGraduates;

    /**
     * 上海户籍员工数量
     */
    @Range(min = 0, max = 99999999, message = "请在上海户籍员工数量中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfShanghai;

    /**
     * 外籍员工数量
     */
    @Range(min = 0, max = 99999999, message = "请在外籍员工数量中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfForeign;

    /**
     * 行政管理人员
     */
    @Range(min = 0, max = 99999999, message = "请在行政管理人员中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfAdministrativeStaff;

    /**
     * 市场营销人员
     */
    @Range(min = 0, max = 99999999, message = "请在市场营销人员中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfMarketingStaff;

    /**
     * 研发设计人员
     */
    @Range(min = 0, max = 99999999, message = "请在研发设计人员中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfResearch;

    /**
     * 领导管理人员
     */
    @Range(min = 0, max = 99999999, message = "请在领导管理人员中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfLead;

    /**
     * 硕士毕业人数
     */
    @Range(min = 0, max = 99999999, message = "请在硕士毕业人数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfMaster;

    /**
     * 本科毕业人数
     */
    @Range(min = 0, max = 99999999, message = "请在本科毕业人数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfUndergraduate;

    /**
     * 中专毕业人数
     */
    @Range(min = 0, max = 99999999, message = "请在中专毕业人数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfSecondaryVocational;

    /**
     * 高中以下人数
     */
    @Range(min = 0, max = 99999999, message = "请在高中以下人数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfHighSchool;

    /**
     * 高级职称人数
     */
    @Range(min = 0, max = 99999999, message = "请在高级职称人数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfSeniorTitle;

    /**
     * 中级职称人数
     */
    @Range(min = 0, max = 99999999, message = "请在中级职称人数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfIntermediateTitle;

    /**
     * 初级职称人数
     */
    @Range(min = 0, max = 99999999, message = "请在初级职称人数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfJuniorTitle;

    /**
     * 无职称人数
     */
    @Range(min = 0, max = 99999999, message = "请在无职称人数中输入[0-99999999]范围内的数值。")
    @Column
    private Integer numberOfNoneTitle;

    public CompanyPersonnel() {
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

    public Integer getLastYearTotalEmployees() {
        return this.lastYearTotalEmployees;
    }

    public void setLastYearTotalEmployees(Integer lastYearTotalEmployees) {
        this.lastYearTotalEmployees = lastYearTotalEmployees;
    }

    public Integer getNumberOfDoctor() {
        return this.numberOfDoctor;
    }

    public void setNumberOfDoctor(Integer numberOfDoctor) {
        this.numberOfDoctor = numberOfDoctor;
    }

    public Integer getNumberOfReturnedStudents() {
        return this.numberOfReturnedStudents;
    }

    public void setNumberOfReturnedStudents(Integer numberOfReturnedStudents) {
        this.numberOfReturnedStudents = numberOfReturnedStudents;
    }

    public Integer getNumberOfThousandsPlans() {
        return this.numberOfThousandsPlans;
    }

    public void setNumberOfThousandsPlans(Integer numberOfThousandsPlans) {
        this.numberOfThousandsPlans = numberOfThousandsPlans;
    }

    public Integer getNumberOfGraduates() {
        return this.numberOfGraduates;
    }

    public void setNumberOfGraduates(Integer numberOfGraduates) {
        this.numberOfGraduates = numberOfGraduates;
    }

    public Integer getNumberOfCollegeGraduates() {
        return this.numberOfCollegeGraduates;
    }

    public void setNumberOfCollegeGraduates(Integer numberOfCollegeGraduates) {
        this.numberOfCollegeGraduates = numberOfCollegeGraduates;
    }

    public Integer getNumberOfShanghai() {
        return this.numberOfShanghai;
    }

    public void setNumberOfShanghai(Integer numberOfShanghai) {
        this.numberOfShanghai = numberOfShanghai;
    }

    public Integer getNumberOfForeign() {
        return this.numberOfForeign;
    }

    public void setNumberOfForeign(Integer numberOfForeign) {
        this.numberOfForeign = numberOfForeign;
    }

    public Integer getNumberOfAdministrativeStaff() {
        return this.numberOfAdministrativeStaff;
    }

    public void setNumberOfAdministrativeStaff(Integer numberOfAdministrativeStaff) {
        this.numberOfAdministrativeStaff = numberOfAdministrativeStaff;
    }

    public Integer getNumberOfMarketingStaff() {
        return this.numberOfMarketingStaff;
    }

    public void setNumberOfMarketingStaff(Integer numberOfMarketingStaff) {
        this.numberOfMarketingStaff = numberOfMarketingStaff;
    }

    public Integer getNumberOfResearch() {
        return this.numberOfResearch;
    }

    public void setNumberOfResearch(Integer numberOfResearch) {
        this.numberOfResearch = numberOfResearch;
    }

    public Integer getNumberOfLead() {
        return this.numberOfLead;
    }

    public void setNumberOfLead(Integer numberOfLead) {
        this.numberOfLead = numberOfLead;
    }

    public Integer getNumberOfMaster() {
        return this.numberOfMaster;
    }

    public void setNumberOfMaster(Integer numberOfMaster) {
        this.numberOfMaster = numberOfMaster;
    }

    public Integer getNumberOfUndergraduate() {
        return this.numberOfUndergraduate;
    }

    public void setNumberOfUndergraduate(Integer numberOfUndergraduate) {
        this.numberOfUndergraduate = numberOfUndergraduate;
    }

    public Integer getNumberOfSecondaryVocational() {
        return this.numberOfSecondaryVocational;
    }

    public void setNumberOfSecondaryVocational(Integer numberOfSecondaryVocational) {
        this.numberOfSecondaryVocational = numberOfSecondaryVocational;
    }

    public Integer getNumberOfHighSchool() {
        return this.numberOfHighSchool;
    }

    public void setNumberOfHighSchool(Integer numberOfHighSchool) {
        this.numberOfHighSchool = numberOfHighSchool;
    }

    public Integer getNumberOfSeniorTitle() {
        return this.numberOfSeniorTitle;
    }

    public void setNumberOfSeniorTitle(Integer numberOfSeniorTitle) {
        this.numberOfSeniorTitle = numberOfSeniorTitle;
    }

    public Integer getNumberOfIntermediateTitle() {
        return this.numberOfIntermediateTitle;
    }

    public void setNumberOfIntermediateTitle(Integer numberOfIntermediateTitle) {
        this.numberOfIntermediateTitle = numberOfIntermediateTitle;
    }

    public Integer getNumberOfJuniorTitle() {
        return this.numberOfJuniorTitle;
    }

    public void setNumberOfJuniorTitle(Integer numberOfJuniorTitle) {
        this.numberOfJuniorTitle = numberOfJuniorTitle;
    }

    public Integer getNumberOfNoneTitle() {
        return this.numberOfNoneTitle;
    }

    public void setNumberOfNoneTitle(Integer numberOfNoneTitle) {
        this.numberOfNoneTitle = numberOfNoneTitle;
    }

}