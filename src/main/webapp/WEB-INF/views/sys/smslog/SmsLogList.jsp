<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/smslog/SmsLogList.js?${version}"></script>

<title>短信日志一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper SmsLogList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">短信日志一览</div>
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
                    <label>短信内容</label>
                    <input id="message" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="短信内容"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>短信供应商</label>
                    <input id="vendor" data-alias-table="main" type="text" maxlength="8" class="form-control" data-title="短信供应商"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>手机号码</label>
                    <input id="mobile" data-alias-table="main" type="text" maxlength="11" class="form-control" data-title="手机号码"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.SmsLogListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_SmsLog}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_SmsLog}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_SmsLog}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function SmsLogList_Page_Load() {
        SysApp.Sys.SmsLogListIns = new SysApp.Sys.SmsLogList();
        var instance = SysApp.Sys.SmsLogListIns;

        instance.selfInstance = "SysApp.Sys.SmsLogListIns";
        instance.controller = "${ctx}/sys/smslog/";
        instance.clientID = "SmsLogList";
        instance.tableName = "sys_sms_log";
        instance.detailInstance = SysApp.Sys.SmsLogDetailIns;
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        SmsLogList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/sys/smslog/SmsLogPopupDetail.jsp" %>