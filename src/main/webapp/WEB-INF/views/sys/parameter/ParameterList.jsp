<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/parameter/ParameterList.js?${version}"></script>

<title>参数配置一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper ParameterList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">参数配置一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增参数配置
            </button>
            <button id="btnImport" type="button" class="btn btn-primary">
                <i class="fa fa-cloud-upload"></i>参数配置导入
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <input id="regSystem" data-alias-table="main" type="hidden" value="0"/>
                <div class="col-md-2 form-group">
                    <label>应用名称</label>
                    <select id="appCode" data-alias-table="main" class="form-control" data-title="应用名称">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>参数名称</label>
                    <input id="name" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="参数名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>参数值</label>
                    <input id="value" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="参数值"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>参数描述</label>
                    <input id="description" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="参数描述"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.ParameterListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Parameter}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Parameter}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Parameter}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ParameterList_Page_Load() {
        SysApp.Sys.ParameterListIns = new SysApp.Sys.ParameterList();
        var instance = SysApp.Sys.ParameterListIns;

        instance.selfInstance = "SysApp.Sys.ParameterListIns";
        instance.controller = "${ctx}/sys/parameter/";
        instance.clientID = "ParameterList";
        instance.tableName = "sys_parameter";
        instance.inputInstance = SysApp.Sys.ParameterInputIns;
        instance.importUrl = "${ctx}/sys/parameter/importInit";
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        ParameterList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑控件--%>
<%@ include file="/WEB-INF/views/sys/parameter/ParameterPopupInput.jsp" %>
