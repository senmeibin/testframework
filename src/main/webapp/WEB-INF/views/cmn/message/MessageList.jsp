<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/message/MessageList.js?${version}"></script>

<title>消息推送一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper MessageList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">消息推送一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增消息推送
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>消息类型</label>
                    <select id="messageTypeCd" data-alias-table="main" class="form-control" data-title="消息类型">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>消息标题</label>
                    <input id="messageTitle" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="消息标题"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>重要度</label>
                    <select id="importanceDegreeCd" data-alias-table="main" class="form-control" data-title="重要度">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>发送区分</label>
                    <select id="sendTypeCd" data-alias-table="main" class="form-control" data-title="发送区分">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>消息提醒开始时间(起)</label>
                    <div class='input-group datetime-picker'>
                        <input id="messageStartDate$from_search" data-alias-table="main" type='text' class="form-control" data-title="消息提醒开始时间(起)"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
                <div class="col-md-2 form-group">
                    <label>消息提醒开始时间(止)</label>
                    <div class='input-group datetime-picker'>
                        <input id="messageStartDate$to_search" data-alias-table="main" type='text' class="form-control" data-title="消息提醒开始时间(止)"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
                <div class="clear-both dashed-line">
                </div>

                <div>
                    <button id="btnSearch" type="button" class="btn btn-primary">
                        <i class="fa fa-search"></i>查询
                    </button>
                    <button id="btnClear" type="button" class="btn btn-default">
                        <i class="fa fa-eraser"></i>清空
                    </button>
                    <ctag:PagerSettingIcon pageInstance="SysApp.Cmn.MessageListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Message}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Message}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Message}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function MessageList_Page_Load() {
        SysApp.Cmn.MessageListIns = new SysApp.Cmn.MessageList();
        var instance = SysApp.Cmn.MessageListIns;

        instance.selfInstance = "SysApp.Cmn.MessageListIns";
        instance.controller = "${ctx}/cmn/message/";
        instance.inputUrl = "${ctx}/cmn/message/input";
        instance.clientID = "MessageList";
        instance.tableName = "cmn_message";
        instance.inputInstance = SysApp.Cmn.MessageInputIns;
        instance.detailInstance = SysApp.Cmn.MessageDetailIns;
        instance.serviceTime = "${serviceTime}";
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    $(function () {
        MessageList_Page_Load();
    });
    //]]>
</script>
<%@ include file="/WEB-INF/views/cmn/message/MessagePopupDetail.jsp" %>
<%@ include file="/WEB-INF/views/cmn/message/messageuser/MessageUserPopupList.jsp" %>