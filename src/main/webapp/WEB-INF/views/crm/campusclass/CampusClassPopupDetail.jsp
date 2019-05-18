<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CampusClassDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">所属校区</label>
            <label class="col-sm-9 control-label content-label" id="campusName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">班级分类</label>
            <label class="col-sm-9 control-label content-label" id="categoryName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">班级级别</label>
            <label class="col-sm-9 control-label content-label" id="classLevelName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">开班年月</label>
            <label class="col-sm-9 control-label content-label" id="classYearMonth"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">开班日期</label>
            <label class="col-sm-9 control-label content-label" id="classStartDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">结束日期</label>
            <label class="col-sm-9 control-label content-label" id="classEndDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">上课时间</label>
            <label class="col-sm-9 control-label content-label" id="classTime"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">开班期数</label>
            <label class="col-sm-9 control-label content-label" id="classSeq"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">班级编号</label>
            <label class="col-sm-9 control-label content-label" id="classNumber"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">班级教室</label>
            <label class="col-sm-9 control-label content-label" id="classroom"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">授课讲师</label>
            <label class="col-sm-9 control-label content-label" id="teacherUserName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">课程顾问</label>
            <label class="col-sm-9 control-label content-label" id="consultantUserName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">学员限制</label>
            <label class="col-sm-9 control-label content-label" id="studentLimit"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">班级人数</label>
            <label class="col-sm-9 control-label content-label" id="studentCount"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">班级学员</label>
            <label class="col-sm-9 control-label content-label" id="studentNames"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <label class="col-sm-9 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/campusclass/CampusClassDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CampusClassDetail_Page_Load() {
        SysApp.Crm.CampusClassDetailIns = new SysApp.Crm.CampusClassDetail();
        var instance = SysApp.Crm.CampusClassDetailIns;
        instance.selfInstance = "SysApp.Crm.CampusClassDetailIns";
        instance.clientID = "CampusClassDetail";
        instance.isDetail = true;
        instance.entry = "${entry}";
        instance.controller = "${ctx}/crm/campusclass/";
        instance.init();
    }

    CampusClassDetail_Page_Load();
    //]]>
</script>