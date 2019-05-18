package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.UserInfo;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_user_info")
public class UserInfoExt extends UserInfo {
    private static final long serialVersionUID = -20171110103121174L;
}