<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content FaqDetail-MainContent" style="width: 1300px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal" style="height: 450px; overflow: auto;">
        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">应用编号</label>
            <label class="col-sm-9 control-label content-label" id="appCode"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">应用名称</label>
            <label class="col-sm-9 control-label content-label" id="appName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">模块名称</label>
            <label class="col-sm-9 control-label content-label" id="moduleName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">问题描述</label>
            <label class="col-sm-9 control-label content-label" id="question"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">问题详细</label>
            <label class="col-sm-9 control-label content-label" id="questionDesc" data-textarea="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">问题解答</label>
            <label class="col-sm-9 control-label content-label" id="answer"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">操作说明</label>
            <label class="col-sm-9 control-label content-label" id="answerDesc" data-textarea="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">适用角色</label>
            <label class="col-sm-9 control-label content-label" id="applicableRole"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">责任人</label>
            <label class="col-sm-9 control-label content-label" id="responsibleRole"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">备注</label>
            <label class="col-sm-9 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
        <ctag:FileUpload clientID="FileUpload" relationUid="UNKNOWN" appCode="CMN" moduleName="常见问题" panelTitle="附件一览"></ctag:FileUpload>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/faq/FaqDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function FaqDetail_Page_Load() {
        SysApp.Cmn.FaqDetailIns = new SysApp.Cmn.FaqDetail();
        var instance = SysApp.Cmn.FaqDetailIns;
        instance.selfInstance = "SysApp.Cmn.FaqDetailIns";
        instance.clientID = "FaqDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/cmn/faq/";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    FaqDetail_Page_Load();
    //]]>
</script>