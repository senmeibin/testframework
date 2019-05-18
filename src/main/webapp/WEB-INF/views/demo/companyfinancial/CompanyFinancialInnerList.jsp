<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/CompanyExtendedCommon.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyfinancial/CompanyFinancialInnerList.js?${version}"></script>

<div class="panel-body CompanyFinancialInnerList-MainContent">
    <button id="btnAdd" type="button" class="btn btn-primary">
        <i class="fa fa-plus"></i>新增财务信息
    </button>
    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm">
        <input id="companyUid" data-alias-table="main" data-search-mode="=" type='hidden' class="form-control" data-title=""/>
    </form>
    <input type="hidden" id="jsonListData" value="${jsonDataList_CompanyFinancial}"/>
    <%--上次检索条件--%>
    <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_CompanyFinancial}"/>
    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyFinancial}"/>
    <div class="margin-top-space" id="divList">
    </div>
</div>

<%--POPUP--%>
<%@ include file="/WEB-INF/views/demo/companyfinancial/CompanyFinancialInnerInput.jsp" %>

<script type="text/javascript">
    //<![CDATA[
    function CompanyFinancialInnerList_Page_Load() {
        SysApp.Demo.CompanyFinancialInnerListIns = new SysApp.Demo.CompanyFinancialInnerList();
        var instance = SysApp.Demo.CompanyFinancialInnerListIns;

        instance.selfInstance = "SysApp.Demo.CompanyFinancialInnerListIns";
        instance.controller = "${ctx}/demo/companyfinancial/";
        instance.clientID = "CompanyFinancialInnerList";
        instance.tableName = "demo_company_financial";
        instance.inputInstance = SysApp.Demo.CompanyFinancialInnerInputIns;

        instance.init();
    }

    CompanyFinancialInnerList_Page_Load();
    //]]>
</script>