<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<head>
    <title>${systemName}-访问禁止</title>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/bootstrap/css/bootstrap.min.css?${version}"/>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/font-awesome/css/font-awesome.min.css?${version}"/>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/base/main.css?${version}"/>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/base/error.css?${version}"/>
</head>

<body class="full-width">
<div>
    <%--Ajax用应用访问禁止判断标志位--%>
    <input type="hidden" id="SYSTEM_ACCESS_FORBIDDEN_FLAG">
    <div class="error-head"></div>
    <div class="error-logo"><i class="fa fa-windows"></i>&nbsp;${systemName}<span>错误提示</span></div>
    <div class="error-content">
        <div class="error-message">
            <img src="${staticContentsServer}/static/images/base/warning.png">
            <span style="color:#FF0000;">应用模块【${appCd}】的访问权限暂未开通，请与系统管理员联系。</span>
        </div>
        <div class="error-button">
            <button type="button" class="btn btn-primary" onclick="window.location='${ctx}/'">返回首页</button>
        </div>
    </div>
    <div class="error-foot">2016 © ${companyName}-${systemName}</div>
</div>
</body>
</html>
