<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/CompanyExtendedCommon.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyinstitutional/CompanyInstitutionalInput.js?${version}"></script>

<title>机构信息编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height">
        <div class="panel-heading CompanyInstitutionalInput-MainContent">
            <em></em>
            <div class="panel-title" id="ctlTitle">机构信息编辑</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <%@ include file="/WEB-INF/views/demo/companyinformation/CompanyInformationInnerDetail.jsp" %>
            <div class=" CompanyInstitutionalInput-MainContent margin-top-space">
                <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
                <form id="MainForm" class="form-horizontal">
                    <input id="uid" type="hidden">
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
                    <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                    <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                    <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
                </div>

                <%--原始对象Json字符串[实体属性校验用]--%>
                <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_CompanyInstitutional}"/>

                <%--下拉框选项数据集合--%>
                <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyInstitutional}"/>
            </div>
            <%@ include file="/WEB-INF/views/demo/companyinstitutional/companyinstitutionaldetail/CompanyInstitutionalDetailInnerList.jsp" %>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyInstitutionalInput_Page_Load() {
        SysApp.Demo.CompanyInstitutionalInputIns = new SysApp.Demo.CompanyInstitutionalInput();
        var instance = SysApp.Demo.CompanyInstitutionalInputIns;
        instance.selfInstance = "SysApp.Demo.CompanyInstitutionalInputIns";
        instance.controller = "${ctx}/demo/companyinstitutional/";
        instance.listUrl = "${ctx}/demo/companyinstitutional/list";
        instance.clientID = "CompanyInstitutionalInput";

        instance.init();
    }

    CompanyInstitutionalInput_Page_Load();
    //]]>
</script>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.CompanyInstitutionalInputIns"/>