<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/chargesinformation/ChargesInformationInput.js?${version}"></script>

<title>企业收费信息编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height">
        <div class="panel-heading ChargesInformationInput-MainContent">
            <em></em>
            <div class="panel-title" id="ctlTitle">企业收费信息编辑</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <%@ include file="/WEB-INF/views/demo/companyinformation/CompanyInformationInnerDetail.jsp" %>
            <div class="ChargesInformationInput-MainContent margin-top-space">
                <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
                <form id="MainForm" class="form-horizontal">
                    <input id="uid" type="hidden">
                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">单位</label>
                        <div class="col-lg-4">
                            <select id="unitCd" class="form-control" data-title="单位" style="width: 220px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="unitCd_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">数量</label>
                        <div class="col-lg-4">
                            <input id="quantity" type="text" class="form-control" data-title="数量" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;"/>
                            <label id="quantity_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">工位位置</label>
                        <div class="col-lg-4">
                            <input id="stationPosition" type="text" class="form-control" data-title="工位位置" maxlength="64" data-rangelength="[0,64]" style="width: 220px;"/>
                            <label id="stationPosition_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">工位租金</label>
                        <div class="col-lg-4">
                            <input id="stationRent" type="text" class="form-control" data-title="工位租金" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                            <label id="stationRent_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">收租方式</label>
                        <div class="col-lg-4">
                            <select id="rentWayCd" class="form-control" data-title="收租方式" style="width: 220px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="rentWayCd_Error" class="validator-error"></label>
                        </div>

                        <label class="col-lg-1 control-label">收费方式</label>
                        <div class="col-lg-4">
                            <select id="chargesTypeCd" class="form-control required" data-title="收费方式" style="width: 220px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="chargesTypeCd_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">押金方式</label>
                        <div class="col-lg-4">
                            <select id="depositMethodCd" class="form-control" data-title="押金方式" style="width: 220px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="depositMethodCd_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">企业押金</label>
                        <div class="col-lg-4">
                            <input id="businessDeposit" type="text" class="form-control" data-title="企业押金" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                            <label id="businessDeposit_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">第一次收费日期</label>
                        <div class="col-lg-4">
                            <ctag:CalendarSelect id="firstChargeDate" title="第一次收费日期" showValidateError="true" width="181px"></ctag:CalendarSelect>
                        </div>
                        <label class="col-lg-1 control-label">每期收费金额</label>
                        <div class="col-lg-4">
                            <input id="eachChargeAmount" type="text" class="form-control" data-title="每期收费金额" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                            <label id="eachChargeAmount_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">下次收款日期</label>
                        <div class="col-lg-4">
                            <ctag:CalendarSelect id="nextReceiptDate" title="下次收款日期" showValidateError="true" width="181px"></ctag:CalendarSelect>
                        </div>
                        <label class="col-lg-1 control-label">备注</label>
                        <div class="col-lg-4">
                            <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 220px;"></textarea>
                            <label id="remark_Error" class="validator-error"></label>
                        </div>
                    </div>
                </form>

                <div class="text-center">
                    <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                    <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                    <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
                </div>

                <%--原始对象Json字符串[实体属性校验用]--%>
                <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_ChargesInformation}"/>

                <%--下拉框选项数据集合--%>
                <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ChargesInformation}"/>
            </div>
            <%@ include file="/WEB-INF/views/demo/chargesinformation/chargesinformationdetail/ChargesInformationDetailInnerList.jsp" %>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ChargesInformationInput_Page_Load() {
        SysApp.Demo.ChargesInformationInputIns = new SysApp.Demo.ChargesInformationInput();
        var instance = SysApp.Demo.ChargesInformationInputIns;
        instance.selfInstance = "SysApp.Demo.ChargesInformationInputIns";
        instance.controller = "${ctx}/demo/chargesinformation/";
        instance.listUrl = "${ctx}/demo/chargesinformation/list";
        instance.clientID = "ChargesInformationInput";

        instance.init();
    }

    ChargesInformationInput_Page_Load();
    //]]>
</script>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.ChargesInformationInputIns"/>