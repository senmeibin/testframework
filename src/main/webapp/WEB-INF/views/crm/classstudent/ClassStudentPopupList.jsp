<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/classstudent/ClassStudentPopupList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content ClassStudentPopupList-MainContent" style="width: 1200px; display: none;">
    <ctag:ModalHeader modalTitle="班级学员一览"></ctag:ModalHeader>

    <div class="modal-body">
        <button id="btnAdd" type="button" class="btn btn-primary">
            <i class="fa fa-plus"></i>新增班级学员
        </button>
        <div class="clear-both dashed-line">
        </div>
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm">
            <div class="col-md-2 form-group">
                <label>备注</label>
                <input id="remark" data-camel-field="true" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="备注"/>
            </div>
            <div class="col-md-2 form-group" style="width: 300px;">
                <label>
                    &nbsp;
                </label>
                <div>
                    <button id="btnSearch" type="button" class="btn btn-primary">
                        <i class="fa fa-search"></i>查询
                    </button>
                    <button id="btnClear" type="button" class="btn btn-default">
                        <i class="fa fa-eraser"></i>清空
                    </button>
                </div>
            </div>
        </form>
        <%--上次检索条件--%>
        <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_ClassStudent}"/>
        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ClassStudent}"/>
        <div class="margin-top-space" id="divList" style="width: 100%">
        </div>
    </div>

    <ctag:ModalFooter></ctag:ModalFooter>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ClassStudentPopupList_Page_Load() {
        SysApp.Crm.ClassStudentPopupListIns = new SysApp.Crm.ClassStudentPopupList();
        var instance = SysApp.Crm.ClassStudentPopupListIns;

        instance.selfInstance = "SysApp.Crm.ClassStudentPopupListIns";
        instance.clientID = "ClassStudentPopupList";
        instance.tableName = "crm_class_student";
        instance.controller = "${ctx}/crm/classstudent/";
        instance.entry = "${entry}";

        instance.init();
    }

    $(function () {
        ClassStudentPopupList_Page_Load();
    });
    //]]>
</script>

