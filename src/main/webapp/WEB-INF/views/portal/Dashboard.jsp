<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<title>系统首页</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper Dashboard-MainContent">
    <script>
        <c:choose>
        <c:when test="${tenant == '2'}">
            window.location = "${ctx}/crm/desktop";
        </c:when>
        <c:otherwise>
            window.location = "${ctx}/pms/companyinfo";
        </c:otherwise>
        </c:choose>

    </script>
</div>