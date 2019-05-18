<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<head>
    <title>${systemName}-应用模块${appCd}维护中</title>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/bootstrap/css/bootstrap.min.css?${version}"/>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/font-awesome/css/font-awesome.min.css?${version}"/>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/base/main.css?${version}"/>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/base/error.css?${version}"/>
</head>

<body class="full-width">
<div>
    <%--Ajax用应用维护判断标志位--%>
    <input type="hidden" id="SYSTEM_MAINTENANCE_FLAG">
    <div class="error-head"></div>
    <div class="error-logo"><i class="fa fa-windows"></i>&nbsp;${systemName}<span>错误提示</span></div>
    <div class="error-content">
        <div class="error-message">
            <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td valign="middle">
                        <img src="${staticContentsServer}/static/images/base/warning.png">
                    </td>
                    <td style="color:#FF0000;font-size: 18px;padding: 10px 10px 10px 50px;">
                        【${model.appName}】维护中，请稍后重试。<br/>
                        开始时间：${model.mainteStartDate}<br/>
                        结束时间：${model.mainteEndDate}<br/>
                        维护内容：${model.mainteContent}<br/>
                    </td>
                </tr>
            </table>
        </div>
        <div class="error-button">
            <button type="button" class="btn btn-primary" onclick="window.location='${ctx}/'">返回首页</button>
        </div>
    </div>
    <div class="error-foot">2016 © ${companyName}-${systemName}</div>
</div>
</body>
</html>