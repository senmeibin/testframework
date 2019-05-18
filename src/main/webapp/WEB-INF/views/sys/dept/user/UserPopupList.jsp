<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/dept/user/UserPopupList.js?${version}"></script>
<title>用户一览</title>
<div id="ctlFrame" class="modal-content UserPopupList-MainContent" style="width: 1300px; display: none;">
    <ctag:ModalHeader modalTitle="用户一览"></ctag:ModalHeader>

    <div class="modal-body">
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm">
            <%--用户所属部门--%>
            <input id="deptUid" data-alias-table="main" type="hidden" data-search-mode="="/>

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
                <button id="btnClear" type="button" class="btn btn-default">
                    <i class="fa fa-eraser"></i>清空
                </button>
            </div>
        </form>
        <%--上次检索条件--%>
        <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_User}"/>
        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_User}"/>
        <div class="margin-top-space" id="divList" style="width: 100%;">
        </div>
    </div>

    <div class=" modal-footer">
        <button type="button" class="btn btn-default" id="btnClose"><i class="fa fa-remove"></i>关闭</button>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function UserPopupList_Page_Load() {
        SysApp.Sys.UserPopupListIns = new SysApp.Sys.UserPopupList();
        var instance = SysApp.Sys.UserPopupListIns;
        instance.selfInstance = "SysApp.Sys.UserPopupListIns";
        instance.controller = "${ctx}/sys/user/";
        instance.inputUrl = "${ctx}/sys/user/input";
        instance.clientID = "UserPopupList";
        instance.tableName = "sys_user";
        instance.inputInstance = SysApp.Sys.UserInputIns;
        instance.detailInstance = SysApp.Sys.UserDetailIns;
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        UserPopupList_Page_Load();
    });
    //]]>
</script>