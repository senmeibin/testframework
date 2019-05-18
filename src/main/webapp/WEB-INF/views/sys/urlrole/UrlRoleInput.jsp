<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/urlrole/UrlRoleInput.js?${version}"></script>

<title>标签编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper UrlRoleInput-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">标签编辑</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">
                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label">访问路径</label>
                    <div class="col-sm-9">
                        <input id="url" type="text" class="form-control" data-title="访问路径" maxlength="128" data-rangelength="[0,128]" style="width: 350px;"/>
                        <label id="url_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label">描述</label>
                    <div class="col-sm-9">
                        <textarea id="description" rows="3" cols="30" class="form-control" data-title="描述" maxlength="2048" data-rangelength="[0,2048]" style="width: 350px;"></textarea>
                        <label id="description_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label">备注</label>
                    <div class="col-sm-9">
                        <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"></textarea>
                        <label id="remark_Error" class="validator-error"></label>
                    </div>
                </div>

                <ctag:Fold id="roleInfo" name="角色权限" marginTop="15px"/>
                <div id="roleInfo" class="separate-block clearfix">
                    <div class="col-sm-12" style="padding: 0px 15px 0px 15px;">
                        <div id="roles"></div>
                    </div>
                </div>
            </form>

            <div class="text-center margin-top-space">
                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
            </div>

            <%--原始对象Json字符串[实体属性校验用]--%>
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_UrlRole}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_UrlRole}"/>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function UrlRoleInput_Page_Load() {
        SysApp.Sys.UrlRoleInputIns = new SysApp.Sys.UrlRoleInput();
        var instance = SysApp.Sys.UrlRoleInputIns;
        instance.selfInstance = "SysApp.Sys.UrlRoleInputIns";
        instance.controller = "${ctx}/sys/urlrole/";
        instance.listUrl = "${ctx}/sys/urlrole/list";
        instance.clientID = "UrlRoleInput";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    UrlRoleInput_Page_Load();
    //]]>
</script>