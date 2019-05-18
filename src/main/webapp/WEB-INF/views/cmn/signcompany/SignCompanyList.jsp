<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/signcompany/SignCompanyList.js?${version}"></script>

<title>签单公司一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper SignCompanyList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">签单公司一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增签单公司
            </button>
            <c:if test="${dataImportApiExist}">
                <button id="btnApiImport" type="button" class="btn btn-danger">
                    <i class="fa fa-spinner"></i>同步数据
                </button>
            </c:if>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>公司名称</label>
                    <input id="companyName" data-alias-table="main" type="text" maxlength="64" class="form-control"
                           data-title="公司名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>公司地址</label>
                    <input id="companyAddr" data-alias-table="main" type="text" maxlength="128" class="form-control"
                           data-title="公司地址"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>公司电话</label>
                    <input id="companyTelephone" data-alias-table="main" type="text" maxlength="32" class="form-control"
                           data-title="公司电话"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>开户行</label>
                    <input id="bankName" data-alias-table="main" type="text" maxlength="64" class="form-control"
                           data-title="开户行"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>账号</label>
                    <input id="bankNumber" data-alias-table="main" type="text" maxlength="32" class="form-control"
                           data-title="账号"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>纳税人识别号</label>
                    <input id="identificationNumber" data-alias-table="main" type="text" maxlength="64" class="form-control"
                           data-title="纳税人识别号"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Cmn.SignCompanyListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_SignCompany}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_SignCompany}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_SignCompany}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function SignCompanyList_Page_Load() {
        SysApp.Cmn.SignCompanyListIns = new SysApp.Cmn.SignCompanyList();
        var instance = SysApp.Cmn.SignCompanyListIns;

        instance.selfInstance = "SysApp.Cmn.SignCompanyListIns";
        instance.controller = "${ctx}/cmn/signcompany/";
        instance.clientID = "SignCompanyList";
        instance.tableName = "cmn_sign_company";
        instance.inputInstance = SysApp.Cmn.SignCompanyInputIns;
        instance.detailInstance = SysApp.Cmn.SignCompanyDetailIns;
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    $(function () {
        SignCompanyList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑控件--%>
<%@ include file="/WEB-INF/views/cmn/signcompany/SignCompanyPopupInput.jsp" %>
<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/cmn/signcompany/SignCompanyPopupDetail.jsp" %>