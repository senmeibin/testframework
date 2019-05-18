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
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/auth/auth.css?${version}"/>

    <script type="text/javascript" src="${staticContentsServer}/static/js/Namespace.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/jquery.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/prototype.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/MainScript.min.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/Core.min.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/plugins/dcjqaccordion/js/jquery.cookie.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/auth/Login.js?${version}"></script>
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <script>
        $(function () {
            //输入区，垂直居中；获取屏幕高度
            var heightTop = $(window).height() - 560;
            $(".unified-login-content").css("top", heightTop / 2);
        })
    </script>
</head>
<body>
<!--头部-->
<div class="unified-login-top">
    <!--logo-->
    <div class="unified-login-logo"><i class="fa fa-windows"></i>&nbsp;${systemName}</div>
</div>
<!--内容区-->
<div class="unified-login-content">
    <!--背景图-->
    <div><img src="${staticContentsServer}/static/images/auth/black/login_bg_black.jpg"></div>
    <!--登录区-->
    <div class="unified-login-input">
        <div class="unified-login-edit">
            <form id="loginForm" action="${ctx}/login" method="post">
                <input type="hidden" id="SESSION_TIME_OUT" value="true"/>
                <h3>${systemName}</h3>
                <!--表单-->
                <ul>
                    <div id="loginErrorMessage" style="color: Red;">${error}${accessIpError}</div>
                    <li><i class="fa fa-user-circle-o"></i><input class="unified-input" name="username" type="text" value="${username}" placeholder="请输入用户名"></li>
                    <li><i class="fa fa-key"></i><input class="unified-input" name="password" type="password" value="${password}" placeholder="请输入密码"></li>
                    <li style="overflow: hidden;">
                        <div class="unified-left checkbox"><label><input name="rememberMe" type="checkbox" value="${rememberMe}" id="rememberMe"> 记住账号</label></div>
                        <div class="unified-right"><a href="${ctx}/auth/retrievepassword">忘记密码？</a></div>
                    </li>
                    <li>
                        <button type="submit" id="loginBtn" class="unified-button">立即登录</button>
                    </li>
                </ul>
            </form>
        </div>
        <!--提示信息-->
        <div class="unified-login-note">浏览器 <strong style="color: #bcbcbd;">推荐使用</strong><br>谷歌(Chrome)、火狐(Firefox)、360(极速模式)、IE10(以上)版本！</div>
    </div>
</div>
<!--底部导航-->
<div class="unified-login-footer">
    <ul>
        <li style="border: none;width: 100%;color: #FFF;">版权所有：${systemDate.substring(0, 4)} &copy; ${companyName}-${systemName}</li>
    </ul>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
</body>
</html>
<%
    //清空IP访问限制错误消息
    session.removeAttribute("accessIpError");
%>
