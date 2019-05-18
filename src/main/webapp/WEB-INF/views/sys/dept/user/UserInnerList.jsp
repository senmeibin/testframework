<script type="text/javascript" src="${staticContentsServer}/static/js/sys/dept/user/UserInnerList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="DeptUserInnerList-MainContent">
    <ctag:Fold id="divBelongUser" name="所属用户" marginTop="15px"></ctag:Fold>
    <div id="divBelongUser">
        <form id="MainForm" class="margin-top-space" style="display: none;">
            <input id="deptUid" data-alias-table="main" data-search-mode="=" type="hidden" class="form-control" data-title="父级部门"/>
            <div class="col-md-2 form-group">
                <label>用户名</label>
                <input id="userCd" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="用户名"/>
            </div>
            <div class="col-md-2 form-group">
                <label>姓名</label>
                <input id="userName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="姓名"/>
            </div>
            <div class="col-md-2 form-group">
                <label>邮箱地址</label>
                <input id="userMail" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="邮箱地址"/>
            </div>
            <div class="col-md-2 form-group">
                <label>手机号码</label>
                <input id="userPhone" data-alias-table="main" type="text" maxlength="11" class="form-control" data-title="手机号码"/>
            </div>
            <div class="clear-both dashed-line">
            </div>
            <div>
                <button id="btnSearch" type="button" class="btn btn-primary">
                    <i class="fa fa-search"></i>查询
                </button>
                <shiro:hasRole name="DataExport">
                    <button id="btnExport" type="button" class="btn btn-primary">
                        <i class="fa fa-cloud-download"></i>
                        导出
                    </button>
                </shiro:hasRole>
                <button id="btnClear" type="button" class="btn btn-default">
                    <i class="fa fa-eraser"></i>清空
                </button>
            </div>
        </form>
        <div id="divList" style="width: 100%;">
        </div>
    </div>
</div>
<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/sys/user/UserPopupDetail.jsp" %>

<script type="text/javascript">
    //<![CDATA[
    function DeptUserInnerList_Page_Load() {
        SysApp.Sys.DeptUserInnerListIns = new SysApp.Sys.DeptUserInnerList();
        var instance = SysApp.Sys.DeptUserInnerListIns;
        instance.selfInstance = "SysApp.Sys.DeptUserInnerListIns";
        instance.controller = "${ctx}/sys/user/";
        instance.clientID = "DeptUserInnerList";
        instance.tableName = "sys_user";
        instance.userInputUrl = "${ctx}/sys/user/input?entry=dept";
        instance.detailInstance = SysApp.Sys.UserDetailIns;
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }
    DeptUserInnerList_Page_Load();
    //]]>
</script>