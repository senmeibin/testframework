<script type="text/javascript" src="${staticContentsServer}/static/js/sys/dept/DeptChildList.js?${version}"></script>

<title>当前选择的下属部门一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="DeptChildList-MainContent">
    <ctag:Fold id="divDeptChild" name="下级部门" marginTop="15px"></ctag:Fold>
    <div id="divDeptChild">
        <form id="MainForm">
            <input id="parentDeptUid" data-alias-table="main" data-search-mode="=" type="hidden" class="form-control" data-title="父级部门"/>
        </form>
        <div id="divList" style="width: 100%;">
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function DeptChildList_Page_Load() {
        SysApp.Sys.DeptChildListIns = new SysApp.Sys.DeptChildList();
        var instance = SysApp.Sys.DeptChildListIns;

        instance.selfInstance = "SysApp.Sys.DeptChildListIns";
        instance.controller = "${ctx}/sys/dept/";
        instance.clientID = "DeptChildList";
        instance.inputInstance = SysApp.Sys.DeptInputIns;
        instance.tableName = "sys_dept";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    $(function () {
        DeptChildList_Page_Load();
    });
    //]]>
</script>