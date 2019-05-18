<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content ParameterInput-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <input id="uid" type="hidden">

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">系统编号</label>
            <div class="col-sm-9">
                <select id="systemCode" class="form-control" data-title="系统编号" style="width: 600px;">
                    <option value="">请选择</option>
                </select>
                <label id="systemCode_Error" class="validator-error"></label>
                <br/>
                <div style="margin-top: 5px;margin-bottom: 15px;">（<i class="fa fa-hand-o-right fa-lg"></i>同DB多服务部署可通过设置系统编号解决参数分离问题，单服务部署无需设置）</div>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">应用名称</label>
            <div class="col-sm-9">
                <select id="appCode" class="form-control required" data-title="应用名称" style="width: 600px;">
                    <option value="">请选择</option>
                </select>
                <label id="appCode_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">参数名称</label>
            <div class="col-sm-9">
                <input id="name" type="text" class="form-control required" data-title="参数名称" maxlength="64" data-rangelength="[0,64]" style="width: 600px;"/>
                <label id="name_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">参数值</label>
            <div class="col-sm-9">
                <textarea id="value" rows="3" cols="30" class="form-control required" data-title="参数值" data-rangelength="[0,16777215]" style="width: 600px;"></textarea>
                <label id="value_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">参数描述</label>
            <div class="col-sm-9">
                <input id="description" type="text" class="form-control required" data-title="参数描述" maxlength="64" data-rangelength="[0,64]" style="width: 600px;"/>
                <label id="description_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">备注</label>
            <div class="col-sm-9">
                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 600px;"></textarea>
                <label id="remark_Error" class="validator-error"></label>
            </div>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Parameter}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Parameter}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/parameter/ParameterInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ParameterInput_Page_Load() {
        SysApp.Sys.ParameterInputIns = new SysApp.Sys.ParameterInput();
        var instance = SysApp.Sys.ParameterInputIns;
        instance.selfInstance = "SysApp.Sys.ParameterInputIns";
        instance.controller = "${ctx}/sys/parameter/";
        instance.clientID = "ParameterInput";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    ParameterInput_Page_Load();
    //]]>
</script>