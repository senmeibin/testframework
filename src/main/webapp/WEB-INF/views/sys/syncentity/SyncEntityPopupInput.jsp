<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content SyncEntityInput-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="form-horizontal margin-top-space">
        <input id="uid" type="hidden">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">类名称</label>
            <div class="col-sm-9">
                <input id="entityClassName" type="text" class="form-control required" data-title="类名称" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                <label id="entityClassName_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">类路径</label>
            <div class="col-sm-9">
                <%--<input id="entityClassPath" type="text" class="form-control required" data-title="类路径" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>--%>
                <select id="entityClassPath" class="form-control required" data-title="类路径" style="width: 350px;">
                    <option value="">请选择</option>
                </select>
                <label id="entityClassPath_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <div class="col-sm-9">
                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 350px;"></textarea>
                <label id="remark_Error" class="validator-error"></label>
            </div>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_SyncEntity}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_SyncEntity}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/syncentity/SyncEntityInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function SyncEntityInput_Page_Load() {
        SysApp.Sys.SyncEntityInputIns = new SysApp.Sys.SyncEntityInput();
        var instance = SysApp.Sys.SyncEntityInputIns;
        instance.selfInstance = "SysApp.Sys.SyncEntityInputIns";
        instance.controller = "${ctx}/sys/syncentity/";
        instance.clientID = "SyncEntityInput";

        instance.init();
    }

    SyncEntityInput_Page_Load();
    //]]>
</script>