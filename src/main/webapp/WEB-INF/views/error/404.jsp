<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<head>
    <title>${systemName}-页面不存在</title>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/bootstrap/css/bootstrap.min.css?${version}"/>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/font-awesome/css/font-awesome.min.css?${version}"/>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/base/main.css?${version}"/>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/base/error.css?${version}"/>
</head>

<body class="full-width">
<div>
    <%--Ajax用404页面不存在判断标志位--%>
    <input type="hidden" id="SYSTEM_404_FLAG">
    <div class="error-head"></div>
    <div class="error-logo"><i class="fa fa-windows"></i>&nbsp;${systemName}<span>错误提示</span></div>
    <div class="error-content">
        <div class="error-message">
            <img src="${staticContentsServer}/static/images/base/warning.png">
            <span style="color:#FF0000;">404-您所访问的页面不存在。</span>
        </div>
        <div class="error-button">
            <button type="button" class="btn btn-primary" onclick="window.location='${ctx}/'">返回首页</button>
        </div>
    </div>
    <div class="error-foot">2016 © ${companyName}-${systemName}</div>
</div>
</body>
</html>