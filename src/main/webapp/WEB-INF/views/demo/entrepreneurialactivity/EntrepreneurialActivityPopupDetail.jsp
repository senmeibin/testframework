<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content EntrepreneurialActivityDetail-MainContent" style="width: 1200px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">活动编号</label>
            <label class="col-lg-4 control-label content-label" id="activityNo"></label>
            <label class="col-lg-1 control-label">活动主题</label>
            <label class="col-lg-4 control-label content-label" id="activityTopic"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">会务角色</label>
            <label class="col-lg-4 control-label content-label" id="conferenceRoleName"></label>
            <label class="col-lg-1 control-label">服务类型</label>
            <label class="col-lg-4 control-label content-label" id="serviceTypeName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">活动地点</label>
            <label class="col-lg-4 control-label content-label" id="activityLocation"></label>
            <label class="col-lg-1 control-label">服务内容</label>
            <label class="col-lg-4 control-label content-label" id="serviceContentName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">活动类型</label>
            <label class="col-lg-4 control-label content-label" id="activityTypeName"></label>
            <label class="col-lg-1 control-label">活动人数</label>
            <label class="col-lg-4 control-label content-label" id="activityNumber"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">参与导师</label>
            <label class="col-lg-4 control-label content-label" id="tutorName"></label>
            <label class="col-lg-1 control-label">活动时间</label>
            <label class="col-lg-4 control-label content-label" id="activityTime" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">活动金额</label>
            <label class="col-lg-4 control-label content-label" id="activityAmount" data-money="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">活动纪要</label>
            <label class="col-lg-4 control-label content-label" id="activitySummary"></label>
            <label class="col-lg-1 control-label">备注</label>
            <label class="col-lg-4 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/entrepreneurialactivity/EntrepreneurialActivityDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function EntrepreneurialActivityDetail_Page_Load() {
        SysApp.Demo.EntrepreneurialActivityDetailIns = new SysApp.Demo.EntrepreneurialActivityDetail();
        var instance = SysApp.Demo.EntrepreneurialActivityDetailIns;
        instance.selfInstance = "SysApp.Demo.EntrepreneurialActivityDetailIns";
        instance.clientID = "EntrepreneurialActivityDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/demo/entrepreneurialactivity/";
        instance.init();
    }

    EntrepreneurialActivityDetail_Page_Load();
    //]]>
</script>