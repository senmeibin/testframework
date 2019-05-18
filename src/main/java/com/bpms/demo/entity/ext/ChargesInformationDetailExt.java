package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.ChargesInformationDetail;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_charges_information_detail")
public class ChargesInformationDetailExt extends ChargesInformationDetail {
    private static final long serialVersionUID = -20170418164404480L;
}