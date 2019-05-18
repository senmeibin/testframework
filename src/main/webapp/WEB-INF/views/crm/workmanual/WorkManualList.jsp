<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<title>${category}工作手册一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper FileUploadList-MainContent">
    <c:if test="${category != 'CRM'}">
        <c:redirect url="${ctx}/error/404"/>
    </c:if>
    <c:if test="${category == 'CRM'}">
        <c:set var="relationUid" value="${category == 'CRM' ? '20180101010000000111000000000001' : '20180101010000000111000000000001'}"/>
        <ctag:FileUpload clientID="FileUpload${category}" relationUid="${relationUid}" appCode="${category}" enableContentMinHeight="true" disableMarginTopSpace="true" showRemarkColumn="true"
                         uploadButtonText="${category}工作手册" uploadButtonWidth="120" moduleName="工作手册" fileSizeLimit="50MB" panelTitle="${category}工作手册一览"/>
    </c:if>
</div>