package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.OnlineUser;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_online_user")
public class OnlineUserExt extends OnlineUser {
    private static final long serialVersionUID = -20160505095713140L;
}