<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CompanyFinancialDetail-MainContent" style="width: 1200px; display: none;">
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
                <label class="col-lg-3 control-label content-label" id="particularYear"></label>
                <label class="col-lg-1 control-label">年末负债总额(万元)</label>
                <label class="col-lg-3 control-label content-label" id="totalLiabilities" data-money="true"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">年末总资产(万元)</label>
                <label class="col-lg-3 control-label content-label" id="totalAssets" data-money="true"></label>
                <label class="col-lg-1 control-label">年末固定资产总额(万元)</label>
                <label class="col-lg-3 control-label content-label" id="totalFixedAssets" data-money="true"></label>
                <label class="col-lg-1 control-label">年末完成固定资产投资额(万元)</label>
                <label class="col-lg-3 control-label content-label" id="totalCompletedAssets" data-money="true"></label>
            </div>
        </div>
        <ctag:Fold id="divBusinessIncome" name="营业收入" marginTop="10px"></ctag:Fold>
        <div id="divBusinessIncome" class="separate-block clearfix">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">营业收入(万元)</label>
                <label class="col-lg-3 control-label content-label" id="businessIncome" data-money="true"></label>
                <label class="col-lg-1 control-label">主营业收入(万元)</label>
                <label class="col-lg-3 control-label content-label" id="mainBusinessIncome" data-money="true"></label>
                <label class="col-lg-1 control-label">高新技术产品销售收入(万元)</label>
                <label class="col-lg-3 control-label content-label" id="highTechIncome" data-money="true"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">软件产品及服务收入(万元)</label>
                <label class="col-lg-3 control-label content-label" id="softwareServicesIncome" data-money="true"></label>
            </div>
        </div>
        <ctag:Fold id="divGrossIndustrialOutputValue" name="工业总产值" marginTop="10px"></ctag:Fold>
        <div id="divGrossIndustrialOutputValue" class="separate-block clearfix">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">工业总产值(万元)</label>
                <label class="col-lg-3 control-label content-label" id="grossOutput" data-money="true"></label>
                <label class="col-lg-1 control-label">高新技术产品产值(万元)</label>
                <label class="col-lg-3 control-label content-label" id="highTechOutput" data-money="true"></label>
                <label class="col-lg-1 control-label">自主知识产权产品产值(万元)</label>
                <label class="col-lg-3 control-label content-label" id="independentOutput" data-money="true"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">增加值(万元)</label>
                <label class="col-lg-3 control-label content-label" id="increaseValue" data-money="true"></label>
            </div>
        </div>

        <ctag:Fold id="divForeignExchangeEarning" name="出口创汇" marginTop="10px"></ctag:Fold>
        <div id="divForeignExchangeEarning" class="separate-block clearfix">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">出口创汇(万元)</label>
                <label class="col-lg-3 control-label content-label" id="exportEarnings" data-money="true"></label>
                <label class="col-lg-1 control-label">当年R&D投入(万元)</label>
                <label class="col-lg-3 control-label content-label" id="rdInvestment" data-money="true"></label>
                <label class="col-lg-1 control-label">高新技术产品出口额(万元)</label>
                <label class="col-lg-3 control-label content-label" id="highTechProductsOutlet" data-money="true"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">税后利润(万元)</label>
                <label class="col-lg-3 control-label content-label" id="afterTaxProfits" data-money="true"></label>
            </div>
        </div>

        <ctag:Fold id="divTurnOverTaxesAndFees" name="上缴税费" marginTop="10px"></ctag:Fold>
        <div id="divTurnOverTaxesAndFees" class="separate-block clearfix">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">实际上缴税费(万元)</label>
                <label class="col-lg-3 control-label content-label" id="totalTax" data-money="true"></label>
                <label class="col-lg-1 control-label">增值税(万元)</label>
                <label class="col-lg-3 control-label content-label" id="addedValueTax" data-money="true"></label>
                <label class="col-lg-1 control-label">营业税(万元)</label>
                <label class="col-lg-3 control-label content-label" id="businessTax" data-money="true"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">所得税(万元)</label>
                <label class="col-lg-3 control-label content-label" id="incomeTax" data-money="true"></label>
                <label class="col-lg-1 control-label">个人所得税(万元)</label>
                <label class="col-lg-3 control-label content-label" id="personalIncomeTax" data-money="true"></label>
                <label class="col-lg-1 control-label">其他税(万元)</label>
                <label class="col-lg-3 control-label content-label" id="otherTax" data-money="true"></label>
            </div>
        </div>
        <ctag:Fold id="divTaxRelief" name="减免税费" marginTop="10px"></ctag:Fold>
        <div id="divTaxRelief" class="separate-block clearfix">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">实际减免税费总额(万元)</label>
                <label class="col-lg-3 control-label content-label" id="totalDeductionsTax" data-money="true"></label>
                <label class="col-lg-1 control-label">减免增值税(万元)</label>
                <label class="col-lg-3 control-label content-label" id="deductionsAddedValueTax" data-money="true"></label>
                <label class="col-lg-1 control-label">减免营业额(万元)</label>
                <label class="col-lg-3 control-label content-label" id="deductionsBusinessTax" data-money="true"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">减免所得税(万元)</label>
                <label class="col-lg-3 control-label content-label" id="deductionsIncomeTax" data-money="true"></label>
                <label class="col-lg-1 control-label">研发加计扣除减免(万元)</label>
                <label class="col-lg-3 control-label content-label" id="deductionsRdTax" data-money="true"></label>
                <label class="col-lg-1 control-label">高新技术企业专项减免(万元)</label>
                <label class="col-lg-3 control-label content-label" id="deductionsHighTechTax" data-money="true"></label>
            </div>
        </div>
        <ctag:Fold id="divRemark" name="备注信息" marginTop="10px"></ctag:Fold>
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
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyfinancial/CompanyFinancialDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyFinancialDetail_Page_Load() {
        SysApp.Demo.CompanyFinancialDetailIns = new SysApp.Demo.CompanyFinancialDetail();
        var instance = SysApp.Demo.CompanyFinancialDetailIns;
        instance.selfInstance = "SysApp.Demo.CompanyFinancialDetailIns";
        instance.clientID = "CompanyFinancialDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/demo/companyfinancial/";
        instance.init();
    }

    CompanyFinancialDetail_Page_Load();
    //]]>
</script>