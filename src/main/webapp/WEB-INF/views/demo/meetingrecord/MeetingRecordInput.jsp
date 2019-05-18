<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/meetingrecord/MeetingRecordInput.js?${version}"></script>

<title>会议记录编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper MeetingRecordInput-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">会议记录编辑</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">所属基地</label>
                    <div class="col-lg-4 select2-required">
                        <ctag:Select2 selectId="baseUid" dataTitle="所属基地" required="required" style="width: 220px;"/>
                        <label id="baseUid_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">会议主题</label>
                    <div class="col-lg-4">
                        <input id="subject" type="text" class="form-control required" data-title="会议主题" maxlength="64" data-rangelength="[0,64]" style="width: 220px;"/>
                        <label id="subject_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">会议时间</label>
                    <div class="col-lg-4">
                        <ctag:CalendarSelect id="meetingTime" title="会议时间" required="required" showValidateError="true" width="181px"></ctag:CalendarSelect>
                    </div>
                    <label class="col-lg-1 control-label">会议地点</label>
                    <div class="col-lg-4">
                        <input id="meetingPlace" type="text" class="form-control required" data-title="会议地点" maxlength="128" data-rangelength="[0,128]" style="width: 220px;"/>
                        <label id="meetingPlace_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">与会人员</label>
                    <div class="col-lg-4">
                        <input id="attendees" type="text" class="form-control" data-title="与会人员" maxlength="256" data-rangelength="[0,256]" style="width: 220px;"/>
                        <label id="attendees_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">记录人</label>
                    <div class="col-lg-4">
                        <ctag:ComboTree valueDomId="recorderUid" textDomId="recorderName" parentInstance="SysApp.Demo.MeetingRecordInputIns" dataTitle="记录人" style="width:181px;" required="required"
                                        selectParent="false" idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dispName="deptName" dataUrl="${ctx}/demo/user/getAllUserTree"/>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">主要内容</label>
                    <div class="col-lg-8">
                        <textarea id="meetingContent" rows="5" cols="30" class="form-control" data-title="主要内容" data-rangelength="[0,512]" style="width: 98%;"></textarea>
                        <label id="meetingContent_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">会议议题及结论</label>
                    <div class="col-lg-8">
                        <textarea id="conferencesConclusions" rows="5" cols="30" class="form-control" data-title="会议议题及结论" data-rangelength="[0,512]" style="width: 98%;"></textarea>
                        <label id="conferencesConclusions_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">备注</label>
                    <div class="col-lg-8">
                        <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 98%;"></textarea>
                        <label id="remark_Error" class="validator-error"></label>
                    </div>
                </div>
            </form>

            <div class="text-center">
                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
            </div>

            <%--原始对象Json字符串[实体属性校验用]--%>
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_MeetingRecord}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_MeetingRecord}"/>
        </div>
    </div>
    <ctag:FileUpload clientID="FileUpload" relationUid="${meetingRecordUid}" appCode="TUS" moduleName="BPMS演示" panelTitle="附件一览"></ctag:FileUpload>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function MeetingRecordInput_Page_Load() {
        SysApp.Demo.MeetingRecordInputIns = new SysApp.Demo.MeetingRecordInput();
        var instance = SysApp.Demo.MeetingRecordInputIns;
        instance.selfInstance = "SysApp.Demo.MeetingRecordInputIns";
        instance.controller = "${ctx}/demo/meetingrecord/";
        instance.listUrl = "${ctx}/demo/meetingrecord/list";
        instance.clientID = "MeetingRecordInput";

        instance.init();
    }

    MeetingRecordInput_Page_Load();
    //]]>
</script>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.MeetingRecordInputIns"/>