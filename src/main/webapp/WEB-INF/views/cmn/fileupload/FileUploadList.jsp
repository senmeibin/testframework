<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<title>${empty searchModuleName? '知识库' : searchModuleName}一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper FileUploadList-MainContent">
    <ctag:FileUpload clientID="FileUpload" relationUid="20160101010000000111000000000001" appCode="${appCode}" enableContentMinHeight="true" disableMarginTopSpace="true" showRemarkColumn="false" showSimpleList="true"
                     uploadButtonText="上传${empty searchModuleName? '知识库' : searchModuleName}" uploadButtonWidth="120" moduleName="知识库" fileSizeLimit="50MB" panelTitle="${empty searchModuleName? '知识库' : searchModuleName}一览" searchModuleName="${empty searchModuleName? '' : searchModuleName}"></ctag:FileUpload>
</div>