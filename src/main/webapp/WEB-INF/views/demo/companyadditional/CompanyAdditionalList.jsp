<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyadditional/CompanyAdditionalList.js?${version}"></script>

<title>附加信息一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper CompanyAdditionalList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">附加信息一览</div>
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
                    <label>证券代码</label>
                    <input id="stockCode" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="证券代码"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>上市时间(起)</label>
                    <ctag:CalendarSelect id="listedTime$from_search" title="上市时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>上市时间(止)</label>
                    <ctag:CalendarSelect id="listedTime$to_search" title="上市时间(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>上市市场</label>
                    <select id="listedTypeCd" data-alias-table="main" class="form-control" data-title="上市市场">
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
            <input type="hidden" id="jsonListData" value="${jsonDataList_CompanyAdditional}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_CompanyAdditional}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyAdditional}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function CompanyAdditionalList_Page_Load() {
        SysApp.Demo.CompanyAdditionalListIns = new SysApp.Demo.CompanyAdditionalList();
        var instance = SysApp.Demo.CompanyAdditionalListIns;

        instance.selfInstance = "SysApp.Demo.CompanyAdditionalListIns";
        instance.controller = "${ctx}/demo/companyadditional/";
        instance.inputUrl = "${ctx}/demo/companyadditional/input";
        instance.clientID = "CompanyAdditionalList";
        instance.tableName = "demo_company_additional";
        instance.detailInstance = SysApp.Demo.CompanyAdditionalDetailIns;

        instance.init();
    }

    $(function () {
        CompanyAdditionalList_Page_Load();
    });
    //]]>
</script>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/demo/companyadditional/CompanyAdditionalPopupDetail.jsp" %>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.CompanyAdditionalListIns"/>