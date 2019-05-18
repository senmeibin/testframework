<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content ActivityDetail-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">活动分类</label>
            <label class="col-sm-4 control-label content-label" id="categoryName"></label>
            <label class="col-sm-2 control-label">活动种类</label>
            <label class="col-sm-4 control-label content-label" id="typeName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">活动主题</label>
            <label class="col-sm-10 control-label content-label" id="title"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">活动目的</label>
            <label class="col-sm-10 control-label content-label" id="purpose"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">活动开始日期</label>
            <label class="col-sm-4 control-label content-label" id="startDate" data-date="true"></label>
            <label class="col-sm-2 control-label">活动结束日期</label>
            <label class="col-sm-4 control-label content-label" id="endDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">活动地址</label>
            <label class="col-sm-10 control-label content-label" id="address"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">活动对象</label>
            <label class="col-sm-10 control-label content-label" id="target"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">活动内容</label>
            <label class="col-sm-10 control-label content-label" id="contents" data-textarea="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">所需物资</label>
            <label class="col-sm-10 control-label content-label" id="requiredMaterials" data-textarea="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">负责人</label>
            <label class="col-sm-4 control-label content-label" id="responsibleUserName"></label>
            <label class="col-sm-2 control-label">申请校区</label>
            <label class="col-sm-4 control-label content-label" id="applyCampusName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">申请人</label>
            <label class="col-sm-4 control-label content-label" id="applyUserName"></label>
            <label class="col-sm-2 control-label">申请日期</label>
            <label class="col-sm-4 control-label content-label" id="applyDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">审批时间</label>
            <label class="col-sm-4 control-label content-label" id="auditDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">审批人</label>
            <label class="col-sm-4 control-label content-label" id="auditUserName"></label>
            <label class="col-sm-2 control-label">审批状态</label>
            <label class="col-sm-4 control-label content-label" id="auditStatusName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">审批意见</label>
            <label class="col-sm-10 control-label content-label" id="auditComment"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">参与校区</label>
            <label class="col-sm-10 control-label content-label" id="campusNames"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">备注</label>
            <label class="col-sm-10 control-label content-label" id="remark" data-textarea="true"></label>
        </div>


    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/activity/ActivityDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ActivityDetail_Page_Load() {
        SysApp.Crm.ActivityDetailIns = new SysApp.Crm.ActivityDetail();
        var instance = SysApp.Crm.ActivityDetailIns;
        instance.selfInstance = "SysApp.Crm.ActivityDetailIns";
        instance.clientID = "ActivityDetail";
        instance.isDetail = true;
        instance.entry = "${entry}";
        instance.controller = "${ctx}/crm/activity/";
        instance.init();
    }

    ActivityDetail_Page_Load();
    //]]>
</script>