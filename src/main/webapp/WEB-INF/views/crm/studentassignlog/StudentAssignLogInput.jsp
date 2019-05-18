<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/crm/studentassignlog/StudentAssignLogInput.js?${version}"></script>
	
<title>学员分配日志编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper StudentAssignLogInput-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">学员分配日志编辑</div>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
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

            <div class="text-center">
                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
            </div>

            <%--原始对象Json字符串[实体属性校验用]--%>
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_StudentAssignLog}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_StudentAssignLog}"/>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function StudentAssignLogInput_Page_Load() {
        SysApp.Crm.StudentAssignLogInputIns = new SysApp.Crm.StudentAssignLogInput();
        var instance = SysApp.Crm.StudentAssignLogInputIns;
        instance.selfInstance = "SysApp.Crm.StudentAssignLogInputIns";
        instance.controller = "${ctx}/crm/studentassignlog/";
        instance.listUrl = "${ctx}/crm/studentassignlog/list";
        instance.clientID = "StudentAssignLogInput";
        instance.entry = "${entry}";
        
        instance.init();
    }

    StudentAssignLogInput_Page_Load();
    //]]>
</script>