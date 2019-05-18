<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/CompanyExtendedCommon.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyadditional/CompanyAdditionalInnerInput.js?${version}"></script>

<div class="panel-body CompanyAdditionalInnerInput-MainContent" id="companyAdditionalInnerInputDiv" style="display: none;padding-top:0px;">
    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="form-horizontal">
        <div style="border: 1px solid #09c; border-top-width: 3px; padding: 15px 0;">
            <input id="uid" type="hidden" data-allow-clear="true">

            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">上市时间</label>
                <div class="col-lg-4">
                    <ctag:CalendarSelect id="listedTime" title="上市时间" showValidateError="true" width="181px"></ctag:CalendarSelect>
                </div>
                <label class="col-lg-1 control-label">上市市场</label>
                <div class="col-lg-4">
                    <select id="listedTypeCd" class="form-control" data-title="上市市场" style="width: 220px;">
                        <option value="">请选择</option>
                    </select>
                    <label id="listedTypeCd_Error" class="validator-error"></label>
                </div>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">证券代码</label>
                <div class="col-lg-4">
                    <input id="stockCode" type="text" class="form-control" data-title="证券代码" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                    <label id="stockCode_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">总股本</label>
                <div class="col-lg-4">
                    <input id="totalShare" type="text" class="form-control" data-title="总股本" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                    <label id="totalShare_Error" class="validator-error"></label>
                </div>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">年底市值(万元)</label>
                <div class="col-lg-4">
                    <input id="yearEndMarketValue" type="text" class="form-control" data-title="年底市值(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" data-money="true" style="width: 220px;"/>
                    <label id="yearEndMarketValue_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">退市时间</label>
                <div class="col-lg-4">
                    <ctag:CalendarSelect id="delistingDate" title="退市时间" showValidateError="true" width="181px"></ctag:CalendarSelect>
                </div>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">备注</label>
                <div class="col-lg-8">
                    <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 98%;"></textarea>
                    <label id="remark_Error" class="validator-error"></label>
                </div>
            </div>
            <div class="text-center margin-top-space">
                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存附加信息</button>
                <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                <button type="button" class="btn btn-default btn-100px" id="btnClose"><i class="fa fa-close"></i>关闭</button>
            </div>
        </div>
    </form>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_CompanyAdditional}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyAdditional}"/>
</div>

<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyAdditionalInnerInput_Page_Load() {
        SysApp.Demo.CompanyAdditionalInnerInputIns = new SysApp.Demo.CompanyAdditionalInnerInput();
        var instance = SysApp.Demo.CompanyAdditionalInnerInputIns;
        instance.selfInstance = "SysApp.Demo.CompanyAdditionalInnerInputIns";
        instance.controller = "${ctx}/demo/companyadditional/";
        instance.clientID = "CompanyAdditionalInnerInput";

        instance.init();
    }

    CompanyAdditionalInnerInput_Page_Load();
    //]]>
</script>