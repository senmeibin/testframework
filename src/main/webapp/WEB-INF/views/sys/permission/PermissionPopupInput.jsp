﻿<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content PermissionInput-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <input id="uid" type="hidden">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">应用名称</label>
            <div class="col-sm-9">
                <select id="appCode" class="form-control required" data-title="应用名称" style="width: 350px;">
                    <option value="">请选择</option>
                </select>
                <label id="appCode_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">权限类型</label>
            <div class="col-sm-9">
                <select id="typeCd" class="form-control required" data-title="权限类型" style="width: 350px;">
                    <option value="">请选择</option>
                </select>
                <label id="typeCd_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">权限名称</label>
            <div class="col-sm-9">
                <input id="permissionName" type="text" class="form-control required" data-title="权限名称" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                <label id="permissionName_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">域</label>
            <div class="col-sm-9">
                <input id="domain" type="text" class="form-control" data-title="域" maxlength="64" data-rangelength="[0,64]" style="width: 350px;"/>
                <label id="domain_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">目标集合</label>
            <div class="col-sm-9">
                <input id="targets" type="text" class="form-control" data-title="目标集合" maxlength="64" data-rangelength="[0,64]" style="width: 350px;"/>
                <label id="targets_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">行为集合</label>
            <div class="col-sm-9">
                <input id="actions" type="text" class="form-control" data-title="行为集合" maxlength="64" data-rangelength="[0,64]" style="width: 350px;"/>
                <label id="actions_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <div class="col-sm-9">
                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 350px;"></textarea>
                <label id="remark_Error" class="validator-error"></label>
            </div>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Permission}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Permission}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/permission/PermissionInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function PermissionInput_Page_Load() {
        SysApp.Sys.PermissionInputIns = new SysApp.Sys.PermissionInput();
        var instance = SysApp.Sys.PermissionInputIns;
        instance.selfInstance = "SysApp.Sys.PermissionInputIns";
        instance.controller = "${ctx}/sys/permission/";
        instance.clientID = "PermissionInput";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    PermissionInput_Page_Load();
    //]]>
</script>