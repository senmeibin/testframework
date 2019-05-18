<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/role/RoleInput.js?${version}"></script>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height RoleInput-MainContent">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">角色编辑</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">
                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">应用名称</label>
                    <div class="col-sm-9">
                        <select id="appCode" class="form-control required" data-title="应用名称" style="width: 350px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="appCode_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">角色编号</label>
                    <div class="col-sm-9">
                        <input id="roleCode" type="text" class="form-control required duplication" data-title="角色编号" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                        <label id="roleCode_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label"></label>
                    <div class="col-sm-9">
                        (注：填写格式要求：首字母大写，例：SystemManagement/SalesManagement)
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">角色名称</label>
                    <div class="col-sm-9">
                        <input id="roleName" type="text" class="form-control required" data-title="角色名称" maxlength="32"
                               data-rangelength="[0,32]" style="width: 350px;"/>
                        <label id="roleName_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">角色描述</label>
                    <div class="col-sm-9">
                        <input id="description" type="text" class="form-control" data-title="角色描述" maxlength="128"
                               data-rangelength="[0,128]" style="width: 350px;"/>
                        <label id="description_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">显示顺序</label>
                    <div class="col-sm-9">
                        <input id="dispSeq" type="text" class="form-control" data-title="显示顺序" maxlength="5"
                               data-rangelength="[0,5]" data-range="[0,9999]" data-digits="true" style="width: 350px;"/>
                        <label id="dispSeq_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">备注</label>
                    <div class="col-sm-9">
                        <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 350px;"></textarea>
                        <label id="remark_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline" id="divPermission">
                    <label class="col-sm-2 control-label">权限列表</label>
                    <div class="col-sm-9" style="width: 800px;padding-top: 5px;">
                        <div id="permissions"></div>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">用户列表</label>
                    <div class="col-sm-9">
                        <ctag:Select2 selectId="userUids" dataTitle="用户列表" multiple="true" style="width:100%"></ctag:Select2>
                    </div>
                </div>

            </form>

            <div class="text-center">
                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
            </div>

            <%--原始对象Json字符串[实体属性校验用]--%>
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Role}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Role}"/>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function RoleInput_Page_Load() {
        SysApp.Sys.RoleInputIns = new SysApp.Sys.RoleInput();
        var instance = SysApp.Sys.RoleInputIns;
        instance.selfInstance = "SysApp.Sys.RoleInputIns";
        instance.controller = "${ctx}/sys/role/";
        instance.listUrl = "${ctx}/sys/role/list";
        instance.clientID = "RoleInput";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    RoleInput_Page_Load();
    //]]>
</script>