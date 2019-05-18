<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content RegistrationInput-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <input id="uid" type="hidden">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">学员姓名</label>
            <div class="col-sm-9">
                <input id="studentUid" type="hidden"/>
                <input id="studentName" type="text" class="form-control required" data-title="学员姓名" readonly style="width: 500px;"/>
                <label id="studentUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">课程顾问</label>
            <div class="col-sm-9">
                <select id="registrationBelongConsultantUserUid" class="form-control required" data-title="课程顾问" style="width: 500px;">
                    <option value="">请选择</option>
                </select>
                <label id="registrationBelongConsultantUserUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">报名时间</label>
            <div class="col-sm-9">
                <ctag:CalendarSelect id="registrationDate" title="报名时间" required="required" showValidateError="true" width="120px"></ctag:CalendarSelect>
                <ctag:TimeSelect id="registrationTime" title="报名时间" clientID="FollowupInput" fromHour="7" toHour="22" showMinute="true" stepMinute="5" width="44px"></ctag:TimeSelect>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">报名校区</label>
            <div class="col-sm-9">
                <select id="registrationCampusUid" class="form-control required" data-title="报名校区" style="width: 500px;">
                    <option value="">请选择</option>
                </select>
                <label id="registrationCampusUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">报名班级</label>
            <div class="col-sm-9">
                <select id="registrationCampusClassUid" class="form-control" data-title="报名班级" style="width: 500px;">
                    <option value="">请选择</option>
                </select>
                <label id="registrationCampusClassUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">新老学员</label>
            <div class="col-sm-9">
                <select id="isNewStudent" class="form-control" data-title="新老学员">
                    <option value="">请选择</option>
                    <option value="0">老学员</option>
                    <option value="1">新学员</option>
                </select>
                <label id="isNewStudent_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <div class="col-sm-9">
                <textarea id="remark" rows="5" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 500px;"></textarea>
                <label id="remark_Error" class="validator-error"></label>
            </div>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Registration}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Registration}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/registration/RegistrationInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function RegistrationInput_Page_Load() {
        SysApp.Crm.RegistrationInputIns = new SysApp.Crm.RegistrationInput();
        var instance = SysApp.Crm.RegistrationInputIns;
        instance.selfInstance = "SysApp.Crm.RegistrationInputIns";
        instance.clientID = "RegistrationInput";
        instance.entry = "${entry}";
        instance.controller = "${ctx}/crm/registration/";

        instance.init();
    }

    RegistrationInput_Page_Load();
    //]]>
</script>