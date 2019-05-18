<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ attribute name="tips" type="java.lang.String" required="false" %>

<%--一览显示自定义列设定ICON图标--%>

<div class="panel-title-setup" id="btnColumnSetting">
    <span class='popovers' data-animation="true" data-trigger='hover' data-placement='left' data-content='${empty tips ? '一览显示自定义列设定' : tips}'>
       <i class="fa fa-list fa-blue"></i>
    </span>
</div>