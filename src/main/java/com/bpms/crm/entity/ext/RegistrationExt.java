package com.bpms.crm.entity.ext;

import com.bpms.crm.entity.Registration;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "crm_registration")
public class RegistrationExt extends Registration {
    private static final long serialVersionUID = -20180407161905314L;
}