<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content AttachmentImportDetail-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">应用名称</label>
            <label class="col-sm-4 control-label content-label" id="appName"></label>
            <label class="col-sm-2 control-label">模块名称</label>
            <label class="col-sm-4 control-label content-label" id="moduleName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">导入批次号</label>
            <label class="col-sm-4 control-label content-label" id="batchNo"></label>
            <label class="col-sm-2 control-label">文件名称</label>
            <label class="col-sm-4 control-label content-label" id="fileName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">导入者</label>
            <label class="col-sm-4 control-label content-label" id="insertUserName"></label>
            <label class="col-sm-2 control-label">导入日期</label>
            <label class="col-sm-4 control-label content-label" id="insertDate"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">导入结果</label>
            <label class="col-sm-4 control-label content-label" id="importResult"></label>
            <label class="col-sm-2 control-label">文件大小</label>
            <label class="col-sm-4 control-label content-label" id="fileSize"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">导入详细结果</label>
            <label class="col-sm-9 control-label content-label" id="importResultDetailMessage" data-textarea="true" style="word-break: break-all;height: 150px;overflow-y:auto;"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">导入属性1</label>
            <label class="col-sm-4 control-label content-label" id="importAttribute01"></label>
            <label class="col-sm-2 control-label">导入属性2</label>
            <label class="col-sm-4 control-label content-label" id="importAttribute02"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">导入属性3</label>
            <label class="col-sm-4 control-label content-label" id="importAttribute03"></label>
            <label class="col-sm-2 control-label">导入属性4</label>
            <label class="col-sm-4 control-label content-label" id="importAttribute04"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">导入属性5</label>
            <label class="col-sm-4 control-label content-label" id="importAttribute05"></label>
            <label class="col-sm-2 control-label">导入属性6</label>
            <label class="col-sm-4 control-label content-label" id="importAttribute06"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">导入属性7</label>
            <label class="col-sm-4 control-label content-label" id="importAttribute07"></label>
            <label class="col-sm-2 control-label">导入属性8</label>
            <label class="col-sm-4 control-label content-label" id="importAttribute08"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">导入属性9</label>
            <label class="col-sm-4 control-label content-label" id="importAttribute09"></label>
            <label class="col-sm-2 control-label">导入属性10</label>
            <label class="col-sm-4 control-label content-label" id="importAttribute10"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/attachmentimport/AttachmentImportDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function AttachmentImportDetail_Page_Load() {
        SysApp.Sys.AttachmentImportDetailIns = new SysApp.Sys.AttachmentImportDetail();
        var instance = SysApp.Sys.AttachmentImportDetailIns;
        instance.selfInstance = "SysApp.Sys.AttachmentImportDetailIns";
        instance.clientID = "AttachmentImportDetail";
        instance.isDetail = true;
        instance.entry = "${entry}";
        instance.controller = "${ctx}/sys/attachmentimport/";
        instance.init();
    }

    AttachmentImportDetail_Page_Load();
    //]]>
</script>