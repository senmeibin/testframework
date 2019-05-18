<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/sys/activemq/ActiveMQInput.js?${version}"></script>

<title>ActiveMQ消息队列</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height ActiveMQInput-MainContent">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">ActiveMQ消息队列</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <div id="divNoticeMessage" class="flash-error" style="margin-bottom: 10px;display: none;">
                ActiveMQ消息队列未启用
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">
                <ctag:Fold id="divSendMail" name="发送邮件"></ctag:Fold>
                <div id="divSendMail" class="separate-block clearfix">
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">接收邮件地址</label>
                        <div class="col-lg-3">
                            <input id="mailTo" type="text" class="form-control required" data-title="接收邮件地址" style="width: 200px;"/>
                            <label id="mailTo_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">抄送邮件地址</label>
                        <div class="col-lg-3">
                            <input id="mailCc" type="text" class="form-control" data-title="抄送邮件地址" style="width: 200px;"/>
                            <label id="mailCc_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">密送邮件地址</label>
                        <div class="col-lg-3">
                            <input id="mailBcc" type="text" class="form-control" data-title="密送邮件地址" style="width: 200px;"/>
                            <label id="mailBcc_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">邮件主题</label>
                        <div class="col-lg-3">
                            <input id="subject" type="text" class="form-control required" data-title="邮件主题" style="width: 200px;"/>
                            <label id="subject_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">邮件内容</label>
                        <div class="col-lg-3">
                            <input id="mailMessage" type="text" class="form-control required" data-title="邮件内容" style="width: 200px;"/>
                            <label id="mailMessage_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <div class="col-lg-12 text-center">
                            <button type="button" class="btn btn-primary btn-100px" id="btnSendMail"><i class="fa fa-envelope"></i>发送测试邮件</button>
                        </div>
                    </div>
                </div>
                <br/>
                <ctag:Fold id="divSendSms" name="发送短信"></ctag:Fold>
                <div id="divSendSms" class="separate-block clearfix">
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">手机号码</label>
                        <div class="col-lg-3">
                            <input id="mobile" type="text" class="form-control required" data-title="手机号码" style="width: 200px;"/>
                            <label id="mobile_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">短信内容</label>
                        <div class="col-lg-3">
                            <input id="smsMessage" type="text" class="form-control required" data-title="短信内容" style="width: 200px;"/>
                            <label id="smsMessage_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <div class="col-lg-12 text-center">
                            <button type="button" class="btn btn-primary btn-100px" id="btnSendSms"><i class="fa fa-comments"></i>发送测试短信</button>
                        </div>
                    </div>
                </div>
                <br/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function RoleInput_Page_Load() {
        SysApp.Sys.ActiveMQInputIns = new SysApp.Sys.ActiveMQInput();
        var instance = SysApp.Sys.ActiveMQInputIns;
        instance.selfInstance = "SysApp.Sys.ActiveMQInputIns";
        instance.controller = "${ctx}/sys/activemq/";
        instance.clientID = "ActiveMQInput";
        instance.isEnable = ("${activemqEnable}" === "true");
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    RoleInput_Page_Load();
    //]]>
</script>