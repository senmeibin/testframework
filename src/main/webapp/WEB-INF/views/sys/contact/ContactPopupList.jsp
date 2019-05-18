<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/contact/ContactList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="modal-body ContactList-MainContent">
    <button id="btnAdd" type="button" class="btn btn-primary">
        <i class="fa fa-plus"></i>新增联系人
    </button>
    <div class="clear-both dashed-line">
    </div>
    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" style="margin-top: 10px; display: none;">
        <%--来往单位UID--%>
        <input id="companyUid" data-alias-table="main" type="hidden" data-allow-clear="false" value="${companyUid}" data-search-mode="="/>
        <div class="col-md-2 form-group">
            <label>姓名</label>
            <input id="name" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="姓名"/>
        </div>
        <div class="col-md-2 form-group">
            <label>部门</label>
            <input id="department" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="部门"/>
        </div>
        <div class="col-md-2 form-group">
            <label>职务</label>
            <input id="post" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="职务"/>
        </div>
        <div class="col-md-2 form-group">
            <label>主要职责</label>
            <input id="responsibility" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="主要职责"/>
        </div>
        <div class="col-md-2 form-group">
            <label>电话</label>
            <input id="telephone" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="电话"/>
        </div>
        <div class="col-md-2 form-group">
            <label>传真</label>
            <input id="fax" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="传真"/>
        </div>
        <div class="col-md-2 form-group">
            <label>邮箱</label>
            <input id="email" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="邮箱"/>
        </div>
        <div class="col-md-2 form-group">
            <label>手机</label>
            <input id="mobile" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="手机"/>
        </div>
        <div class="col-md-2 form-group">
            <label>生日From</label>
            <div class='input-group datetime-picker'>
                <input id="birthday$from_search" data-alias-table="main" type='text' class="form-control" data-title="生日From"/>
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                </span>
            </div>
        </div>
        <div class="col-md-2 form-group">
            <label>生日To</label>
            <div class='input-group datetime-picker'>
                <input id="birthday$to_search" data-alias-table="main" type='text' class="form-control" data-title="生日To"/>
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                </span>
            </div>
        </div>
        <div class="col-md-2 form-group">
            <label>家庭地址</label>
            <input id="homeAddress" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="家庭地址"/>
        </div>
        <div class="col-md-2 form-group">
            <label>家庭电话</label>
            <input id="homeTelephone" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="家庭电话"/>
        </div>
        <div class="col-md-2 form-group">
            <label>家庭邮编</label>
            <input id="homeZipcode" data-alias-table="main" type="text" maxlength="6" class="form-control" data-title="家庭邮编"/>
        </div>
        <div class="col-md-2 form-group">
            <label>爱好</label>
            <input id="interest" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="爱好"/>
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
    <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Contact}"/>
    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Contact}"/>
    <div class="margin-top-space" id="divList" style="width: 100%">
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ContactList_Page_Load() {
        SysApp.Sys.ContactListIns = new SysApp.Sys.ContactList();
        var instance = SysApp.Sys.ContactListIns;

        instance.selfInstance = "SysApp.Sys.ContactListIns";
        instance.controller = "${ctx}/sys/contact/";
        instance.clientID = "ContactList";
        instance.tableName = "sys_contact";
        instance.companyUid = "${companyUid}";
        instance.inputInstance = SysApp.Sys.ContactInputIns;
        instance.detailInstance = SysApp.Sys.ContactDetailIns;

        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    $(function () {
        ContactList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑控件--%>
<%@ include file="/WEB-INF/views/sys/contact/ContactPopupInput.jsp" %>
<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/sys/contact/ContactPopupDetail.jsp" %>