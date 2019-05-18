<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%--全局资源文件：--%>
<%--    1、第三方插件css及js文件加载（如：bootstrap/select2/jquery-ui等）；--%>
<%--    2、系统平台基础css及核心js文件加载（如：Core.js/main.css等）；--%>
<%--    3、Js window 全局变量定义（如：window.contextPath/window.imagesPath等）；--%>
<%--    4、系统平台共通css及js文件加载（如：MainScript.js/Help.js等）；--%>
<title>${systemName}-<sitemesh:title/></title>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link type="image/x-icon" href="${staticContentsServer}/favicon.ico?${version}" rel="shortcut icon">
<link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/bootstrap/css/bootstrap.min.css?${version}"/>
<link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css?${version}"/>
<link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/bootstrap-switch/css/bootstrap3/bootstrap-switch.min.css?${version}">
<link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/font-awesome/css/font-awesome.min.css?${version}"/>
<link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/select2/css/select2.min.css?${version}"/>
<c:if test="${useJqueryUI}">
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/jquery-ui/jquery-ui.min.css?${version}"/>
</c:if>
<link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/ztree/css/zTreeStyle/zTreeStyle.min.css?${version}">
<link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/base/main.css?${version}"/>

<script type="text/javascript">
    //<![CDATA[
    window.contextPath = "${ctx}";
    window.systemDate = "${systemDate}";
    window.imagesPath = "${staticContentsServer}/static/images/";
    window.loginUserUid = "${loginUser.userUid}";
    window.loginUserName = "${loginUser.userName}";
    //]]>
</script>

<script type="text/javascript" src="${staticContentsServer}/static/js/Namespace.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/jquery.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/prototype.js?${version}"></script>

<script type="text/javascript" src="${staticContentsServer}/static/plugins/dcjqaccordion/js/jquery.cookie.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/jquery-validation/jquery.validate.min.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/jquery-validation/localization/messages_zh.min.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/bootstrap/js/bootstrap.min.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/viewer/viewer.min.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/moment/moment-with-locales.min.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/bootstrap-switch/js/bootstrap-switch.min.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/bootstrap-switch/js/toggle-init.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/select2/js/select2.min.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/select2/js/i18n/zh-CN.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/ztree/js/jquery.ztree.core.min.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/ztree/js/jquery.ztree.excheck.min.js?${version}"></script>
<c:if test="${useJqueryUI}">
    <script type="text/javascript" src="${staticContentsServer}/static/plugins/jquery-ui/jquery-ui.min.js?${version}"></script>
</c:if>

<c:if test="${useUEditor}">
    <script type="text/javascript" src="${staticContentsServer}${empty configPath ? "/static/plugins/ueditor/ueditor.config.js": configPath} "></script>
    <script type="text/javascript" src="${staticContentsServer}/static/plugins/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
</c:if>



<script type="text/javascript" src="${staticContentsServer}/static/js/MainScript<c:if test="${debugMode == '0'}">.min</c:if>.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/Core<c:if test="${debugMode == '0'}">.min</c:if>.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/DataGrid<c:if test="${debugMode == '0'}">.min</c:if>.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/CoreAddon<c:if test="${debugMode == '0'}">.min</c:if>.js?${version}"></script>



<%@ include file="/WEB-INF/views/cmn/common/Common.jsp" %>