package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.ThirdPartyServiceContacts;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_third_party_service_contacts")
public class ThirdPartyServiceContactsExt extends ThirdPartyServiceContacts {
    private static final long serialVersionUID = -20170509141338882L;
}