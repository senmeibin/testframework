<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
    //设置返回码200，避免浏览器自带的错误页面
    response.setStatus(200);
%>

<title>${systemName}-系统内部错误</title>
<link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/base/error.css?${version}"/>
<div class="wrapper">
    <%--Ajax用500系统错误判断标志位--%>
    <input type="hidden" id="SYSTEM_500_FLAG">
    <div class="error-content" style="margin-top: 50px">
        <div class="error-message">
            <img src="${staticContentsServer}/static/images/base/warning.png" style="margin-left:20px;">
            <span style="color:#FF0000;">500-发生系统错误，请与系统管理员联系。</span>
            <span style="color:#FF0000;">
                <b>异常详细信息：</b>
                <h3>
                    <%= (exception == null) ? "" : exception.toString() %>
                    <% if (exception != null) exception.printStackTrace();%>
                </h3>
            </span>
        </div>
        <div class="error-button">
            <button type="button" class="btn btn-primary" onclick="window.location='${ctx}/'">返回首页</button>
        </div>
        <script>
            //Response重定向失败的场合，强制迁移到首页
            if ("<%=exception.toString()%>".indexOf("sendRedirect") != -1) {
                window.location = '${ctx}/';
            }
        </script>
    </div>
</div>