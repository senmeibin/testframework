<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/sys/smsauthcode/SmsAuthCodeInput.js?${version}"></script>
	
<title>短信验证码编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper SmsAuthCodeInput-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">短信验证码编辑</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">手机号码</label>
            <div class="col-sm-9">
                <input id="mobile" type="text" class="form-control" data-title="手机号码" maxlength="11" data-rangelength="[0,11]" style="width: 350px;"/>
                <label id="mobile_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">验证码</label>
            <div class="col-sm-9">
                <input id="authCode" type="text" class="form-control" data-title="验证码" maxlength="8" data-rangelength="[0,8]" style="width: 350px;"/>
                <label id="authCode_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">验证码类型</label>
            <div class="col-sm-9">
                <input id="authCodeType" type="text" class="form-control" data-title="验证码类型" maxlength="8" data-rangelength="[0,8]" style="width: 350px;"/>
                <label id="authCodeType_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">有效期限</label>
            <div class="col-sm-9">
                <ctag:CalendarSelect id="validTime" title="有效期限" showValidateError="true" width="120px"></ctag:CalendarSelect>
            </div>
        </div>
            </form>

            <div class="text-center">
                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
            </div>

            <%--原始对象Json字符串[实体属性校验用]--%>
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_SmsAuthCode}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_SmsAuthCode}"/>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function SmsAuthCodeInput_Page_Load() {
        SysApp.Sys.SmsAuthCodeInputIns = new SysApp.Sys.SmsAuthCodeInput();
        var instance = SysApp.Sys.SmsAuthCodeInputIns;
        instance.selfInstance = "SysApp.Sys.SmsAuthCodeInputIns";
        instance.controller = "${ctx}/sys/smsauthcode/";
        instance.listUrl = "${ctx}/sys/smsauthcode/list";
        instance.clientID = "SmsAuthCodeInput";
        instance.entry = "${entry}";
        
        instance.init();
    }

    SmsAuthCodeInput_Page_Load();
    //]]>
</script>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Sys.SmsAuthCodeInputIns"/>