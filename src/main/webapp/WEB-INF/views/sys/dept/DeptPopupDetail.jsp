<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content DeptDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">部门名称</label>
            <label class="col-sm-9 control-label content-label" id="deptName"/>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">部门编号</label>
            <label class="col-sm-9 control-label content-label" id="deptCode"/>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">部门别名</label>
            <label class="col-sm-9 control-label content-label" id="deptAliasName"/>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">部门全称</label>
            <label class="col-sm-9 control-label content-label" id="deptFullName"/>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">父级部门</label>
            <label class="col-sm-9 control-label content-label" id="parentDeptName"/>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">部门层级</label>
            <label class="col-sm-9 control-label content-label" id="deptClassName"/>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">部门分类</label>
            <label class="col-sm-9 control-label content-label" id="deptCategoryName"/>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">表示顺序</label>
            <label class="col-sm-9 control-label content-label" id="dispSeq"/>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <label class="col-sm-9 control-label content-label" id="remark" data-textarea="true"/>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/dept/DeptDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function DeptDetail_Page_Load() {
        SysApp.Sys.DeptDetailIns = new SysApp.Sys.DeptDetail();
        var instance = SysApp.Sys.DeptDetailIns;
        instance.selfInstance = "SysApp.Sys.DeptDetailIns";
        instance.clientID = "DeptDetail";
        instance.controller = "${ctx}/sys/dept/";
        instance.isDetail = true;
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    DeptDetail_Page_Load();
    //]]>
</script>