<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content PotentialCustomerDetail-MainContent" style="width: 1200px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <ctag:Fold id="divBaseInfo" name="基本信息"></ctag:Fold>
        <div id="divBaseInfo" class="separate-block clearfix">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">企业名称</label>
                <label class="col-lg-3 control-label content-label" id="companyName"></label>
                <label class="col-lg-1 control-label">组织机构代码</label>
                <label class="col-lg-3 control-label content-label" id="organizationNo"></label>
                <label class="col-lg-1 control-label">成立时间</label>
                <label class="col-lg-3 control-label content-label" id="establishmentDate" data-date="true"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">注册地区（省）</label>
                <label class="col-lg-3 control-label content-label" id="registeredProvinceName"></label>
                <label class="col-lg-1 control-label">注册地区（市）</label>
                <label class="col-lg-3 control-label content-label" id="registeredCityName"></label>
                <label class="col-lg-1 control-label">注册地区（县）</label>
                <label class="col-lg-3 control-label content-label" id="registeredAreaName"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">注册地址</label>
                <label class="col-lg-3 control-label content-label" id="registeredAddress"></label>
                <label class="col-lg-1 control-label">注册资金</label>
                <label class="col-lg-3 control-label content-label" id="registeredCapital"></label>
            </div>
        </div>
        <ctag:Fold id="divConcatInfo" name="联系方式" marginTop="10px"></ctag:Fold>
        <div id="divConcatInfo" class="separate-block clearfix">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">实际负责人</label>
                <label class="col-lg-3 control-label content-label" id="companyPrincipal"></label>
                <label class="col-lg-1 control-label">负责人电话</label>
                <label class="col-lg-3 control-label content-label" id="principalPhone"></label>
                <label class="col-lg-1 control-label">负责人邮箱</label>
                <label class="col-lg-3 control-label content-label" id="principalMail"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">联系人</label>
                <label class="col-lg-3 control-label content-label" id="contactPerson"></label>
                <label class="col-lg-1 control-label">联系人电话</label>
                <label class="col-lg-3 control-label content-label" id="contactPhone"></label>
                <label class="col-lg-1 control-label">联系人邮箱</label>
                <label class="col-lg-3 control-label content-label" id="contactMail"></label>
            </div>
        </div>
        <ctag:Fold id="divFollowUpInfo" name="对接信息" marginTop="10px"></ctag:Fold>
        <div id="divFollowUpInfo" class="separate-block clearfix">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">负责人</label>
                <label class="col-lg-3 control-label content-label" id="principal"></label>
                <label class="col-lg-1 control-label">企业类型</label>
                <label class="col-lg-3 control-label content-label" id="companyTypeName"></label>
                <label class="col-lg-1 control-label">入驻概率</label>
                <label class="col-lg-3 control-label content-label" id="entryProbabilityName"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">联络内容</label>
                <label class="col-lg-3 control-label content-label" id="contactContent"></label>
                <label class="col-lg-1 control-label">后续对接方案</label>
                <label class="col-lg-3 control-label content-label" id="followUpScheme"></label>
            </div>
        </div>
        <ctag:Fold id="divOtherInfo" name="其他信息" marginTop="10px"></ctag:Fold>
        <div id="divOtherInfo" class="separate-block clearfix">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">办公地址</label>
                <label class="col-lg-3 control-label content-label" id="officeAddress"></label>
                <label class="col-lg-1 control-label">办公地邮政编码</label>
                <label class="col-lg-3 control-label content-label" id="officePostalCode"></label>
                <label class="col-lg-1 control-label">登记注册类型</label>
                <label class="col-lg-3 control-label content-label" id="registrationTypeName"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">高新技术分类</label>
                <label class="col-lg-3 control-label content-label" id="highTechType"></label>
                <label class="col-lg-1 control-label">行业分类</label>
                <label class="col-lg-3 control-label content-label" id="industryCategory"></label>
                <label class="col-lg-1 control-label">专业技术方向</label>
                <label class="col-lg-3 control-label content-label" id="professionalTechnicalName"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">网站/APP</label>
                <label class="col-lg-3 control-label content-label" id="website"></label>
                <label class="col-lg-1 control-label">企业简介</label>
                <label class="col-lg-3 control-label content-label" id="companyProfile"></label>
                <label class="col-lg-1 control-label">备注</label>
                <label class="col-lg-3 control-label content-label" id="remark" data-textarea="true"></label>
            </div>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/potentialcustomer/PotentialCustomerDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function PotentialCustomerDetail_Page_Load() {
        SysApp.Demo.PotentialCustomerDetailIns = new SysApp.Demo.PotentialCustomerDetail();
        var instance = SysApp.Demo.PotentialCustomerDetailIns;
        instance.selfInstance = "SysApp.Demo.PotentialCustomerDetailIns";
        instance.clientID = "PotentialCustomerDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/demo/potentialcustomer/";
        instance.init();
    }

    PotentialCustomerDetail_Page_Load();
    //]]>
</script>