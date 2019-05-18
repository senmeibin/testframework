<%@ tag pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ attribute name="selectId" type="java.lang.String" required="true" %>
<%@ attribute name="aliasTable" type="java.lang.String" required="false" %>
<%@ attribute name="dataTitle" type="java.lang.String" required="true" %>
<%@ attribute name="style" type="java.lang.String" required="false" %>
<%@ attribute name="required" type="java.lang.String" required="false" %>
<%@ attribute name="searchMode" type="java.lang.String" required="false" %>
<%@ attribute name="multiple" type="java.lang.String" required="false" %>
<%@ attribute name="otherAttr" type="java.lang.String" required="false" %>
<%@ attribute name="needBlank" type="java.lang.Boolean" required="false" %>
<%@ attribute name="camelField" type="java.lang.String" required="false" %>
<%@ attribute name="allowClear" type="java.lang.String" required="false" %>

<select ${empty multiple ? "" : "multiple=\"multiple\""}
        data-search-mode="${empty searchMode ? '=' : searchMode}"
        select2
        data-need-blank="${empty needBlank ? true:needBlank}"
        class="form-control ${required}"
        id="${selectId}"
        data-alias-table="${empty aliasTable ? 'main' : aliasTable}"
        data-camel-field="${empty camelField ? 'false' : camelField}"
        data-title="${dataTitle}"
        data-allow-clear="${empty allowClear ? 'true' : allowClear}"
        style="${style}"
${otherAttr}>
</select>

<script type="text/javascript">
    $(document).ready(function () {
        $("select[select2]").select2({
        });
    });
</script>