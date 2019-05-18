<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%--注：用户编辑画面Innner专用--%>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/customeruser/CustomerUserByUserInnerList.js?${version}"></script>

<title>客户用户所属一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="CustomerUserByUserInnerList-MainContent">
    <ctag:Fold id="contractUserInfo" name="合同客户" marginTop="10px"/>
    <div id="contractUserInfo" class="separate-block clearfix">
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm">
            <input type="hidden" id="userUid" value="${uid}" data-alias-table="main" data-allow-clear="false" data-search-mode="=">
            <div class="col-md-2 form-group">
                <label>客户编号</label>
                <input id="customerCode" data-alias-table="customer" type="text" maxlength="32" class="form-control" data-title="客户编号"/>
            </div>
            <div class="col-md-2 form-group">
                <label>客户名称</label>
                <input id="customerName" data-alias-table="customer" type="text" maxlength="128" class="form-control" data-title="客户名称"/>
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
                        <i class="fa fa-plus"></i>新增合同客户
                    </button>
                </div>
            </div>
        </form>
        <input type="hidden" id="jsonListData" value="${jsonDataList_CustomerUser}"/>
        <%--上次检索条件--%>
        <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_CustomerUser}"/>
        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CustomerUser}"/>
        <div class="margin-top-space" id="divList" style="padding-bottom: 10px;">
        </div>
    </div>

</div>

<script type="text/javascript">
    //<![CDATA[
    function CustomerUserByUserInnerList_Page_Load() {
        SysApp.Cmn.CustomerUserByUserInnerListIns = new SysApp.Cmn.CustomerUserByUserInnerList();
        var instance = SysApp.Cmn.CustomerUserByUserInnerListIns;

        instance.selfInstance = "SysApp.Cmn.CustomerUserByUserInnerListIns";
        instance.controller = "${ctx}/cmn/customeruser/";
        instance.clientID = "CustomerUserByUserInnerList";
        instance.tableName = "cmn_customer_user";
        instance.userUid = "${uid}";

        instance.init();
    }

    $(function () {
        CustomerUserByUserInnerList_Page_Load();
    });
    //]]>
</script>

<%--合同客户POPUP--%>
<%@ include file="/WEB-INF/views/cmn/customeruser/CustomerPopupList.jsp" %>