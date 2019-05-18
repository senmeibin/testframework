<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/pms/researchinfo/ResearchInfoList.js?${version}"></script>

<title>市场调研一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper ResearchInfoList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">市场调研一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增市场调研
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>类别</label>
                    <select id="researchInfoCd" data-alias-table="main" class="form-control" data-title="类别">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>标题</label>
                    <input id="title" data-camel-field="true" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="标题"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>内容</label>
                    <input id="content" data-camel-field="true" data-alias-table="main" type="text" class="form-control" data-title="内容"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>有效开始日期(起)</label>
                    <ctag:CalendarSelect id="effectStartDate$from_search" title="有效开始日期(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>有效开始日期(止)</label>
                    <ctag:CalendarSelect id="effectStartDate$to_search" title="有效开始日期(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>有效终了日期(起)</label>
                    <ctag:CalendarSelect id="effectEndDate$from_search" title="有效终了日期(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>有效终了日期(止)</label>
                    <ctag:CalendarSelect id="effectEndDate$to_search" title="有效终了日期(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>流程节点</label>
                    <input id="workflowNode" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="流程节点"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>项目</label>
                    <%--
                    <input id="projectUid" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="负责部门"/>
                    --%>
                    <select id="projectUid" data-alias-table="main" class="form-control" data-title="项目" >
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>备注</label>
                    <input id="remark" data-camel-field="true" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="备注"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Pms.ResearchInfoListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_ResearchInfo}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_ResearchInfo}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ResearchInfo}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ResearchInfoList_Page_Load() {
        SysApp.Pms.ResearchInfoListIns = new SysApp.Pms.ResearchInfoList();
        var instance = SysApp.Pms.ResearchInfoListIns;

        instance.selfInstance = "SysApp.Pms.ResearchInfoListIns";
        instance.controller = "${ctx}/pms/researchinfo/";
        instance.inputUrl = "${ctx}/pms/researchinfo/input";
        instance.clientID = "ResearchInfoList";
        instance.tableName = "pms_research_info";
        instance.entry = "${entry}";
        instance.pageSize = "${pageSize}";
        instance.detailInstance = SysApp.Pms.ResearchInfoDetailIns;

        instance.init();
    }

    $(function() {
        ResearchInfoList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/pms/researchinfo/ResearchInfoPopupDetail.jsp" %>

<%--评论POPUP编辑--%>
<%@ include file="/WEB-INF/views/pms/researchinfocomment/ResearchInfoCommentPopupInput.jsp" %>
<%--评论POPUP详细--%>
<%@ include file="/WEB-INF/views/pms/researchinfocomment/ResearchInfoCommentPopupDetail.jsp" %>
<%--评论POPUP一览--%>
<%@ include file="/WEB-INF/views/pms/researchinfocomment/ResearchInfoCommentPopupList.jsp" %>