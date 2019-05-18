<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/syncentity/operationlog/OperationLogList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content OperationLogList-MainContent" style="width: 1300px; display: none;">
    <ctag:ModalHeader modalTitle="操作日志一览"></ctag:ModalHeader>

    <div class="modal-body">
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm">
            <input type="hidden" id="appCode" data-alias-table="main"/>
            <input type="hidden" id="moduleName" data-alias-table="main"/>
            <input type="hidden" id="functionName" value="save,delete" data-alias-table="main" data-search-mode="in"/>
            <div class="col-md-2 form-group">
                <label>操作日期(起)</label>
                <div class='input-group datetime-picker'>
                    <input id="insertDate$from_search" data-alias-table="main" type='text' class="form-control" data-title="操作日期(起)"/>
                    <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                </div>
            </div>
            <div class="col-md-2 form-group">
                <label>操作日期(止)</label>
                <div class='input-group datetime-picker'>
                    <input id="insertDate$to_search" data-alias-table="main" type='text' class="form-control" data-title="操作日期(止)"/>
                    <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                </div>
            </div>
            <div class="col-md-2 form-group">
                <label>操作者</label>
                <input id="userName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="操作者"/>
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
                <ctag:PagerSettingIcon pageInstance="SysApp.Sys.OperationLogListIns"/>
            </div>
        </form>
        <input type="hidden" id="jsonListData" value="${jsonDataList_OperationLog}"/>
        <%--上次检索条件--%>
        <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_OperationLog}"/>
        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_OperationLog}"/>
        <div class="margin-top-space" id="divList" style="width: 100%">
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="false" showDeleteButton="false"></ctag:ModalFooter>
</div>

<script type="text/javascript">
    //<![CDATA[
    function OperationLogList_Page_Load() {
        SysApp.Sys.OperationLogListIns = new SysApp.Sys.OperationLogList();
        var instance = SysApp.Sys.OperationLogListIns;

        instance.selfInstance = "SysApp.Sys.OperationLogListIns";
        instance.controller = "${ctx}/sys/operationlog/";
        instance.clientID = "OperationLogList";
        instance.tableName = "sys_operation_log";
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        OperationLogList_Page_Load();
    });
    //]]>
</script>