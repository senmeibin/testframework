<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyinstitutional/CompanyInstitutionalList.js?${version}"></script>

<title>机构信息一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper CompanyInstitutionalList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">机构信息一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>企业名称</label>
                    <input id="companyName" data-alias-table="company" type="text" maxlength="32" class="form-control" data-title="企业名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>控股母公司</label>
                    <input id="parentCompanyName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="控股母公司"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>纳税人识别号</label>
                    <input id="taxpayerIdentificationNo" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="纳税人识别号"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>地址</label>
                    <input id="address" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="地址"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>经营范围（营业执照）</label>
                    <input id="businessScope" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="经营范围（营业执照）"/>
                </div>
                <div class="clear-both dashed-line">
                </div>

                <div>
                    <button id="btnSearch" type="button" class="btn btn-primary">
                        <i class="fa fa-search"></i>查询
                    </button>
                    <shiro:hasRole name="DataExport">
                        <button id="btnExport" type="button" class="btn btn-primary">
                            <i class="fa fa-cloud-download"></i>导出
                        </button>
                    </shiro:hasRole>
                    <button id="btnClear" type="button" class="btn btn-default">
                        <i class="fa fa-eraser"></i>清空
                    </button>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_CompanyInstitutional}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_CompanyInstitutional}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyInstitutional}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function CompanyInstitutionalList_Page_Load() {
        SysApp.Demo.CompanyInstitutionalListIns = new SysApp.Demo.CompanyInstitutionalList();
        var instance = SysApp.Demo.CompanyInstitutionalListIns;

        instance.selfInstance = "SysApp.Demo.CompanyInstitutionalListIns";
        instance.controller = "${ctx}/demo/companyinstitutional/";
        instance.inputUrl = "${ctx}/demo/companyinstitutional/input";
        instance.clientID = "CompanyInstitutionalList";
        instance.tableName = "demo_company_institutional";
        instance.detailInstance = SysApp.Demo.CompanyInstitutionalDetailIns;

        instance.init();
    }

    $(function () {
        CompanyInstitutionalList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/demo/companyinstitutional/CompanyInstitutionalPopupDetail.jsp" %>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.CompanyInstitutionalListIns"/>