<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/CompanyExtendedCommon.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyteam/CompanyTeamInnerList.js?${version}"></script>

<title>核心团队人员一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="panel-body CompanyTeamInnerList-MainContent">
    <button id="btnAdd" type="button" class="btn btn-primary">
        <i class="fa fa-plus"></i>新增核心人员
    </button>
    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm">
        <input id="companyUid" data-alias-table="main" data-search-mode="=" type='hidden' class="form-control" data-title=""/>
    </form>
    <input type="hidden" id="jsonListData" value="${jsonDataList_CompanyTeam}"/>
    <%--上次检索条件--%>
    <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_CompanyTeam}"/>
    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyTeam}"/>
    <div class="margin-top-space" id="divList">
    </div>
</div>

<%--POPUP编辑控件--%>
<%@ include file="/WEB-INF/views/demo/companyteam/CompanyTeamInnerInput.jsp" %>

<script type="text/javascript">
    //<![CDATA[
    function CompanyTeamInnerList_Page_Load() {
        SysApp.Demo.CompanyTeamInnerListIns = new SysApp.Demo.CompanyTeamInnerList();
        var instance = SysApp.Demo.CompanyTeamInnerListIns;

        instance.selfInstance = "SysApp.Demo.CompanyTeamInnerListIns";
        instance.controller = "${ctx}/demo/companyteam/";
        instance.clientID = "CompanyTeamInnerList";
        instance.tableName = "demo_company_team";
        instance.inputInstance = SysApp.Demo.CompanyTeamInnerInputIns;

        instance.init();
    }

    CompanyTeamInnerList_Page_Load();
    //]]>
</script>