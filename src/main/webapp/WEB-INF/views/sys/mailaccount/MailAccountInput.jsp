<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/mailaccount/MailAccountInput.js?${version}"></script>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height MailAccountInput-MainContent">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">邮箱设定</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">

                <div class="form-group form-inline">
                    <label class="col-lg-3 control-label">SMTP服务器</label>
                    <div class="col-lg-9">
                        <input id="smtp" type="text" class="form-control required" data-title="SMTP服务器" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                        <label id="smtp_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-3 control-label">端口</label>
                    <div class="col-lg-9">
                        <input id="port" type="text" class="form-control" data-title="端口" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                        ※端口未设定的场合，使用默认端口25。
                        <label id="port_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-3 control-label">发件人</label>
                    <div class="col-lg-9">
                        <input id="sendUser" type="text" class="form-control" data-title="发件人" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                        ※未设定的场合，默认发件人为SMTP认证账号。
                        <label id="sendUser_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-3 control-label">SMTP认证账号</label>
                    <div class="col-lg-9">
                        <input id="userName" type="text" class="form-control required" data-title="SMTP认证账号" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                        <label id="userName_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-3 control-label">SMTP认证密码</label>
                    <div class="col-lg-9">
                        <input id="password" type="password" class="form-control" placeholder="******" data-title="SMTP认证密码" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                        <label id="password_Error" class="validator-error"></label>
                    </div>
                </div>
            </form>

            <div class="text-center">
                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
            </div>

            <%--原始对象Json字符串[实体属性校验用]--%>
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_MailAccount}"/>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function MailAccountInput_Page_Load() {
        SysApp.Sys.MailAccountInputIns = new SysApp.Sys.MailAccountInput();
        var instance = SysApp.Sys.MailAccountInputIns;
        instance.selfInstance = "SysApp.Sys.MailAccountInputIns";
        instance.controller = "${ctx}/sys/mailaccount/";
        instance.clientID = "MailAccountInput";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    MailAccountInput_Page_Load();
    //]]>
</script>