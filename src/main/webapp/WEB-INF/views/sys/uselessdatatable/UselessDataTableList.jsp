<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/uselessdatatable/UselessDataTableList.js?${version}"></script>

<title>数据表一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper UselessDataTableList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">数据表一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>表名称</label>
                    <input id="tableName" data-alias-table="main" type="text" maxlength="64" class="form-control required" data-title="表名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>表注释</label>
                    <input id="tableComment" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="表注释"/>
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
            <input type="hidden" id="jsonListData" value="${jsonDataList_UselessDataTable}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_UselessDataTable}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_UselessDataTable}"/>
            <div class="margin-top-space" id="divList">
                <div class="message-nodata">请输入查询条件进行数据查询</div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function UselessDataTableList_Page_Load() {
        SysApp.Sys.UselessDataTableListIns = new SysApp.Sys.UselessDataTableList();
        var instance = SysApp.Sys.UselessDataTableListIns;

        instance.selfInstance = "SysApp.Sys.UselessDataTableListIns";
        instance.controller = "${ctx}/sys/uselessdatatable/";
        instance.clientID = "UselessDataTableList";
        instance.init();
    }

    $(function () {
        UselessDataTableList_Page_Load();
    });
    //]]>
</script>

