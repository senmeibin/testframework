<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CampusDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">校区名称</label>
            <label class="col-sm-9 control-label content-label" id="name"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">校区代号</label>
            <label class="col-sm-9 control-label content-label" id="code"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">校区分类</label>
            <label class="col-sm-9 control-label content-label" id="categoryName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">校区介绍</label>
            <label class="col-sm-9 control-label content-label" id="introduction" data-textarea="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">校区地址</label>
            <label class="col-sm-9 control-label content-label" id="address"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">邮政编码</label>
            <label class="col-sm-9 control-label content-label" id="zipCode"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">联系电话</label>
            <label class="col-sm-9 control-label content-label" id="telephone"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">传真</label>
            <label class="col-sm-9 control-label content-label" id="fax"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">邮箱</label>
            <label class="col-sm-9 control-label content-label" id="email"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">门店店长</label>
            <label class="col-sm-9 control-label content-label" id="managerUserName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">门店副店长</label>
            <label class="col-sm-9 control-label content-label" id="assistantManagerUserName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">门店助理</label>
            <label class="col-sm-9 control-label content-label" id="assistantUserName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">校区顾问</label>
            <label class="col-sm-9 control-label content-label" id="consultantUserNames"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">表示顺序</label>
            <label class="col-sm-9 control-label content-label" id="dispSeq"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <label class="col-sm-9 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/campus/CampusDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CampusDetail_Page_Load() {
        SysApp.Crm.CampusDetailIns = new SysApp.Crm.CampusDetail();
        var instance = SysApp.Crm.CampusDetailIns;
        instance.selfInstance = "SysApp.Crm.CampusDetailIns";
        instance.clientID = "CampusDetail";
        instance.isDetail = true;
        instance.entry = "${entry}";
        instance.controller = "${ctx}/crm/campus/";
        instance.init();
    }

    CampusDetail_Page_Load();
    //]]>
</script>