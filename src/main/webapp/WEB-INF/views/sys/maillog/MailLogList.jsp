<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/maillog/MailLogList.js?${version}"></script>

<title>邮件日志一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper MailLogList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">邮件日志一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>发送时间(起)</label>
                    <div class='input-group datetime-picker'>
                        <input id="sendDate$from_search" data-alias-table="main" type='text' class="form-control" data-title="发送时间(起)"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
                <div class="col-md-2 form-group">
                    <label>发送时间(止)</label>
                    <div class='input-group datetime-picker'>
                        <input id="sendDate$to_search" data-alias-table="main" type='text' class="form-control" data-title="发送时间(止)"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
                <div class="col-md-2 form-group">
                    <label>应用名称</label>
                    <select id="appCode" data-alias-table="main" class="form-control" data-title="应用名称">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>邮件主题</label>
                    <input id="subject" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="邮件主题"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>发送邮件地址</label>
                    <input id="mailFrom" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="发送邮件地址"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>接受邮件地址(To)</label>
                    <input id="mailTo" data-alias-table="main" type="text" maxlength="512" class="form-control" data-title="接受邮件地址(To)"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>接受邮件地址(Cc)</label>
                    <input id="mailCc" data-alias-table="main" type="text" maxlength="512" class="form-control" data-title="接受邮件地址(Cc)"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>接受邮件地址(Bcc)</label>
                    <input id="mailBcc" data-alias-table="main" type="text" maxlength="512" class="form-control" data-title="接受邮件地址(Bcc)"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>发送结果</label>
                    <select id="sendResult" data-alias-table="main" class="form-control" data-title="发送结果">
                        <option value="">请选择</option>
                        <option value="1">成功</option>
                        <option value="-1">失败</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>错误消息</label>
                    <input id="errorMessage" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="错误消息"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.MailLogListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_MailLog}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_MailLog}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_MailLog}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function MailLogList_Page_Load() {
        SysApp.Sys.MailLogListIns = new SysApp.Sys.MailLogList();
        var instance = SysApp.Sys.MailLogListIns;

        instance.selfInstance = "SysApp.Sys.MailLogListIns";
        instance.controller = "${ctx}/sys/maillog/";
        instance.clientID = "MailLogList";
        instance.tableName = "sys_mail_log";
        instance.detailInstance = SysApp.Sys.MailLogDetailIns;
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        MailLogList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/sys/maillog/MailLogPopupDetail.jsp" %>