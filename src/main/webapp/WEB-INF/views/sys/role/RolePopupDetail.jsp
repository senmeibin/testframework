<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content RoleDetail-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">应用名称</label>
            <label class="col-sm-9 control-label content-label" id="appName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">角色编号</label>
            <label class="col-sm-9 control-label content-label" id="roleCode"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">角色名称</label>
            <label class="col-sm-9 control-label content-label" id="roleName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">角色描述</label>
            <label class="col-sm-9 control-label content-label" id="description"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">显示顺序</label>
            <label class="col-sm-9 control-label content-label" id="dispSeq"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">备注</label>
            <label class="col-sm-9 control-label content-label" id="remark"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">权限列表</label>
            <div class="col-sm-9" style="width: 500px;padding-top: 5px;">
                <div id="permissions"></div>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">用户列表</label>
            <div class="col-sm-9" style="width: 700px;padding-top: 5px;">
                <div id="users"></div>
            </div>
        </div>
    </div>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Role}"/>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="false" showDeleteButton="false"></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/role/RoleDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function RoleDetail_Page_Load() {
        SysApp.Sys.RoleDetailIns = new SysApp.Sys.RoleDetail();
        var instance = SysApp.Sys.RoleDetailIns;
        instance.selfInstance = "SysApp.Sys.RoleDetailIns";
        instance.controller = "${ctx}/sys/role/";
        instance.clientID = "RoleDetail";
        instance.isDetail = true;
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    RoleDetail_Page_Load();
    //]]>
</script>