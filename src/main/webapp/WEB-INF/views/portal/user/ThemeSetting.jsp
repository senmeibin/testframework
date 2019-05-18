<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<script type="text/javascript" src="${staticContentsServer}/static/js/portal/user/ThemeSetting.js?${version}"></script>

<title>个人信息</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">个人主题设置</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body ThemeSetting-MainContent">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal margin-top-space">
                <div class="theme-setting">
                    <div class="col-lg-4">
                        <b>浅色系主题菜单</b><br/>
                        <a class="theme-setting-unselect" href="#" data-theme="theme-gray">
                            <img src="${staticContentsServer}/static/images/base/theme_gray.jpg" alt=""/><i></i>
                        </a>
                    </div>
                    <div class="col-lg-4">
                        <b>黑色系主题菜单</b><br/>
                        <a class="theme-setting-unselect" href="#" data-theme="theme-black">
                            <img src="${staticContentsServer}/static/images/base/theme_black.jpg" alt=""/><i></i>
                        </a>
                    </div>
                    <div class="col-lg-4">
                        <b>蓝色系主题菜单</b><br/>
                        <a class="theme-setting-unselect" href="#" data-theme="theme-blue">
                            <img src="${staticContentsServer}/static/images/base/theme_blue.jpg" alt=""/><i></i>
                        </a>
                    </div>
                </div>
            </form>
            <div class="text-center clear-both">
                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存个人主题
                </button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ThemeSetting_Page_Load() {
        SysApp.Portal.ThemeSettingIns = new SysApp.Portal.ThemeSetting();
        var instance = SysApp.Portal.ThemeSettingIns;
        instance.selfInstance = "SysApp.Portal.ThemeSettingIns";
        instance.clientID = "ThemeSetting";
        instance.listUrl = "${ctx}/portal/user/themeSetting";
        instance.controller = "${ctx}/portal/user/";
        instance.themeName = "${loginUser.themeName}";

        instance.init();
    }

    ThemeSetting_Page_Load();
    //]]>
</script>