package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.ChargesInformation;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "demo_charges_information")
public class ChargesInformationExt extends ChargesInformation {
    private static final long serialVersionUID = -20170418164256888L;

    /**
     * 企业收费明细实体类列表
     */
    @Transient
    private List<ChargesInformationDetailExt> chargesInformationDetailList;

    /**
     * 所属基地
     */
    @Transient
    private String baseName;

    public List<ChargesInformationDetailExt> getChargesInformationDetailList() {
        return chargesInformationDetailList;
    }

    public void setChargesInformationDetailList(List<ChargesInformationDetailExt> chargesInformationDetailList) {
        this.chargesInformationDetailList = chargesInformationDetailList;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }
}