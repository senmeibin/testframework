<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/crm/reservation/ReservationList.js?${version}"></script>

<title>预约记录一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper ReservationList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">预约记录一览</div>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary" style="display: none;">
                <i class="fa fa-plus"></i>新增预约记录
            </button>
            <div class="clear-both dashed-line" style="display: none;">
            </div>
            <div class="fast-search">
                <label>快速查询</label>
                <a onclick="SysApp.Crm.ReservationListIns.fastSearch(1, this)"><i class="fa fa-calendar"></i>当日预约<i class="fa fa-check"></i></a>
                <a onclick="SysApp.Crm.ReservationListIns.fastSearch(2, this)"><i class="fa fa-calendar"></i>最近三日预约<i class="fa fa-check"></i></a>
                <a onclick="SysApp.Crm.ReservationListIns.fastSearch(3, this)"><i class="fa fa-calendar"></i>最近一周预约<i class="fa fa-check"></i></a>
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>学员姓名/家长姓名/手机号码</label>
                    <input id="student.name,student.parentName,student.mobile" type="text" data-search-mode="OR" maxlength="32" class="form-control" data-title="学员姓名/家长姓名/手机号码"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>预约日期(起)</label>
                    <ctag:CalendarSelect id="reservationDate$from_search" title="预约日期(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>预约日期(止)</label>
                    <ctag:CalendarSelect id="reservationDate$to_search" title="预约日期(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>预约目的</label>
                    <select id="purposeCd" data-alias-table="main" class="form-control" data-title="预约目的">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>预约内容</label>
                    <input id="contents" data-camel-field="true" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="预约内容"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>是否到访</label>
                    <select id="isVisited" data-alias-table="main" class="form-control" data-title="是否到访">
                        <option value="">请选择</option>
                        <option value="0">未到访</option>
                        <option value="1">已到访</option>
                    </select>
                </div>

                <ctag:Fold id="divOtherSearchCondition" name="其他查询条件" foldIconClass="fa-caret-up"></ctag:Fold>
                <div id="divOtherSearchCondition" class="separate-block clearfix" style="display: none;" data-force-fold="true">
                    <div class="col-md-2 form-group">
                        <label>预约校区</label>
                        <select id="reservationCampusUid" data-alias-table="main" class="form-control" data-title="预约校区">
                            <option value="">请选择</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>预约班级</label>
                        <input id="reservationCampusClassUid" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="预约班级"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>是否报名</label>
                        <select id="isRegistration" data-alias-table="main" class="form-control" data-title="是否报名">
                            <option value="">请选择</option>
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>备注</label>
                        <input id="remark" data-camel-field="true" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="备注"/>
                    </div>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Crm.ReservationListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Reservation}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Reservation}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Reservation}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_Reservation}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ReservationList_Page_Load() {
        SysApp.Crm.ReservationListIns = new SysApp.Crm.ReservationList();
        var instance = SysApp.Crm.ReservationListIns;

        instance.selfInstance = "SysApp.Crm.ReservationListIns";
        instance.controller = "${ctx}/crm/reservation/";
        instance.clientID = "ReservationList";
        instance.tableName = "crm_reservation";
        instance.entry = "${entry}";
        instance.pageSize = "${pageSize}";
        instance.inputInstance = SysApp.Crm.ReservationInputIns;
        instance.detailInstance = SysApp.Crm.ReservationDetailIns;

        instance.init();
    }

    $(function () {
        ReservationList_Page_Load();
    });
    //]]>
</script>

<ctag:ColumnSettingPopup pageInstance="SysApp.Crm.ReservationListIns" width="1200px"/>
<%--POPUP编辑--%>
<%@ include file="/WEB-INF/views/crm/reservation/ReservationPopupInput.jsp" %>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/crm/reservation/ReservationPopupDetail.jsp" %>
