<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content FollowupDetail-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">学员姓名</label>
            <label class="col-sm-9 control-label content-label" id="studentName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">所属校区</label>
            <label class="col-sm-9 control-label content-label" id="followupBelongCampusName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">课程顾问</label>
            <label class="col-sm-9 control-label content-label" id="followupBelongConsultantUserName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">跟进时间</label>
            <label class="col-sm-9 control-label content-label" id="followupDate" data-datetime="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">跟进方式</label>
            <label class="col-sm-9 control-label content-label" id="followupMethodName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">跟进内容</label>
            <label class="col-sm-9 control-label content-label" id="contents" data-textarea="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">销售进程</label>
            <label class="col-sm-9 control-label content-label" id="saleProcessName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">是否预约</label>
            <label class="col-sm-9 control-label content-label" id="acceptShiftName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">下次跟进时间</label>
            <label class="col-sm-9 control-label content-label" id="nextFollowupDate" data-datetime="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <label class="col-sm-9 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/followup/FollowupDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function FollowupDetail_Page_Load() {
        SysApp.Crm.FollowupDetailIns = new SysApp.Crm.FollowupDetail();
        var instance = SysApp.Crm.FollowupDetailIns;
        instance.selfInstance = "SysApp.Crm.FollowupDetailIns";
        instance.clientID = "FollowupDetail";
        instance.isDetail = true;
        instance.entry = "${entry}";
        instance.controller = "${ctx}/crm/followup/";
        instance.init();
    }

    FollowupDetail_Page_Load();
    //]]>
</script>