package com.bpms.core.security;

import com.bpms.cmn.entity.ext.RegionExt;
import com.bpms.cmn.entity.ext.SignCompanyExt;
import com.bpms.sys.entity.ext.DeptExt;
import com.bpms.sys.entity.ext.UserExt;
import com.bpms.sys.entity.ext.UserInfoExt;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
 */
public class ShiroUser implements Serializable {
    private static final long serialVersionUID = -1373760761780840081L;

    private UserExt user;
    private String userName;
    private String userCd;
    private String ip;
    private String userUid;
    private String deptUid;
    private String deptName;
    private String positionUid;
    private String positionName;
    private Integer forceChangePswd;
    private Integer recordStatus;
    private Integer accountLock = 0;
    private String themeName;

    /**
     * 企业UID
     */
    private Integer enterpriseUid;

    /**
     * 外包系统的职位id(多个职位采用逗号分隔)
     */
    private String positionIds;

    /**
     * 所属分公司
     */
    private String belongCompanyDeptUid = "UNKNOWN";
    private String belongCompanyDeptName = "UNKNOWN";

    /**
     * 所属大区
     */
    private String belongAreaDeptUid = "UNKNOWN";
    private String belongAreaDeptName = "UNKNOWN";

    /**
     * 所属集团
     */
    private String belongGroupDeptUid = "UNKNOWN";
    private String belongGroupDeptName = "UNKNOWN";

    /**
     * 办公地点
     */
    private String locationUid;
    private String locationName;

    /**
     * 角色集合
     */
    private List<String> roles;

    /**
     * 构造函数
     *
     * @param user 登录用户实体
     */
    public ShiroUser(UserExt user) {
        this(user, null);
    }

    /**
     * 构造函数
     *
     * @param user 登录用户实体
     * @param ip   登录IP
     */
    public ShiroUser(UserExt user, String ip) {
        this.user = user;
        this.userUid = user.getUid();
        this.userCd = user.getUserCd();
        this.userName = user.getUserName();
        this.forceChangePswd = user.getForceChangePswd();
        this.deptUid = user.getDeptUid();
        this.deptName = user.getDeptName();
        this.positionUid = user.getPositionUid();
        this.positionName = user.getPositionName();
        this.ip = ip;
        this.recordStatus = user.getRecordStatus();
        this.accountLock = user.getAccountLock();
        this.themeName = user.getThemeName();
        this.positionIds = StringUtils.EMPTY;
        this.enterpriseUid = user.getEnterpriseUid();

        if (user.getBelongCompanyDept() != null) {
            this.belongCompanyDeptUid = user.getBelongCompanyDept().getUid();
            this.belongCompanyDeptName = user.getBelongCompanyDept().getDeptName();
        }
        if (user.getBelongAreaDept() != null) {
            this.belongAreaDeptUid = user.getBelongAreaDept().getUid();
            this.belongAreaDeptName = user.getBelongAreaDept().getDeptName();
        }
        if (user.getBelongGroupDept() != null) {
            this.belongGroupDeptUid = user.getBelongGroupDept().getUid();
            this.belongGroupDeptName = user.getBelongGroupDept().getDeptName();
        }
    }

    /**
     * 取得用户所属部门树集合
     *
     * @return 部门树集合
     */
    public List<DeptExt> getBelongDeptTree() {
        return this.user.getBelongDeptTree();
    }

    /**
     * 从部门树集合中取得指定类别的部门实体
     *
     * @param deptClass 部门层级
     * @return 部门实体
     */
    public DeptExt getDeptFromBelongDeptTree(Integer deptClass) {
        for (DeptExt dept : this.getBelongDeptTree()) {
            if (dept.getDeptClass() == deptClass) {
                return dept;
            }
        }
        return new DeptExt();
    }

    public String getUserPhone() {
        if (this.user == null) {
            return StringUtils.EMPTY;
        }
        return this.user.getUserPhone();
    }

    public Integer getRegSystem() {
        return this.user.getRegSystem();
    }

    public String getDesPassword() {
        return this.user.getDesPassword();
    }

    public String getUserNumber() {
        return this.user.getUserNumber();
    }

    public String getUserAreaUid() {
        return this.user.getUserAreaUid();
    }

