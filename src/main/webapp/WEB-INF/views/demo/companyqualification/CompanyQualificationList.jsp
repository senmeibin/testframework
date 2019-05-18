<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyqualification/CompanyQualificationList.js?${version}"></script>

<title>企业资质一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper CompanyQualificationList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">企业资质一览</div>
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
                    <label>认定机构</label>
                    <input id="certificationAuthority" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="认定机构"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>编号</label>
                    <input id="serialNumber" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="编号"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>认定年份</label>
                    <input id="certificationYear" data-alias-table="main" type="text" maxlength="4" class="form-control" data-title="认定年份"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>复核/验收年份</label>
                    <input id="acceptanceYear" data-alias-table="main" type="text" maxlength="4" class="form-control" data-title="复核/验收年份"/>
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
            <input type="hidden" id="jsonListData" value="${jsonDataList_CompanyQualification}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_CompanyQualification}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyQualification}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function CompanyQualificationList_Page_Load() {
        SysApp.Demo.CompanyQualificationListIns = new SysApp.Demo.CompanyQualificationList();
        var instance = SysApp.Demo.CompanyQualificationListIns;

        instance.selfInstance = "SysApp.Demo.CompanyQualificationListIns";
        instance.controller = "${ctx}/demo/companyqualification/";
        instance.inputUrl = "${ctx}/demo/companyqualification/input";
        instance.clientID = "CompanyQualificationList";
        instance.tableName = "demo_company_qualification";
        instance.detailInstance = SysApp.Demo.CompanyQualificationDetailIns;

        instance.init();
    }

    $(function () {
        CompanyQualificationList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/demo/companyqualification/CompanyQualificationPopupDetail.jsp" %>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.CompanyQualificationListIns"/>