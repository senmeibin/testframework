<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content StudentDetail-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">学员姓名</label>
            <label class="col-sm-3 control-label content-label" id="name"></label>
            <label class="col-sm-2 control-label">证件号码</label>
            <label class="col-sm-3 control-label content-label" id="cardNumber"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">出生年月</label>
            <label class="col-sm-3 control-label content-label" id="birthday"></label>
            <label class="col-sm-2 control-label">围棋基础</label>
            <label class="col-sm-3 control-label content-label" id="baseLevelName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">所属校区</label>
            <label class="col-sm-3 control-label content-label" id="campusName"></label>
            <label class="col-sm-2 control-label">课程顾问</label>
            <label class="col-sm-3 control-label content-label" id="consultantUserName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">咨询方式</label>
            <label class="col-sm-3 control-label content-label" id="consultMethodName"></label>
            <label class="col-sm-2 control-label">信息来源</label>
            <label class="col-sm-3 control-label content-label" id="sourceTypeName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">家长姓名</label>
            <label class="col-sm-3 control-label content-label" id="parentName"></label>
            <label class="col-sm-2 control-label">手机号码</label>
            <label class="col-sm-3 control-label content-label" id="mobile"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">电子邮件</label>
            <label class="col-sm-3 control-label content-label" id="email"></label>
            <label class="col-sm-2 control-label">家庭住址</label>
            <label class="col-sm-3 control-label content-label" id="homeAddress"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">之前学校</label>
            <label class="col-sm-3 control-label content-label" id="beforeSchool"></label>
            <label class="col-sm-2 control-label">学员状态</label>
            <label class="col-sm-3 control-label content-label" id="studentStatusName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">最近跟进</label>
            <label class="col-sm-3 control-label content-label" id="lastFollowupName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">首次咨询时间</label>
            <label class="col-sm-3 control-label content-label" id="firstConsultTime"></label>
            <label class="col-sm-2 control-label">最近咨询时间</label>
            <label class="col-sm-3 control-label content-label" id="recentConsultTime"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">备注</label>
            <label class="col-sm-10 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/student/StudentDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function StudentDetail_Page_Load() {
        SysApp.Crm.StudentDetailIns = new SysApp.Crm.StudentDetail();
        var instance = SysApp.Crm.StudentDetailIns;
        instance.selfInstance = "SysApp.Crm.StudentDetailIns";
        instance.clientID = "StudentDetail";
        instance.isDetail = true;
        instance.entry = "${entry}";
        instance.controller = "${ctx}/crm/student/";
        instance.init();
    }

    StudentDetail_Page_Load();
    //]]>
</script>