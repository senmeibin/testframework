<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CompanyInfoDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">类别</label>
            <label class="col-sm-9 control-label content-label" id="companyInfoName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">标题</label>
            <label class="col-sm-9 control-label content-label" id="title"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">内容</label>
            <label class="col-sm-9 control-label content-label" id="content" data-textarea="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">有效开始日期</label>
            <label class="col-sm-9 control-label content-label" id="effectStartDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">有效终了日期</label>
            <label class="col-sm-9 control-label content-label" id="effectEndDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">负责人</label>
            <label class="col-sm-9 control-label content-label" id="chargeUserName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">负责部门</label>
            <label class="col-sm-9 control-label content-label" id="chargeDeptName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <label class="col-sm-9 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/pms/companyinfo/CompanyInfoDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
        function CompanyInfoDetail_Page_Load() {
            SysApp.Pms.CompanyInfoDetailIns = new SysApp.Pms.CompanyInfoDetail();
            var instance = SysApp.Pms.CompanyInfoDetailIns;
            instance.selfInstance = "SysApp.Pms.CompanyInfoDetailIns";
            instance.clientID = "CompanyInfoDetail";
            instance.isDetail = true;
            instance.entry = "${entry}";
            instance.controller = "${ctx}/pms/companyinfo/";
            instance.init();
        }

        CompanyInfoDetail_Page_Load();
    //]]>
</script>