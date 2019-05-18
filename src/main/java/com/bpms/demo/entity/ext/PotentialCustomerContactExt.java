package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.PotentialCustomerContact;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_potential_customer_contact")
public class PotentialCustomerContactExt extends PotentialCustomerContact {
    private static final long serialVersionUID = -20170420163649571L;
}