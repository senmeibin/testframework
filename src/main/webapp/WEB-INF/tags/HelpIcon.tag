<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ attribute name="tips" type="java.lang.String" required="false" %>

<%--操作说明ICON图标--%>

<div class="panel-title-setup" id="btnHelp">
    <span class='popovers' data-animation="true" data-trigger='hover' data-placement='left' data-content='${empty tips ? '操作说明' : tips}'>
       <i class="fa fa-question fa-blue" style="font-size: 1.1em;"></i>
    </span>
</div>