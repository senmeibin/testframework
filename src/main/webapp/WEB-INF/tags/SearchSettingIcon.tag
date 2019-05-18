<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ attribute name="tips" type="java.lang.String" required="false" %>

<%--一览显示自定义列设定ICON图标--%>

<div class="panel-title-setup" id="btnSearchSetting">
    <span class='popovers' data-animation="true" data-trigger='hover' data-placement='left' data-content='${empty tips ? '检索条件自定义设定' : tips}'>
       <i class="fa fa-gears fa-blue"></i>
    </span>
</div>