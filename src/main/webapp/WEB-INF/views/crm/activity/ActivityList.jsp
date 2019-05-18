<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/crm/activity/ActivityList.js?${version}"></script>

<title>活动一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper ActivityList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">活动一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:SearchSettingIcon tips="检索条件自定义设定"></ctag:SearchSettingIcon>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增活动
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>活动分类</label>
                    <select id="categoryCd" data-alias-table="main" class="form-control" data-title="活动分类">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>活动种类</label>
                    <select id="typeCd" data-alias-table="main" class="form-control" data-title="活动种类">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>活动主题</label>
                    <input id="title" data-camel-field="true" data-alias-table="main" type="text" maxlength="128" class="form-control" data-title="活动主题"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>活动目的</label>
                    <input id="purpose" data-camel-field="true" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="活动目的"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>活动对象</label>
                    <input id="target" data-camel-field="true" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="活动对象"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>所需物资</label>
                    <input id="requiredMaterials" data-alias-table="main" type="text" maxlength="0" class="form-control" data-title="所需物资"/>
                </div>

                <ctag:Fold id="divOtherSearchCondition" name="其他查询条件" foldIconClass="fa-caret-up"></ctag:Fold>
                <div id="divOtherSearchCondition" class="separate-block clearfix" style="display: none;" data-force-fold="true">
                    <div class="col-md-2 form-group">
                        <label>活动开始日期(起)</label>
                        <ctag:CalendarSelect id="startDate$from_search" title="活动开始日期(起)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>活动开始日期(止)</label>
                        <ctag:CalendarSelect id="startDate$to_search" title="活动开始日期(止)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>活动结束日期(起)</label>
                        <ctag:CalendarSelect id="endDate$from_search" title="活动结束日期(起)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>活动结束日期(止)</label>
                        <ctag:CalendarSelect id="endDate$to_search" title="活动结束日期(止)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>活动地址</label>
                        <input id="address" data-camel-field="true" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="活动地址"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>申请日期(起)</label>
                        <ctag:CalendarSelect id="applyDate$from_search" title="申请日期(起)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>申请日期(止)</label>
                        <ctag:CalendarSelect id="applyDate$to_search" title="申请日期(止)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>审批时间(起)</label>
                        <ctag:CalendarSelect id="auditDate$from_search" title="审批时间(起)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>审批时间(止)</label>
                        <ctag:CalendarSelect id="auditDate$to_search" title="审批时间(止)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>审批状态</label>
                        <select id="auditStatusCd" data-alias-table="main" class="form-control" data-title="审批状态">
                            <option value="">请选择</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>审批意见</label>
                        <input id="auditComment" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="审批意见"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Crm.ActivityListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Activity}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Activity}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Activity}"/>
            <%--自定义检索条件设置--%>
            <input type="hidden" id="jsonSearchSetting" value="${jsonSearchSetting_Activity}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_Activity}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ActivityList_Page_Load() {
        SysApp.Crm.ActivityListIns = new SysApp.Crm.ActivityList();
        var instance = SysApp.Crm.ActivityListIns;

        instance.selfInstance = "SysApp.Crm.ActivityListIns";
        instance.controller = "${ctx}/crm/activity/";
        instance.inputUrl = "${ctx}/crm/activity/input";
        instance.clientID = "ActivityList";
        instance.tableName = "crm_activity";
        instance.entry = "${entry}";
        instance.pageSize = "${pageSize}";
        instance.detailInstance = SysApp.Crm.ActivityDetailIns;

        instance.init();
    }

    $(function () {
        ActivityList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/crm/activity/ActivityPopupDetail.jsp" %>
<ctag:SearchSettingPopup pageInstance="SysApp.Crm.ActivityListIns" width="1000px"/>
<ctag:ColumnSettingPopup pageInstance="SysApp.Crm.ActivityListIns" width="1000px"/>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Crm.ActivityListIns"/>