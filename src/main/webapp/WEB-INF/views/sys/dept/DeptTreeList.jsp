<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/dept/DeptTreeList.js?${version}"></script>

<title>部门一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="section">
    <div class="section-icon popovers" data-trigger="hover" data-placement="right" data-content="隐藏/显示组织架构"></div>
    <div class="section-left">
        <h3>组织架构</h3>
        <div style="width:100%;">
            <select id="treeEnterpriseUid" data-alias-table="main" class="form-control" data-title="企业">
                <option value="">请选择企业</option>
            </select>
        </div>
        <div class="section-tree">
            <ctag:TreeView treeId="deptStructure" parentInstance="SysApp.Sys.DeptListIns" style="width:310px;"
                           idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dispName="deptFullName" dataUrl="${ctx}/sys/dept/getAllDeptList?enterpriseUid=${enterpriseUid}"/>
        </div>
        <div class="clear"></div>
    </div>
    <div class="section-right">
        <div style=" height:auto; overflow:hidden; margin-top:0px;">
            <div class="wrapper">
                <div class="panel">
                    <div class="panel-heading">
                        <em></em>
                        <div class="panel-title">部门编辑</div>
                    </div>
                    <div class="panel-body">
                        <div class="DeptList-MainContent">
                            <button id="btnAdd" type="button" class="btn btn-primary">
                                <i class="fa fa-plus"></i>新增部门
                            </button>
                            <button id="btnAddUser" type="button" class="btn btn-primary">
                                <i class="fa fa-plus"></i>新增用户
                            </button>
                            <!--有配置DATA_IMPORT_SYS_API才显示同步按钮 -->
                            <c:if test="${dataImportApiExist}">
                                <button id="btnSyncData" type="button" class="btn btn-danger">
                                    <i class="fa fa-spinner"></i>同步数据
                                </button>
                            </c:if>
                            <button id="btnDeptList" type="button" class="btn btn-default">
                                <i class="fa fa-list"></i>组织架构平铺展示
                            </button>

                            <div class="clear-both dashed-line">
                            </div>

                            <ctag:Fold id="divCurrentDept" name="当前部门" marginTop="15px"></ctag:Fold>
                            <div id="divCurrentDept">
                                <form id="MainForm">
                                    <input id="uid" data-alias-table="main" data-search-mode="=" type="hidden" class="form-control"/>
                                </form>
                                <div id="divList" style="width: 100%;">
                                </div>
                            </div>
                        </div>

                        <%@ include file="/WEB-INF/views/sys/dept/user/UserInnerList.jsp" %>

                        <%@ include file="/WEB-INF/views/sys/dept/DeptChildList.jsp" %>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    //<![CDATA[
    function DeptList_Page_Load() {
        SysApp.Sys.DeptListIns = new SysApp.Sys.DeptTreeList();
        var instance = SysApp.Sys.DeptListIns;

        instance.selfInstance = "SysApp.Sys.DeptListIns";
        instance.controller = "${ctx}/sys/dept/";
        instance.clientID = "DeptList";
        instance.tableName = "sys_dept";
        instance.inputInstance = SysApp.Sys.DeptInputIns;
        instance.userInputUrl = "${ctx}/sys/user/input?entry=dept";
        instance.deptListUrl = "${ctx}/sys/dept?entry=menu";
        instance.detailInstance = SysApp.Sys.DeptDetailIns;
        instance.deptChildListInstance = SysApp.Sys.DeptChildListIns;
        instance.deptUserInnerListInstance = SysApp.Sys.DeptUserInnerListIns;
        instance.deptUid = "${deptUid}";
        instance.rootDeptUid = "${rootDeptUid}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.enterpriseUid = "${enterpriseUid}";
        instance.deptTreeUrl = "${ctx}/sys/dept/tree?entry=menu";

        instance.init();
    }

    $(function () {
        DeptList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑控件--%>
<%@ include file="/WEB-INF/views/sys/dept/DeptPopupInput.jsp" %>
