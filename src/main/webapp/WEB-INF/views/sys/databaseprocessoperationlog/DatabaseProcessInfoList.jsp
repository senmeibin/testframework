<script type="text/javascript" src="${staticContentsServer}/static/js/sys/databaseprocessoperationlog/DatabaseProcessInfoList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="panel-body DatabaseProcessInfoList-MainContent">
    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm">
        <div class="col-md-2 form-group">
            <label>主机地址</label>
            <input id="host" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="主机地址"/>
        </div>
        <div class="col-md-2 form-group">
            <label>执行信息</label>
            <input id="info" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="执行信息"/>
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
    function DatabaseProcessInfoList_Page_Load() {
        SysApp.Sys.DatabaseProcessInfoListIns = new SysApp.Sys.DatabaseProcessInfoList();
        var instance = SysApp.Sys.DatabaseProcessInfoListIns;
        instance.selfInstance = "SysApp.Sys.DatabaseProcessInfoListIns";
        instance.controller = "${ctx}/sys/databaseprocessoperationlog/";
        instance.clientID = "DatabaseProcessInfoList";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }
    DatabaseProcessInfoList_Page_Load();
    //]]>
</script>
