<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/CompanyExtendedCommon.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyinvestment/CompanyInvestmentInput.js?${version}"></script>

<title>投融资编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height">
        <div class="panel-heading CompanyInvestmentInput-MainContent">
            <em></em>
            <div class="panel-title" id="ctlTitle">投融资编辑</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <%@ include file="/WEB-INF/views/demo/companyinformation/CompanyInformationInnerDetail.jsp" %>
            <div class="CompanyInvestmentInput-MainContent margin-top-space">
                <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
                <form id="MainForm" class="form-horizontal">
                    <input id="uid" type="hidden">
                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">接收投资时间</label>
                        <div class="col-lg-4">
                            <ctag:CalendarSelect id="investmentDate" title="接收投资时间" showValidateError="true" width="181px" required="required"></ctag:CalendarSelect>
                        </div>
                        <label class="col-lg-1 control-label">投资人</label>
                        <div class="col-lg-4">
                            <input id="investor" type="text" class="form-control" data-title="投资人" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                            <label id="investor_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">投资金额(万元)</label>
                        <div class="col-lg-4">
                            <input id="investmentAmount" type="text" class="form-control" data-title="投资金额(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                            <label id="investmentAmount_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">占股权比例(%)</label>
                        <div class="col-lg-4">
                            <input id="proportionOfStock" type="text" class="form-control" data-title="占股权比例" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;"/>
                            <label id="proportionOfStock_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">账面估值(万元)</label>
                        <div class="col-lg-4">
                            <input id="bookValuation" type="text" class="form-control" data-title="账面估值(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                            <label id="bookValuation_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">属于孵化基金</label>
                        <div class="col-lg-4">
                            <select id="incubationFundCd" class="form-control" data-title="属于孵化基金" style="width: 220px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="incubationFundCd_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">备注</label>
                        <div class="col-lg-8">
                            <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 98%;"></textarea>
                            <label id="remark_Error" class="validator-error"></label>
                        </div>
                    </div>
                </form>

                <div class="text-center margin-top-space">
                    <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                    <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                    <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
                </div>

                <%--原始对象Json字符串[实体属性校验用]--%>
                <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_CompanyInvestment}"/>

                <%--下拉框选项数据集合--%>
                <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyInvestment}"/>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyInvestmentInput_Page_Load() {
        SysApp.Demo.CompanyInvestmentInputIns = new SysApp.Demo.CompanyInvestmentInput();
        var instance = SysApp.Demo.CompanyInvestmentInputIns;
        instance.selfInstance = "SysApp.Demo.CompanyInvestmentInputIns";
        instance.controller = "${ctx}/demo/companyinvestment/";
        instance.listUrl = "${ctx}/demo/companyinvestment/list";
        instance.clientID = "CompanyInvestmentInput";

        instance.init();
    }

    CompanyInvestmentInput_Page_Load();
    //]]>
</script>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.CompanyInvestmentInputIns"/>