<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companycopyright/CompanyCopyrightList.js?${version}"></script>

<title>专利版权一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper CompanyCopyrightList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">专利版权一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>企业名称</label>
                    <input id="companyName" data-alias-table="company" type="text" maxlength="32" class="form-control" data-title="企业名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>年份</label>
                    <input id="year" data-alias-table="main" type="text" maxlength="4" class="form-control" data-title="年份"/>
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
            <input type="hidden" id="jsonListData" value="${jsonDataList_CompanyCopyright}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_CompanyCopyright}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyCopyright}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_CompanyCopyright}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function CompanyCopyrightList_Page_Load() {
        SysApp.Demo.CompanyCopyrightListIns = new SysApp.Demo.CompanyCopyrightList();
        var instance = SysApp.Demo.CompanyCopyrightListIns;

        instance.selfInstance = "SysApp.Demo.CompanyCopyrightListIns";
        instance.controller = "${ctx}/demo/companycopyright/";
        instance.inputUrl = "${ctx}/demo/companycopyright/input";
        instance.clientID = "CompanyCopyrightList";
        instance.tableName = "demo_company_copyright";
        instance.detailInstance = SysApp.Demo.CompanyCopyrightDetailIns;

        instance.init();
    }

    $(function () {
        CompanyCopyrightList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/demo/companycopyright/CompanyCopyrightPopupDetail.jsp" %>
<ctag:ColumnSettingPopup pageInstance="SysApp.Demo.CompanyCopyrightListIns" width="1000px"/>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.CompanyCopyrightListIns"/>