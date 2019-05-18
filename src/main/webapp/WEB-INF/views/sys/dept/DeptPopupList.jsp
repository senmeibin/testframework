<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/dept/DeptList.js?${version}"></script>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content DeptList-MainContent" style="width: 1300px; display: none;">
    <ctag:ModalHeader modalTitle="部门一览"></ctag:ModalHeader>
    <div class="modal-body">
        <div class="clear-both dashed-line">
        </div>
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm">
            <div class="col-md-2 form-group">
                <label>部门名称</label>
                <input id="deptName" data-alias-table="main" type="text" maxlength="64" class="form-control"
                       data-title="部门名称"/>
            </div>
            <div class="col-md-2 form-group">
                <label>部门编号</label>
                <input id="deptCode" data-alias-table="main" type="text" maxlength="16" class="form-control"
                       data-title="部门编号"/>
            </div>
            <div class="col-md-2 form-group">
                <label>部门全称</label>
                <input id="deptFullName" data-alias-table="main" type="text" maxlength="128" class="form-control"
                       data-title="部门全称"/>
            </div>
            <div class="clear-both dashed-line">
            </div>
            <div>
                <button id="btnSearch" type="button" class="btn btn-primary">
                    <i class="fa fa-search"></i>查询
                </button>
                <button id="btnExport" type="button" class="btn btn-primary">
                    <i class="fa fa-cloud-download"></i>导出
                </button>
                <button id="btnClear" type="button" class="btn btn-default">
                    <i class="fa fa-eraser"></i>清空
                </button>
            </div>
        </form>
        <%--上次检索条件--%>
        <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Dept}"/>
        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Dept}"/>
        <div class="margin-top-space" id="divList" style="width: 100%">
        </div>
    </div>

    <ctag:ModalFooter></ctag:ModalFooter>
</div>

<script type="text/javascript">
    //<![CDATA[
    function DeptList_Page_Load() {
        SysApp.Sys.DeptListIns = new SysApp.Sys.DeptList();
        var instance = SysApp.Sys.DeptListIns;

        instance.selfInstance = "SysApp.Sys.DeptListIns";
        instance.controller = "${ctx}/sys/dept/";
        instance.clientID = "DeptList";
        instance.tableName = "sys_dept";
        instance.inputInstance = SysApp.Sys.DeptInputIns;
        instance.detailInstance = SysApp.Sys.DeptDetailIns;
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        DeptList_Page_Load();
    });
    //]]>
</script>