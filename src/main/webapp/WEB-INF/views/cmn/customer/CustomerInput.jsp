<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/customer/CustomerInput.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">合同客户录入</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <div class="CustomerInput-MainContent">
                <ctag:Fold id="customerInfo" name="客户信息"/>
                <div id="customerInfo" class="separate-block clearfix">
                    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
                    <form id="MainForm" class="form-horizontal">
                        <input id="uid" type="hidden">

                        <div class="form-group form-inline">
                            <label class="col-sm-1 control-label">客户名称</label>
                            <div class="col-sm-3">
                                <input id="customerName" type="text" class="form-control required duplication" data-title="客户名称" maxlength="128" data-rangelength="[0,128]" style="width: 200px;"/>
                                <label id="customerName_Error" class="validator-error"></label>
                            </div>
                            <label class="col-sm-1 control-label">客户简称</label>
                            <div class="col-sm-3">
                                <input id="customerAbbreviation" type="text" class="form-control" data-title="客户简称" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                                <label id="customerAbbreviation_Error" class="validator-error"></label>
                            </div>
                            <label class="col-sm-1 control-label">主客户</label>
                            <div class="col-sm-3">
                                <ctag:Select2 selectId="mainCustomerUid" dataTitle="主客户" style="width:200px"/>
                                <label id="mainCustomerUid_Error" class="validator-error"></label>
                            </div>
                        </div>

                        <div class="form-group form-inline">
                            <label class="col-sm-1 control-label">地址</label>
                            <div class="col-sm-3">
                                <input id="address" type="text" class="form-control" data-title="地址" maxlength="128" data-rangelength="[0,128]" style="width: 200px;"/>
                                <label id="address_Error" class="validator-error"></label>
                            </div>

                            <label class="col-sm-1 control-label">电话</label>
                            <div class="col-sm-3">
                                <input id="telephone" type="text" class="form-control" data-title="电话" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                                <label id="telephone_Error" class="validator-error"></label>
                            </div>

                            <label class="col-sm-1 control-label">开票抬头</label>
                            <div class="col-sm-3">
                                <input id="invoiceTitle" type="text" class="form-control" data-title="开票抬头" maxlength="128" data-rangelength="[0,128]" style="width: 200px;"/>
                                <label id="invoiceTitle_Error" class="validator-error"></label>
                            </div>
                        </div>

                        <div class="form-group form-inline">
                            <label class="col-sm-1 control-label">开票注册地址</label>
                            <div class="col-sm-3">
                                <input id="invoiceRegisteredAddress" type="text" class="form-control" data-title="开票注册地址" maxlength="128" data-rangelength="[0,128]" style="width: 200px;"/>
                                <label id="invoiceRegisteredAddress_Error" class="validator-error"></label>
                            </div>

                            <label class="col-sm-1 control-label">开票电话</label>
                            <div class="col-sm-3">
                                <input id="invoiceTelephone" type="text" class="form-control" data-title="开票电话" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                                <label id="invoiceTelephone_Error" class="validator-error"></label>
                            </div>

                            <label class="col-sm-1 control-label">开票开户行</label>
                            <div class="col-sm-3">
                                <input id="invoiceBank" type="text" class="form-control" data-title="开票开户行" maxlength="64" data-rangelength="[0,64]" style="width: 200px;"/>
                                <label id="invoiceBank_Error" class="validator-error"></label>
                            </div>
                        </div>

                        <div class="form-group form-inline">
                            <label class="col-sm-1 control-label">开票帐号</label>
                            <div class="col-sm-3">
                                <input id="invoiceAccountNo" type="text" class="form-control" data-title="开票帐号" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                                <label id="invoiceAccountNo_Error" class="validator-error"></label>
                            </div>

                            <label class="col-sm-1 control-label">开票税号</label>
                            <div class="col-sm-3">
                                <input id="invoiceTaxNo" type="text" class="form-control" data-title="开票税号" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                                <label id="invoiceTaxNo_Error" class="validator-error"></label>
                            </div>

                            <label class="col-sm-1 control-label">所属行业</label>
                            <div class="col-sm-3">
                                <select id="industryCd" class="form-control" data-title="所属行业" style="width: 200px;">
                                    <option value="">请选择</option>
                                </select>
                                <label id="industryCd_Error" class="validator-error"></label>
                            </div>
                        </div>

                        <div class="form-group form-inline">
                            <label class="col-sm-1 control-label">备注</label>
                            <div class="col-sm-9">
                                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 200px;"></textarea>
                                <label id="remark_Error" class="validator-error"></label>
                            </div>
                        </div>
                    </form>
                    <div class="text-center" style="padding-bottom: 10px;">
                        <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                        <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                        <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
                    </div>
                </div>

                <%--原始对象Json字符串[实体属性校验用]--%>
                <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Customer}"/>

                <%--下拉框选项数据集合--%>
                <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Customer}"/>
            </div>
            <%@ include file="/WEB-INF/views/cmn/customeruser/CustomerUserByCustomerInnerList.jsp" %>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CustomerInput_Page_Load() {
        SysApp.Cmn.CustomerInputIns = new SysApp.Cmn.CustomerInput();
        var instance = SysApp.Cmn.CustomerInputIns;
        instance.selfInstance = "SysApp.Cmn.CustomerInputIns";
        instance.controller = "${ctx}/cmn/customer/";
        instance.listUrl = "${ctx}/cmn/customer/list";
        instance.clientID = "CustomerInput";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    CustomerInput_Page_Load();
    //]]>
</script>