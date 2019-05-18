﻿<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content FileUploadInput-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal attachment-input">
        <input id="uid" type="hidden">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">应用名称</label>
            <div class="col-sm-9">
                <input id="appName" type="text" class="form-control required" data-title="应用名称" maxlength="8" data-rangelength="[0,8]" style="width: 350px;"/>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">模块名称</label>
            <div class="col-sm-9">
                <select id="moduleName" class="form-control" data-title="模块名称" style="width: 350px;">
                    <option value="">请选择</option>
                </select>
                <label id="moduleName_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">附件分类</label>
            <div class="col-sm-9">
                <input id="categoryCd" type="text" class="form-control" data-title="附件分类" maxlength="8" data-rangelength="[0,8]" style="width: 350px;"/>
                <label id="categoryCd_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">文件名称</label>
            <div class="col-sm-9">
                <input id="fileName" type="text" class="form-control required" data-title="文件名称" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                <label id="fileName_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">文件路径</label>
            <div class="col-sm-9">
                <input id="filePath" type="text" class="form-control required" data-title="文件路径" maxlength="512" data-rangelength="[0,512]" style="width: 350px;"/>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">文件大小</label>
            <div class="col-sm-9">
                <input id="fileSize" type="text" class="form-control required" data-title="文件大小" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
            </div>
        </div>

        <div class="form-group form-inline" style="display: none;">
            <label class="col-sm-3 control-label">MIME类型</label>
            <div class="col-sm-9">
                <input id="mimeType" type="text" class="form-control required" data-title="MIME类型" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">文件描述</label>
            <div class="col-sm-9">
                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="文件描述" data-rangelength="[0,256]" style="width: 350px;"></textarea>
                <label id="remark_Error" class="validator-error"></label>
            </div>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Attachment}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Attachment}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/fileupload/FileUploadInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function FileUploadInput_Page_Load() {
        SysApp.Cmn.FileUploadInputIns = new SysApp.Cmn.FileUploadInput();
        var instance = SysApp.Cmn.FileUploadInputIns;
        instance.selfInstance = "SysApp.Cmn.FileUploadInputIns";
        instance.controller = "${ctx}/cmn/fileupload/";
        instance.clientID = "FileUploadInput";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    FileUploadInput_Page_Load();
    //]]>
</script>