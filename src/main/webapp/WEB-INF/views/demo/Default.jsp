<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/Default.js?${version}"></script>
<title>系统首页</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper Default-MainContent">
    <div class="panel content-min-height" style="min-height: 800px;">

        <div class="panel-heading">
            <div class="panel-title"><i class="fa fa-home fa-blue" style="font-size: 1.3em;"></i>系统首页</div>
        </div>
        <div class="panel-body">
            <div class="col-lg-12" style="padding:0px">
                <span id="greetingsSpan"></span><span class="username">${loginUser.userName}</span>。
            </div>
            <form id="MainForm">

            </form>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function Default_Page_Load() {
        SysApp.Demo.DefaultIns = new SysApp.Demo.Default();
        var instance = SysApp.Demo.DefaultIns;
        instance.selfInstance = "SysApp.Demo.DefaultIns";
        instance.controller = "${ctx}/demo/default/";
        instance.clientID = "Default";
        instance.contextPath = "${ctx}";
        //初始化Default
        instance.initDefault();
    }

    Default_Page_Load();
    //]]>
</script>
