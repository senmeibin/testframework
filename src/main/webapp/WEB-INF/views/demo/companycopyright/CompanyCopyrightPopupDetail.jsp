<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CompanyCopyrightDetail-MainContent" style="width: 1200px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <ctag:Fold id="divBaseInfo" name="基本信息"></ctag:Fold>
        <div id="divBaseInfo" class="separate-block clearfix">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">企业名称</label>
                <label class="col-lg-3 control-label content-label" id="companyName"></label>
                <label class="col-lg-1 control-label">年份</label>
                <label class="col-lg-3 control-label content-label" id="year"></label>
                <label class="col-lg-1 control-label">当前承担国家级科技计划项目数</label>
                <label class="col-lg-3 control-label content-label" id="nationalPlanningProjectNum"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">当前获得省级以上奖励(万元)</label>
                <label class="col-lg-3 control-label content-label" id="provincialLevelNum" data-money="true"></label>
                <label class="col-lg-1 control-label">购买（被许可）国外专利</label>
                <label class="col-lg-3 control-label content-label" id="foreignPatentNum"></label>
                <label class="col-lg-1 control-label">技术合同交易数量</label>
                <label class="col-lg-3 control-label content-label" id="technicalContractNum"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">技术合同交易额(万元)</label>
                <label class="col-lg-3 control-label content-label" id="technicalContractAmount" data-money="true"></label>
                <label class="col-lg-1 control-label">研发加计备案数</label>
                <label class="col-lg-3 control-label content-label" id="rdCasesNum"></label>
            </div>
        </div>
        <ctag:Fold id="divResearchActivities" name="科研活动"></ctag:Fold>
        <div id="divResearchActivities" class="separate-block clearfix">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">累计工程中心数</label>
                <label class="col-lg-3 control-label content-label" id="totalEngineeringNum"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">国家级</label>
                <label class="col-lg-3 control-label content-label" id="enNationalLevelNum"></label>
                <label class="col-lg-1 control-label">省级</label>
                <label class="col-lg-3 control-label content-label" id="enProvincialLevelNum"></label>
                <label class="col-lg-1 control-label">市级</label>
                <label class="col-lg-3 control-label content-label" id="enMunicipalLevelNum"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">累计项目数量</label>
                <label class="col-lg-3 control-label content-label" id="totalProjectNum"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">国家级</label>
                <label class="col-lg-3 control-label content-label" id="projectNationalLevelNum"></label>
                <label class="col-lg-1 control-label">省级</label>
                <label class="col-lg-3 control-label content-label" id="projectProvincialLevelNum"></label>
                <label class="col-lg-1 control-label">市级</label>
                <label class="col-lg-3 control-label content-label" id="projectMunicipalLevelNum"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">累计获得国家资助经费金额(万元)</label>
                <label class="col-lg-3 control-label content-label" id="stateSubsidyAmount" data-money="true"></label>
                <label class="col-lg-1 control-label">累计获得省级资助经费金额(万元)</label>
                <label class="col-lg-3 control-label content-label" id="provincialSubsidyAmount" data-money="true"></label>
                <label class="col-lg-1 control-label">累计获得市级资助经费金额(万元)</label>
                <label class="col-lg-3 control-label content-label" id="municipalSubsidyAmount" data-money="true"></label>
            </div>
        </div>
        <ctag:Fold id="divIntellectualPropertyRight" name="知识产权"></ctag:Fold>
        <div id="divIntellectualPropertyRight" class="separate-block clearfix">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">当年知识产权申请数</label>
                <label class="col-lg-3 control-label content-label" id="iprApplicationsNum"></label>
                <label class="col-lg-1 control-label">当年发明专利申请数</label>
                <label class="col-lg-3 control-label content-label" id="patentInventionNum"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">当前知识产权授权数</label>
                <label class="col-lg-3 control-label content-label" id="intellectualPropertyNum"></label>
                <label class="col-lg-1 control-label">发明专利授权数</label>
                <label class="col-lg-3 control-label content-label" id="inventionPatentsAuthNum"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">拥有有效知识产权数</label>
                <label class="col-lg-3 control-label content-label" id="effectiveIntellectualNum"></label>
                <label class="col-lg-1 control-label">发明专利数</label>
                <label class="col-lg-3 control-label content-label" id="inventionPatentsNum"></label>
                <label class="col-lg-1 control-label">软件著作权登记数</label>
                <label class="col-lg-3 control-label content-label" id="softwareCopyrightNum"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">植物新品种</label>
                <label class="col-lg-3 control-label content-label" id="newPlantNum"></label>
                <label class="col-lg-1 control-label">集成电路布图设计登记数</label>
                <label class="col-lg-3 control-label content-label" id="icLayoutNum"></label>
                <label class="col-lg-1 control-label">其他知识产权登记数</label>
                <label class="col-lg-3 control-label content-label" id="otherIntellectualNum"></label>
            </div>
        </div>
        <ctag:Fold id="divRemark" name="备注信息"></ctag:Fold>
        <div id="divRemark" class="separate-block clearfix">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">备注</label>
                <label class="col-lg-8 control-label content-label" id="remark" data-textarea="true"></label>
            </div>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companycopyright/CompanyCopyrightDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyCopyrightDetail_Page_Load() {
        SysApp.Demo.CompanyCopyrightDetailIns = new SysApp.Demo.CompanyCopyrightDetail();
        var instance = SysApp.Demo.CompanyCopyrightDetailIns;
        instance.selfInstance = "SysApp.Demo.CompanyCopyrightDetailIns";
        instance.clientID = "CompanyCopyrightDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/demo/companycopyright/";
        instance.init();
    }

    CompanyCopyrightDetail_Page_Load();
    //]]>
</script>