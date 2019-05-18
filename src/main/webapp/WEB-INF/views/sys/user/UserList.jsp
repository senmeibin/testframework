<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/sys/user/UserList.js?${version}"></script>

<title>用户一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper UserList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">用户一览</div>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增用户
            </button>
            <!--有配置DATA_IMPORT_SYS_API才显示同步按钮 -->
            <c:if test="${dataImportApiExist}">
                <button id="btnSyncData" type="button" class="btn btn-danger">
                    <i class="fa fa-spinner"></i>同步数据
                </button>
            </c:if>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>用户名</label>
                    <input id="userCd" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="用户名"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>所属部门</label>
                    <ctag:ComboCheckTree valueDomId="deptUid" textDomId="deptName" aliasTable="main" parentInstance="SysApp.Sys.UserListIns" dataTitle="所属部门" searchMode="in"
                                         idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dispName="deptFullName" dataUrl="${ctx}/sys/dept/getAllDeptList"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>角色权限</label>
                    <ctag:ComboCheckTree valueDomId="roleUid" textDomId="roleName" aliasTable="main" parentInstance="SysApp.Sys.UserListIns" dataTitle="用户角色" searchMode="ignore_search"
                                         nodeName="roleName" idKey="uid" parentIdKey="parentRoleUid" dataUrl="${ctx}/sys/role/getAllRoleList"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>姓名</label>
                    <input id="userName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="姓名"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>邮箱地址</label>
                    <input id="userMail" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="邮箱地址"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>职位</label>
                    <input id="positionName" data-alias-table="post" type="text" maxlength="64" class="form-control" data-title="职位"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>是否启用</label>
                    <select id="recordStatus" data-alias-table="main" class="form-control" data-title="是否启用">
                        <option value="">-全部-</option>
                        <option value="1">启用</option>
                        <option value="8">停用</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>工号</label>
                    <input id="userNumber" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="工号"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>手机号码</label>
                    <input id="userPhone" data-alias-table="main" type="text" maxlength="11" class="form-control" data-title="手机号码"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>分机号码</label>
                    <input id="userExtensionNumber" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="分机号码"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.UserListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_User}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_User}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_User}"/>
            <%--自定义列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_User}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function UserList_Page_Load() {
        SysApp.Sys.UserListIns = new SysApp.Sys.UserList();
        var instance = SysApp.Sys.UserListIns;
        instance.selfInstance = "SysApp.Sys.UserListIns";
        instance.controller = "${ctx}/sys/user/";
        instance.inputUrl = "${ctx}/sys/user/input";
        instance.clientID = "UserList";
        instance.tableName = "sys_user";
        instance.inputInstance = SysApp.Sys.UserInputIns;
        instance.detailInstance = SysApp.Sys.UserDetailIns;
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    $(function () {
        UserList_Page_Load();
    });
    //]]>
</script>
<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/sys/user/UserPopupDetail.jsp" %>
<ctag:ColumnSettingPopup pageInstance="SysApp.Sys.UserListIns" width="1000px"/>