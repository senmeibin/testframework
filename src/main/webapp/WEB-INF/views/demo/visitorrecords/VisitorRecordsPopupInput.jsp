<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content VisitorRecordsInput-MainContent" style="width: 1200px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <input id="uid" type="hidden">
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">所属基地</label>
            <div class="col-lg-4 select2-required">
                <ctag:Select2 selectId="baseUid" dataTitle="所属基地" required="required" style="width: 220px;"/>
                <label id="baseUid_Error" class="validator-error"></label>
            </div>
            <label class="col-lg-1 control-label">来访单位</label>
            <div class="col-lg-4">
                <input id="visitingUnits" type="text" class="form-control" data-title="来访单位" maxlength="64" data-rangelength="[0,64]" style="width: 220px;"/>
                <label id="visitingUnits_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">来访时间</label>
            <div class="col-lg-4">
                <ctag:CalendarSelect id="visitingTime" title="来访时间" showValidateError="true" width="181px"></ctag:CalendarSelect>
            </div>
            <label class="col-lg-1 control-label">接待人</label>
            <div class="col-lg-4">
                <ctag:ComboTree valueDomId="receptionistUid" textDomId="receptionistName" parentInstance="SysApp.Demo.VisitorRecordsInputIns" dataTitle="接待人" style="width:181px;" required="required"
                                selectParent="false" idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dispName="deptName" dataUrl="${ctx}/demo/user/getAllUserTree"/>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">来访人数</label>
            <div class="col-lg-4">
                <input id="numberOfVisitors" type="text" class="form-control" data-title="来访人数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                <label id="numberOfVisitors_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">来访事由</label>
            <div class="col-lg-8">
                <textarea id="visitingReasons" rows="5" cols="30" class="form-control" data-title="来访事由" data-rangelength="[0,256]" style="width: 706px;"></textarea>
                <label id="visitingReasons_Error" class="validator-error"></label>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">备注</label>
            <div class="col-lg-8">
                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 706px;"></textarea>
                <label id="remark_Error" class="validator-error"></label>
            </div>
        </div>
    </form>

    <div style="border: solid 1px #D9DEE4;margin: 0px 15px 15px 15px;">
        <ctag:FileUpload clientID="FileUpload" relationUid="UNKNOWN" appCode="TUS" moduleName="BPMS演示" panelTitle="附件一览" disableMarginTopSpace="true"></ctag:FileUpload>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_VisitorRecords}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_VisitorRecords}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/visitorrecords/VisitorRecordsInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function VisitorRecordsInput_Page_Load() {
        SysApp.Demo.VisitorRecordsInputIns = new SysApp.Demo.VisitorRecordsInput();
        var instance = SysApp.Demo.VisitorRecordsInputIns;
        instance.selfInstance = "SysApp.Demo.VisitorRecordsInputIns";
        instance.clientID = "VisitorRecordsInput";
        instance.controller = "${ctx}/demo/visitorrecords/";

        instance.init();
    }

    VisitorRecordsInput_Page_Load();
    //]]>
</script>