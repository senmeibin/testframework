package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.CompanyStock;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_company_stock")
public class CompanyStockExt extends CompanyStock {
    private static final long serialVersionUID = -20170511161824404L;
}