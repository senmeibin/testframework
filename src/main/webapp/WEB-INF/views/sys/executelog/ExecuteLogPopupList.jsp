<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/executelog/ExecuteLogList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content ExecuteLogList-MainContent" style="width: 1300px; display: none;">
    <div class="modal-header">
        <button class="close" type="button" id="btnTopClose">
            ×
        </button>
        <h4 class="modal-title" id="ctlTitle">执行日志一览</h4>
    </div>

    <div class="modal-body">
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm">
            <input id="executeRecordUid" data-alias-table="main" type="hidden" class="form-control" data-search-mode="=" data-allow-clear="false"/>
            <div class="col-md-2 form-group">
                <label>执行时间(起)</label>
                <div class='input-group datetime-picker'>
                    <input id="insertDate$from_search" data-alias-table="main" type='text' class="form-control required" data-title="执行时间(起)"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
            </div>
            <div class="col-md-2 form-group">
                <label>执行时间(止)</label>
                <div class='input-group datetime-picker'>
                    <input id="insertDate$to_search" data-alias-table="main" type='text' class="form-control required" data-title="执行时间(止)"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
            </div>
            <div class="col-md-2 form-group">
                <label>执行结果</label>
                <select id="result" data-alias-table="main" class="form-control" data-title="功能类型">
                    <option value="">请选择</option>
                    <option value="1">成功</option>
                    <option value="-1">失败</option>
                </select>
            </div>

            <div class="col-md-2 form-group">
                <label>&nbsp;</label>
                <div>
                    <button id="btnSearch" type="button" class="btn btn-primary">
                        <i class="fa fa-search"></i>查询
                    </button>
                    <button id="btnClear" type="button" class="btn btn-default">
                        <i class="fa fa-eraser"></i>清空
                    </button>
                </div>
            </div>
            <div class="clear-both dashed-line">
            </div>
        </form>
        <%--上次检索条件--%>
        <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_ExecuteLog}"/>
        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ExecuteLog}"/>
        <div class="margin-top-space" id="divList" style="width: 100%;height: 370px;overflow-y: auto;">
        </div>
    </div>

    <div class="modal-footer">
        <button type="button" class="btn btn-default" id="btnClose"><i class="fa fa-remove"></i>关闭</button>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ExecuteLogList_Page_Load() {
        SysApp.Sys.ExecuteLogListIns = new SysApp.Sys.ExecuteLogList();
        var instance = SysApp.Sys.ExecuteLogListIns;

        instance.selfInstance = "SysApp.Sys.ExecuteLogListIns";
        instance.clientID = "ExecuteLogList";
        instance.tableName = "sys_execute_log";
        instance.controller = "${ctx}/sys/executelog/";
        instance.detailInstance = SysApp.Sys.ExecuteLogDetailIns;
        instance.autoSearch = false;
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        ExecuteLogList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细执行日志表画面--%>
<%@ include file="/WEB-INF/views/sys/executelog/ExecuteLogPopupDetail.jsp" %>
