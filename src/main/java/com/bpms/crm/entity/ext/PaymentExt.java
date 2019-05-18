package com.bpms.crm.entity.ext;

import com.bpms.crm.entity.Payment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "crm_payment")
public class PaymentExt extends Payment {
    private static final long serialVersionUID = -20180407162122602L;
}