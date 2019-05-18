<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/baseinfo/BaseInfoList.js?${version}"></script>

<title>基地信息一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper BaseInfoList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">基地信息一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增基地信息
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>基地名称</label>
                    <input id="baseName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="基地名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>地点</label>
                    <input id="baseAddress" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="地点"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>负责人</label>
                    <input id="principal" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="负责人"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>成立时间(起)</label>
                    <ctag:CalendarSelect id="foundingTime$from_search" title="成立时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>成立时间(止)</label>
                    <ctag:CalendarSelect id="foundingTime$to_search" title="成立时间(止)"></ctag:CalendarSelect>
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
            <input type="hidden" id="jsonListData" value="${jsonDataList_BaseInfo}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_BaseInfo}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_BaseInfo}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function BaseInfoList_Page_Load() {
        SysApp.Demo.BaseInfoListIns = new SysApp.Demo.BaseInfoList();
        var instance = SysApp.Demo.BaseInfoListIns;

        instance.selfInstance = "SysApp.Demo.BaseInfoListIns";
        instance.controller = "${ctx}/demo/baseinfo/";
        instance.clientID = "BaseInfoList";
        instance.tableName = "demo_base_info";
        instance.inputInstance = SysApp.Demo.BaseInfoInputIns;
        instance.detailInstance = SysApp.Demo.BaseInfoDetailIns;

        instance.init();
    }

    $(function () {
        BaseInfoList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑--%>
<%@ include file="/WEB-INF/views/demo/baseinfo/BaseInfoPopupInput.jsp" %>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/demo/baseinfo/BaseInfoPopupDetail.jsp" %>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.BaseInfoListIns"/>