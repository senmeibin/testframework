<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/crm/student/StudentList.js?${version}"></script>

<title>学员信息一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper StudentList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">学员信息一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:SearchSettingIcon tips="检索条件自定义设定"></ctag:SearchSettingIcon>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增学员
            </button>
            <button id="btnImport" type="button" class="btn btn-primary">
                <i class="fa fa-cloud-upload"></i>批量学员导入
            </button>
            <%--FileImportList禁止放在form tag内--%>
            <ctag:FileImportList appCode="CRM" moduleName="学员信息导入" buttonText="查看学员信息导入日志" clientID="StudentPopupImport"></ctag:FileImportList>
            <div class="clear-both dashed-line">
            </div>

            <div class="fast-search">
                <label>快速查询</label>
                <a onclick="SysApp.Crm.StudentListIns.fastSearch(1, this)"><i class="fa fa-calendar"></i>当日咨询<i class="fa fa-check"></i></a>
                <a onclick="SysApp.Crm.StudentListIns.fastSearch(2, this)"><i class="fa fa-calendar"></i>最近三日咨询<i class="fa fa-check"></i></a>
                <a onclick="SysApp.Crm.StudentListIns.fastSearch(3, this)"><i class="fa fa-calendar"></i>最近一周咨询<i class="fa fa-check"></i></a>
            </div>

            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>学员姓名/家长姓名/手机号码</label>
                    <input id="main.name,main.parentName,main.mobile" type="text" data-search-mode="OR" maxlength="32" class="form-control" data-title="学员姓名/家长姓名/手机号码"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>咨询方式</label>
                    <select id="consultMethodCd" data-alias-table="main" class="form-control" data-title="咨询方式">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>所属校区</label>
                    <select id="studentBelongCampusUid" data-alias-table="main" class="form-control" data-title="所属校区">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>课程顾问</label>
                    <ctag:Select2 selectId="studentBelongConsultantUserUid" aliasTable="main" dataTitle="课程顾问" multiple="true" searchMode="in"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>最近咨询时间(起)</label>
                    <ctag:CalendarSelect id="recentConsultTime$from_search" title="最近咨询时间(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>最近咨询时间(止)</label>
                    <ctag:CalendarSelect id="recentConsultTime$to_search" title="最近咨询时间(止)"></ctag:CalendarSelect>
                </div>

                <ctag:Fold id="divOtherSearchCondition" name="其他查询条件" foldIconClass="fa-caret-up"></ctag:Fold>
                <div id="divOtherSearchCondition" class="separate-block clearfix" style="display: none;" data-force-fold="true">
                    <div class="col-md-2 form-group">
                        <label>信息来源</label>
                        <select id="sourceTypeCd" data-alias-table="main" class="form-control" data-title="信息来源">
                            <option value="">请选择</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>学员状态</label>
                        <select id="studentStatusCd" data-alias-table="main" class="form-control" data-title="学员状态">
                            <option value="">请选择</option>
                        </select>
                    </div>

                    <div class="col-md-2 form-group">
                        <label>围棋基础</label>
                        <select id="baseLevelCd" data-alias-table="main" class="form-control" data-title="围棋基础">
                            <option value="">请选择</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>出生年月(起)</label>
                        <ctag:CalendarSelect id="birthday$from_search" camelField="true" title="出生年月(起)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>出生年月(止)</label>
                        <ctag:CalendarSelect id="birthday$to_search" camelField="true" title="出生年月(止)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>性别</label>
                        <select id="genderCd" data-alias-table="main" class="form-control" data-title="性别">
                            <option value="">请选择</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>证件类型</label>
                        <select id="cardTypeCd" data-alias-table="main" class="form-control" data-title="证件类型">
                            <option value="">请选择</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>证件号码</label>
                        <input id="cardNumber" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="证件号码"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>电子邮件</label>
                        <input id="email" data-camel-field="true" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="电子邮件"/>
                    </div>

                    <div class="col-md-2 form-group">
                        <label>所在省</label>
                        <ctag:ComboTree valueDomId="provinceUid" aliasTable="main" searchMode="=" textDomId="provinceName" parentInstance="SysApp.Crm.StudentListIns" dataTitle="所在省"
                                        nodeName="regionName" idKey="uid" parentIdKey="parentUid" dataUrl="${ctx}/cmn/region/getAllRegion/1" selectParent="false"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>所在市</label>
                        <ctag:ComboTree valueDomId="cityUid" aliasTable="main" searchMode="=" textDomId="cityName" parentInstance="SysApp.Crm.StudentListIns" dataTitle="所在市" parentDomId="provinceUid"
                                        parentDataTitle="所在市" nodeName="regionName" idKey="uid" parentIdKey="parentUid" dataUrl="${ctx}/cmn/region/getAllRegion/2" selectParent="false"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>所在区</label>
                        <ctag:ComboTree valueDomId="regionUid" aliasTable="main" searchMode="=" textDomId="regionName" parentInstance="SysApp.Crm.StudentListIns" dataTitle="所在区" parentDomId="cityUid"
                                        parentDataTitle="所在区" nodeName="regionName" idKey="uid" parentIdKey="parentUid" dataUrl="${ctx}/cmn/region/getAllRegion/3" selectParent="false"/>
                    </div>

                    <div class="col-md-2 form-group">
                        <label>家庭住址</label>
                        <input id="homeAddress" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="家庭住址"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>之前学校</label>
                        <input id="beforeSchool" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="之前学校"/>
                    </div>

                    <div class="col-md-2 form-group">
                        <label>首次咨询时间(起)</label>
                        <ctag:CalendarSelect id="firstConsultTime$from_search" title="首次咨询时间(起)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>首次咨询时间(止)</label>
                        <ctag:CalendarSelect id="firstConsultTime$to_search" title="首次咨询时间(止)"></ctag:CalendarSelect>
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

                    <ctag:PagerSettingIcon pageInstance="SysApp.Crm.StudentListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Student}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Student}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Student}"/>
            <%--自定义检索条件设置--%>
            <input type="hidden" id="jsonSearchSetting" value="${jsonSearchSetting_Student}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_Student}"/>

            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function StudentList_Page_Load() {
        SysApp.Crm.StudentListIns = new SysApp.Crm.StudentList();
        var instance = SysApp.Crm.StudentListIns;

        instance.selfInstance = "SysApp.Crm.StudentListIns";
        instance.controller = "${ctx}/crm/student/";
        instance.inputUrl = "${ctx}/crm/student/input";
        instance.clientID = "StudentList";
        instance.tableName = "crm_student";
        instance.entry = "${entry}";
        instance.pageSize = "${pageSize}";
        instance.detailInstance = SysApp.Crm.StudentDetailIns;

        instance.init();
    }

    $(function () {
        StudentList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/crm/student/StudentPopupDetail.jsp" %>
<ctag:SearchSettingPopup pageInstance="SysApp.Crm.StudentListIns" width="1000px"/>
<ctag:ColumnSettingPopup pageInstance="SysApp.Crm.StudentListIns" width="1000px"/>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Crm.StudentListIns"/>
<!--学员信息导入Popup-->
<%@ include file="/WEB-INF/views/crm/student/StudentPopupImport.jsp" %>

<%--跟进POPUP编辑--%>
<%@ include file="/WEB-INF/views/crm/followup/FollowupPopupInput.jsp" %>
<%--跟进POPUP详细--%>
<%@ include file="/WEB-INF/views/crm/followup/FollowupPopupDetail.jsp" %>
<%--跟进POPUP一览--%>
<%@ include file="/WEB-INF/views/crm/followup/FollowupPopupList.jsp" %>

