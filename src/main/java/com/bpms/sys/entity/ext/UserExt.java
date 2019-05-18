package com.bpms.sys.entity.ext;

import com.bpms.cmn.entity.ext.CustomerExt;
import com.bpms.cmn.entity.ext.RegionExt;
import com.bpms.cmn.entity.ext.SignCompanyExt;
import com.bpms.sys.entity.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sys_user")
public class UserExt extends User {
    private static final long serialVersionUID = -635944257524382461L;

    /**
     * 用户所属部门树集合（包含当前所属的部门/上级部门/所属分公司/所属大区）
     */
    @Transient
    private List<DeptExt> belongDeptTree = new ArrayList<>();

    /**
     * 取得用户所属部门树集合
     *
     * @return 部门树集合
     */
    public List<DeptExt> getBelongDeptTree() {
        return belongDeptTree;
    }

    /**
     * 从部门树集合中取得指定类别的部门实体
     *
     * @param deptClass 部门层级
     * @return 部门实体
     */
    public DeptExt getDeptFromBelongDeptTree(Integer deptClass) {
        for (DeptExt dept : belongDeptTree) {
            if (dept.getDeptClass() == deptClass) {
                return dept;
            }
        }
        return new DeptExt();
    }

    /**
     * 追加部门实体到所属部门树集合
     *
     * @param belongDept 部门实体
     */
    public void addDeptToBelongDeptTree(DeptExt belongDept) {
        this.belongDeptTree.add(belongDept);
    }

    /**
     * 部门全称
     */
    @Transient
    private String deptFullName;

    /**
     * 所属集团
     */
    @Transient
    private DeptExt belongGroupDept;

    /**
     * 所属大区
     */
    @Transient
    private DeptExt belongAreaDept;

    /**
     * 所属分公司
     */
    @Transient
    private DeptExt belongCompanyDept;

    /**
     * 所管辖部门
     */
    @Transient
    private List<DeptExt> manageDepts;

    /**
     * 所管辖城市 如果为空则全部管辖
     */
    @Transient
    private List<RegionExt> manageCityList;

    /**
     * 所管辖签单分公司 如果为空则全部管辖
     */
    @Transient
    private List<SignCompanyExt> manageSignCompanyList;

    /**
     * 密码明文
     */
    @Transient
    private String plainPassword;

    /**
     * 关联角色一览
     */
    @Transient
    private List<RoleExt> roleList;

    /**
     * 关联可产看角色一览
     */
    @Transient
    private List<RoleExt> productRoleList;

    /**
     * 上级部门UID
     */
    @Transient
    private String parentDeptUid;

    /**
     * 查看产品名称(逗号分隔）
     */
    @Transient
    private String productNames;

    /**
     * 管辖分公司uid (逗号分隔）
     */
    @Transient
    private String deptManageUids;

    /**
     * 管辖城市uid (逗号分隔）
     */
    @Transient
    private String cityManageUids;

    /**
     * 管辖签单分公司uid (逗号分隔）
     */
    @Transient
    private String signCompanyManageUids;

    /**
     * 所属分公司uid【部门表】
     */
    @Transient
    private String userCompanyUid;

    /**
     * 所属分公司名称【部门表】
     */
    @Transient
    private String userCompanyName;

    /**
     * 所属分公司全称【部门表】
     */
    @Transient
    private String userCompanyFullName;

    /**
     * 所属大区uid
     */
    @Transient
    private String userAreaUid;

    /**
     * 所属大区名称
     */
    @Transient
    private String userAreaName;

    /**
     * 所属大区全称
     */
    @Transient
    private String userAreaFullName;

    /**
     * 该用户分配角色名称
     */
    @Transient
    private String userRoleNames;

    /**
     * 用户信息实体
     */
    @Transient
    private UserInfoExt userInfo = new UserInfoExt();

    /**
     * 所属合同客户
     */
    @Transient
    private List<CustomerExt> customerList;

