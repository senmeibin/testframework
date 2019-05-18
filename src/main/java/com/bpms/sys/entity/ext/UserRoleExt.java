package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.UserRole;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_user_role")
public class UserRoleExt extends UserRole {
    private static final long serialVersionUID = -20160420132119024L;
}