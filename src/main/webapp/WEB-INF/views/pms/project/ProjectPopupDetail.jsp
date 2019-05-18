<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content ProjectDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">名称</label>
            <label class="col-sm-9 control-label content-label" id="name"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">地址</label>
            <label class="col-sm-9 control-label content-label" id="address"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">报名开始时间</label>
            <label class="col-sm-9 control-label content-label" id="entryStartDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">报名终了时间</label>
            <label class="col-sm-9 control-label content-label" id="entryEndDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">踏勘开始时间</label>
            <label class="col-sm-9 control-label content-label" id="explorationStartDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">开标时间</label>
            <label class="col-sm-9 control-label content-label" id="bidDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">进场时间</label>
            <label class="col-sm-9 control-label content-label" id="projectStartDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">实施能力</label>
            <label class="col-sm-9 control-label content-label" id="exeCapability" data-textarea="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">预算价格</label>
            <label class="col-sm-9 control-label content-label" id="budgetaryPrice" data-textarea="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">价格评估</label>
            <label class="col-sm-9 control-label content-label" id="priceEvaluation" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/pms/project/ProjectDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
        function ProjectDetail_Page_Load() {
            SysApp.Pms.ProjectDetailIns = new SysApp.Pms.ProjectDetail();
            var instance = SysApp.Pms.ProjectDetailIns;
            instance.selfInstance = "SysApp.Pms.ProjectDetailIns";
            instance.clientID = "ProjectDetail";
            instance.isDetail = true;
            instance.entry = "${entry}";
            instance.controller = "${ctx}/pms/project/";
            instance.init();
        }

        ProjectDetail_Page_Load();
    //]]>
</script>