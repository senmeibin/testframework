﻿<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companypersonnel/CompanyPersonnelList.js?${version}"></script>

<title>人员结构一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper CompanyPersonnelList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">人员结构一览</div>
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
            <input type="hidden" id="jsonListData" value="${jsonDataList_CompanyPersonnel}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_CompanyPersonnel}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyPersonnel}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_CompanyPersonnel}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function CompanyPersonnelList_Page_Load() {
        SysApp.Demo.CompanyPersonnelListIns = new SysApp.Demo.CompanyPersonnelList();
        var instance = SysApp.Demo.CompanyPersonnelListIns;

        instance.selfInstance = "SysApp.Demo.CompanyPersonnelListIns";
        instance.controller = "${ctx}/demo/companypersonnel/";
        instance.inputUrl = "${ctx}/demo/companypersonnel/input";
        instance.clientID = "CompanyPersonnelList";
        instance.tableName = "demo_company_personnel";
        instance.detailInstance = SysApp.Demo.CompanyPersonnelDetailIns;

        instance.init();
    }

    $(function () {
        CompanyPersonnelList_Page_Load();
    });
    //]]>
</script>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/demo/companypersonnel/CompanyPersonnelPopupDetail.jsp" %>
<ctag:ColumnSettingPopup pageInstance="SysApp.Demo.CompanyPersonnelListIns" width="1000px"/>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.CompanyPersonnelListIns"/>