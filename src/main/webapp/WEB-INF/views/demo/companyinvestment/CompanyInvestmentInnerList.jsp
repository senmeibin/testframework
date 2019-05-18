<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/CompanyExtendedCommon.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyinvestment/CompanyInvestmentInnerList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="panel-body CompanyInvestmentInnerList-MainContent">
    <button id="btnAdd" type="button" class="btn btn-primary">
        <i class="fa fa-plus"></i>新增附加信息
    </button>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm">
        <input id="companyUid" data-alias-table="main" data-search-mode="=" type='hidden' class="form-control" data-title=""/>
    </form>
    <input type="hidden" id="jsonListData" value="${jsonDataList_CompanyInvestment}"/>
    <%--上次检索条件--%>
    <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_CompanyInvestment}"/>
    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyInvestment}"/>
    <div class="margin-top-space" id="divList">
    </div>
</div>

<%--POPUP编辑控件--%>
<%@ include file="/WEB-INF/views/demo/companyinvestment/CompanyInvestmentInnerInput.jsp" %>

<script type="text/javascript">
    //<![CDATA[
    function CompanyInvestmentInnerList_Page_Load() {
        SysApp.Demo.CompanyInvestmentInnerListIns = new SysApp.Demo.CompanyInvestmentInnerList();
        var instance = SysApp.Demo.CompanyInvestmentInnerListIns;

        instance.selfInstance = "SysApp.Demo.CompanyInvestmentInnerListIns";
        instance.controller = "${ctx}/demo/companyinvestment/";
        instance.clientID = "CompanyInvestmentInnerList";
        instance.tableName = "demo_company_investment";
        instance.inputInstance = SysApp.Demo.CompanyInvestmentInnerInputIns;

        instance.init();
    }

    CompanyInvestmentInnerList_Page_Load();
    //]]>
</script>