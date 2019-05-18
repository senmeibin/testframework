package com.bpms.pms.entity.ext;

import javax.persistence.*;
import com.bpms.pms.entity.CompanyInfo;

@Entity
@Table(name = "pms_company_info")
public class CompanyInfoExt extends CompanyInfo{
    private static final long serialVersionUID = -20180428060243669L;
}