<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%--调用画面JS实例字符串--%>
<%@ attribute name="pageInstance" type="java.lang.String" required="true" %>
<%--自定义显示条数列表--%>
<%@ attribute name="pageListParams" type="java.lang.String" required="false" %>
<%--画面模式，解决不同画面使用相同的js实例的业务场景--%>
<%@ attribute name="pageMode" type="java.lang.String" required="false" %>
<%@ attribute name="clientID" type="java.lang.String" required="false" %>

<div id="divPagerSetting" style="float:right;text-align: right;padding-top: 5px;">

</div>
<div class="clear-both"></div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function PagerSetting_Page_Load_${clientID}() {
        SysApp.Cmn.PagerSetting${clientID}Ins = new SysApp.Cmn.PagerSetting();
        var instance = SysApp.Cmn.PagerSetting${clientID}Ins;
        instance.selfInstance = "SysApp.Cmn.PagerSetting${clientID}Ins";
        instance.pageInstance = "${pageInstance}";
        instance.controller = "${ctx}/cmn/customsetting/";
        instance.clientID = "PagerSetting";
        instance.pageMode = "${pageMode}";
        instance.pageSize = "${pageSize}";
        instance.pageListParams = "${pageListParams}";
        instance.init();
    }

    $(function () {
        PagerSetting_Page_Load_${clientID}();
    });
    //]]>
</script>