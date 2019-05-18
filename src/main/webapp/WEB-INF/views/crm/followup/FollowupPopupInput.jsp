<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content FollowupInput-MainContent" style="width: 1000px; display: none;">
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
                <select id="followupBelongConsultantUserUid" class="form-control required" data-title="课程顾问" style="width: 500px;">
                    <option value="">请选择</option>
                </select>
                <label id="followupBelongConsultantUserUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">跟进时间</label>
            <div class="col-sm-9">
                <ctag:CalendarSelect id="followupDate" title="跟进时间" required="required" showValidateError="true" width="120px" maxlength="16"></ctag:CalendarSelect>
                <ctag:TimeSelect id="followupTime" title="跟进时间" clientID="FollowupInput" fromHour="7" toHour="22" showMinute="true" stepMinute="5" width="44px"></ctag:TimeSelect>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">跟进方式</label>
            <div class="col-sm-9">
                <select id="followupMethodCd" class="form-control required" data-title="跟进方式" style="width: 500px;">
                    <option value="">请选择</option>
                </select>
                <label id="followupMethodCd_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">跟进内容</label>
            <div class="col-sm-9">
                <textarea id="contents" rows="5" cols="30" class="form-control required" data-title="跟进内容" data-rangelength="[0,256]" style="width: 500px;"></textarea>
                <label id="contents_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">销售进程</label>
            <div class="col-sm-9">
                <select id="saleProcessCd" class="form-control" data-title="销售进程" style="width: 500px;">
                    <option value="">请选择</option>
                </select>
                <label id="saleProcessCd_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">是否预约</label>
            <div class="col-sm-9">
                <select id="acceptShiftCd" class="form-control" data-title="是否预约" style="width: 500px;">
                    <option value="">请选择</option>
                </select>
                <label id="acceptShiftCd_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">下次跟进时间</label>
            <div class="col-sm-9">
                <ctag:CalendarSelect id="nextFollowupDate" title="下次跟进时间" showValidateError="true" width="120px" maxlength="16"></ctag:CalendarSelect>
                <ctag:TimeSelect id="nextFollowupTime" title="跟进时间" clientID="FollowupInput" fromHour="7" toHour="22" showMinute="true" stepMinute="5" width="44px"></ctag:TimeSelect>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <div class="col-sm-9">
                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 500px;"></textarea>
                <label id="remark_Error" class="validator-error"></label>
            </div>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Followup}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Followup}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/followup/FollowupInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function FollowupInput_Page_Load() {
        SysApp.Crm.FollowupInputIns = new SysApp.Crm.FollowupInput();
        var instance = SysApp.Crm.FollowupInputIns;
        instance.selfInstance = "SysApp.Crm.FollowupInputIns";
        instance.clientID = "FollowupInput";
        instance.entry = "${entry}";
        instance.controller = "${ctx}/crm/followup/";

        instance.init();
    }

    FollowupInput_Page_Load();
    //]]>
</script>