<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/sys/attachmentimport/AttachmentImportList.js?${version}"></script>

<title>附件导入一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper AttachmentImportList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">数据导入附件一览</div>
            <ctag:SearchSettingIcon tips="检索条件自定义设定"></ctag:SearchSettingIcon>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>应用名称</label>
                    <select id="appCode" data-alias-table="main" class="form-control" data-title="应用名称">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>模块名称</label>
                    <input id="moduleName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="模块名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>文件名称</label>
                    <input id="fileName" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="文件名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>导入者</label>
                    <input id="userName" data-alias-table="insert_user" type="text" maxlength="32" class="form-control" data-title="导入者"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>导入日期(起)</label>
                    <div class='input-group datetime-picker'>
                        <input id="insertDate$from_search" data-alias-table="main" type='text' class="form-control" data-title="导入日期(起)"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
                <div class="col-md-2 form-group">
                    <label>导入日期(止)</label>
                    <div class='input-group datetime-picker'>
                        <input id="insertDate$to_search" data-alias-table="main" type='text' class="form-control" data-title="导入日期(止)"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
                <div class="col-md-2 form-group">
                    <label>导入批次号</label>
                    <input id="batchNo" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="导入批次号"/>
                </div>
                <ctag:Fold id="divOtherSearch" name="其他查询" foldIconClass="fa-caret-up"></ctag:Fold>
                <div id="divOtherSearch" style="display:none;" class="separate-block clearfix" data-force-fold="true">
                    <div class="col-md-2 form-group">
                        <label>导入属性名1</label>
                        <input id="importAttributeName01" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="导入属性名1"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>导入属性名2</label>
                        <input id="importAttributeName02" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="导入属性名2"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>导入属性名3</label>
                        <input id="importAttributeName03" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="导入属性名3"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>导入属性名4</label>
                        <input id="importAttributeName04" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="导入属性名4"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>导入属性名5</label>
                        <input id="importAttributeName05" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="导入属性名5"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>导入属性值1</label>
                        <input id="importAttributeValue01" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="导入属性值1"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>导入属性值2</label>
                        <input id="importAttributeValue02" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="导入属性值2"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>导入属性值3</label>
                        <input id="importAttributeValue03" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="导入属性值3"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>导入属性值4</label>
                        <input id="importAttributeValue04" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="导入属性值4"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>导入属性值5</label>
                        <input id="importAttributeValue05" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="导入属性值5"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>导入结果</label>
                        <input id="importResult" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="导入结果"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>导入详细结果</label>
                        <input id="importResultDetailMessage" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="导入详细结果"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>备注</label>
                        <input id="remark" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="备注"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.AttachmentImportListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_AttachmentImport}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_AttachmentImport}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_AttachmentImport}"/>
            <%--自定义检索条件设置--%>
            <input type="hidden" id="jsonSearchSetting" value="${jsonSearchSetting_AttachmentImport}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_AttachmentImport}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function AttachmentImportList_Page_Load() {
        SysApp.Sys.AttachmentImportListIns = new SysApp.Sys.AttachmentImportList();
        var instance = SysApp.Sys.AttachmentImportListIns;

        instance.selfInstance = "SysApp.Sys.AttachmentImportListIns";
        instance.controller = "${ctx}/sys/attachmentimport/";
        instance.clientID = "AttachmentImportList";
        instance.tableName = "sys_attachment_import";
        instance.entry = "${entry}";
        instance.pageSize = "${pageSize}";
        instance.detailInstance = SysApp.Sys.AttachmentImportDetailIns;
        instance.filePreviewInstance = SysApp.Cmn.FilePreviewIns;

        instance.init();
    }

    $(function () {
        AttachmentImportList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/sys/attachmentimport/AttachmentImportPopupDetail.jsp" %>
<ctag:SearchSettingPopup pageInstance="SysApp.Sys.AttachmentImportListIns" width="1000px"/>
<ctag:ColumnSettingPopup pageInstance="SysApp.Sys.AttachmentImportListIns" width="1000px"/>
<%--POPUP文件预览控件--%>
<jsp:include page="/WEB-INF/views/cmn/filepreview/FilePreview.jsp"/>