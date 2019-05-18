<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/sys/sensitivedataexportlog/SensitiveDataExportLogList.js?${version}"></script>

<title>敏感数据导出日志一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper SensitiveDataExportLogList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">敏感数据导出日志一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>手机号码</label>
                    <input id="mobile" data-camel-field="true" data-alias-table="main" type="text" maxlength="11" class="form-control" data-title="手机号码"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>邮箱地址</label>
                    <input id="email" data-camel-field="true" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="邮箱地址"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>路径</label>
                    <input id="url" data-camel-field="true" data-alias-table="main" type="text" maxlength="512" class="form-control" data-title="路径"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>远程IP</label>
                    <input id="remoteIp" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="远程IP"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.SensitiveDataExportLogListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_SensitiveDataExportLog}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_SensitiveDataExportLog}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_SensitiveDataExportLog}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function SensitiveDataExportLogList_Page_Load() {
        SysApp.Sys.SensitiveDataExportLogListIns = new SysApp.Sys.SensitiveDataExportLogList();
        var instance = SysApp.Sys.SensitiveDataExportLogListIns;

        instance.selfInstance = "SysApp.Sys.SensitiveDataExportLogListIns";
        instance.controller = "${ctx}/sys/sensitivedataexportlog/";
        instance.clientID = "SensitiveDataExportLogList";
        instance.tableName = "sys_sensitive_data_export_log";
        instance.entry = "${entry}";
        instance.pageSize = "${pageSize}";

        instance.init();
    }

    $(function () {
        SensitiveDataExportLogList_Page_Load();
    });
    //]]>
</script>

