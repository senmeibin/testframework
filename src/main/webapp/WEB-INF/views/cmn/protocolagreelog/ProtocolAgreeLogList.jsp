<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/protocolagreelog/ProtocolAgreeLogList.js?${version}"></script>

<title>协议同意日志一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper ProtocolAgreeLogList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">协议同意日志一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>手机号</label>
                    <input id="mobile" data-camel-field="true" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="手机号"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>确认IP</label>
                    <input id="signIp" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="确认IP"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>用户代理</label>
                    <input id="userAgent" data-alias-table="main" type="text" maxlength="512" class="form-control" data-title="用户代理"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Cmn.ProtocolAgreeLogListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_ProtocolAgreeLog}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_ProtocolAgreeLog}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ProtocolAgreeLog}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ProtocolAgreeLogList_Page_Load() {
        SysApp.Cmn.ProtocolAgreeLogListIns = new SysApp.Cmn.ProtocolAgreeLogList();
        var instance = SysApp.Cmn.ProtocolAgreeLogListIns;

        instance.selfInstance = "SysApp.Cmn.ProtocolAgreeLogListIns";
        instance.controller = "${ctx}/cmn/protocolagreelog/";
        instance.clientID = "ProtocolAgreeLogList";
        instance.tableName = "cmn_protocol_agree_log";
        instance.entry = "${entry}";
        instance.pageSize = "${pageSize}";

        instance.init();
    }

    $(function () {
        ProtocolAgreeLogList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/cmn/protocol/ProtocolPopupDetail.jsp" %>