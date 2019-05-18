<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/courseschedule/CourseSchedulePopupList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CourseSchedulePopupList-MainContent" style="width: 1200px; display: none;">
    <ctag:ModalHeader modalTitle="课程表一览"></ctag:ModalHeader>
    
    <div class="modal-body">
        <button id="btnAdd" type="button" class="btn btn-primary">
            <i class="fa fa-plus"></i>新增课程表
        </button>
        <div class="clear-both dashed-line">
        </div>
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>班级UID</label>
                    <input id="campusClassUid" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="班级UID"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>课程次数</label>
                    <input id="courseNumber" data-alias-table="main" type="text" maxlength="8" class="form-control" data-title="课程次数"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>课程日期(起)</label>
                    <ctag:CalendarSelect id="courseDate$from_search" title="课程日期(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>课程日期(止)</label>
                    <ctag:CalendarSelect id="courseDate$to_search" title="课程日期(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>课程内容</label>
                    <input id="courseContent" data-alias-table="main" type="text" maxlength="0" class="form-control" data-title="课程内容"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>备注</label>
                    <input id="remark" data-camel-field="true" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="备注"/>
                </div>
            <div class="col-md-2 form-group" style="width: 300px;">
                <label>
                    &nbsp;
                </label>
                <div>
                    <button id="btnSearch" type="button" class="btn btn-primary">
                        <i class="fa fa-search"></i>查询
                    </button>
                    <button id="btnClear" type="button" class="btn btn-default">
                        <i class="fa fa-eraser"></i>清空
                    </button>
                </div>
            </div>
        </form>
        <%--上次检索条件--%>
        <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_CourseSchedule}"/>
        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CourseSchedule}"/>
        <div class="margin-top-space" id="divList" style="width: 100%">
        </div>
    </div>

    <ctag:ModalFooter></ctag:ModalFooter>
</div>

<script type="text/javascript">
    //<![CDATA[
    function CourseSchedulePopupList_Page_Load() {
        SysApp.Crm.CourseSchedulePopupListIns = new SysApp.Crm.CourseSchedulePopupList();
        var instance = SysApp.Crm.CourseSchedulePopupListIns;
        
        instance.selfInstance = "SysApp.Crm.CourseSchedulePopupListIns";
        instance.clientID = "CourseSchedulePopupList";
        instance.tableName = "crm_course_schedule";
        instance.inputInstance = SysApp.Crm.CourseScheduleInputIns;
        instance.detailInstance = SysApp.Crm.CourseScheduleDetailIns;
        instance.controller = "${ctx}/crm/courseschedule/";
        instance.entry = "${entry}";

        instance.init();
    }

    $(function() {
        CourseSchedulePopupList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑--%>
<%@ include file="/WEB-INF/views/crm/courseschedule/CourseSchedulePopupInput.jsp" %>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/crm/courseschedule/CourseSchedulePopupDetail.jsp" %>