<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CourseScheduleDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">班级</label>
            <label class="col-sm-9 control-label content-label" id="campusClassName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">课程次数</label>
            <label class="col-sm-9 control-label content-label" id="courseNumber"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">课程日期</label>
            <label class="col-sm-9 control-label content-label" id="courseDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">课程内容</label>
            <label class="col-sm-9 control-label content-label" id="courseContent" data-textarea="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <label class="col-sm-9 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/courseschedule/CourseScheduleDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
        function CourseScheduleDetail_Page_Load() {
            SysApp.Crm.CourseScheduleDetailIns = new SysApp.Crm.CourseScheduleDetail();
            var instance = SysApp.Crm.CourseScheduleDetailIns;
            instance.selfInstance = "SysApp.Crm.CourseScheduleDetailIns";
            instance.clientID = "CourseScheduleDetail";
            instance.isDetail = true;
            instance.entry = "${entry}";
            instance.controller = "${ctx}/crm/courseschedule/";
            instance.init();
        }

        CourseScheduleDetail_Page_Load();
    //]]>
</script>