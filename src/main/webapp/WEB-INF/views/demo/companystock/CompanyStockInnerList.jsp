﻿<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/CompanyExtendedCommon.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companystock/CompanyStockInnerList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="panel-body CompanyStockInnerList-MainContent">
    <button id="btnAdd" type="button" class="btn btn-primary">
        <i class="fa fa-plus"></i>新增股权信息
    </button>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm">
        <input id="companyUid" data-alias-table="main" data-search-mode="=" type='hidden' class="form-control" data-title=""/>
    </form>
    <input type="hidden" id="jsonListData" value="${jsonDataList_CompanyStock}"/>
    <%--上次检索条件--%>
    <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_CompanyStock}"/>
    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyStock}"/>
    <div class="margin-top-space" id="divList">
    </div>
</div>

<%--POPUP编辑控件--%>
<%@ include file="/WEB-INF/views/demo/companystock/CompanyStockInnerInput.jsp" %>

<script type="text/javascript">
    //<![CDATA[
    function CompanyStockInnerList_Page_Load() {
        SysApp.Demo.CompanyStockInnerListIns = new SysApp.Demo.CompanyStockInnerList();
        var instance = SysApp.Demo.CompanyStockInnerListIns;

        instance.selfInstance = "SysApp.Demo.CompanyStockInnerListIns";
        instance.controller = "${ctx}/demo/companystock/";
        instance.clientID = "CompanyStockInnerList";
        instance.tableName = "demo_company_stock";
        instance.inputInstance = SysApp.Demo.CompanyStockInnerInputIns;

        instance.init();
    }

    CompanyStockInnerList_Page_Load();
    //]]>
</script>