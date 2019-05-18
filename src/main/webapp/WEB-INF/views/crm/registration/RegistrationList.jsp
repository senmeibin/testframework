<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/crm/registration/RegistrationList.js?${version}"></script>

<title>报名记录一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper RegistrationList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">报名记录一览</div>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary" style="display: none;">
                <i class="fa fa-plus"></i>新增报名记录
            </button>
            <div class="clear-both dashed-line" style="display: none;">
            </div>
            <div class="fast-search">
                <label>快速查询</label>
                <a onclick="SysApp.Crm.RegistrationListIns.fastSearch(1, this)"><i class="fa fa-calendar"></i>当日报名<i class="fa fa-check"></i></a>
                <a onclick="SysApp.Crm.RegistrationListIns.fastSearch(2, this)"><i class="fa fa-calendar"></i>最近三日报名<i class="fa fa-check"></i></a>
                <a onclick="SysApp.Crm.RegistrationListIns.fastSearch(3, this)"><i class="fa fa-calendar"></i>最近一周报名<i class="fa fa-check"></i></a>
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>学员姓名/家长姓名/手机号码</label>
                    <input id="student.name,student.parentName,student.mobile" type="text" data-search-mode="OR" maxlength="32" class="form-control" data-title="学员姓名/家长姓名/手机号码"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>报名时间(起)</label>
                    <ctag:CalendarSelect id="registrationDate$from_search" title="报名时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>报名时间(止)</label>
                    <ctag:CalendarSelect id="registrationDate$to_search" title="报名时间(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>课程顾问</label>
                    <select id="registrationBelongConsultantUserUid" data-alias-table="main" class="form-control" data-title="课程顾问">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>报名校区</label>
                    <select id="registrationCampusUid" data-alias-table="main" class="form-control" data-title="报名校区">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>报名班级</label>
                    <select id="registrationCampusClassUid" data-alias-table="main" class="form-control" data-title="报名班级">
                        <option value="">请选择</option>
                    </select>
                </div>

                <ctag:Fold id="divOtherSearchCondition" name="其他查询条件" foldIconClass="fa-caret-up"></ctag:Fold>
                <div id="divOtherSearchCondition" class="separate-block clearfix" style="display: none;" data-force-fold="true">
                    <div class="col-md-2 form-group">
                        <label>所属校区</label>
                        <select id="registrationBelongCampusUid" data-alias-table="main" class="form-control" data-title="所属校区">
                            <option value="">请选择</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>新老学员</label>
                        <select id="isNewStudent" data-alias-table="main" class="form-control" data-title="新老学员">
                            <option value="">请选择</option>
                            <option value="0">老学员</option>
                            <option value="1">新学员</option>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Crm.RegistrationListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Registration}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Registration}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Registration}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_Registration}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function RegistrationList_Page_Load() {
        SysApp.Crm.RegistrationListIns = new SysApp.Crm.RegistrationList();
        var instance = SysApp.Crm.RegistrationListIns;

        instance.selfInstance = "SysApp.Crm.RegistrationListIns";
        instance.controller = "${ctx}/crm/registration/";
        instance.clientID = "RegistrationList";
        instance.tableName = "crm_registration";
        instance.entry = "${entry}";
        instance.pageSize = "${pageSize}";
        instance.inputInstance = SysApp.Crm.RegistrationInputIns;
        instance.detailInstance = SysApp.Crm.RegistrationDetailIns;

        instance.init();
    }

    $(function () {
        RegistrationList_Page_Load();
    });
    //]]>
</script>

<ctag:ColumnSettingPopup pageInstance="SysApp.Crm.RegistrationListIns" width="1200px"/>
<%--POPUP编辑--%>
<%@ include file="/WEB-INF/views/crm/registration/RegistrationPopupInput.jsp" %>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/crm/registration/RegistrationPopupDetail.jsp" %>