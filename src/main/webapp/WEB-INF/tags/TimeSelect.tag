<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ attribute name="id" type="java.lang.String" required="true" %>
<%--标题--%>
<%@ attribute name="title" type="java.lang.String" required="true" %>
<%--是否必须选择校验--%>
<%@ attribute name="required" type="java.lang.String" required="false" %>

<%@ attribute name="clientID" type="java.lang.String" required="true" %>
<%--下拉显示用开始小时与结束小时（默认显示9-18点）--%>
<%@ attribute name="fromHour" type="java.lang.Integer" required="false" %>
<%@ attribute name="toHour" type="java.lang.Integer" required="false" %>

<%--下拉显示用开始分钟与结束分钟（默认显示0-59分）--%>
<%@ attribute name="fromMinute" type="java.lang.Integer" required="false" %>
<%@ attribute name="toMinute" type="java.lang.Integer" required="false" %>

<%--是否显示时分下拉选择框（默认显示）--%>
<%@ attribute name="showHour" type="java.lang.Boolean" required="false" %>
<%@ attribute name="showMinute" type="java.lang.Boolean" required="false" %>

<%--时分显示步长（时：默认1小时/分：默认5分钟）--%>
<%@ attribute name="stepHour" type="java.lang.Integer" required="false" %>
<%@ attribute name="stepMinute" type="java.lang.Integer" required="false" %>

<%--时分下拉框的宽度（默认宽44px）--%>
<%@ attribute name="width" type="java.lang.String" required="false" %>

<%@ attribute name="searchMode" type="java.lang.String" required="false" %>
<%@ attribute name="showValidateError" type="java.lang.Boolean" required="false" %>

<c:if test="${empty showHour || showHour}">
    <select id="${id}Hour" class="form-control ${required}" data-title="${title}（小时部分）" style="width: ${empty width ? '44px' : width};padding: 0px;" data-search-mode="${empty searchMode ? '' : searchMode}" data-allow-clear="true">
    </select>
</c:if>

<c:if test="${empty showMinute || showMinute}">
    <select id="${id}Minute" class="form-control ${required}" data-title="${title}（分钟部分）" style="width: ${empty width ? '44px' : width};padding: 0px;" data-search-mode="${empty searchMode ? '' : searchMode}" data-allow-clear="true">
    </select>
</c:if>
<% if (showValidateError != null && showValidateError == true) { %>
<label id="${id}Hour_Error" class="validator-error"></label>
<label id="${id}Minute_Error" class="validator-error"></label>
<% } %>
<input type="hidden" id="${id}">

<%-- 唯一JS实例临时变量--%>
<c:set var="jsInstance" value="SysCmn.TimeSelect.${id}"/>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function TimeSelect_${id}_Page_Load() {
        ${jsInstance} = new SysApp.Cmn.TimeSelect();
        var instance = ${jsInstance};

        instance.selfInstance = "${jsInstance}";
        instance.id = "${id}";
        instance.clientID = "${clientID}";

        instance.showHour = ${empty showHour || showHour ? true : false};
        instance.showMinute = ${empty showMinute || showMinute ? true : false};

        instance.fromHour = ${empty fromHour ? 9 : fromHour};
        instance.toHour = ${empty toHour ? 18 : toHour};

        instance.fromMinute = ${empty fromMinute ? 0 : fromMinute};
        instance.toMinute = ${empty toMinute ? 59 : toMinute};

        instance.stepHour = ${empty stepHour ? 1 : stepHour};
        instance.stepMinute = ${empty stepMinute ? 5 : stepMinute};

        //初始化实例对象
        instance.init();
    }

    TimeSelect_${id}_Page_Load();
    //]]>
</script>