<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content AttachmentDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">应用名称</label>
            <label class="col-sm-9 control-label content-label" id="appName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">模块名称</label>
            <label class="col-sm-9 control-label content-label" id="moduleName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">附件分类</label>
            <label class="col-sm-9 control-label content-label" id="categoryCd"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">文件名称</label>
            <label class="col-sm-9 control-label content-label" id="fileName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">文件路径</label>
            <label class="col-sm-9 control-label content-label" id="filePath"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">文件大小</label>
            <label class="col-sm-9 control-label content-label" id="fileSize"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">MIME类型</label>
            <label class="col-sm-9 control-label content-label" id="mimeType"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">预览次数</label>
            <label class="col-sm-9 control-label content-label" id="previewCount"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">下载次数</label>
            <label class="col-sm-9 control-label content-label" id="downloadCount"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/attachment/AttachmentDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function AttachmentDetail_Page_Load() {
        SysApp.Sys.AttachmentDetailIns = new SysApp.Sys.AttachmentDetail();
        var instance = SysApp.Sys.AttachmentDetailIns;
        instance.selfInstance = "SysApp.Sys.AttachmentDetailIns";
        instance.controller = "${ctx}/sys/attachment/";
        instance.clientID = "AttachmentDetail";
        instance.isDetail = true;
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    AttachmentDetail_Page_Load();
    //]]>
</script>