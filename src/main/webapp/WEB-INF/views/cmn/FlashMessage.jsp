<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--notice消息提示--%>
<c:if test="${not empty notice}">
    <div class="flash-notice">
        ${notice}
    </div>
</c:if>
<%--error消息提示--%>
<c:if test="${not empty error}">
    <div class="flash-error">
        ${error}
    </div>
</c:if>