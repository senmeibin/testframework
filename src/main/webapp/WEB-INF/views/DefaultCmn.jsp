<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uri" value="${pageContext.request.requestURI}"/>
<!doctype html>
<html>
<head>
    <%--全局资源文件--%>
    <%@ include file="/WEB-INF/views/cmn/GlobalResource.jsp" %>
    <sitemesh:head/>
</head>
<body class="full-width ${loginUser.themeName}">

<%--共通包含文件--%>
<%@ include file="/WEB-INF/views/cmn/CommonInclude.jsp" %>

<%--画面内容区域--%>
<div id="main-content">
    <%@ include file="/WEB-INF/views/cmn/common/Menu.jsp" %>
    <sitemesh:body/>
</div>

<%--画面尾部区域--%>
<%@ include file="/WEB-INF/views/cmn/Footer.jsp" %>

</body>
</html>