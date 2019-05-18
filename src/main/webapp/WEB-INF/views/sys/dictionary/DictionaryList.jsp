<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/dictionary/DictionaryList.js?${version}"></script>

<title>数据字典一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper DictionaryList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">数据字典一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增数据字典
            </button>
            <button id="btnImport" type="button" class="btn btn-primary">
                <i class="fa fa-cloud-upload"></i>批量数据导入
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>大区分CD</label>
                    <input id="mainCd" data-alias-table="main" type="text" maxlength="8" class="form-control" data-title="大区分CD"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>大区分名称</label>
                    <input id="mainName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="大区分名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>小区分CD</label>
                    <input id="subCd" data-alias-table="main" type="text" maxlength="8" class="form-control" data-title="小区分CD"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>小区分名称</label>
                    <input id="subName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="小区分名称"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.DictionaryListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Dictionary}"/>
            <!--上次检索条件-->
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Dictionary}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function DictionaryList_Page_Load() {
        SysApp.Sys.DictionaryListIns = new SysApp.Sys.DictionaryList();
        var instance = SysApp.Sys.DictionaryListIns;

        instance.selfInstance = "SysApp.Sys.DictionaryListIns";
        instance.controller = "${ctx}/sys/dictionary/";
        instance.importUrl = "${ctx}/sys/dictionary/importInit";
        instance.clientID = "DictionaryList";
        instance.tableName = "sys_dictionary";
        instance.inputInstance = SysApp.Sys.DictionaryInputIns;
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        DictionaryList_Page_Load();
    });
    //]]>
</script>

<%--POPUP编辑控件--%>
<%@ include file="/WEB-INF/views/sys/dictionary/DictionaryPopupInput.jsp" %>