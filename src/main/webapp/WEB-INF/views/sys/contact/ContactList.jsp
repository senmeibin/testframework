<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/contact/ContactList.js?${version}"></script>

<title>联系人一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper ContactList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">联系人一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增联系人
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>来往单位</label>
                    <select id="companyUid" data-alias-table="main" class="form-control" data-title="来往单位">
                        <option value="">请选择</option>
                    </select>
                </div>
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
                <div class="clear-both dashed-line">
                </div>
                <div>
                    <button id="btnSearch" type="button" class="btn btn-primary">
                        <i class="fa fa-search"></i>查询
                    </button>
                    <shiro:hasRole name="DataExport">
                        <button id="btnExport" type="button" class="btn btn-primary">
                            <i class="fa fa-cloud-download"></i>导出
                        </button>
                    </shiro:hasRole>
                    <button id="btnClear" type="button" class="btn btn-default">
                        <i class="fa fa-eraser"></i>清空
                    </button>
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.ContactListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Contact}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Contact}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Contact}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ContactList_Page_Load() {
        SysApp.Sys.ContactListIns = new SysApp.Sys.ContactList();
        var instance = SysApp.Sys.ContactListIns;

        instance.selfInstance = "SysApp.Sys.ContactListIns";
        instance.controller = "${ctx}/sys/contact/";
        instance.inputUrl = "${ctx}/sys/contact/input";
        instance.clientID = "ContactList";
        instance.tableName = "sys_contact";
        instance.detailInstance = SysApp.Sys.ContactDetailIns;
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        ContactList_Page_Load();
    });
    //]]>
</script>
<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/sys/contact/ContactPopupDetail.jsp" %>