<%@ tag pageEncoding="UTF-8" %>

<%@ attribute name="modalTitle" type="java.lang.String" required="false" %>
<%@ attribute name="clientID" type="java.lang.String" required="false" %>
<%--无效化最大化功能（默认有效）--%>
<%@ attribute name="hideMaximizationIcon" type="java.lang.Boolean" required="false" %>
<%--有效化帮助功能（默认无效）--%>
<%@ attribute name="showHelpIcon" type="java.lang.Boolean" required="false" %>
<%--有效化检索条件自定义设定功能（默认无效）--%>
<%@ attribute name="showSearchSettingIcon" type="java.lang.Boolean" required="false" %>
<%--有效化一览显示自定义设定功能（默认无效）--%>
<%@ attribute name="showColumnSettingIcon" type="java.lang.Boolean" required="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<div class="modal-header <c:if test="${!empty clientID}">${clientID}-MainContent</c:if>" onselectstart="return false;">
    <div class="header-setting">
        <div class="panel-title-setup">
            <span class='popovers' data-animation="true" data-trigger='hover' data-placement='left' data-content='关闭'>
                <i id="btnTopClose" class="fa fa-close" style="font-size: 1.2em;"></i>
            </span>
        </div>

        <c:if test="${hideMaximizationIcon == null || hideMaximizationIcon == false}">
            <div class="panel-title-setup">
            <span class='popovers' data-animation="true" data-trigger='hover' data-placement='left' data-content='最大化/复原'>
                <i id="btnMaximization" class="fa fa-window-maximize fa-blue maximization" style="font-size: 0.9em;"></i>
            </span>
            </div>
        </c:if>

        <c:if test="${showHelpIcon}">
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </c:if>

        <c:if test="${showSearchSettingIcon}">
            <ctag:SearchSettingIcon tips="检索条件自定义设定"></ctag:SearchSettingIcon>
        </c:if>

        <c:if test="${showColumnSettingIcon}">
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
        </c:if>
    </div>
    <h4 class="modal-title" id="ctlTitle">${modalTitle}</h4>
</div>