<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content DeptInput-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <div class="form-group" style="display: none">
            <div>
                <input id="uid" type="hidden">
                <input id="enterpriseUid" type="hidden">
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">上级部门</label>
            <div class="col-sm-9">
                <ctag:ComboTree valueDomId="parentDeptUid" textDomId="parentDeptName" parentInstance="SysApp.Sys.DeptInputIns" dataTitle="上级部门" style="width:310px;"
                                idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dispName="deptFullName" dataUrl="${ctx}/sys/dept/getAllDeptList"/>
                <label id="parentDeptName_Error" class="validator-error"/>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">部门层级</label>
            <div class="col-sm-9">
                <select id="deptClass" class="form-control required" data-title="部门层级" style="width: 350px;">
                    <option value="">请选择</option>
                </select>
                <label id="deptClass_Error" class="validator-error"/>
            </div>
        </div>
        <div class="form-group form-inline dept-category-row">
            <label class="col-sm-3 control-label">部门分类</label>
            <div class="col-sm-9">
                <select id="deptCategoryCd" class="form-control" data-title="部门分类" style="width: 350px;" >
                    <option value="">请选择</option>
                </select>
                <label id="deptCategoryCd_Error" class="validator-error"></label>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">部门名称</label>
            <div class="col-sm-9">
                <input id="deptName" type="text" class="form-control required" data-title="部门名称" maxlength="64" data-rangelength="[0,64]" style="width: 350px;"/>
                <label id="deptName_Error" class="validator-error"/>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">部门别名</label>
            <div class="col-sm-9">
                <input id="deptAliasName" type="text" class="form-control" data-title="部门别名" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                <label id="deptAliasName_Error" class="validator-error"/>
            </div>
        </div>
        <div class="form-group form-inline" style="display: none;">
            <label class="col-sm-3 control-label">部门全称</label>
            <div class="col-sm-9">
                <input id="deptFullName" type="hidden" class="form-control duplication" data-title="部门全称" maxlength="128" data-rangelength="[0,128]" style="width: 350px;"/>
                <label id="deptFullName_Error" class="validator-error"/>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">表示顺序</label>
            <div class="col-sm-9">
                <input id="dispSeq" type="text" class="form-control" data-title="表示顺序" maxlength="5" data-rangelength="[0,5]" data-range="[0,99999]" data-digits="true" style="width: 350px;"/>
                <label id="dispSeq_Error" class="validator-error"/>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <div class="col-sm-9">
                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 350px;"></textarea>
                <label id="remark_Error" class="validator-error"/>
            </div>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Dept}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Dept}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/dept/DeptInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function DeptInput_Page_Load() {
        SysApp.Sys.DeptInputIns = new SysApp.Sys.DeptInput();
        var instance = SysApp.Sys.DeptInputIns;
        instance.selfInstance = "SysApp.Sys.DeptInputIns";
        instance.clientID = "DeptInput";
        instance.controller = "${ctx}/sys/dept/";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    DeptInput_Page_Load();
    //]]>
</script>