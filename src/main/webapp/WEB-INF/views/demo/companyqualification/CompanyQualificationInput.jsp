<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/CompanyExtendedCommon.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyqualification/CompanyQualificationInput.js?${version}"></script>

<title>企业资质编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height">
        <div class="panel-heading CompanyQualificationInput-MainContent">
            <em></em>
            <div class="panel-title" id="ctlTitle">企业资质编辑</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <%@ include file="/WEB-INF/views/demo/companyinformation/CompanyInformationInnerDetail.jsp" %>
            <div class="CompanyQualificationInput-MainContent margin-top-space">
                <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
                <form id="MainForm" class="form-horizontal">
                    <input id="uid" type="hidden">
                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">认定机构</label>
                        <div class="col-lg-4">
                            <input id="certificationAuthority" type="text" class="form-control" data-title="认定机构" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                            <label id="certificationAuthority_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">编号</label>
                        <div class="col-lg-4">
                            <input id="serialNumber" type="text" class="form-control" data-title="编号" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                            <label id="serialNumber_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">认定年份</label>
                        <div class="col-lg-4">
                            <input id="certificationYear" type="text" class="form-control" data-title="认定年份" maxlength="4" data-rangelength="[0,4]" style="width: 220px;"/>
                            <label id="certificationYear_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">复核/验收年份</label>
                        <div class="col-lg-4">
                            <input id="acceptanceYear" type="text" class="form-control" data-title="复核/验收年份" maxlength="4" data-rangelength="[0,4]" style="width: 220px;"/>
                            <label id="acceptanceYear_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">资助金额(万元)</label>
                        <div class="col-lg-4">
                            <input id="subsidyAmount" type="text" class="form-control" data-title="资助金额(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                            <label id="subsidyAmount_Error" class="validator-error"></label>
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
                <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_CompanyQualification}"/>

                <%--下拉框选项数据集合--%>
                <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyQualification}"/>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyQualificationInput_Page_Load() {
        SysApp.Demo.CompanyQualificationInputIns = new SysApp.Demo.CompanyQualificationInput();
        var instance = SysApp.Demo.CompanyQualificationInputIns;
        instance.selfInstance = "SysApp.Demo.CompanyQualificationInputIns";
        instance.controller = "${ctx}/demo/companyqualification/";
        instance.listUrl = "${ctx}/demo/companyqualification/list";
        instance.clientID = "CompanyQualificationInput";

        instance.init();
    }

    CompanyQualificationInput_Page_Load();
    //]]>
</script>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.CompanyQualificationInputIns"/>