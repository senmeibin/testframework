<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/smsaccount/SmsAccountInput.js?${version}"></script>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height SmsAccountInput-MainContent">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">短信设定</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">

                <div class="form-group form-inline">
                    <label class="col-lg-3 control-label">短信供应商</label>
                    <div class="col-lg-9">
                        <input id="vendor" type="text" class="form-control " data-title="短信供应商" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                        <label id="vendor_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-3 control-label">短信URL</label>
                    <div class="col-lg-9">
                        <input id="url" type="text" class="form-control required" data-title="短信URL" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                        <label id="url_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-3 control-label">账号名</label>
                    <div class="col-lg-9">
                        <input id="account" type="text" class="form-control required" data-title="账号名" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                        <label id="account_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-3 control-label">密码</label>
                    <div class="col-lg-9">
                        <input id="password" type="password" placeholder="******" class="form-control" data-title="密码" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                        <label id="password_Error" class="validator-error"></label>
                    </div>
                </div>
            </form>

            <div class="text-center">
                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
            </div>

            <%--原始对象Json字符串[实体属性校验用]--%>
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_SmsAccount}"/>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function SmsAccountInput_Page_Load() {
        SysApp.Sys.SmsAccountInputIns = new SysApp.Sys.SmsAccountInput();
        var instance = SysApp.Sys.SmsAccountInputIns;
        instance.selfInstance = "SysApp.Sys.SmsAccountInputIns";
        instance.controller = "${ctx}/sys/smsaccount/";
        instance.clientID = "SmsAccountInput";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    SmsAccountInput_Page_Load();
    //]]>
</script>