<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/attachment/AttachmentList.js?${version}"></script>

<title>附件文件一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper AttachmentList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">文件上传附件一览</div>
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
                    <label>文件名称</label>
                    <input id="fileName" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="文件名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>文件路径</label>
                    <input id="filePath" data-alias-table="main" type="text" maxlength="512" class="form-control" data-title="文件路径"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>上传者</label>
                    <input id="userName" data-alias-table="insert_user" type="text" maxlength="32" class="form-control" data-title="上传者"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>上传日期(起)</label>
                    <div class='input-group datetime-picker'>
                        <input id="insertDate$from_search" data-alias-table="main" type='text' class="form-control" data-title="上传日期(起)"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
                <div class="col-md-2 form-group">
                    <label>上传日期(止)</label>
                    <div class='input-group datetime-picker'>
                        <input id="insertDate$to_search" data-alias-table="main" type='text' class="form-control" data-title="上传日期(止)"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.AttachmentListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Attachment}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Attachment}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Attachment}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function AttachmentList_Page_Load() {
        SysApp.Sys.AttachmentListIns = new SysApp.Sys.AttachmentList();
        var instance = SysApp.Sys.AttachmentListIns;

        instance.selfInstance = "SysApp.Sys.AttachmentListIns";
        instance.controller = "${ctx}/sys/attachment/";
        instance.inputUrl = "${ctx}/sys/attachment/input";
        instance.clientID = "AttachmentList";
        instance.tableName = "sys_attachment";
        instance.inputInstance = SysApp.Sys.AttachmentInputIns;
        instance.detailInstance = SysApp.Sys.AttachmentDetailIns;
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.filePreviewInstance = SysApp.Cmn.FilePreviewIns;
        instance.init();
    }

    $(function () {
        AttachmentList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑控件--%>
<%@ include file="/WEB-INF/views/sys/attachment/AttachmentPopupInput.jsp" %>
<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/sys/attachment/AttachmentPopupDetail.jsp" %>
<%--POPUP文件预览控件--%>
<jsp:include page="/WEB-INF/views/cmn/filepreview/FilePreview.jsp"/>