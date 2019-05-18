package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.RolePermission;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_role_permission")
public class RolePermissionExt extends RolePermission {
    private static final long serialVersionUID = -20160420122128287L;
}