<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/dept/DeptList.js?${version}"></script>

<title>部门一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper DeptList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">部门一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增部门
            </button>

            <!--有配置DATA_IMPORT_SYS_API才显示同步按钮 -->
            <c:if test="${dataImportApiExist}">
                <button id="btnSyncData" type="button" class="btn btn-danger">
                    <i class="fa fa-spinner"></i>同步数据
                </button>
            </c:if>

            <button id="btnDeptList" type="button" class="btn btn-default">
                <i class="fa fa-sitemap"></i>组织架构树形展示
            </button>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Dept}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Dept}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Dept}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function DeptList_Page_Load() {
        SysApp.Sys.DeptListIns = new SysApp.Sys.DeptList();
        var instance = SysApp.Sys.DeptListIns;

        instance.selfInstance = "SysApp.Sys.DeptListIns";
        instance.controller = "${ctx}/sys/dept/";
        instance.deptListUrl = "${ctx}/sys/dept/tree?entry=menu";
        instance.clientID = "DeptList";
        instance.tableName = "sys_dept";
        instance.inputInstance = SysApp.Sys.DeptInputIns;
        instance.detailInstance = SysApp.Sys.DeptDetailIns;
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    $(function () {
        DeptList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑控件--%>
<%@ include file="/WEB-INF/views/sys/dept/DeptPopupInput.jsp" %>
<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/sys/dept/DeptPopupDetail.jsp" %>
<%--POPUP用户list--%>
<%@ include file="/WEB-INF/views/sys/dept/user/UserPopupList.jsp" %>
