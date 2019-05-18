<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content PermissionDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">应用名称</label>
            <label class="col-sm-9 control-label content-label" id="appName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">权限类型</label>
            <label class="col-sm-9 control-label content-label" id="typeName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">权限名称</label>
            <label class="col-sm-9 control-label content-label" id="permissionName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">域</label>
            <label class="col-sm-9 control-label content-label" id="domain"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">行为集合</label>
            <label class="col-sm-9 control-label content-label" id="actions"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">目标集合</label>
            <label class="col-sm-9 control-label content-label" id="targets"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <label class="col-sm-9 control-label content-label" id="remark"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/permission/PermissionDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function PermissionDetail_Page_Load() {
        SysApp.Sys.PermissionDetailIns = new SysApp.Sys.PermissionDetail();
        var instance = SysApp.Sys.PermissionDetailIns;
        instance.selfInstance = "SysApp.Sys.PermissionDetailIns";
        instance.controller = "${ctx}/sys/permission/";
        instance.clientID = "PermissionDetail";
        instance.isDetail = true;
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    PermissionDetail_Page_Load();
    //]]>
</script>