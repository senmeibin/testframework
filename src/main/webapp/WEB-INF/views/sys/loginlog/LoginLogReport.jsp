<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/loginlog/LoginLogReport.js?${version}"></script>

<title>登录统计</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper LoginLogReport-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <div class="panel-title">登录统计</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>开始日期(起)</label>
                    <div class='input-group datetime-picker'>
                        <input id="beginDate" data-search-mode="ignore_search" type='text' data-allow-clear="false" class="form-control required" data-title="开始日期(起)"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
                <div class="col-md-2 form-group">
                    <label>结束日期(止)</label>
                    <div class='input-group datetime-picker'>
                        <input id="endDate" data-search-mode="ignore_search" type='text' data-allow-clear="false" class="form-control required" data-title="结束日期(止)"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
                <div class="col-md-2 form-group">
                    <label>所属分公司</label>
                    <ctag:ComboCheckTree valueDomId="uid" aliasTable="company" textDomId="companyName" parentInstance="SysApp.Sys.LoginLogReportIns" dataTitle="所属分公司" searchMode="in"
                                         idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dispName="deptFullName" dataUrl="${ctx}/sys/dept/getAllCompanyList"/>
                </div>

                <div class="col-md-2 form-group">
                    <label>所属部门</label>
                    <ctag:ComboCheckTree valueDomId="deptUid" textDomId="deptName" aliasTable="main" parentInstance="SysApp.Sys.LoginLogReportIns" dataTitle="所属部门" searchMode="in"
                                         idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dispName="deptFullName" dataUrl="${ctx}/sys/dept/getAllDeptList"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>职位</label>
                    <input id="positionName" data-alias-table="position" type="text" maxlength="32" class="form-control" data-title="职位"/>
                </div>

                <div class="col-md-12 form-group" style="padding-left: 0px;">
                    <label>姓名</label>
                    <input id="userName" data-alias-table="main" type="text" class="form-control" data-search-mode="in" data-title="姓名（多个时半角逗号分隔）"/>
                </div>
                <div class="col-md-12 form-group" style="padding-left: 0px;">
                    <label>邮箱</label>
                    <input id="user_mail" data-alias-table="main" type="text" class="form-control" data-search-mode="in" data-title="邮箱（多个时半角逗号分隔）"/>
                </div>

                <div class="clear-both dashed-line">
                </div>

                <div>
                    <div>
                        <button id="btnSearch" type="button" class="btn btn-primary">
                            <i class="fa fa-search"></i>
                            查询
                        </button>
                        <shiro:hasRole name="DataExport">
                            <button id="btnExport" type="button" class="btn btn-primary">
                                <i class="fa fa-cloud-download"></i>导出
                            </button>
                        </shiro:hasRole>
                        <button id="btnClear" type="button" class="btn btn-default">
                            <i class="fa fa-eraser"></i>
                            清空
                        </button>
                        <ctag:PagerSettingIcon pageInstance="SysApp.Sys.LoginLogReportIns"/>
                    </div>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_LoginLog}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_LoginLog}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_LoginLog}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function LoginLogReport_Page_Load() {
        SysApp.Sys.LoginLogReportIns = new SysApp.Sys.LoginLogReport();
        var instance = SysApp.Sys.LoginLogReportIns;

        instance.selfInstance = "SysApp.Sys.LoginLogReportIns";
        instance.controller = "${ctx}/sys/loginlog/";
        instance.searchMethod = "reportSearch";
        instance.clientID = "LoginLogReport";
        instance.detailInstance = SysApp.Sys.LoginLogDetailIns;
        instance.pageSize = "${pageSize}";
        instance.init();
    }

    $(function () {
        LoginLogReport_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细控件--%>
<%@ include file="LoginLogPopupList.jsp" %>