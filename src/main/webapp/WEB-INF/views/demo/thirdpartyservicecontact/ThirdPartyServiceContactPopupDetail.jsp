<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ThirdPartyServiceContactDetail_ctlFrame" class="modal-content" style="width: 1200px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader clientID="ThirdPartyServiceContactDetail"></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <ul class="tmv-customize">
            <li class="active"><span>第三方服务信息</span></li>
            <em></em>
        </ul>
        <div class="ThirdPartyServiceContactDetail-MainContent  tmv-content">
            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">所属基地</label>
                <label class="col-lg-4 control-label content-label" id="baseName"></label>
                <label class="col-lg-1 control-label">企业名称</label>
                <label class="col-lg-4 control-label content-label" id="companyName"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">服务时间</label>
                <label class="col-lg-4 control-label content-label" id="serviceDate" data-date="true"></label>
                <label class="col-lg-1 control-label">服务类别</label>
                <label class="col-lg-4 control-label content-label" id="serviceType"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">服务内容</label>
                <label class="col-lg-8 control-label content-label" id="serviceContent"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">标签</label>
                <label class="col-lg-8 control-label content-label" id="thirdPartyServiceTag"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">评价状态</label>
                <label class="col-lg-8 control-label content-label" id="evaluateStatusName"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">评价</label>
                <label class="col-lg-8 control-label content-label" id="evaluate"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">备注</label>
                <label class="col-lg-8 control-label content-label" id="remark" data-textarea="true"></label>
            </div>
        </div>
        <ul class="tmv-customize">
            <li class="active"><span>服务明细</span></li>
            <em></em>
        </ul>
        <%@ include file="/WEB-INF/views/demo/thirdpartyservicecontact/resourcesdockingrecord/ResourcesDockingRecordInnerList.jsp" %>
    </div>
    <%--POPUP控件Footer--%>
    <ctag:ModalFooter clientID="ThirdPartyServiceContactDetail"></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/thirdpartyservicecontact/ThirdPartyServiceContactDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ThirdPartyServiceContactDetail_Page_Load() {
        SysApp.Demo.ThirdPartyServiceContactDetailIns = new SysApp.Demo.ThirdPartyServiceContactDetail();
        var instance = SysApp.Demo.ThirdPartyServiceContactDetailIns;
        instance.selfInstance = "SysApp.Demo.ThirdPartyServiceContactDetailIns";
        instance.clientID = "ThirdPartyServiceContactDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/demo/thirdpartyservicecontact/";
        instance.init();
    }

    ThirdPartyServiceContactDetail_Page_Load();
    //]]>
</script>