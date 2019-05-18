<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/CompanyExtendedCommon.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companystock/CompanyStockInnerInput.js?${version}"></script>

<div class="panel-body CompanyStockInnerInput-MainContent" id="companyStockInnerInputDiv" style="display: none;padding-top:0px;">
    <div style="border: 1px solid #09c; border-top-width: 3px; padding: 15px 0;">
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm" class="form-horizontal">
            <input id="uid" type="hidden" data-allow-clear="true">
            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">股东类型</label>
                <div class="col-lg-4">
                    <select id="shareholderTypeCd" class="form-control" data-title="股东类型" style="width: 220px;">
                        <option value="">请选择</option>
                    </select>
                    <label id="shareholderTypeCd_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">股东名称</label>
                <div class="col-lg-4">
                    <input id="shareholderName" type="text" class="form-control" data-title="股东名称" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                    <label id="shareholderName_Error" class="validator-error"></label>
                </div>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">证件类型</label>
                <div class="col-lg-4">
                    <select id="certificateTypeCd" class="form-control" data-title="证件类型" style="width: 220px;">
                        <option value="">请选择</option>
                    </select>
                    <label id="certificateTypeCd_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">上市公司</label>
                <div class="col-lg-4">
                    <select id="listedCompanyCd" class="form-control" data-title="上市公司" style="width: 220px;">
                        <option value="">请选择</option>
                    </select>
                    <label id="listedCompanyCd_Error" class="validator-error"></label>
                </div>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">入选千人计划</label>
                <div class="col-lg-4">
                    <select id="thousandsPeoplePlanCd" class="form-control" data-title="入选千人计划" style="width: 220px;">
                        <option value="">请选择</option>
                    </select>
                    <label id="thousandsPeoplePlanCd_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">所占股份</label>
                <div class="col-lg-4">
                    <input id="sharesProportion" type="text" class="form-control" data-title="所占股份" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                    <label id="sharesProportion_Error" class="validator-error"></label>
                </div>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">投资总额(万元)</label>
                <div class="col-lg-4">
                    <input id="totalInvestment" type="text" class="form-control" data-title="投资总额(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                    <label id="totalInvestment_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">境外公司或外籍</label>
                <div class="col-lg-4">
                    <select id="overseasCompanyCd" class="form-control" data-title="境外公司或外籍" style="width: 220px;">
                        <option value="">请选择</option>
                    </select>
                    <label id="overseasCompanyCd_Error" class="validator-error"></label>
                </div>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">外资部门所占股份总和(%)</label>
                <div class="col-lg-4">
                    <input id="totalShareForeignCapital" type="text" class="form-control" data-title="外资部门所占股份总和(%)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;"/>
                    <label id="totalShareForeignCapital_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">上市企业所占股份总和(%)</label>
                <div class="col-lg-4">
                    <input id="totalSharesListedCompany" type="text" class="form-control" data-title="上市企业所占股份总和(%)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;"/>
                    <label id="totalSharesListedCompany_Error" class="validator-error"></label>
                </div>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">风险投资（基金）公司</label>
                <div class="col-lg-4">
                    <select id="fundCompanyCd" class="form-control" data-title="风险投资（基金）公司" style="width: 220px;">
                        <option value="">请选择</option>
                    </select>
                    <label id="fundCompanyCd_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">第一投资时间</label>
                <div class="col-lg-4">
                    <ctag:CalendarSelect id="firstInvestmentDate" title="第一投资时间" showValidateError="true" width="181px"></ctag:CalendarSelect>
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
            <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存股权信息</button>
            <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
            <button type="button" class="btn btn-default btn-100px" id="btnClose"><i class="fa fa-close"></i>关闭</button>
        </div>

        <%--原始对象Json字符串[实体属性校验用]--%>
        <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_CompanyStock}"/>

        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyStock}"/>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyStockInnerInput_Page_Load() {
        SysApp.Demo.CompanyStockInnerInputIns = new SysApp.Demo.CompanyStockInnerInput();
        var instance = SysApp.Demo.CompanyStockInnerInputIns;
        instance.selfInstance = "SysApp.Demo.CompanyStockInnerInputIns";
        instance.controller = "${ctx}/demo/companystock/";
        instance.clientID = "CompanyStockInnerInput";

        instance.init();
    }

    CompanyStockInnerInput_Page_Load();
    //]]>
</script>