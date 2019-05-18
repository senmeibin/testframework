<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content MeetingRecordDetail-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">所属基地</label>
            <label class="col-lg-4 control-label content-label" id="baseName"></label>
            <label class="col-lg-2 control-label">会议主题</label>
            <label class="col-lg-4 control-label content-label" id="subject"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">会议时间</label>
            <label class="col-lg-4 control-label content-label" id="meetingTime" data-date="true"></label>
            <label class="col-lg-2 control-label">会议地点</label>
            <label class="col-lg-4 control-label content-label" id="meetingPlace"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">与会人员</label>
            <label class="col-lg-4 control-label content-label" id="attendees"></label>
            <label class="col-lg-2 control-label">记录人</label>
            <label class="col-lg-4 control-label content-label" id="recorderName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">主要内容</label>
            <label class="col-lg-8 control-label content-label" id="meetingContent"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">会议议题及结论</label>
            <label class="col-lg-8 control-label content-label" id="conferencesConclusions"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">备注</label>
            <label class="col-lg-8 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/meetingrecord/MeetingRecordDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function MeetingRecordDetail_Page_Load() {
        SysApp.Demo.MeetingRecordDetailIns = new SysApp.Demo.MeetingRecordDetail();
        var instance = SysApp.Demo.MeetingRecordDetailIns;
        instance.selfInstance = "SysApp.Demo.MeetingRecordDetailIns";
        instance.clientID = "MeetingRecordDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/demo/meetingrecord/";
        instance.init();
    }

    MeetingRecordDetail_Page_Load();
    //]]>
</script>