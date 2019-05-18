<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content RegistrationDetail-MainContent" style="width: 1000px; display: none;">
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
            <label class="col-sm-9 control-label content-label" id="registrationBelongCampusName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">课程顾问</label>
            <label class="col-sm-9 control-label content-label" id="registrationBelongConsultantUserName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">报名时间</label>
            <label class="col-sm-9 control-label content-label" id="registrationDate" data-datetime="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">报名校区</label>
            <label class="col-sm-9 control-label content-label" id="registrationCampusName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">报名班级</label>
            <label class="col-sm-9 control-label content-label" id="registrationCampusClassName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">新老学员</label>
            <label class="col-sm-9 control-label content-label" id="isNewStudent"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <label class="col-sm-9 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/registration/RegistrationDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function RegistrationDetail_Page_Load() {
        SysApp.Crm.RegistrationDetailIns = new SysApp.Crm.RegistrationDetail();
        var instance = SysApp.Crm.RegistrationDetailIns;
        instance.selfInstance = "SysApp.Crm.RegistrationDetailIns";
        instance.clientID = "RegistrationDetail";
        instance.isDetail = true;
        instance.entry = "${entry}";
        instance.controller = "${ctx}/crm/registration/";
        instance.init();
    }

    RegistrationDetail_Page_Load();
    //]]>
</script>