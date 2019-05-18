<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/pms/companyinfo/CompanyInfoList.js?${version}"></script>

<title>公司资讯一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper CompanyInfoList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">公司资讯一览</div>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增公司资讯
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>类别</label>
                    <select id="companyInfoCd" data-alias-table="main" class="form-control" data-title="类别">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>标题</label>
                    <input id="title" data-camel-field="true" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="标题"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>内容</label>
                    <input id="content" data-camel-field="true" data-alias-table="main" type="text"  class="form-control" data-title="内容"/>
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
                    <label>负责人</label>
                    <%--
                    <input id="chargeUserUid" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="负责人"/>
                    --%>
                    <select id="chargeUserUid" data-alias-table="main" class="form-control" data-title="负责人" >
                        <option value="">请选择</option>
                    </select>

                </div>
                <div class="col-md-2 form-group">
                    <label>负责部门</label>
                    <%--
                    <input id="chargeDeptUid" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="负责部门"/>
                    --%>
                    <select id="chargeDeptUid" data-alias-table="main" class="form-control" data-title="负责部门" >
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Pms.CompanyInfoListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_CompanyInfo}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_CompanyInfo}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyInfo}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function CompanyInfoList_Page_Load() {
        SysApp.Pms.CompanyInfoListIns = new SysApp.Pms.CompanyInfoList();
        var instance = SysApp.Pms.CompanyInfoListIns;

        instance.selfInstance = "SysApp.Pms.CompanyInfoListIns";
        instance.controller = "${ctx}/pms/companyinfo/";
        instance.inputUrl = "${ctx}/pms/companyinfo/input";
        instance.clientID = "CompanyInfoList";
        instance.tableName = "pms_company_info";
        instance.detailInstance = SysApp.Pms.CompanyInfoDetailIns;
        instance.entry = "${entry}";
        instance.pageSize = "${pageSize}";

        instance.init();
    }

    $(function() {
        CompanyInfoList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/pms/companyinfo/CompanyInfoPopupDetail.jsp" %>
