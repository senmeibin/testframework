package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.UrlRole;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "sys_url_role")
public class UrlRoleExt extends UrlRole {
    private static final long serialVersionUID = -20161028160714619L;

    /**
     * 是否覆盖（父路径删除权限，子路径是否一并删除 true:删除，false:不删除）
     */
    @Transient
    private boolean isCover;

    @Transient
    private List<String> deleteRoleCodes;

    public List<String> getDeleteRoleCodes() {
        return deleteRoleCodes;
    }

    public void setDeleteRoleCodes(List<String> deleteRoleCodes) {
        this.deleteRoleCodes = deleteRoleCodes;
    }

    public boolean getIsCover() {
        return isCover;
    }

    public void setIsCover(boolean isCover) {
        this.isCover = isCover;
    }
}