    public List<RegionExt> getManageCityList() {
        return manageCityList;
    }

    public void setManageCityList(List<RegionExt> manageCityList) {
        this.manageCityList = manageCityList;
    }

    public void setManageSignCompanyList(List<SignCompanyExt> manageSignCompanyList) {
        this.manageSignCompanyList = manageSignCompanyList;
    }

    public List<SignCompanyExt> getManageSignCompanyList() {
        return this.manageSignCompanyList;
    }

    public String getCityManageUids() {
        return cityManageUids;
    }

    public void setCityManageUids(String cityManageUids) {
        this.cityManageUids = cityManageUids;
    }

    public String getDeptFullName() {
        return deptFullName;
    }

    public void setDeptFullName(String deptFullName) {
        this.deptFullName = deptFullName;
    }

    public DeptExt getBelongGroupDept() {
        return belongGroupDept;
    }

    public void setBelongGroupDept(DeptExt belongGroupDept) {
        this.belongGroupDept = belongGroupDept;
    }

    public DeptExt getBelongAreaDept() {
        return belongAreaDept;
    }

    public void setBelongAreaDept(DeptExt belongAreaDept) {
        this.belongAreaDept = belongAreaDept;
    }

    public DeptExt getBelongCompanyDept() {
        return belongCompanyDept;
    }

    public void setBelongCompanyDept(DeptExt belongCompanyDept) {
        this.belongCompanyDept = belongCompanyDept;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public List<RoleExt> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleExt> roleList) {
        this.roleList = roleList;
    }

    public String getParentDeptUid() {
        return parentDeptUid;
    }

    public void setParentDeptUid(String parentDeptUid) {
        this.parentDeptUid = parentDeptUid;
    }

    public String getDeptManageUids() {
        return deptManageUids;
    }

    public void setDeptManageUids(String deptManageUids) {
        this.deptManageUids = deptManageUids;
    }

    public List<DeptExt> getManageDepts() {
        return manageDepts;
    }

    public void setManageDepts(List<DeptExt> manageDepts) {
        this.manageDepts = manageDepts;
    }

    public String getSignCompanyManageUids() {
        return signCompanyManageUids;
    }

    public void setSignCompanyManageUids(String signCompanyManageUids) {
        this.signCompanyManageUids = signCompanyManageUids;
    }

    public String getProductNames() {
        return productNames;
    }

    public void setProductNames(String productNames) {
        this.productNames = productNames;
    }

    public List<RoleExt> getProductRoleList() {
        return productRoleList;
    }

    public void setProductRoleList(List<RoleExt> productRoleList) {
        this.productRoleList = productRoleList;
    }

    public String getUserCompanyUid() {
        return userCompanyUid;
    }

    public void setUserCompanyUid(String userCompanyUid) {
        this.userCompanyUid = userCompanyUid;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getUserCompanyFullName() {
        return userCompanyFullName;
    }

    public void setUserCompanyFullName(String userCompanyFullName) {
        this.userCompanyFullName = userCompanyFullName;
    }

    public String getUserAreaUid() {
        return userAreaUid;
    }

    public void setUserAreaUid(String userAreaUid) {
        this.userAreaUid = userAreaUid;
    }

    public String getUserAreaName() {
        return userAreaName;
    }

    public void setUserAreaName(String userAreaName) {
        this.userAreaName = userAreaName;
    }

    public String getUserAreaFullName() {
        return userAreaFullName;
    }

    public void setUserAreaFullName(String userAreaFullName) {
        this.userAreaFullName = userAreaFullName;
    }

    public UserInfoExt getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoExt userInfo) {
        this.userInfo = userInfo;
    }

    public String getUserRoleNames() {
        return userRoleNames;
    }

    public void setUserRoleNames(String userRoleNames) {
        this.userRoleNames = userRoleNames;
    }

    public List<CustomerExt> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<CustomerExt> customerList) {
        this.customerList = customerList;
    }
}