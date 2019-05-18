<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/protocol/ProtocolList.js?${version}"></script>

<title>协议一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper ProtocolList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">协议一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增协议
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>协议名称</label>
                    <input id="name" data-camel-field="true" data-alias-table="main" type="text" maxlength="16" class="form-control" data-title="协议名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>协议分类</label>
                    <select id="categoryCd" data-alias-table="main" class="form-control" data-title="协议分类">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>协议版本</label>
                    <input id="version" data-camel-field="true" data-alias-table="main" type="text" maxlength="16" class="form-control" data-title="协议版本"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>使用状态</label>
                    <select id="useStatusCd" data-alias-table="main" class="form-control" data-title="使用状态">
                        <option value="">请选择</option>
                    </select>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Cmn.ProtocolListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Protocol}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Protocol}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Protocol}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ProtocolList_Page_Load() {
        SysApp.Cmn.ProtocolListIns = new SysApp.Cmn.ProtocolList();
        var instance = SysApp.Cmn.ProtocolListIns;

        instance.selfInstance = "SysApp.Cmn.ProtocolListIns";
        instance.controller = "${ctx}/cmn/protocol/";
        instance.inputUrl = "${ctx}/cmn/protocol/input";
        instance.clientID = "ProtocolList";
        instance.tableName = "cmn_protocol";
        instance.entry = "${entry}";
        instance.pageSize = "${pageSize}";
        instance.detailInstance = SysApp.Cmn.ProtocolDetailIns;

        instance.init();
    }

    $(function () {
        ProtocolList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/cmn/protocol/ProtocolPopupDetail.jsp" %>
