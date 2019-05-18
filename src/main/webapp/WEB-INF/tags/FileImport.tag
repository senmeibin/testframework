<%@ tag pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%-- 文件ID--%>
<%@ attribute name="fileId" type="java.lang.String" required="true" %>
<%-- clientID--%>
<%@ attribute name="clientID" type="java.lang.String" required="true" %>
<%@ attribute name="placeholder" type="java.lang.String" required="false" %>
<%@ attribute name="style" type="java.lang.String" required="false" %>
<%@ attribute name="accept" type="java.lang.String" required="false" %>
<div class="file-import-wrapper">
    <label for="${clientID}_${fileId}" data-client-id="false">
        <input type="file" id="${clientID}_${fileId}" name="${fileId}" data-client-id="false" data-allow-clear="false" accept="${empty accept ? "*" : accept}"/>
        <div class="file-select">
            <input type="text" class="form-control file-name-text" style="${style}" id="${clientID}_${fileId}_rightText" placeholder="${empty placeholder ? "请选择文件" : placeholder}" readonly="readonly"
                   data-client-id="false" data-allow-clear="false"/>
            <span class="btn btn-default browse-button"><i class="fa fa-folder-open" style="margin-left: 4px;"></i>浏览</span>
        </div>
    </label>
</div>

<script type="text/javascript">
    //<![CDATA[
    $(function () {
        $("#${clientID}_${fileId}").on("change", function () {
            var index = $(this).val().lastIndexOf("\\");
            var fileName = $(this).val().substr((index + 1));
            $("#" + this.id + "_rightText").val(fileName);
        });
    });
    //]]>
</script>