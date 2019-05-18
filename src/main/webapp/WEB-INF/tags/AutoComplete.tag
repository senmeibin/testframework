<%@ tag pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%-- 文本框对应的hidden 域的ID--%>
<%@ attribute name="selectId" type="java.lang.String" required="true" %>
<%-- 文本框的ID--%>
<%@ attribute name="selectLabel" type="java.lang.String" required="true" %>
<%-- 文本框所在块的clientID--%>
<%@ attribute name="clientID" type="java.lang.String" required="true" %>
<%-- 文本框请求数据的url--%>
<%@ attribute name="url" type="java.lang.String" required="true" %>
<%-- 文本框标题--%>
<%@ attribute name="dataTitle" type="java.lang.String" required="true" %>
<%-- 文本输入最大长度--%>
<%@ attribute name="maxlength" type="java.lang.String" required="true" %>
<%-- 当前页面的js对象--%>
<%@ attribute name="pageInstance" type="java.lang.String" required="true" %>
<%-- 文本框是否必须，默认为非必需--%>
<%@ attribute name="required" type="java.lang.String" required="false" %>
<%-- url对应参数名，如果不传入默认用selectLabel的值--%>
<%@ attribute name="paramName" type="java.lang.String" required="false" %>
<%-- 文本样式--%>
<%@ attribute name="style" type="java.lang.String" required="false" %>
<%-- autoComplete 检索最小长度，小于这个字数不做匹配，默认为 2--%>
<%@ attribute name="searchMinLength" type="java.lang.Integer" required="false" %>
<%@ attribute name="aliasTable" type="java.lang.String" required="false" %>
<%@ attribute name="searchMode" type="java.lang.String" required="false" %>
<%@ attribute name="placeholder" type="java.lang.String" required="false" %>

<input id="${clientID}_${selectLabel}" type="text" class="form-control ${empty required ? '' : required}" data-enter-search="false" data-title="${dataTitle}" maxlength="${maxlength}" placeholder="${placeholder}"
       data-rangelength="[0, ${maxlength}]" style="${style}" data-search-mode="ignore_search"/>
<input type="hidden" id="${clientID}_${selectId}" data-search-mode="${empty searchMode ? '=' : searchMode}" data-alias-table="${empty aliasTable ? 'main' : aliasTable}" data-allow-restore="true"/>

<%-- 唯一JS实例临时变量--%>
<c:set var="jsInstance" value="SysCmn.AutoComplete.${selectId}"/>

<script type="text/javascript">
    //<![CDATA[
    function AutoComplete_${selectId}_Page_Load() {
        ${jsInstance} = new SysApp.Cmn.AutoComplete();
        var instance = ${jsInstance};

        instance.selfInstance = "${jsInstance}";

        instance.selectId = "${selectId}";
        instance.selectLabel = "${selectLabel}";
        instance.clientID = "${clientID}";
        instance.url = "${url}";
        instance.paramName = "${empty paramName ? selectLabel : paramName}";
        instance.searchMinLength = "${empty searchMinLength ? 2 : searchMinLength}";
        instance.pageInstance = "${pageInstance}";

        instance.autoCompleteSuccessCallback = "${pageInstance}.autoCompleteSuccessCallback_${selectLabel}";
        instance.autoCompleteSelectCallback = "${pageInstance}.autoCompleteSelectCallback_${selectLabel}";

        //初始化实例对象
        instance.init();
    }

    AutoComplete_${selectId}_Page_Load();
    //]]>
</script>