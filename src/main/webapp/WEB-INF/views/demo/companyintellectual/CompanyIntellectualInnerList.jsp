<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/CompanyExtendedCommon.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyintellectual/CompanyIntellectualInnerList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="panel-body CompanyIntellectualInnerList-MainContent">
    <button id="btnAdd" type="button" class="btn btn-primary">
        <i class="fa fa-plus"></i>新增知识产权
    </button>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm">
        <input id="companyUid" data-alias-table="main" data-search-mode="=" type='hidden' class="form-control" data-title=""/>
    </form>
    <input type="hidden" id="jsonListData" value="${jsonDataList_CompanyIntellectual}"/>
    <%--上次检索条件--%>
    <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_CompanyIntellectual}"/>
    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyIntellectual}"/>
    <div class="margin-top-space" id="divList">
    </div>
</div>

<%--POPUP编辑控件--%>
<%@ include file="/WEB-INF/views/demo/companyintellectual/CompanyIntellectualInnerInput.jsp" %>

<script type="text/javascript">
    //<![CDATA[
    function CompanyIntellectualInnerList_Page_Load() {
        SysApp.Demo.CompanyIntellectualInnerListIns = new SysApp.Demo.CompanyIntellectualInnerList();
        var instance = SysApp.Demo.CompanyIntellectualInnerListIns;

        instance.selfInstance = "SysApp.Demo.CompanyIntellectualInnerListIns";
        instance.controller = "${ctx}/demo/companyintellectual/";
        instance.clientID = "CompanyIntellectualInnerList";
        instance.tableName = "demo_company_intellectual";
        instance.inputInstance = SysApp.Demo.CompanyIntellectualInnerInputIns;

        instance.init();
    }

    CompanyIntellectualInnerList_Page_Load();
    //]]>
</script>