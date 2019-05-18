<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content EnterpriseDetail-MainContent" style="width: 1200px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal" style="height: 450px;overflow:auto;overflow-x: hidden">
        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">企业名称</label>
            <label class="col-sm-4 control-label content-label" id="enterpriseName"></label>
            <label class="col-sm-2 control-label">企业简称</label>
            <label class="col-sm-4 control-label content-label" id="abbreviation"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">企业LOGO</label>
            <label class="col-sm-4 control-label content-label" id="logo"></label>
            <label class="col-sm-2 control-label">法人代表</label>
            <label class="col-sm-4 control-label content-label" id="legalPersonName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">企业标签</label>
            <label class="col-sm-10 control-label content-label" id="enterpriseLabel"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">所属行业</label>
            <label class="col-sm-4 control-label content-label" id="industryName"></label>
            <label class="col-sm-2 control-label">企业性质</label>
            <label class="col-sm-4 control-label content-label" id="propertyName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">企业规模</label>
            <label class="col-sm-4 control-label content-label" id="scaleName"></label>
            <label class="col-sm-2 control-label">省-市-区</label>
            <label class="col-sm-1 control-label content-label" id="provinceName"></label>
            <label class="col-sm-1 control-label content-label" id="cityName"></label>
            <label class="col-sm-1 control-label content-label" id="regionName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">企业地址</label>
            <label class="col-sm-4 control-label content-label" id="address"></label>
            <label class="col-sm-2 control-label">邮编</label>
            <label class="col-sm-4 control-label content-label" id="zipcode"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">电话</label>
            <label class="col-sm-4 control-label content-label" id="telephone"></label>
            <label class="col-sm-2 control-label">传真</label>
            <label class="col-sm-4 control-label content-label" id="fax"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">邮箱</label>
            <label class="col-sm-4 control-label content-label" id="email"></label>
            <label class="col-sm-2 control-label">网址</label>
            <label class="col-sm-4 control-label content-label" id="url"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">注册资金</label>
            <label class="col-sm-4 control-label content-label" id="registeredCapital"></label>
            <label class="col-sm-2 control-label">开票抬头</label>
            <label class="col-sm-4 control-label content-label" id="invoiceTitle"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">开票注册地址</label>
            <label class="col-sm-4 control-label content-label" id="invoiceRegisteredAddress"></label>
            <label class="col-sm-2 control-label">开票电话</label>
            <label class="col-sm-4 control-label content-label" id="invoiceTelephone"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">开票开户行</label>
            <label class="col-sm-4 control-label content-label" id="invoiceBank"></label>
            <label class="col-sm-2 control-label">开票帐号</label>
            <label class="col-sm-4 control-label content-label" id="invoiceAccountNo"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">开票税号</label>
            <label class="col-sm-4 control-label content-label" id="invoiceTaxNo"></label>
            <label class="col-sm-2 control-label">联系人</label>
            <label class="col-sm-4 control-label content-label" id="contactName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">联系人电话</label>
            <label class="col-sm-4 control-label content-label" id="contactTelephone"></label>
            <label class="col-sm-2 control-label">邮箱</label>
            <label class="col-sm-4 control-label content-label" id="contactEmail"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">业务范围</label>
            <label class="col-sm-10 control-label content-label" id="businessScope"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">企业简介</label>
            <label class="col-sm-10 control-label content-label" id="introduction" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/enterprise/EnterpriseDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function EnterpriseDetail_Page_Load() {
        SysApp.Sys.EnterpriseDetailIns = new SysApp.Sys.EnterpriseDetail();
        var instance = SysApp.Sys.EnterpriseDetailIns;
        instance.selfInstance = "SysApp.Sys.EnterpriseDetailIns";
        instance.clientID = "EnterpriseDetail";
        instance.isDetail = true;
        instance.entry = "${entry}";
        instance.controller = "${ctx}/sys/enterprise/";
        instance.init();
    }

    EnterpriseDetail_Page_Load();
    //]]>
</script>