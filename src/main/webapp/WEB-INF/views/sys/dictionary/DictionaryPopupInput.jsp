<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content DictionaryInput-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <div class="form-group" style="display: none">
            <div>
                <input id="uid" type="hidden">
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">大区分CD</label>
            <div class="col-sm-9">
                <input id="mainCd" type="text" class="form-control required" data-title="大区分CD" maxlength="8" data-rangelength="[0,8]" style="width: 350px;"/>
                <label id="mainCd_Error" class="validator-error"/>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">大区分名称</label>
            <div class="col-sm-9">
                <input id="mainName" type="text" class="form-control required" data-title="大区分名称" maxlength="64" data-rangelength="[0,64]" style="width: 350px;"/>
                <label id="mainName_Error" class="validator-error"/>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">小区分CD</label>
            <div class="col-sm-9">
                <input id="subCd" type="text" class="form-control required" data-title="小区分CD" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                <label id="subCd_Error" class="validator-error"/>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">小区分名称</label>
            <div class="col-sm-9">
                <input id="subName" type="text" class="form-control required" data-title="小区分名称" maxlength="64" data-rangelength="[0,64]" style="width: 350px;"/>
                <label id="subName_Error" class="validator-error"/>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">表示顺序</label>
            <div class="col-sm-9">
                <input id="dispSeq" type="text" class="form-control required" data-title="表示顺序" maxlength="5" data-rangelength="[0,5]" data-range="[0,99999]" data-digits="true" style="width: 350px;"/>
                <label id="dispSeq_Error" class="validator-error"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">备注</label>
            <div class="col-sm-9">
                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 350px;"></textarea>
            </div>
        </div>
    </form>
    <%--操作按钮区域--%>
    <ctag:ModalFooter showDeleteButton="true" showSaveButton="true"></ctag:ModalFooter>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Dictionary}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/dictionary/DictionaryInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function DictionaryInput_Page_Load() {
        SysApp.Sys.DictionaryInputIns = new SysApp.Sys.DictionaryInput();
        var instance = SysApp.Sys.DictionaryInputIns;
        instance.selfInstance = "SysApp.Sys.DictionaryInputIns";
        instance.clientID = "DictionaryInput";
        instance.controller = "${ctx}/sys/dictionary/";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    DictionaryInput_Page_Load();
    //]]>
</script>