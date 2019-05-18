<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyinvestment/CompanyInvestmentList.js?${version}"></script>

<title>投融资一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper CompanyInvestmentList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">投融资一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>企业名称</label>
                    <input id="companyName" data-alias-table="company" type="text" maxlength="32" class="form-control" data-title="企业名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>接收投资时间(起)</label>
                    <ctag:CalendarSelect id="investmentDate$from_search" title="接收投资时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>接收投资时间(止)</label>
                    <ctag:CalendarSelect id="investmentDate$to_search" title="接收投资时间(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>投资人</label>
                    <input id="investor" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="投资人"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>是否属于孵化基金</label>
                    <select id="incubationFundCd" data-alias-table="main" class="form-control" data-title="是否属于孵化基金">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="clear-both dashed-line">
                </div>

                <div>
                    <button id="btnSearch" type="button" class="btn btn-primary">
                        <i class="fa fa-search"></i>查询
                    </button>
                    <shiro:hasRole name="DataExport">
                        <button id="btnExport" type="button" class="btn btn-primary">
                            <i class="fa fa-cloud-download"></i>导出
                        </button>
                    </shiro:hasRole>
                    <button id="btnClear" type="button" class="btn btn-default">
                        <i class="fa fa-eraser"></i>清空
                    </button>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_CompanyInvestment}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_CompanyInvestment}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyInvestment}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function CompanyInvestmentList_Page_Load() {
        SysApp.Demo.CompanyInvestmentListIns = new SysApp.Demo.CompanyInvestmentList();
        var instance = SysApp.Demo.CompanyInvestmentListIns;

        instance.selfInstance = "SysApp.Demo.CompanyInvestmentListIns";
        instance.controller = "${ctx}/demo/companyinvestment/";
        instance.inputUrl = "${ctx}/demo/companyinvestment/input";
        instance.clientID = "CompanyInvestmentList";
        instance.tableName = "demo_company_investment";
        instance.detailInstance = SysApp.Demo.CompanyInvestmentDetailIns;

        instance.init();
    }

    $(function () {
        CompanyInvestmentList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/demo/companyinvestment/CompanyInvestmentPopupDetail.jsp" %>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.CompanyInvestmentListIns"/>