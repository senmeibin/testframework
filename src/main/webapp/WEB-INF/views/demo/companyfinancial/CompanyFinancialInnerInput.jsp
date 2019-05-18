<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/CompanyExtendedCommon.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyfinancial/CompanyFinancialInnerInput.js?${version}"></script>


<div class="panel-body CompanyFinancialInnerInput-MainContent" id="companyFinancialInnerInputDiv" style="display: none;padding-top:0px;">
    <div style="border: 1px solid #09c; border-top-width: 3px; padding: 15px 15px;">
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm" class="form-horizontal">
            <input id="uid" type="hidden" data-allow-clear="true">

            <ctag:Fold id="divBaseInfo" name="基本信息"></ctag:Fold>
            <div id="divBaseInfo" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">年份</label>
                    <div class="col-lg-4">
                        <input id="particularYear" type="text" class="form-control" data-title="年份" maxlength="4" data-rangelength="[0,4]" style="width: 220px;"/>
                        <label id="particularYear_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">年末总资产(万元)</label>
                    <div class="col-lg-4">
                        <input id="totalAssets" type="text" class="form-control" data-title="年末总资产(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="totalAssets_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">年末负债总额(万元)</label>
                    <div class="col-lg-4">
                        <input id="totalLiabilities" type="text" class="form-control" data-title="年末负债总额(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="totalLiabilities_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">年末固定资产总额(万元)</label>
                    <div class="col-lg-4">
                        <input id="totalFixedAssets" type="text" class="form-control" data-title="年末固定资产总额(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="totalFixedAssets_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">年末完成固定资产投资额(万元)</label>
                    <div class="col-lg-4">
                        <input id="totalCompletedAssets" type="text" class="form-control" data-title="年末完成固定资产投资额(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;"
                               data-money="true"/>
                        <label id="totalCompletedAssets_Error" class="validator-error"></label>
                    </div>

                </div>
            </div>

            <ctag:Fold id="divBusinessIncome" name="营业收入" marginTop="10px"></ctag:Fold>
            <div id="divBusinessIncome" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">营业收入(万元)</label>
                    <div class="col-lg-4">
                        <input id="businessIncome" type="text" class="form-control" data-title="营业收入(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="businessIncome_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">主营业收入(万元)</label>
                    <div class="col-lg-4">
                        <input id="mainBusinessIncome" type="text" class="form-control" data-title="主营业收入(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="mainBusinessIncome_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">高新技术产品销售收入(万元)</label>
                    <div class="col-lg-4">
                        <input id="highTechIncome" type="text" class="form-control" data-title="高新技术产品销售收入(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="highTechIncome_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">软件产品及服务收入(万元)</label>
                    <div class="col-lg-4">
                        <input id="softwareServicesIncome" type="text" class="form-control" data-title="软件产品及服务收入(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;"
                               data-money="true"/>
                        <label id="softwareServicesIncome_Error" class="validator-error"></label>
                    </div>
                </div>
            </div>

            <ctag:Fold id="divGrossIndustrialOutputValue" name="工业总产值" marginTop="10px"></ctag:Fold>
            <div id="divGrossIndustrialOutputValue" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">工业总产值(万元)</label>
                    <div class="col-lg-4">
                        <input id="grossOutput" type="text" class="form-control" data-title="工业总产值(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="grossOutput_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">高新技术产品产值(万元)</label>
                    <div class="col-lg-4">
                        <input id="highTechOutput" type="text" class="form-control" data-title="高新技术产品产值(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="highTechOutput_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">自主知识产权产品产值(万元)</label>
                    <div class="col-lg-4">
                        <input id="independentOutput" type="text" class="form-control" data-title="自主知识产权产品产值" (万元) maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;"
                               data-money="true"/>
                        <label id="independentOutput_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">增加值(万元)</label>
                    <div class="col-lg-4">
                        <input id="increaseValue" type="text" class="form-control" data-title="增加值(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="increaseValue_Error" class="validator-error"></label>
                    </div>
                </div>
            </div>

            <ctag:Fold id="divForeignExchangeEarning" name="出口创汇" marginTop="10px"></ctag:Fold>
            <div id="divForeignExchangeEarning" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">出口创汇(万元)</label>
                    <div class="col-lg-4">
                        <input id="exportEarnings" type="text" class="form-control" data-title="出口创汇(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="exportEarnings_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">当年R&D投入(万元)</label>
                    <div class="col-lg-4">
                        <input id="rdInvestment" type="text" class="form-control" data-title="当年R&D投入(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="rdInvestment_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">高新技术产品出口额(万元)</label>
                    <div class="col-lg-4">
                        <input id="highTechProductsOutlet" type="text" class="form-control" data-title="高新技术产品出口额(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;"
                               data-money="true"/>
                        <label id="highTechProductsOutlet_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">税后利润(万元)</label>
                    <div class="col-lg-4">
                        <input id="afterTaxProfits" type="text" class="form-control" data-title="税后利润(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="afterTaxProfits_Error" class="validator-error"></label>
                    </div>
                </div>
            </div>

            <ctag:Fold id="divTurnOverTaxesAndFees" name="上缴税费" marginTop="10px"></ctag:Fold>
            <div id="divTurnOverTaxesAndFees" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">实际上缴税费(万元)</label>
                    <div class="col-lg-4">
                        <input id="totalTax" type="text" class="form-control" data-title="实际上缴税费(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="totalTax_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">增值税(万元)</label>
                    <div class="col-lg-4">
                        <input id="addedValueTax" type="text" class="form-control" data-title="增值税(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="addedValueTax_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">营业税(万元)</label>
                    <div class="col-lg-4">
                        <input id="businessTax" type="text" class="form-control" data-title="营业税(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="businessTax_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">所得税(万元)</label>
                    <div class="col-lg-4">
                        <input id="incomeTax" type="text" class="form-control" data-title="所得税(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="incomeTax_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">个人所得税(万元)</label>
                    <div class="col-lg-4">
                        <input id="personalIncomeTax" type="text" class="form-control" data-title="个人所得税(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="personalIncomeTax_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">其他税(万元)</label>
                    <div class="col-lg-4">
                        <input id="otherTax" type="text" class="form-control" data-title="其他税(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="otherTax_Error" class="validator-error"></label>
                    </div>
                </div>
            </div>

            <ctag:Fold id="divTaxRelief" name="减免税费" marginTop="10px"></ctag:Fold>
            <div id="divTaxRelief" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">实际减免税费总额(万元)</label>
                    <div class="col-lg-4">
                        <input id="totalDeductionsTax" type="text" class="form-control" data-title="实际减免税费总额(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="totalDeductionsTax_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">减免增值税(万元)</label>
                    <div class="col-lg-4">
                        <input id="deductionsAddedValueTax" type="text" class="form-control" data-title="减免增值税(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;"
                               data-money="true"/>
                        <label id="deductionsAddedValueTax_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">减免营业额(万元)</label>
                    <div class="col-lg-4">
                        <input id="deductionsBusinessTax" type="text" class="form-control" data-title="减免营业额(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="deductionsBusinessTax_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">减免所得税</label>
                    <div class="col-lg-4">
                        <input id="deductionsIncomeTax" type="text" class="form-control" data-title="减免所得税" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="deductionsIncomeTax_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">研发加计扣除减免(万元)</label>
                    <div class="col-lg-4">
                        <input id="deductionsRdTax" type="text" class="form-control" data-title="研发加计扣除减免(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="deductionsRdTax_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">高新技术企业专项减免(万元)</label>
                    <div class="col-lg-4">
                        <input id="deductionsHighTechTax" type="text" class="form-control" data-title="高新技术企业专项减免(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;"
                               data-money="true"/>
                        <label id="deductionsHighTechTax_Error" class="validator-error"></label>
                    </div>
                </div>
            </div>
            <ctag:Fold id="divRemark" name="备注信息" marginTop="10px"></ctag:Fold>
            <div id="divRemark" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">备注</label>
                    <div class="col-lg-8">
                        <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 98%;"></textarea>
                        <label id="remark_Error" class="validator-error"></label>
                    </div>
                </div>
            </div>
        </form>

        <div class="text-center margin-top-space">
            <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存财务信息</button>
            <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
            <button type="button" class="btn btn-default btn-100px" id="btnClose"><i class="fa fa-close"></i>关闭</button>
        </div>

        <%--原始对象Json字符串[实体属性校验用]--%>
        <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_CompanyFinancial}"/>

        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyFinancial}"/>
    </div>

</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyFinancialInnerInput_Page_Load() {
        SysApp.Demo.CompanyFinancialInnerInputIns = new SysApp.Demo.CompanyFinancialInnerInput();
        var instance = SysApp.Demo.CompanyFinancialInnerInputIns;
        instance.selfInstance = "SysApp.Demo.CompanyFinancialInnerInputIns";
        instance.controller = "${ctx}/demo/companyfinancial/";
        instance.clientID = "CompanyFinancialInnerInput";

        instance.init();
    }

    CompanyFinancialInnerInput_Page_Load();
    //]]>
</script>