<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/CompanyExtendedCommon.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyinstitutional/CompanyInstitutionalInnerInput.js?${version}"></script>

<div class="panel-body" id="CompanyInstitutionalInnerInput_companyInstitutionalInnerInputDiv" style="display: none;padding-top:0px;">
    <div style="border: 1px solid #09c; border-top-width: 3px; padding: 15px 0;">
        <div class="CompanyInstitutionalInnerInput-MainContent">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden" data-allow-clear="true">
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">控股母公司</label>
                    <div class="col-lg-4">
                        <input id="parentCompanyName" type="text" class="form-control" data-title="控股母公司" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                        <label id="parentCompanyName_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">纳税人识别号</label>
                    <div class="col-lg-4">
                        <input id="taxpayerIdentificationNo" type="text" class="form-control" data-title="纳税人识别号" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                        <label id="taxpayerIdentificationNo_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">地址</label>
                    <div class="col-lg-8">
                        <input id="address" type="text" class="form-control" data-title="地址" maxlength="64" data-rangelength="[0,64]" style="width: 98%;"/>
                        <label id="address_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">经营范围（营业执照）</label>
                    <div class="col-lg-8">
                        <input id="businessScope" type="text" class="form-control" data-title="经营范围（营业执照）" maxlength="64" data-rangelength="[0,64]" style="width: 98%;"/>
                        <label id="businessScope_Error" class="validator-error"></label>
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
                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存机构信息</button>
                <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                <button type="button" class="btn btn-default btn-100px" id="btnClose"><i class="fa fa-close"></i>关闭</button>
            </div>

            <%--原始对象Json字符串[实体属性校验用]--%>
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_CompanyInstitutional}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyInstitutional}"/>
        </div>
        <div style="padding: 0 15px;">
            <%@ include file="/WEB-INF/views/demo/companyinstitutional/companyinstitutionaldetail/CompanyInstitutionalDetailInnerList.jsp" %>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyInstitutionalInnerInput_Page_Load() {
        SysApp.Demo.CompanyInstitutionalInnerInputIns = new SysApp.Demo.CompanyInstitutionalInnerInput();
        var instance = SysApp.Demo.CompanyInstitutionalInnerInputIns;
        instance.selfInstance = "SysApp.Demo.CompanyInstitutionalInnerInputIns";
        instance.controller = "${ctx}/demo/companyinstitutional/";
        instance.clientID = "CompanyInstitutionalInnerInput";

        instance.init();
    }

    CompanyInstitutionalInnerInput_Page_Load();
    //]]>
</script>