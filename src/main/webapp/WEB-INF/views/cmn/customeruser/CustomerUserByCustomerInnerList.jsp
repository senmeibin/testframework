<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%--注：合同客户编辑画面Innner专用--%>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/customeruser/CustomerUserByCustomerInnerList.js?${version}"></script>

<title>客户用户所属一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="CustomerUserByCustomerInnerList-MainContent">
    <ctag:Fold id="contractUserInfo" name="可查阅用户" marginTop="10px"/>
    <div id="contractUserInfo" class="separate-block clearfix">
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm">
            <input type="hidden" id="customerUid" value="${uid}" data-alias-table="main" data-allow-clear="false" data-search-mode="=">
            <div class="col-md-2 form-group">
                <label>用户名</label>
                <input id="userCd" data-alias-table="users" type="text" maxlength="64" class="form-control" data-title="用户名"/>
            </div>
            <div class="col-md-2 form-group">
                <label>姓名</label>
                <input id="userName" data-alias-table="users" type="text" maxlength="32" class="form-control" data-title="姓名"/>
            </div>
            <div class="col-md-8 form-group">
                <label>&nbsp;</label>
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

                    <button id="btnAdd" type="button" class="btn btn-primary" style="margin-left: 20px;">
                        <i class="fa fa-plus"></i>新增所属用户
                    </button>
                </div>
            </div>
        </form>
        <input type="hidden" id="jsonListData" value="${jsonDataList_CustomerUser}"/>
        <%--上次检索条件--%>
        <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_CustomerUser}"/>
        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CustomerUser}"/>
        <div class="margin-top-space" id="divList">
        </div>
    </div>

</div>

<script type="text/javascript">
    //<![CDATA[
    function CustomerUserByCustomerInnerList_Page_Load() {
        SysApp.Cmn.CustomerUserByCustomerInnerListIns = new SysApp.Cmn.CustomerUserByCustomerInnerList();
        var instance = SysApp.Cmn.CustomerUserByCustomerInnerListIns;

        instance.selfInstance = "SysApp.Cmn.CustomerUserByCustomerInnerListIns";
        instance.controller = "${ctx}/cmn/customeruser/";
        instance.clientID = "CustomerUserByCustomerInnerList";
        instance.tableName = "cmn_customer_user";
        instance.inputInstance = "SysApp.Cmn.UserPopupListIns";
        instance.customerUid = "${uid}";

        instance.init();
    }

    $(function () {
        CustomerUserByCustomerInnerList_Page_Load();
    });
    //]]>
</script>

<!--用户选择POPUP-->
<%@ include file="/WEB-INF/views/cmn/customeruser/UserPopupList.jsp" %>