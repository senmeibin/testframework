<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content BaseInfoInput-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <input id="uid" type="hidden">
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">基地名称</label>
            <div class="col-lg-4">
                <input id="baseName" type="text" class="form-control" data-title="基地名称" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                <label id="baseName_Error" class="validator-error"></label>
            </div>
            <label class="col-lg-1 control-label">地点</label>
            <div class="col-lg-4">
                <input id="baseAddress" type="text" class="form-control" data-title="地点" maxlength="64" data-rangelength="[0,64]" style="width: 220px;"/>
                <label id="baseAddress_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">负责人</label>
            <div class="col-lg-4">
                <input id="principal" type="text" class="form-control" data-title="负责人" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                <label id="principal_Error" class="validator-error"></label>
            </div>
            <label class="col-lg-1 control-label">成立时间</label>
            <div class="col-lg-4">
                <ctag:CalendarSelect id="foundingTime" title="成立时间" showValidateError="true" width="181px"></ctag:CalendarSelect>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">人数</label>
            <div class="col-lg-4">
                <input id="numberOfPeople" type="text" class="form-control" data-title="人数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                <label id="numberOfPeople_Error" class="validator-error"></label>
            </div>
            <label class="col-lg-1 control-label">联系电话</label>
            <div class="col-lg-4">
                <input id="contactNumber" type="text" class="form-control" data-title="联系电话" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                <label id="contactNumber_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">备注</label>
            <div class="col-lg-4">
                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 220px;"></textarea>
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
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_BaseInfo}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_BaseInfo}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/baseinfo/BaseInfoInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function BaseInfoInput_Page_Load() {
        SysApp.Demo.BaseInfoInputIns = new SysApp.Demo.BaseInfoInput();
        var instance = SysApp.Demo.BaseInfoInputIns;
        instance.selfInstance = "SysApp.Demo.BaseInfoInputIns";
        instance.clientID = "BaseInfoInput";
        instance.controller = "${ctx}/demo/baseinfo/";

        instance.init();
    }

    BaseInfoInput_Page_Load();
    //]]>
</script>