    public String getUserAreaName() {
        return this.user.getUserAreaName();
    }

    public String getUserCompanyUid() {
        return this.user.getUserCompanyUid();
    }

    public String getUserCompanyName() {
        return this.user.getUserCompanyName();
    }

    public String getAccessIp() {
        return this.user.getAccessIp();
    }

    public String getUserMail() {
        return this.user.getUserMail();
    }

    public String getCityManageUids() {
        return this.user.getCityManageUids();
    }

    public List<RegionExt> getManageCityList() {
        return this.user.getManageCityList();
    }

    public List<SignCompanyExt> getManageSignCompanyList() {
        return this.user.getManageSignCompanyList();
    }


    public String getDeptManageUids() {
        return this.user.getDeptManageUids();
    }

    public List<DeptExt> getManageDepts() {
        return this.user.getManageDepts();
    }

    public UserInfoExt getUserInfo() {
        return this.user.getUserInfo();
    }

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        if (StringUtils.isEmpty(this.positionName)) {
            return userName;
        }
        else {
            return userName + "[" + this.positionName + "]";
        }
    }

    /**
     * 重载hashCode,只计算UserCd;
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(userCd);
    }

    /**
     * 重载equals,只计算UserCd;
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ShiroUser other = (ShiroUser) obj;
        if (this.userCd == null) {
            if (other.userCd != null) {
                return false;
            }
        }
        else if (!StringUtils.equals(userCd, other.userCd)) {
            return false;
        }
        return true;
    }

    public Integer getForceChangePswd() {
        return forceChangePswd;
    }

    public void setForceChangePswd(Integer forceChangePswd) {
        this.forceChangePswd = forceChangePswd;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDeptUid() {
        return deptUid;
    }

    public void setDeptUid(String deptUid) {
        this.deptUid = deptUid;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPositionUid() {
        return positionUid;
    }

    public void setPositionUid(String positionUid) {
        this.positionUid = positionUid;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Integer getAccountLock() {
        return accountLock;
    }

    public void setAccountLock(Integer accountLock) {
        this.accountLock = accountLock;
    }

    public String getBelongCompanyDeptUid() {
        return belongCompanyDeptUid;
    }

    public void setBelongCompanyDeptUid(String belongCompanyDeptUid) {
        this.belongCompanyDeptUid = belongCompanyDeptUid;
    }

    public String getBelongCompanyDeptName() {
        return belongCompanyDeptName;
    }

    public void setBelongCompanyDeptName(String belongCompanyDeptName) {
        this.belongCompanyDeptName = belongCompanyDeptName;
    }

    public String getBelongAreaDeptUid() {
        return belongAreaDeptUid;
    }

    public void setBelongAreaDeptUid(String belongAreaDeptUid) {
        this.belongAreaDeptUid = belongAreaDeptUid;
    }

    public String getBelongAreaDeptName() {
        return belongAreaDeptName;
    }

    public void setBelongAreaDeptName(String belongAreaDeptName) {
        this.belongAreaDeptName = belongAreaDeptName;
    }

    public String getBelongGroupDeptUid() {
        return belongGroupDeptUid;
    }

    public void setBelongGroupDeptUid(String belongGroupDeptUid) {
        this.belongGroupDeptUid = belongGroupDeptUid;
    }

    public String getBelongGroupDeptName() {
        return belongGroupDeptName;
    }

    public void setBelongGroupDeptName(String belongGroupDeptName) {
        this.belongGroupDeptName = belongGroupDeptName;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getLocationUid() {
        return locationUid;
    }

    public void setLocationUid(String locationUid) {
        this.locationUid = locationUid;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(String positionIds) {
        this.positionIds = positionIds;
    }

    public Integer getEnterpriseUid() {
        return enterpriseUid;
    }

    public void setEnterpriseUid(Integer enterpriseUid) {
        this.enterpriseUid = enterpriseUid;
    }

    /**
     * 判断是否平台用户
     *
     * @return true：平台用户，false：非平台用户
     */
    public boolean isPlatformUser() {
        //企业UID非空场合
        if (this.enterpriseUid != null) {
            return true;
        }
        return false;
    }
}