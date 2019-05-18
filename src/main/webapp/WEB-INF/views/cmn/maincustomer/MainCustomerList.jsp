<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/maincustomer/MainCustomerList.js?${version}"></script>

<title>主客户一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper MainCustomerList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">主客户一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增主客户
            </button>
            <button id="btnImport" type="button" class="btn btn-primary">
                <i class="fa fa-cloud-upload"></i>主客户导入
            </button>
            <!--有配置DATA_IMPORT_CMN_API才显示同步按钮 -->
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
                    <label>主客户名称</label>
                    <input id="mainCustomerName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="主客户名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>联系人</label>
                    <input id="contactName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="联系人"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>联系电话</label>
                    <input id="contactTelephone" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="联系电话"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>客户概况</label>
                    <input id="introduction" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="客户概况"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>备注</label>
                    <input id="remark" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="备注"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Cmn.MainCustomerListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_MainCustomer}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_MainCustomer}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_MainCustomer}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function MainCustomerList_Page_Load() {
        SysApp.Cmn.MainCustomerListIns = new SysApp.Cmn.MainCustomerList();
        var instance = SysApp.Cmn.MainCustomerListIns;

        instance.selfInstance = "SysApp.Cmn.MainCustomerListIns";
        instance.controller = "${ctx}/cmn/maincustomer/";
        instance.clientID = "MainCustomerList";
        instance.tableName = "cmn_main_customer";
        instance.importUrl = "${ctx}/cmn/maincustomer/importInit";
        instance.inputInstance = SysApp.Cmn.MainCustomerInputIns;
        instance.detailInstance = SysApp.Cmn.MainCustomerDetailIns;
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    $(function () {
        MainCustomerList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑控件--%>
<%@ include file="/WEB-INF/views/cmn/maincustomer/MainCustomerPopupInput.jsp" %>
<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/cmn/maincustomer/MainCustomerPopupDetail.jsp" %>