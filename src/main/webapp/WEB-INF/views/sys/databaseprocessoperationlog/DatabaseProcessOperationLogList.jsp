<script type="text/javascript" src="${staticContentsServer}/static/js/sys/databaseprocessoperationlog/DatabaseProcessOperationLogList.js?${version}"></script>

<div class="panel-body DatabaseProcessOperationLogList-MainContent">
    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm">
        <div class="col-md-2 form-group">
            <label>用户</label>
            <input id="userName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="用户"/>
        </div>
        <div class="col-md-2 form-group">
            <label>主机地址</label>
            <input id="hostName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="主机地址"/>
        </div>
        <div class="col-md-2 form-group">
            <label>数据库名</label>
            <input id="databaseName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="数据库名"/>
        </div>
        <div class="col-md-2 form-group">
            <label>执行等待时间</label>
            <input id="executeWaitTime" data-alias-table="main" type="text" maxlength="7" class="form-control" data-title="执行等待时间"/>
        </div>
        <div class="col-md-2 form-group">
            <label>执行信息</label>
            <input id="executeInformation" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="执行信息"/>
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
    <input type="hidden" id="jsonListData" value="${jsonDataList_DatabaseProcessOperationLog}"/>
    <%--上次检索条件--%>
    <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_DatabaseProcessOperationLog}"/>
    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_DatabaseProcessOperationLog}"/>
    <div class="margin-top-space" id="divList">
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function DatabaseProcessOperationLogList_Page_Load() {
        SysApp.Sys.DatabaseProcessOperationLogListIns = new SysApp.Sys.DatabaseProcessOperationLogList();
        var instance = SysApp.Sys.DatabaseProcessOperationLogListIns;

        instance.selfInstance = "SysApp.Sys.DatabaseProcessOperationLogListIns";
        instance.controller = "${ctx}/sys/databaseprocessoperationlog/";
        instance.clientID = "DatabaseProcessOperationLogList";
        instance.tableName = "sys_database_process_operation_log";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        DatabaseProcessOperationLogList_Page_Load();
    });
    //]]>
</script>