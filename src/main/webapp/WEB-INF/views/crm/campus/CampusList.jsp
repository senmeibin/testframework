<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/crm/campus/CampusList.js?${version}"></script>

<title>校区一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper CampusList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">校区一览</div>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增校区
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-4 form-group" style="padding-left: 0px;">
                    <label>校区名称/代号/地址/联系电话/传真</label>
                    <input id="main.name,main.code,main.address,main.telephone,main.fax" data-search-mode="OR" type="text" maxlength="64" class="form-control" data-title="校区名称/代号/地址/联系电话/传真"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>校区分类</label>
                    <select id="categoryCd" data-alias-table="main" class="form-control" data-title="校区分类">
                        <option value="">请选择</option>
                    </select>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Crm.CampusListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Campus}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Campus}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Campus}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/crm/campus/CampusPopupDetail.jsp" %>
<script type="text/javascript">
    //<![CDATA[
    function CampusList_Page_Load() {
        SysApp.Crm.CampusListIns = new SysApp.Crm.CampusList();
        var instance = SysApp.Crm.CampusListIns;

        instance.selfInstance = "SysApp.Crm.CampusListIns";
        instance.controller = "${ctx}/crm/campus/";
        instance.inputUrl = "${ctx}/crm/campus/input";
        instance.clientID = "CampusList";
        instance.tableName = "crm_campus";
        instance.detailInstance = SysApp.Crm.CampusDetailIns;
        instance.entry = "${entry}";
        instance.pageSize = "${pageSize}";

        instance.init();
    }

    $(function () {
        CampusList_Page_Load();
    });
    //]]>
</script>

