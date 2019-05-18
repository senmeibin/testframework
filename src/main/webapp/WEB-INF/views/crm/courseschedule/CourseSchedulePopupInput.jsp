<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CourseScheduleInput-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <input id="uid" type="hidden">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">班级</label>
            <div class="col-sm-9">
                <input id="campusClassUid" type="text" class="form-control required" data-title="班级" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                <label id="campusClassUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">课程次数</label>
            <div class="col-sm-9">
                <input id="courseNumber" type="text" class="form-control required" data-title="课程次数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 350px;"/>
                <label id="courseNumber_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">课程日期</label>
            <div class="col-sm-9">
                <ctag:CalendarSelect id="courseDate" title="课程日期" required="required" showValidateError="true" width="120px"></ctag:CalendarSelect>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">课程内容</label>
            <div class="col-sm-9">
                <textarea id="courseContent" rows="3" cols="30" class="form-control" data-title="课程内容" data-rangelength="[0,0]" style="width: 350px;"></textarea>
                <label id="courseContent_Error" class="validator-error"></label>
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
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_CourseSchedule}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CourseSchedule}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/courseschedule/CourseScheduleInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CourseScheduleInput_Page_Load() {
        SysApp.Crm.CourseScheduleInputIns = new SysApp.Crm.CourseScheduleInput();
        var instance = SysApp.Crm.CourseScheduleInputIns;
        instance.selfInstance = "SysApp.Crm.CourseScheduleInputIns";
        instance.clientID = "CourseScheduleInput";
        instance.entry = "${entry}";
        instance.controller = "${ctx}/crm/courseschedule/";
        
        instance.init();
    }

    CourseScheduleInput_Page_Load();
    //]]>
</script>