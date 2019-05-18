<%--日历选择--%>
<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="id" type="java.lang.String" required="true" %>
<%@ attribute name="name" type="java.lang.String" required="false" %>
<%@ attribute name="title" type="java.lang.String" required="true" %>
<%@ attribute name="width" type="java.lang.String" required="false" %>
<%@ attribute name="maxlength" type="java.lang.String" required="false" %>
<%@ attribute name="required" type="java.lang.String" required="false" %>
<%@ attribute name="className" type="java.lang.String" required="false" %>
<%@ attribute name="aliasTable" type="java.lang.String" required="false" %>
<%@ attribute name="allowClear" type="java.lang.Boolean" required="false" %>
<%@ attribute name="dataSearchMode" type="java.lang.String" required="false" %>
<%@ attribute name="showValidateError" type="java.lang.Boolean" required="false" %>
<%@ attribute name="camelField" type="java.lang.Boolean" required="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="input-group ${empty className ? 'datetime-picker' : className}">
    <input id="${id}" type="text" class="form-control ${required}" data-title="${title}" maxlength="${empty maxlength ? '10' : maxlength}" style="width:${empty width ? '' : width}"
           data-allow-clear="${empty allowClear ? true : allowClear}" data-alias-table="${empty aliasTable ? 'main' : aliasTable}" data-search-mode="${dataSearchMode}"
           data-camel-field="${empty camelField ? false : camelField}"
           <c:if test="${name != null}">name="${name}"</c:if> />
    <span class="input-group-addon">
        <span class="glyphicon glyphicon-calendar"></span>
    </span>
</div>
<% if (showValidateError != null && showValidateError == true) { %>
<label id="${id}_Error" class="validator-error" style="display: none;"></label>
<% } %>
