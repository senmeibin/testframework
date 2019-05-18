<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/sys/operationsummary/OperationSummaryList.js?${version}"></script>

<title>访问量统计</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper OperationSummaryList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">访问量统计</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>路径</label>
                    <input id="url" data-camel-field="true" data-alias-table="main" type="text" maxlength="512" class="form-control" data-title="路径"/>
                </div>
                <div class="clear-both dashed-line margin-top-space">
                </div>

                <div class="margin-top-space">
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

                    <input id="includeIgnoreUrl" style="margin-left: 30px" data-alias-table="main" data-search-mode="$ignore_search" data-allow-clear="false" ignore_search="true" type="checkbox" data-title="包含忽略Url"/>
                    <label for="includeIgnoreUrl" style="color: red;">包含忽略路径</label>

                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.OperationSummaryListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_OperationSummary}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_OperationSummary}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_OperationSummary}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function OperationSummaryList_Page_Load() {
        SysApp.Sys.OperationSummaryListIns = new SysApp.Sys.OperationSummaryList();
        var instance = SysApp.Sys.OperationSummaryListIns;

        instance.selfInstance = "SysApp.Sys.OperationSummaryListIns";
        instance.controller = "${ctx}/sys/operationsummary/";
        instance.clientID = "OperationSummaryList";
        instance.tableName = "sys_operation_summary";
        instance.entry = "${entry}";
        instance.pageSize = "${pageSize}";

        instance.init();
    }

    $(function () {
        OperationSummaryList_Page_Load();
    });
    //]]>
</script>

