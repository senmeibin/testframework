<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content StudentAssignLogInput-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <input id="uid" type="hidden">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">学员姓名</label>
            <div class="col-sm-9">
                <input id="studentUid" type="text" class="form-control" data-title="学员姓名" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                <label id="studentUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">划转类型</label>
            <div class="col-sm-9">
                <select id="assignTypeCd" class="form-control" data-title="划转类型" style="width: 350px;" >
                    <option value="">请选择</option>
                </select>
                <label id="assignTypeCd_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">变更前课程顾问</label>
            <div class="col-sm-9">
                <input id="beforeUserUid" type="text" class="form-control" data-title="变更前课程顾问" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                <label id="beforeUserUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">变更后课程顾问</label>
            <div class="col-sm-9">
                <input id="afterUserUid" type="text" class="form-control" data-title="变更后课程顾问" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                <label id="afterUserUid_Error" class="validator-error"></label>
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
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_StudentAssignLog}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_StudentAssignLog}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/studentassignlog/StudentAssignLogInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function StudentAssignLogInput_Page_Load() {
        SysApp.Crm.StudentAssignLogInputIns = new SysApp.Crm.StudentAssignLogInput();
        var instance = SysApp.Crm.StudentAssignLogInputIns;
        instance.selfInstance = "SysApp.Crm.StudentAssignLogInputIns";
        instance.clientID = "StudentAssignLogInput";
        instance.entry = "${entry}";
        instance.controller = "${ctx}/crm/studentassignlog/";
        
        instance.init();
    }

    StudentAssignLogInput_Page_Load();
    //]]>
</script>