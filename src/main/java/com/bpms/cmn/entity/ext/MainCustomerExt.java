package com.bpms.cmn.entity.ext;

import com.bpms.cmn.entity.MainCustomer;

import javax.persistence.Entity;
import javax.persistence.Table;

//注：防止共通模块与业务模块Entity同名问题，在共通模块的Entity中加入带前缀的name属性
@Entity(name = "CmnMainCustomer")
@Table(name = "cmn_main_customer")
public class MainCustomerExt extends MainCustomer {
    private static final long serialVersionUID = -20160330185936748L;
}