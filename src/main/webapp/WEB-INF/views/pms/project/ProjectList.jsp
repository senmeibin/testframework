<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/pms/project/ProjectList.js?${version}"></script>

<title>项目一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper ProjectList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">项目一览</div>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增项目
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>名称</label>
                    <input id="name" data-camel-field="true" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>地址</label>
                    <input id="address" data-camel-field="true" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="地址"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>报名开始时间(起)</label>
                    <ctag:CalendarSelect id="entryStartDate$from_search" title="报名开始时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>报名开始时间(止)</label>
                    <ctag:CalendarSelect id="entryStartDate$to_search" title="报名开始时间(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>报名终了时间(起)</label>
                    <ctag:CalendarSelect id="entryEndDate$from_search" title="报名终了时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>报名终了时间(止)</label>
                    <ctag:CalendarSelect id="entryEndDate$to_search" title="报名终了时间(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>踏勘开始时间(起)</label>
                    <ctag:CalendarSelect id="explorationStartDate$from_search" title="踏勘开始时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>踏勘开始时间(止)</label>
                    <ctag:CalendarSelect id="explorationStartDate$to_search" title="踏勘开始时间(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>开标时间(起)</label>
                    <ctag:CalendarSelect id="bidDate$from_search" title="开标时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>开标时间(止)</label>
                    <ctag:CalendarSelect id="bidDate$to_search" title="开标时间(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>进场时间(起)</label>
                    <ctag:CalendarSelect id="projectStartDate$from_search" title="进场时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>进场时间(止)</label>
                    <ctag:CalendarSelect id="projectStartDate$to_search" title="进场时间(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>流程节点</label>
                    <input id="workflowNode" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="流程节点"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>子流程节点</label>
                    <input id="subWorkflowNode" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="子流程节点"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Pms.ProjectListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Project}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Project}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Project}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_Project}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ProjectList_Page_Load() {
        SysApp.Pms.ProjectListIns = new SysApp.Pms.ProjectList();
        var instance = SysApp.Pms.ProjectListIns;

        instance.selfInstance = "SysApp.Pms.ProjectListIns";
        instance.controller = "${ctx}/pms/project/";
        instance.inputUrl = "${ctx}/pms/project/input";
        instance.clientID = "ProjectList";
        instance.tableName = "pms_project";
        instance.entry = "${entry}";
        instance.pageSize = "${pageSize}";
        instance.detailInstance = SysApp.Pms.ProjectDetailIns;

        instance.init();
    }

    $(function() {
        ProjectList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/pms/project/ProjectPopupDetail.jsp" %>
<ctag:ColumnSettingPopup pageInstance="SysApp.Pms.ProjectListIns" width="1000px"/>