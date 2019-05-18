<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/chargesinformation/ChargesInformationList.js?${version}"></script>

<title>企业收费信息一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper ChargesInformationList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">企业收费信息一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:SearchSettingIcon tips="检索条件自定义设定"></ctag:SearchSettingIcon>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
        </div>
        <div class="panel-body">
            <button id="btnAddCharges" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增收费信息
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>所属基地</label>
                    <input id="baseName" data-alias-table="base" type="text" maxlength="32" class="form-control" data-title="所属基地"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>企业名称</label>
                    <input id="companyName" data-alias-table="company" type="text" maxlength="64" class="form-control" data-title="企业名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>收费方式</label>
                    <select id="chargesTypeCd" data-alias-table="main" class="form-control" data-title="收费方式">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>期限查询</label>
                    <select id="queryMode" data-alias-table="main" class="form-control" data-title="期限查询" data-search-mode="ignore_search">
                        <option value="">请选择</option>
                        <option value="0">超期</option>
                        <option value="1">7天内到期</option>
                        <option value="2">15天内到期</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>第一次收费日期(起)</label>
                    <ctag:CalendarSelect id="firstChargeDate$from_search" title="第一次收费日期(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>第一次收费日期(止)</label>
                    <ctag:CalendarSelect id="firstChargeDate$to_search" title="第一次收费日期(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>下次收款日期(起)</label>
                    <ctag:CalendarSelect id="nextReceiptDate$from_search" title="下次收款日期(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>下次收款日期(止)</label>
                    <ctag:CalendarSelect id="nextReceiptDate$to_search" title="下次收款日期(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>单位</label>
                    <select id="unitCd" data-alias-table="main" class="form-control" data-title="单位">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>收租方式</label>
                    <select id="rentWayCd" data-alias-table="main" class="form-control" data-title="收租方式">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>押金方式</label>
                    <select id="depositMethodCd" data-alias-table="main" class="form-control" data-title="押金方式">
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
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_ChargesInformation}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_ChargesInformation}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ChargesInformation}"/>
            <%--自定义检索条件设置--%>
            <input type="hidden" id="jsonSearchSetting" value="${jsonSearchSetting_ChargesInformation}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_ChargesInformation}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ChargesInformationList_Page_Load() {
        SysApp.Demo.ChargesInformationListIns = new SysApp.Demo.ChargesInformationList();
        var instance = SysApp.Demo.ChargesInformationListIns;

        instance.selfInstance = "SysApp.Demo.ChargesInformationListIns";
        instance.controller = "${ctx}/demo/chargesinformation/";
        instance.clientID = "ChargesInformationList";
        instance.tableName = "demo_charges_information";
        instance.companyDetailInstance = SysApp.Demo.CompanyInformationDetailIns;
        instance.inputUrl = "${ctx}/demo/chargesinformation/input";

        instance.init();
    }

    $(function () {
        ChargesInformationList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/demo/chargesinformation/companyinformation/CompanyInformationPopupList.jsp" %>
<ctag:SearchSettingPopup pageInstance="SysApp.Demo.ChargesInformationListIns" width="1000px"/>
<ctag:ColumnSettingPopup pageInstance="SysApp.Demo.ChargesInformationListIns" width="1000px"/>
<%@ include file="/WEB-INF/views/demo/companyinformation/CompanyInformationPopupDetail.jsp" %>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.ChargesInformationListIns"/>