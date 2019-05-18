<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/customeruser/CustomerPopupList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CustomerPopupList-MainContent" style="width: 1200px; display: none;">
    <ctag:ModalHeader modalTitle="合同客户一览"></ctag:ModalHeader>

    <div class="modal-body">
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm">
            <%--用户UID--%>
            <input id="userUid" type="hidden" data-search-mode="ignore_search"/>
            <div class="col-md-2 form-group">
                <label>客户名称</label>
                <input id="customerName" data-alias-table="main" type="text" maxlength="128" class="form-control" data-title="客户名称"/>
            </div>
            <div class="col-md-2 form-group">
                <label>客户简称</label>
                <input id="customerAbbreviation" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="客户简称"/>
            </div>
            <div class="col-md-2 form-group">
                <label>主客户名称</label>
                <input id="main_customer_name" data-alias-table="cmn_main_customer" type="text" maxlength="128" class="form-control" data-title="主客户名称"/>
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
            <div class="clear-both dashed-line">
            </div>
        </form>
        <%--上次检索条件--%>
        <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Customer}"/>
        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Customer}"/>
        <div class="margin-top-space" id="divList" style="width: 100%">
        </div>
    </div>

    <div class="modal-footer">
        <button id="btnBatchAddCustomerUser" type="button" class="btn btn-primary">
            <i class="fa fa-plus"></i>批量添加客户
        </button>
        <button type="button" class="btn btn-default" id="btnClose"><i class="fa fa-remove"></i>关闭</button>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function CustomerPopupList_Page_Load() {
        SysApp.Cmn.CustomerPopupListIns = new SysApp.Cmn.CustomerPopupList();
        var instance = SysApp.Cmn.CustomerPopupListIns;

        instance.selfInstance = "SysApp.Cmn.CustomerPopupListIns";
        instance.clientID = "CustomerPopupList";
        instance.tableName = "cmn_customer";
        instance.controller = "${ctx}/cmn/customer/";
        instance.customerusercontroller = "${ctx}/cmn/customeruser/"
        instance.entry = "${entry}";

        instance.init();
    }

    $(function () {
        CustomerPopupList_Page_Load();
    });
    //]]>
</script>

