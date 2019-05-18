package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.Company;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

//注：防止系统共通模块与业务模块Entity同名问题，在系统共通模块的Entity中加入带前缀的name属性
@Entity(name = "SysCompany")
@Table(name = "sys_company")
public class CompanyExt extends Company {
    private static final long serialVersionUID = -20160405144317319L;

    /**
     * 联系人列表
     */
    @Transient
    private List<ContactExt> contactList;

    public List<ContactExt> getContactList() {
        return contactList;
    }

    public void setContactList(List<ContactExt> contactList) {
        this.contactList = contactList;
    }
}