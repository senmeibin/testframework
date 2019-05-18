<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/urlrole/UrlRoleList.js?${version}"></script>

<title>访问权限配置一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper UrlRoleList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">访问权限配置一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">

            <button id="btnUrlRoleTreeList" type="button" class="btn btn-default">
                <i class="fa fa-sitemap"></i>访问权限树形展示
            </button>

            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>访问路径</label>
                    <input id="url" data-alias-table="main" type="text" maxlength="200" class="form-control" data-title="访问路径"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>角色集合</label>
                    <input id="roles" data-alias-table="main" type="text" maxlength="255" class="form-control" data-title="角色集合"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>描述</label>
                    <input id="description" data-alias-table="main" type="text" maxlength="255" class="form-control" data-title="描述"/>
                </div>
                <div class="clear-both dashed-line">
                </div>

                <div>
                    <button id="btnSearch" type="button" class="btn btn-primary">
                        <i class="fa fa-search"></i>查询
                    </button>
                    <shiro:hasRole name="DataExport">
                        <button id="btnExport" type="button" class="btn btn-primary">
                            <i class="fa fa-cloud-download"></i>导出
                        </button>
                    </shiro:hasRole>
                    <button id="btnClear" type="button" class="btn btn-default">
                        <i class="fa fa-eraser"></i>清空
                    </button>
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.UrlRoleListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_UrlRole}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_UrlRole}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_UrlRole}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function UrlRoleList_Page_Load() {
        SysApp.Sys.UrlRoleListIns = new SysApp.Sys.UrlRoleList();
        var instance = SysApp.Sys.UrlRoleListIns;

        instance.selfInstance = "SysApp.Sys.UrlRoleListIns";
        instance.controller = "${ctx}/sys/urlrole/";
        instance.inputUrl = "${ctx}/sys/urlrole/input";
        instance.clientID = "UrlRoleList";
        instance.tableName = "sys_url_role";
        instance.urlRoleTreeListUrl = "${ctx}/sys/urlrole/tree?entry=menu";
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        UrlRoleList_Page_Load();
    });
    //]]>
</script>

