<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/crm/campusclass/CampusClassList.js?${version}"></script>

<title>校区班级一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper CampusClassList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">校区班级一览</div>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增校区班级
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>班级编号</label>
                    <input id="classNumber" data-alias-table="main" type="text" maxlength="16" class="form-control" data-title="班级编号"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>班级级别</label>
                    <select id="classLevelCd" data-alias-table="main" class="form-control" data-title="班级级别">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>开班年月</label>
                    <input id="classYearMonth" data-alias-table="main" type="text" maxlength="6" class="form-control" data-title="开班年月"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>开班日期(起)</label>
                    <ctag:CalendarSelect id="classStartDate$from_search" title="开班日期(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>开班日期(止)</label>
                    <ctag:CalendarSelect id="classStartDate$to_search" title="开班日期(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>班级教室</label>
                    <input id="classroom" data-camel-field="true" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="班级教室"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Crm.CampusClassListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_CampusClass}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_CampusClass}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CampusClass}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function CampusClassList_Page_Load() {
        SysApp.Crm.CampusClassListIns = new SysApp.Crm.CampusClassList();
        var instance = SysApp.Crm.CampusClassListIns;

        instance.selfInstance = "SysApp.Crm.CampusClassListIns";
        instance.controller = "${ctx}/crm/campusclass/";
        instance.inputUrl = "${ctx}/crm/campusclass/input";
        instance.clientID = "CampusClassList";
        instance.tableName = "crm_campus_class";
        instance.entry = "${entry}";
        instance.pageSize = "${pageSize}";
        instance.detailInstance = SysApp.Crm.CampusClassDetailIns;

        instance.init();
    }

    $(function () {
        CampusClassList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/crm/campusclass/CampusClassPopupDetail.jsp" %>