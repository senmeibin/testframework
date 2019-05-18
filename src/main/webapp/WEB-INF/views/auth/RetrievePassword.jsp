<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<script type="text/javascript">
    //<![CDATA[
    window.contextPath = "${ctx}";
    window.imagesPath = "${staticContentsServer}/static/images/";
    //]]>
</script>
<head>
    <title>${systemName}-找回密码</title>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/bootstrap/css/bootstrap.min.css?${version}"/>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/font-awesome/css/font-awesome.min.css?${version}"/>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/base/main.css?${version}"/>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/auth/auth.css?${version}"/>

    <script type="text/javascript" src="${staticContentsServer}/static/js/Namespace.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/jquery.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/prototype.js?${version}"></script>

    <script type="text/javascript" src="${staticContentsServer}/static/plugins/jquery-validation/jquery.validate.min.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/plugins/jquery-validation/localization/messages_zh.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/plugins/bootstrap/js/bootstrap.min.js?${version}"></script>

    <script type="text/javascript" src="${staticContentsServer}/static/js/MainScript.min.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/Core.min.js?${version}"></script>
</head>

<body class="full-width">
<%@ include file="/WEB-INF/views/cmn/CmnMessage.jsp" %>

<%--画面加载中提示--%>
<%@ include file="/WEB-INF/views/cmn/Loading.jsp" %>

<%@ include file="/WEB-INF/views/auth/common/Common.jsp" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/auth/retrievepassword/RetrievePassword.js?${version}"></script>
<div>
    <div class="auth-center-header"></div>
    <!--logo区开始-->
    <div class="auth-center-logo"><i class="fa fa-windows"></i>&nbsp;${systemName}<span>安全中心</span></div>
    <!--成功后，下面内容隐藏-->
    <div class="retrieve-password-content">
        <div class="retrieve-password-nav"><img src="${staticContentsServer}/static/images/auth/retrieve_nav.jpg"></div>
        <h5>请输入你在系统中的用户账号及用户账号绑定的关联邮箱，点击“找回密码”按钮，我们会把初始密码发送到你的关联邮箱中。</h5>
        <div class="panel-body RetrievePassword-MainContent">
            <form id="MainForm" class="form-horizontal">
                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label">用户账号</label>
                    <div class="col-sm-9">
                        <input id="userCd" type="text" class="form-control required" data-title="用户账号" style="width: 350px;"/>
                        <label id="userCd_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label">邮箱地址</label>
                    <div class="col-sm-9">
                        <input id="userMail" type="text" class="form-control required" data-title="邮箱地址" data-regex="/^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/" data-regex-message="请在{0}中输入正确的格式。"
                               style="width: 350px;"/>
                        <label id="userMail_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline" style="display: none;">
                    <label class="col-sm-3 control-label">验证码</label>
                    <div class="col-sm-9">
                        <input id="VerificationCode" type="text" class="form-control" data-title="验证码" style="width: 350px;"/>
                        <label id="VerificationCode_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="text-center">
                    <button type="button" class="btn btn-primary" style="width: 200px;font-size: 14px;height: 32px;" id="btnSave"><i class="fa fa-user-secret fa-lg"></i>找回密码
                    </button>
                </div>
            </form>
        </div>
    </div>
    <!--成功后，显示下面内容-->
    <div class="retrieve-password-content" style="display:none;">
        <div class="retrieve-password-nav"><img src="${staticContentsServer}/static/images/auth/retrieve_nav2.jpg"></div>
        <div class="retrieve-password-success"><img src="${staticContentsServer}/static/images/auth/success.png"><span>恭喜您！密码邮件发送成功，请注意查收邮件！</span></div>
        <div class="retrieve-password-return">
            <button type="button" class="btn btn-primary btn-100px">返回登录页</button>
        </div>
    </div>
    <div class="auth-center-footer">2016 © ${companyName}-${systemName}</div>
</div>
</body>
</html>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function RetrievePassword_Page_Load() {
        SysApp.Auth.RetrievePasswordIns = new SysApp.Auth.RetrievePassword();
        var instance = SysApp.Auth.RetrievePasswordIns;
        instance.selfInstance = "SysApp.Auth.RetrievePasswordIns";
        instance.clientID = "RetrievePassword";
        instance.controller = "${ctx}/auth/retrievepassword/";
        instance.init();
    }
    RetrievePassword_Page_Load();
    //]]>
</script>