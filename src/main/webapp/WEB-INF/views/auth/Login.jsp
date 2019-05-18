<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<head>
    <title>${systemName}-欢迎登录</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link type="image/x-icon" href="${staticContentsServer}/favicon.ico?${version}" rel="shortcut icon">
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/bootstrap/css/bootstrap.min.css?${version}"/>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/font-awesome/css/font-awesome.min.css?${version}"/>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/base/main.css?${version}"/>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/auth/auth.css?${version}"/>

    <script type="text/javascript" src="${staticContentsServer}/static/js/Namespace.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/jquery.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/prototype.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/MainScript.min.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/Core.min.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/plugins/dcjqaccordion/js/jquery.cookie.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/auth/Login.js?${version}"></script>
</head>

<body class="login-background-image">
<%--画面加载中提示--%>
<%@ include file="/WEB-INF/views/cmn/Loading.jsp" %>
<ctag:BrowserVersion></ctag:BrowserVersion>
<div class="login-frame">
    <div class="login-logo" style="font-size: 28px;color: #0099CC;"><i class="fa fa-windows"></i>&nbsp;${systemName}</div>
    <div class="login-logo-footer"></div>
    <div class="login-content">
        <div class="login-content-left"><img src="${staticContentsServer}/static/images/auth/login_img1.jpg"></div>
        <div class="login-content-rig">
            <form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
                <input type="hidden" id="SESSION_TIME_OUT" value="true"/>
                <h2 class="form-signin-header">${systemName}</h2>
                <div class="user-login-info">
                    <div id="loginErrorMessage" style="color: Red;">${error}${accessIpError}</div>
                    <input class="form-control required" type="text" name="username" value="${username}" placeholder="请输入用户名" autofocus style="width: 250px;"/>
                    <input class="form-control required" type="password" name="password" value="${password}" placeholder="请输入密码" style="margin-top: 20px; width: 250px;"/>
                    <div class="row">
                        <label class="checkbox" style="float: left">
                            <input name="rememberMe" type="checkbox" value="${rememberMe}" id="rememberMe" name="rememberMe" style="margin-top: 2px"/>记住账号
                        </label>
                        <label class="forget-password">
                            <a href="${ctx}/auth/retrievepassword">忘记密码？</a>
                        </label>
                    </div>
                    <input type="submit" id="loginBtn" value="立即登录" class="btn btn-primary" hidefocus="true" style="width: 250px;"/>
                </div>
            </form>
        </div>
    </div>
    <div style="text-align:center" class="margin-top-space">浏览器<span style="color:#0099CB;font-weight: bold;">推荐使用</span>：谷歌(Chrome)、火狐(Firefox)、360(极速模式)、IE10(以上)版本！</div>
</div>
<div class="login-footer">2016 &copy; ${companyName}-${systemName}</div>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
</body>
</html>
<%
    //清空IP访问限制错误消息
    session.removeAttribute("accessIpError");
%>
