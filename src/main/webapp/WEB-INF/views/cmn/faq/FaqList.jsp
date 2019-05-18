<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/faq/FaqList.js?${version}"></script>

<title>常见问题一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper FaqList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">常见问题一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <shiro:hasAnyRoles name="SystemManagement,ItSupport">
                <c:if test="${isReadonly != 'true'}">
                    <button id="btnAdd" type="button" class="btn btn-primary">
                        <i class="fa fa-plus"></i>新增常见问题
                    </button>
                    <div class="clear-both dashed-line">
                    </div>
                </c:if>
            </shiro:hasAnyRoles>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <c:if test="${isReadonly == 'true'}">
                    <input type="hidden" id="main.appCode" value="${appCode.toUpperCase()}" data-allow-clear="false"/>
                </c:if>
                <div class="col-md-2 form-group" id="appCodeSearchDiv">
                    <label>应用名称</label>
                    <select id="appCode" data-alias-table="main" class="form-control" data-title="应用名称">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>模块名称</label>
                    <input id="moduleName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="模块名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>问题描述</label>
                    <input id="question" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="问题描述"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>适用角色</label>
                    <input id="applicableRole" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="适用角色"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>责任人</label>
                    <input id="responsibleRole" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="责任人"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Cmn.FaqListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Faq}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Faq}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Faq}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function FaqList_Page_Load() {
        SysApp.Cmn.FaqListIns = new SysApp.Cmn.FaqList();
        var instance = SysApp.Cmn.FaqListIns;

        instance.selfInstance = "SysApp.Cmn.FaqListIns";
        instance.controller = "${ctx}/${appCode.toLowerCase()}/faq/";
        instance.inputUrl = "${ctx}/${appCode.toLowerCase()}/faq/input?appCode=${appCode}";
        instance.clientID = "FaqList";
        instance.tableName = "cmn_faq";
        instance.detailInstance = SysApp.Cmn.FaqDetailIns;
        instance.isReadonly = "${isReadonly}" == "true";
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    $(function () {
        FaqList_Page_Load();
    });
    //]]>
</script>
<%--操作说明--%>
<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/cmn/faq/FaqPopupDetail.jsp" %>