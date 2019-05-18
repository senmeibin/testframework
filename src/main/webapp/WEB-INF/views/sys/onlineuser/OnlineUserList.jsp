<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/onlineuser/OnlineUserList.js?${version}"></script>

<title>在线用户一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper OnlineUserList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">在线用户一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>用户名</label>
                    <input id="userCd" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="用户名"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>姓名</label>
                    <input id="userName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="姓名"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>所属部门</label>
                    <ctag:ComboCheckTree valueDomId="deptUid" textDomId="deptName" aliasTable="online_user" parentInstance="SysApp.Sys.OnlineUserListIns" dataTitle="所属部门" searchMode="in"
                                         idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dispName="deptFullName" dataUrl="${ctx}/sys/dept/getAllDeptList"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>登录IP</label>
                    <input id="remoteIp" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="登录IP"/>
                </div>
                <div class="col-md-2 form-group" style="display: none;">
                    <label>NETBIOS机器名称</label>
                    <input id="netbiosMachineName" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="NETBIOS机器名称"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.OnlineUserListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_OnlineUser}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_OnlineUser}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_OnlineUser}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
            <button id="btnForceLogout" type="button" class="btn btn-danger margin-top-space">
                <i class="fa fa-mars-double"></i>批量强制退出
            </button>
            <div id="divPager"></div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function OnlineUserList_Page_Load() {
        SysApp.Sys.OnlineUserListIns = new SysApp.Sys.OnlineUserList();
        var instance = SysApp.Sys.OnlineUserListIns;
        instance.selfInstance = "SysApp.Sys.OnlineUserListIns";
        instance.controller = "${ctx}/sys/onlineuser/";
        instance.clientID = "OnlineUserList";
        instance.tableName = "sys_online_user";
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        OnlineUserList_Page_Load();
    });
    //]]>
</script>

