<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--浏览器版本检测--%>
<ctag:BrowserVersion></ctag:BrowserVersion>

<%--共通处理进度条--%>
<ctag:ProgressModal modalTitle="处理进度"/>

<%--共通消息处理--%>
<%@ include file="/WEB-INF/views/cmn/CmnMessage.jsp" %>

<%--画面加载中提示--%>
<%@ include file="/WEB-INF/views/cmn/Loading.jsp" %>

<c:if test="${uri.indexOf('/eip/') == -1}">
    <%--画面头部区域--%>
    <%@ include file="/WEB-INF/views/cmn/Header.jsp" %>
</c:if>

<%-- 操作弹层 --%>
<div class="operation-column-group-button">
    <div id="groupButtonDiv">

    </div>
</div>
<%-- 操作弹层样式表 --%>
<style type="text/css">

</style>
<script type="text/javascript">
    <%-- 鼠标悬浮弹层区域，弹层显示 --%>
    $(".operation-column-group-button").mouseover(function () {
        $(".operation-column-group-button").show();
    });
    <%-- 鼠标离开弹层区域，弹层隐藏 --%>
    $(".operation-column-group-button").mouseout(function () {
        $(".operation-column-group-button").hide();
    });
</script>