package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.PotentialCustomer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_potential_customer")
public class PotentialCustomerExt extends PotentialCustomer {
    private static final long serialVersionUID = -20170420132744093L;
}