<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content UserDetail-MainContent" style="width: 1100px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">部门</label>
            <label class="col-lg-3 control-label content-label" id="deptName"></label>
            <label class="col-lg-1 control-label">职位</label>
            <label class="col-lg-3 control-label content-label" id="positionName"></label>
            <label class="col-lg-1 control-label">用户名</label>
            <label class="col-lg-2 control-label content-label" id="userCd"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">姓名</label>
            <label class="col-lg-3 control-label content-label" id="userName"></label>
            <label class="col-lg-1 control-label">英文名</label>
            <label class="col-lg-3 control-label content-label" id="userEnName"></label>
            <label class="col-lg-1 control-label">工号</label>
            <label class="col-lg-2 control-label content-label" id="userNumber"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">性别</label>
            <label class="col-lg-3 control-label content-label" id="sexName"></label>
            <label class="col-lg-1 control-label">出生日期</label>
            <label class="col-lg-3 control-label content-label" id="birthday" data-date="true"></label>
            <label class="col-lg-1 control-label">手机号码</label>
            <label class="col-lg-2 control-label content-label" id="userPhone"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">邮箱地址</label>
            <label class="col-lg-3 control-label content-label" id="userMail"></label>
            <label class="col-lg-1 control-label">分机号码</label>
            <label class="col-lg-3 control-label content-label" id="userExtensionNumber"></label>
            <label class="col-lg-1 control-label">传真号码</label>
            <label class="col-lg-2 control-label content-label" id="userFaxphone"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">家庭地址</label>
            <label class="col-lg-3 control-label content-label" id="userAddress"></label>
            <label class="col-lg-1 control-label">邮政编码</label>
            <label class="col-lg-3 control-label content-label" id="userZipcode"></label>
            <label class="col-lg-1 control-label">强制密码变更</label>
            <label class="col-lg-2 control-label content-label" id="forceChangePswd"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">密码变更时间</label>
            <label class="col-lg-3 control-label content-label" id="changePswdDatetime" data-date="true"></label>
            <label class="col-lg-1 control-label">入职日期</label>
            <label class="col-lg-3 control-label content-label" id="entryDate" data-date="true"></label>
            <label class="col-lg-1 control-label">退职日期</label>
            <label class="col-lg-2 control-label content-label" id="retireDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">考勤号</label>
            <label class="col-lg-3 control-label content-label" id="attendanceNo"></label>
            <label class="col-lg-1 control-label">账号锁定标志</label>
            <label class="col-lg-3 control-label content-label" id="accountLock"></label>
            <label class="col-lg-1 control-label">可访问IP</label>
            <label class="col-lg-2 control-label content-label" id="accessIp"></label>
        </div>

        <div class="clear-both dashed-line">
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">角色列表</label>
            <div class="col-sm-10" style="width: 800px;padding-top: 5px;">
                <div id="roles" style="padding-top: 2px;"></div>
            </div>
        </div>
        <div class="clear-both dashed-line">
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">用户客户列表</label>
            <div class="col-sm-10" style="width: 800px;padding-top: 5px;">
                <div id="customerList" style="padding-top: 2px;"></div>
            </div>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/user/UserDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function UserDetail_Page_Load() {
        SysApp.Sys.UserDetailIns = new SysApp.Sys.UserDetail();
        var instance = SysApp.Sys.UserDetailIns;
        instance.selfInstance = "SysApp.Sys.UserDetailIns";
        instance.clientID = "UserDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/sys/user/";
        instance.init();
    }

    UserDetail_Page_Load();
    //]]>
</script>