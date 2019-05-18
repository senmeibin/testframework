package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.Contact;

import javax.persistence.Entity;
import javax.persistence.Table;

//注：防止系统共通模块与业务模块Entity同名问题，在系统共通模块的Entity中加入带前缀的name属性
@Entity(name = "SysContact")
@Table(name = "sys_contact")
public class ContactExt extends Contact {
    private static final long serialVersionUID = -20160405164130121L;
}