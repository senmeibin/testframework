<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content BaseInfoDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">基地名称</label>
            <label class="col-lg-4 control-label content-label" id="baseName"></label>
            <label class="col-lg-1 control-label">地点</label>
            <label class="col-lg-4 control-label content-label" id="baseAddress"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">负责人</label>
            <label class="col-lg-4 control-label content-label" id="principal"></label>
            <label class="col-lg-1 control-label">成立时间</label>
            <label class="col-lg-4 control-label content-label" id="foundingTime" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">人数</label>
            <label class="col-lg-4 control-label content-label" id="numberOfPeople"></label>
            <label class="col-lg-1 control-label">联系电话</label>
            <label class="col-lg-4 control-label content-label" id="contactNumber"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">备注</label>
            <label class="col-lg-4 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/baseinfo/BaseInfoDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
        function BaseInfoDetail_Page_Load() {
            SysApp.Demo.BaseInfoDetailIns = new SysApp.Demo.BaseInfoDetail();
            var instance = SysApp.Demo.BaseInfoDetailIns;
            instance.selfInstance = "SysApp.Demo.BaseInfoDetailIns";
            instance.clientID = "BaseInfoDetail";
            instance.isDetail = true;
            instance.controller = "${ctx}/demo/baseinfo/";
            instance.init();
        }

        BaseInfoDetail_Page_Load();
    //]]>
</script>