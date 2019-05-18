package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.CompanyInformation;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "demo_company_information")
public class CompanyInformationExt extends CompanyInformation {
    private static final long serialVersionUID = -20170418095508107L;
    /**
     * 创业企业特征
     */
    @Transient
    private String enterpriseCharacteristics;

    /**
     * 创业企业特征
     */
    @Transient
    private String enterpriseCharacteristicsName;

    /**
     * 市场分类
     */
    @Transient
    private String marketClassification;

    /**
     * 市场分类
     */
    @Transient
    private String marketClassificationName;

    public String getEnterpriseCharacteristics() {
        return enterpriseCharacteristics;
    }

    public void setEnterpriseCharacteristics(String enterpriseCharacteristics) {
        this.enterpriseCharacteristics = enterpriseCharacteristics;
    }

    public String getEnterpriseCharacteristicsName() {
        return enterpriseCharacteristicsName;
    }

    public void setEnterpriseCharacteristicsName(String enterpriseCharacteristicsName) {
        this.enterpriseCharacteristicsName = enterpriseCharacteristicsName;
    }

    public String getMarketClassification() {
        return marketClassification;
    }

    public void setMarketClassification(String marketClassification) {
        this.marketClassification = marketClassification;
    }

    public String getMarketClassificationName() {
        return marketClassificationName;
    }

    public void setMarketClassificationName(String marketClassificationName) {
        this.marketClassificationName = marketClassificationName;
    }
}