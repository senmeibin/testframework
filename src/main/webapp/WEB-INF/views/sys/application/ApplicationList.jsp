<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/application/ApplicationList.js?${version}"></script>

<title>应用模块一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper ApplicationList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">应用模块一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增应用模块
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>应用编号</label>
                    <input id="appCode" data-alias-table="main" type="text" maxlength="16" class="form-control" data-title="应用编号"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>应用名称</label>
                    <input id="appName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="应用名称"/>
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

                    <button id="btnBatchSaveDispSeq" type="button" class="btn btn-primary" style="margin-left: 30px;">
                        <i class="fa fa-save"></i>批量保存表示顺序
                    </button>
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.ApplicationListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Application}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Application}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Application}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ApplicationList_Page_Load() {
        SysApp.Sys.ApplicationListIns = new SysApp.Sys.ApplicationList();
        var instance = SysApp.Sys.ApplicationListIns;

        instance.selfInstance = "SysApp.Sys.ApplicationListIns";
        instance.controller = "${ctx}/sys/application/";
        instance.inputUrl = "${ctx}/sys/application/input";
        instance.clientID = "ApplicationList";
        instance.tableName = "sys_application";
        instance.exportFileName = "应用一览";
        instance.detailInstance = SysApp.Sys.ApplicationDetailIns;
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        ApplicationList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/sys/application/ApplicationPopupDetail.jsp" %>