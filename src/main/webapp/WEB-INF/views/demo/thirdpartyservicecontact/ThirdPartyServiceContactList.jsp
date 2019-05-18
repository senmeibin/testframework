<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/thirdpartyservicecontact/ThirdPartyServiceContactList.js?${version}"></script>

<title>第三方服务联系一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper ThirdPartyServiceContactList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">第三方服务联系一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增服务联系
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>所属基地</label>
                    <ctag:Select2 selectId="baseUid" dataTitle="所属基地" multiple="true"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>企业名称</label>
                    <input id="companyName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="企业名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>服务类别</label>
                    <input id="serviceType" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="服务类别"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>标签</label>
                    <input id="thirdPartyServiceTag" data-alias-table="main" type="text" maxlength="512" class="form-control" data-title="标签"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>服务时间(起)</label>
                    <ctag:CalendarSelect id="serviceDate$from_search" title="服务时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>服务时间(止)</label>
                    <ctag:CalendarSelect id="serviceDate$to_search" title="服务时间(止)"></ctag:CalendarSelect>
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
            <input type="hidden" id="jsonListData" value="${jsonDataList_ThirdPartyServiceContact}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_ThirdPartyServiceContact}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ThirdPartyServiceContact}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ThirdPartyServiceContactList_Page_Load() {
        SysApp.Demo.ThirdPartyServiceContactListIns = new SysApp.Demo.ThirdPartyServiceContactList();
        var instance = SysApp.Demo.ThirdPartyServiceContactListIns;

        instance.selfInstance = "SysApp.Demo.ThirdPartyServiceContactListIns";
        instance.controller = "${ctx}/demo/thirdpartyservicecontact/";
        instance.inputUrl = "${ctx}/demo/thirdpartyservicecontact/input";
        instance.clientID = "ThirdPartyServiceContactList";
        instance.tableName = "demo_third_party_service_contact";
        instance.detailInstance = SysApp.Demo.ThirdPartyServiceContactDetailIns;

        instance.init();
    }

    $(function () {
        ThirdPartyServiceContactList_Page_Load();
    });
    //]]>
</script>

<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.ThirdPartyServiceContactListIns"/>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/demo/thirdpartyservicecontact/ThirdPartyServiceContactPopupDetail.jsp" %>