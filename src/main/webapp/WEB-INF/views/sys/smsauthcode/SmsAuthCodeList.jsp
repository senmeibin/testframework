<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/sys/smsauthcode/SmsAuthCodeList.js?${version}"></script>

<title>短信验证码一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper SmsAuthCodeList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">短信验证码一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>手机号码</label>
                    <input id="mobile" data-alias-table="main" type="text" maxlength="11" class="form-control" data-title="手机号码"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>验证码</label>
                    <input id="authCode" data-alias-table="main" type="text" maxlength="8" class="form-control" data-title="验证码"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>验证码类型</label>
                    <select id="authCodeType" data-alias-table="main" class="form-control" data-title="验证码类型">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>是否已验证</label>
                    <select id="validated" data-alias-table="main" class="form-control" data-title="是否已验证">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>有效期限(起)</label>
                    <ctag:CalendarSelect id="validTime$from_search" title="有效期限(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>有效期限(止)</label>
                    <ctag:CalendarSelect id="validTime$to_search" title="有效期限(止)"></ctag:CalendarSelect>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.SmsAuthCodeListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_SmsAuthCode}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_SmsAuthCode}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_SmsAuthCode}"/>

            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function SmsAuthCodeList_Page_Load() {
        SysApp.Sys.SmsAuthCodeListIns = new SysApp.Sys.SmsAuthCodeList();
        var instance = SysApp.Sys.SmsAuthCodeListIns;

        instance.selfInstance = "SysApp.Sys.SmsAuthCodeListIns";
        instance.controller = "${ctx}/sys/smsauthcode/";
        instance.inputUrl = "${ctx}/sys/smsauthcode/input";
        instance.clientID = "SmsAuthCodeList";
        instance.tableName = "sys_sms_auth_code";
        instance.entry = "${entry}";
        instance.pageSize = "${pageSize}";

        instance.init();
    }

    $(function () {
        SmsAuthCodeList_Page_Load();
    });
    //]]>
</script>