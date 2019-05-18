package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.SmsAuthCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_sms_auth_code")
public class SmsAuthCodeExt extends SmsAuthCode {
    private static final long serialVersionUID = -20171018160939035L;
}