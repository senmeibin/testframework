<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content StudentAssignLogDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">学员姓名</label>
            <label class="col-sm-9 control-label content-label" id="studentName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">划转类型</label>
            <label class="col-sm-9 control-label content-label" id="assignTypeName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">变更前课程顾问</label>
            <label class="col-sm-9 control-label content-label" id="beforeUserName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">变更后课程顾问</label>
            <label class="col-sm-9 control-label content-label" id="afterUserName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <label class="col-sm-9 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/studentassignlog/StudentAssignLogDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
        function StudentAssignLogDetail_Page_Load() {
            SysApp.Crm.StudentAssignLogDetailIns = new SysApp.Crm.StudentAssignLogDetail();
            var instance = SysApp.Crm.StudentAssignLogDetailIns;
            instance.selfInstance = "SysApp.Crm.StudentAssignLogDetailIns";
            instance.clientID = "StudentAssignLogDetail";
            instance.isDetail = true;
            instance.entry = "${entry}";
            instance.controller = "${ctx}/crm/studentassignlog/";
            instance.init();
        }

        StudentAssignLogDetail_Page_Load();
    //]]>
</script>