package com.bpms.pms.entity.ext;

import javax.persistence.*;
import com.bpms.pms.entity.ResearchInfo;

@Entity
@Table(name = "pms_research_info")
public class ResearchInfoExt extends ResearchInfo{
    private static final long serialVersionUID = -20180501150041494L;
}