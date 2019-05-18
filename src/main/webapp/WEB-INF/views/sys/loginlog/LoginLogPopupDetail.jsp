<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content LoginLogDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">用户名</label>
            <label class="col-sm-9 control-label content-label" id="userCd"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">用户名称</label>
            <label class="col-sm-9 control-label content-label" id="userName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">登录IP</label>
            <label class="col-sm-9 control-label content-label" id="remoteIp"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">登录类型</label>
            <label class="col-sm-9 control-label content-label" id="loginTypeName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">登录结果</label>
            <label class="col-sm-9 control-label content-label" id="loginResultName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">路径</label>
            <label class="col-sm-9 control-label content-label" id="url"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">参数</label>
            <label class="col-sm-9 control-label content-label" id="parameters" data-textarea="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <label class="col-sm-9 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/loginlog/LoginLogDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function LoginLogDetail_Page_Load() {
        SysApp.Sys.LoginLogDetailIns = new SysApp.Sys.LoginLogDetail();
        var instance = SysApp.Sys.LoginLogDetailIns;
        instance.selfInstance = "SysApp.Sys.LoginLogDetailIns";
        instance.controller = "${ctx}/sys/loginlog/";
        instance.clientID = "LoginLogDetail";
        instance.isDetail = true;
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    LoginLogDetail_Page_Load();
    //]]>
</script>