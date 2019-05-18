<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/crm/followup/FollowupList.js?${version}"></script>

<title>跟进记录一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper FollowupList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">跟进记录一览</div>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary" style="display: none;">
                <i class="fa fa-plus"></i>新增跟进记录
            </button>
            <div class="clear-both dashed-line" style="display: none;">
            </div>
            <div class="fast-search">
                <label>快速查询</label>
                <a onclick="SysApp.Crm.FollowupListIns.fastSearch(1, this)"><i class="fa fa-calendar"></i>当日跟进<i class="fa fa-check"></i></a>
                <a onclick="SysApp.Crm.FollowupListIns.fastSearch(2, this)"><i class="fa fa-calendar"></i>最近三日跟进<i class="fa fa-check"></i></a>
                <a onclick="SysApp.Crm.FollowupListIns.fastSearch(3, this)"><i class="fa fa-calendar"></i>最近一周跟进<i class="fa fa-check"></i></a>
            </div>

            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>跟进内容/学员姓名/家长姓名/手机号码</label>
                    <input id="main.contents,student.name,student.parentName,student.mobile" type="text" data-search-mode="OR" maxlength="32" class="form-control" data-title="跟进内容/学员姓名/家长姓名/手机号码"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>跟进时间(起)</label>
                    <ctag:CalendarSelect id="followupDate$from_search" title="跟进时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>跟进时间(止)</label>
                    <ctag:CalendarSelect id="followupDate$to_search" title="跟进时间(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>跟进方式</label>
                    <select id="followupMethodCd" data-alias-table="main" class="form-control" data-title="跟进方式">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>是否预约</label>
                    <select id="acceptShiftCd" data-alias-table="main" class="form-control" data-title="是否预约">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>课程顾问</label>
                    <ctag:Select2 selectId="followupBelongConsultantUserUid" aliasTable="main" dataTitle="课程顾问" multiple="true" searchMode="in"/>
                </div>

                <ctag:Fold id="divOtherSearchCondition" name="其他查询条件" foldIconClass="fa-caret-up"></ctag:Fold>
                <div id="divOtherSearchCondition" class="separate-block clearfix" style="display: none;" data-force-fold="true">
                    <div class="col-md-2 form-group">
                        <label>所属校区</label>
                        <select id="followupBelongCampusUid" data-alias-table="main" class="form-control" data-title="所属校区">
                            <option value="">请选择</option>
                        </select>
                    </div>

                    <div class="col-md-2 form-group">
                        <label>销售进程</label>
                        <select id="saleProcessCd" data-alias-table="main" class="form-control" data-title="销售进程">
                            <option value="">请选择</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>下次跟进时间(起)</label>
                        <ctag:CalendarSelect id="nextFollowupDate$from_search" title="下次跟进时间(起)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>下次跟进时间(止)</label>
                        <ctag:CalendarSelect id="nextFollowupDate$to_search" title="下次跟进时间(止)"></ctag:CalendarSelect>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Crm.FollowupListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Followup}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Followup}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Followup}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_Followup}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function FollowupList_Page_Load() {
        SysApp.Crm.FollowupListIns = new SysApp.Crm.FollowupList();
        var instance = SysApp.Crm.FollowupListIns;

        instance.selfInstance = "SysApp.Crm.FollowupListIns";
        instance.controller = "${ctx}/crm/followup/";
        instance.clientID = "FollowupList";
        instance.tableName = "crm_followup";
        instance.entry = "${entry}";
        instance.pageSize = "${pageSize}";
        instance.inputInstance = SysApp.Crm.FollowupInputIns;
        instance.detailInstance = SysApp.Crm.FollowupDetailIns;

        instance.init();
    }

    $(function () {
        FollowupList_Page_Load();
    });
    //]]>
</script>

<ctag:ColumnSettingPopup pageInstance="SysApp.Crm.FollowupListIns" width="1200px"/>
<%--POPUP编辑--%>
<%@ include file="/WEB-INF/views/crm/followup/FollowupPopupInput.jsp" %>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/crm/followup/FollowupPopupDetail.jsp" %>