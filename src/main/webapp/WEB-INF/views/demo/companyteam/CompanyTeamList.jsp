<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/CompanyExtendedCommon.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyteam/CompanyTeamList.js?${version}"></script>

<title>核心团队人员一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper CompanyTeamList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">核心团队人员一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:SearchSettingIcon tips="检索条件自定义设定"></ctag:SearchSettingIcon>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>企业名称</label>
                    <input id="companyName" data-alias-table="company" type="text" maxlength="32" class="form-control" data-title="企业名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>姓名</label>
                    <input id="name" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="姓名"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>出生日期(起)</label>
                    <ctag:CalendarSelect id="birthDate$from_search" title="出生日期(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>出生日期(止)</label>
                    <ctag:CalendarSelect id="birthDate$to_search" title="出生日期(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>部门</label>
                    <input id="dept" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="部门"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>证件类型</label>
                    <select id="certificateTypeCd" data-alias-table="main" class="form-control" data-title="证件类型">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>证件号码</label>
                    <input id="certificateNo" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="证件号码"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>电子邮件</label>
                    <input id="mail" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="电子邮件"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>学历</label>
                    <select id="educationCd" data-alias-table="main" class="form-control" data-title="学历">
                        <option value="">请选择</option>
                    </select>
                </div>
                <ctag:Fold id="divOtherSearch" name="其他查询" foldIconClass="fa-caret-up"></ctag:Fold>
                <div id="divOtherSearch" style="display:none;" class="separate-block clearfix">
                    <div class="col-md-2 form-group">
                        <label>是否本公司股东</label>
                        <select id="shareholdersCd" data-alias-table="main" class="form-control" data-title="是否本公司股东">
                            <option value="">请选择</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>是否实际负责人</label>
                        <select id="actualPersonCd" data-alias-table="main" class="form-control" data-title="是否实际负责人">
                            <option value="">请选择</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>是否连续创业</label>
                        <select id="continuousBusinessCd" data-alias-table="main" class="form-control" data-title="是否连续创业">
                            <option value="">请选择</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>是否千人计划</label>
                        <select id="thousandsPlansCd" data-alias-table="main" class="form-control" data-title="是否千人计划">
                            <option value="">请选择</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>是否大学生科技企业</label>
                        <select id="collegeTechnologyEnterprisesCd" data-alias-table="main" class="form-control" data-title="是否大学生科技企业">
                            <option value="">请选择</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>创业者特征</label>
                        <input id="entrepreneurFeature" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="创业者特征"/>
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
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_CompanyTeam}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_CompanyTeam}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyTeam}"/>
            <%--自定义检索条件设置--%>
            <input type="hidden" id="jsonSearchSetting" value="${jsonSearchSetting_CompanyTeam}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_CompanyTeam}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function CompanyTeamList_Page_Load() {
        SysApp.Demo.CompanyTeamListIns = new SysApp.Demo.CompanyTeamList();
        var instance = SysApp.Demo.CompanyTeamListIns;

        instance.selfInstance = "SysApp.Demo.CompanyTeamListIns";
        instance.controller = "${ctx}/demo/companyteam/";
        instance.inputUrl = "${ctx}/demo/companyteam/input";
        instance.clientID = "CompanyTeamList";
        instance.tableName = "demo_company_team";
        instance.detailInstance = SysApp.Demo.CompanyTeamDetailIns;

        instance.init();
    }

    $(function () {
        CompanyTeamList_Page_Load();
    });
    //]]>
</script>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/demo/companyteam/CompanyTeamPopupDetail.jsp" %>
<ctag:SearchSettingPopup pageInstance="SysApp.Demo.CompanyTeamListIns" width="1000px"/>
<ctag:ColumnSettingPopup pageInstance="SysApp.Demo.CompanyTeamListIns" width="1000px"/>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.CompanyTeamListIns"/>