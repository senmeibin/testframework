<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/potentialcustomercontact/PotentialCustomerContactList.js?${version}"></script>

<title>招商客户联络情况一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper PotentialCustomerContactList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">招商客户联络情况一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:SearchSettingIcon tips="检索条件自定义设定"></ctag:SearchSettingIcon>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增联络情况
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>客户</label>
                    <input id="companyName" data-alias-table="potential_customer" type="text" maxlength="32" class="form-control" data-title="客户"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>联络人（职位）</label>
                    <input id="contactPerson" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="联络人（职位）"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>联络时间(起)</label>
                    <ctag:CalendarSelect id="contactTime$from_search" title="联络时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>联络时间(止)</label>
                    <ctag:CalendarSelect id="contactTime$to_search" title="联络时间(止)"></ctag:CalendarSelect>
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
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_PotentialCustomerContact}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_PotentialCustomerContact}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_PotentialCustomerContact}"/>
            <%--自定义检索条件设置--%>
            <input type="hidden" id="jsonSearchSetting" value="${jsonSearchSetting_PotentialCustomerContact}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_PotentialCustomerContact}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function PotentialCustomerContactList_Page_Load() {
        SysApp.Demo.PotentialCustomerContactListIns = new SysApp.Demo.PotentialCustomerContactList();
        var instance = SysApp.Demo.PotentialCustomerContactListIns;

        instance.selfInstance = "SysApp.Demo.PotentialCustomerContactListIns";
        instance.controller = "${ctx}/demo/potentialcustomercontact/";
        instance.inputUrl = "${ctx}/demo/potentialcustomercontact/input";
        instance.clientID = "PotentialCustomerContactList";
        instance.tableName = "demo_potential_customer_contact";

        instance.init();
    }

    $(function () {
        PotentialCustomerContactList_Page_Load();
    });
    //]]>
</script>


<ctag:SearchSettingPopup pageInstance="SysApp.Demo.PotentialCustomerContactListIns" width="1000px"/>
<ctag:ColumnSettingPopup pageInstance="SysApp.Demo.PotentialCustomerContactListIns" width="1000px"/>
<%--操作说明--%>
<%@ include file="/WEB-INF/views/demo/potentialcustomercontact/PotentialCustomerContactPopupDetail.jsp" %>
<ctag:HelpPopup pageInstance="SysApp.Demo.PotentialCustomerContactListIns"/>