<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/loginlog/LoginLogList.js?${version}"></script>

<title>登录日志一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper LoginLogList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <div class="panel-title">登录日志一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>登录日期(起)</label>
                    <div class='input-group datetime-picker'>
                        <input id="insertDate$from_search" data-alias-table="main" type='text' data-allow-clear="false" class="form-control required" data-title="登录日期(起)"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
                <div class="col-md-2 form-group">
                    <label>登录日期(止)</label>
                    <div class='input-group datetime-picker'>
                        <input id="insertDate$to_search" data-alias-table="main" type='text' data-allow-clear="false" class="form-control required" data-title="登录日期(止)"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
                <div class="col-md-2 form-group">
                    <label>所属部门</label>
                    <ctag:ComboCheckTree valueDomId="deptUid" textDomId="deptName" aliasTable="login_user" parentInstance="SysApp.Sys.LoginLogListIns" dataTitle="所属部门" searchMode="in"
                                         idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dispName="deptFullName" dataUrl="${ctx}/sys/dept/getAllDeptList"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>用户名</label>
                    <input id="userCd" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="用户名"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>姓名</label>
                    <input id="userName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="姓名"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>登录IP</label>
                    <input id="remoteIp" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="登录IP"/>
                </div>

                <div class="clear-both dashed-line">
                </div>

                <div>
                    <div>
                        <button id="btnSearch" type="button" class="btn btn-primary">
                            <i class="fa fa-search"></i>
                            查询
                        </button>
                        <button id="btnClear" type="button" class="btn btn-default">
                            <i class="fa fa-eraser"></i>
                            清空
                        </button>
                        <ctag:PagerSettingIcon pageInstance="SysApp.Sys.LoginLogListIns"/>
                    </div>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_LoginLog}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_LoginLog}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_LoginLog}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function LoginLogList_Page_Load() {
        SysApp.Sys.LoginLogListIns = new SysApp.Sys.LoginLogList();
        var instance = SysApp.Sys.LoginLogListIns;

        instance.selfInstance = "SysApp.Sys.LoginLogListIns";
        instance.controller = "${ctx}/sys/loginlog/";
        instance.clientID = "LoginLogList";
        instance.tableName = "sys_login_log";
        instance.detailInstance = SysApp.Sys.LoginLogDetailIns;
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        LoginLogList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/sys/loginlog/LoginLogPopupDetail.jsp" %>