<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content AreaInput-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <input id="uid" type="hidden">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">大区编号</label>
            <div class="col-sm-9">
                <input id="areaCd" type="text" class="form-control required" data-title="大区编号" maxlength="8" data-rangelength="[0,8]" style="width: 350px;"/>
                <label id="areaCd_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">大区名称</label>
            <div class="col-sm-9">
                <input id="areaName" type="text" class="form-control required duplication" data-title="大区名称" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                <label id="areaName_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">关联大区</label>
            <div class="col-sm-9">
                <select id="deptUid" data-alias-table="main" class="form-control" data-title="关联大区" style="width: 350px;">
                    <option value="">请选择</option>
                </select>
                <label id="deptUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">表示顺序</label>
            <div class="col-sm-9">
                <input id="dispSeq" type="text" class="form-control required" data-title="表示顺序" maxlength="5" data-rangelength="[0,5]" data-range="[0,99999]" data-digits="true" style="width: 350px;"/>
                <label id="dispSeq_Error" class="validator-error"></label>
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
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Area}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Area}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/area/AreaInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function AreaInput_Page_Load() {
        SysApp.Cmn.AreaInputIns = new SysApp.Cmn.AreaInput();
        var instance = SysApp.Cmn.AreaInputIns;
        instance.selfInstance = "SysApp.Cmn.AreaInputIns";
        instance.controller = "${ctx}/cmn/area/";
        instance.clientID = "AreaInput";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    AreaInput_Page_Load();
    //]]>
</script>