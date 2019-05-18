<%@ page language="java" pageEncoding="UTF-8" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%--画面加载中提示--%>
<div id="page-preloader">
    <span class="loading">
        <img src="${staticContentsServer}/static/images/base/loading.gif" style="width: 64px;"/>
    </span>
</div>
<script>
    $(window).load(function () {
        hidePreLoader();
    });

    function hidePreLoader() {
        $("#page-preloader").fadeOut(200);
    }
</script>