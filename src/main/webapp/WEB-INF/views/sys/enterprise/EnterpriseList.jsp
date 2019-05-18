<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/sys/enterprise/EnterpriseList.js?${version}"></script>

<title>企业信息一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper EnterpriseList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">企业信息一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:SearchSettingIcon tips="检索条件自定义设定"></ctag:SearchSettingIcon>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增企业信息
            </button>
            <div class="clear-both dashed-line"></div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>企业名称</label>
                    <input id="enterpriseName" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="企业名称"/>
                </div>

                <div class="col-md-2 form-group">
                    <label>法人代表</label>
                    <input id="legalPersonName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="法人代表"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>所属行业</label>
                    <select id="industryCd" data-alias-table="main" class="form-control" data-title="所属行业">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>企业性质</label>
                    <select id="propertyCd" data-alias-table="main" class="form-control" data-title="企业性质">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>企业规模</label>
                    <select id="scaleCd" data-alias-table="main" class="form-control" data-title="企业规模">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>联系人</label>
                    <input id="contactName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="联系人"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>联系人电话</label>
                    <input id="contactTelephone" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="联系人电话"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Mirp.EnterpriseListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Enterprise}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Enterprise}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Enterprise}"/>
            <%--自定义检索条件设置--%>
            <input type="hidden" id="jsonSearchSetting" value="${jsonSearchSetting_Enterprise}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_Enterprise}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function EnterpriseList_Page_Load() {
        SysApp.Sys.EnterpriseListIns = new SysApp.Sys.EnterpriseList();
        var instance = SysApp.Sys.EnterpriseListIns;

        instance.selfInstance = "SysApp.Sys.EnterpriseListIns";
        instance.controller = "${ctx}/sys/enterprise/";
        instance.inputUrl = "${ctx}/sys/enterprise/input";
        instance.clientID = "EnterpriseList";
        instance.tableName = "sys_enterprise";
        instance.entry = "${entry}";
        instance.pageSize = "${pageSize}";
        instance.detailInstance = SysApp.Sys.EnterpriseDetailIns;

        instance.init();
    }

    $(function () {
        EnterpriseList_Page_Load();
    });
    //]]>
</script>
<%--POPUP详细控件--%>
<ctag:ColumnSettingPopup pageInstance="SysApp.Sys.EnterpriseListIns" width="1000px" pageMode="${type}"/>
<ctag:SearchSettingPopup pageInstance="SysApp.Sys.EnterpriseListIns" width="1000px" pageMode="${type}"/>
<ctag:HelpPopup pageInstance="SysApp.Sys.EnterpriseListIns"/>

<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/sys/enterprise/EnterprisePopupDetail.jsp" %>

<%--企业设置POPUP编辑--%>
<%@ include file="/WEB-INF/views/sys/enterprisesetting/EnterpriseSettingPopupInput.jsp" %>