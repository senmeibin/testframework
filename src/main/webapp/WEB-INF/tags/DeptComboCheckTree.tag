<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="uniqueID" value="${parentInstance.replace('.', '_')}"/>
<%--调用画面JS实例字符串--%>
<%@ attribute name="parentInstance" type="java.lang.String" required="true" %>
<%-- ComboTree数据请求URL--%>
<%@ attribute name="showAreaFlag" type="java.lang.Boolean" required="false" %>
<%@ attribute name="showCompanyFlag" type="java.lang.Boolean" required="false" %>
<%@ attribute name="showDeptFlag" type="java.lang.Boolean" required="false" %>
<%-- 检索用表别名--%>
<%@ attribute name="aliasTable" type="java.lang.String" required="false" %>
<%-- 是否必须 ，默认为非必需--%>
<%@ attribute name="required" type="java.lang.String" required="false" %>
<%-- 是否显示验证消息--%>
<%@ attribute name="showValidateError" type="java.lang.Boolean" required="false" %>
<%-- 是否权限过滤--%>
<%@ attribute name="roleFilter" type="java.lang.Boolean" required="false" %>

<!--无需权限过滤-->
<c:if test="${roleFilter != null && roleFilter == false}">
    <c:if test="${showAreaFlag == null || showAreaFlag == true}">
        <div class="col-md-2 form-group">
            <label>所属大区</label>
            <ctag:ComboCheckTree valueDomId="areaUid" required="${required}" showValidateError="${showValidateError}" textDomId="areaName" parentInstance="${parentInstance}" dataTitle="所属大区"
                                 aliasTable="${empty aliasTable ?'main':aliasTable}" searchMode="in" idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dataUrl="${ctx}/sys/dept/getDeptList"/>
        </div>
    </c:if>

    <c:if test="${showCompanyFlag == null || showCompanyFlag == true}">
        <div class="col-md-2 form-group">
            <label>所属分公司</label>
            <ctag:ComboCheckTree valueDomId="companyUid" required="${required}" showValidateError="${showValidateError}" parentDomId="areaUid" parentDataTitle="所属大区" textDomId="companyName" parentInstance="${parentInstance}" dataTitle="所属分公司"
                                 aliasTable="${empty aliasTable ?'main':aliasTable}" searchMode="in" idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dataUrl="${ctx}/sys/dept/getDeptList"/>
        </div>
    </c:if>

    <c:if test="${showDeptFlag == null || showDeptFlag == true}">
        <div class="col-md-2 form-group">
            <label>所属部门</label>
            <ctag:ComboCheckTree valueDomId="deptUid" required="${required}" showValidateError="${showValidateError}" parentDomId="companyUid" parentDataTitle="所属分公司" textDomId="deptName" parentInstance="${parentInstance}" dataTitle="所属部门"
                                 aliasTable="${empty aliasTable ?'main':aliasTable}" searchMode="in" idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dataUrl="${ctx}/sys/dept/getDeptList"/>
        </div>
    </c:if>
</c:if>

<!--需要权限过滤-->
<c:if test="${roleFilter == null || roleFilter == true}">
    <shiro:hasAnyRoles name="SystemManagement,ItSupport,GroupDataRange">
        <c:if test="${showAreaFlag == null || showAreaFlag == true}">
            <div class="col-md-2 form-group">
                <label>所属大区</label>
                <ctag:ComboCheckTree valueDomId="areaUid" required="${required}" showValidateError="${showValidateError}" textDomId="areaName" parentInstance="${parentInstance}" dataTitle="所属大区"
                                     aliasTable="${empty aliasTable ?'main':aliasTable}" searchMode="in" idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dataUrl="${ctx}/sys/dept/getDeptListByRole"/>
            </div>
        </c:if>

        <c:if test="${showCompanyFlag == null || showCompanyFlag == true}">
            <div class="col-md-2 form-group">
                <label>所属分公司</label>
                <ctag:ComboCheckTree valueDomId="companyUid" required="${required}" showValidateError="${showValidateError}" parentDomId="areaUid" parentDataTitle="所属大区" textDomId="companyName" parentInstance="${parentInstance}" dataTitle="所属分公司"
                                     aliasTable="${empty aliasTable ?'main':aliasTable}" searchMode="in" idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dataUrl="${ctx}/sys/dept/getDeptListByRole"/>
            </div>
        </c:if>

        <c:if test="${showDeptFlag == null || showDeptFlag == true}">
            <div class="col-md-2 form-group">
                <label>所属部门</label>
                <ctag:ComboCheckTree valueDomId="deptUid" required="${required}" showValidateError="${showValidateError}" parentDomId="companyUid" parentDataTitle="所属分公司" textDomId="deptName" parentInstance="${parentInstance}" dataTitle="所属部门"
                                     aliasTable="${empty aliasTable ?'main':aliasTable}" searchMode="in" idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dataUrl="${ctx}/sys/dept/getDeptListByRole"/>
            </div>
        </c:if>
    </shiro:hasAnyRoles>

    <shiro:hasRole name="AreaDataRange">
        <shiro:lacksRole name="GroupDataRange">
            <shiro:lacksRole name="SystemManagement,ItSupport">
                <c:if test="${showCompanyFlag == null || showCompanyFlag == true}">
                    <div class="col-md-2 form-group">
                        <label>所属分公司</label>
                        <ctag:ComboCheckTree valueDomId="companyUid" textDomId="companyName" parentInstance="${parentInstance}" dataTitle="所属分公司"
                                             aliasTable="${empty aliasTable ?'main':aliasTable}" searchMode="in" idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dataUrl="${ctx}/sys/dept/getDeptListByRole"/>
                    </div>
                </c:if>

                <c:if test="${showDeptFlag == null || showDeptFlag == true}">
                    <div class="col-md-2 form-group">
                        <label>所属部门</label>
                        <ctag:ComboCheckTree valueDomId="deptUid" parentDomId="companyUid" parentDataTitle="所属分公司" textDomId="deptsName" parentInstance="${parentInstance}" dataTitle="所属部门"
                                             aliasTable="${empty aliasTable ?'main':aliasTable}" searchMode="in" idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dataUrl="${ctx}/sys/dept/getDeptListByRole"/>
                    </div>
                </c:if>
            </shiro:lacksRole>
        </shiro:lacksRole>
    </shiro:hasRole>

    <shiro:hasRole name="CompanyDataRange">
        <shiro:lacksRole name="GroupDataRange">
            <shiro:lacksRole name="AreaDataRange">
                <c:if test="${showDeptFlag == null || showDeptFlag == true}">
                    <div class="col-md-2 form-group">
                        <label>所属部门</label>
                        <ctag:ComboCheckTree valueDomId="deptUid" textDomId="deptName" parentInstance="${parentInstance}" dataTitle="所属部门"
                                             aliasTable="${empty aliasTable ?'main':aliasTable}" searchMode="in" idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dataUrl="${ctx}/sys/dept/getDeptListByRole"/>
                    </div>
                </c:if>
            </shiro:lacksRole>
        </shiro:lacksRole>
    </shiro:hasRole>
</c:if>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function DeptComboCheckTree_Page_Load() {
        SysApp.Cmn.DeptComboCheckTreeIns = new SysApp.Cmn.DeptComboCheckTree();
        var instance = SysApp.Cmn.DeptComboCheckTreeIns;
        instance.parentInstance = "${parentInstance}";
        instance.initParentInstance();
    }
    $(function () {
        DeptComboCheckTree_Page_Load();
    });
    //]]>
</script>