<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content TutorInput-MainContent" style="width: 1000px; display: none;">
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
            <label class="col-lg-1 control-label">姓名</label>
            <div class="col-lg-4">
                <input id="tutorName" type="text" class="form-control required" data-title="姓名" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                <label id="tutorName_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">单位</label>
            <div class="col-lg-4">
                <input id="tutorUnit" type="text" class="form-control" data-title="单位" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                <label id="tutorUnit_Error" class="validator-error"></label>
            </div>
            <label class="col-lg-1 control-label">职务</label>
            <div class="col-lg-4">
                <input id="tutorPosition" type="text" class="form-control" data-title="职务" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                <label id="tutorPosition_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">联系方式</label>
            <div class="col-lg-4">
                <input id="contactInfo" type="text" class="form-control" data-title="联系方式" maxlength="256" data-rangelength="[0,256]" style="width: 220px;"/>
                <label id="contactInfo_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">方向</label>
            <div class="col-lg-8">
                <textarea id="direction" rows="3" cols="30" class="form-control" data-title="方向" data-rangelength="[0,64]" style="width: 625px;"></textarea>
                <label id="direction_Error" class="validator-error"></label>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">备注</label>
            <div class="col-lg-8">
                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 625px;"></textarea>
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
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Tutor}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Tutor}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/tutor/TutorInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function TutorInput_Page_Load() {
        SysApp.Demo.TutorInputIns = new SysApp.Demo.TutorInput();
        var instance = SysApp.Demo.TutorInputIns;
        instance.selfInstance = "SysApp.Demo.TutorInputIns";
        instance.clientID = "TutorInput";
        instance.controller = "${ctx}/demo/tutor/";

        instance.init();
    }

    TutorInput_Page_Load();
    //]]>
</script>