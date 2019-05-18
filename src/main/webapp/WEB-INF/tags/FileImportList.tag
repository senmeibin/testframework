<%--数据导入文件列表标签--%>
<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%@ attribute name="appCode" type="java.lang.String" required="true" %>
<%@ attribute name="moduleName" type="java.lang.String" required="true" %>
<%@ attribute name="clientID" type="java.lang.String" required="false" %>
<%@ attribute name="tagWidth" type="java.lang.String" required="false" %>
<%@ attribute name="showSearchCondition" type="java.lang.Boolean" required="false" %>
<%@ attribute name="buttonStyle" type="java.lang.String" required="false" %>
<%@ attribute name="buttonText" type="java.lang.String" required="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/sys/Common.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/fileimport/FileImportList.js?${version}"></script>

<button id="FileImportList${clientID}_btnShowFileImportList" type="button" class="btn btn-default" style="${buttonStyle}">
    <i class="fa fa-list"></i>${empty buttonText ? "查看数据导入日志" : buttonText}
</button>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content FileImportList${clientID}-MainContent" style="width: ${empty tagWidth ? '1300px' : tagWidth}; display: none;">
    <ctag:ModalHeader></ctag:ModalHeader>

    <div class="modal-body">
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm" style="display: ${showSearchCondition ? '' : 'none'};">
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
            <div class="col-md-2 form-group">
                <label>导入文件名</label>
                <input id="fileName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="导入文件名"/>
            </div>
            <div class="col-md-4 form-group">
                <label>&nbsp;</label>
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
            </div>

            <div class="clear-both dashed-line" style="display: ${showSearchCondition ? '' : 'none'};">
            </div>
        </form>
        <div id="divList" style="width: 100%">
        </div>
    </div>

    <ctag:ModalFooter></ctag:ModalFooter>
</div>

<%--POPUP编辑控件--%>
<jsp:include page="/WEB-INF/views/cmn/filepreview/FilePreview.jsp"/>
<%--POPUP详细--%>
<jsp:include page="/WEB-INF/views/sys/attachmentimport/AttachmentImportPopupDetail.jsp"/>

<script type="text/javascript">
    //<![CDATA[
    function FileImportList${clientID}_Page_Load() {
        SysApp.Cmn.FileImportList${clientID}Ins = new SysApp.Cmn.FileImportList();
        var instance = SysApp.Cmn.FileImportList${clientID}Ins;

        instance.selfInstance = "SysApp.Cmn.FileImportList${clientID}Ins";
        instance.controller = "${ctx}/cmn/fileimport/";
        instance.clientID = "FileImportList${clientID}";
        instance.tableName = "sys_attachmentimport";
        instance.showRecordInfo = false;
        instance.appCode = "${appCode}";
        instance.moduleName = "${moduleName}";
        instance.showSearchCondition = ${empty showSearchCondition ? false : showSearchCondition};
        instance.detailInstance = SysApp.Sys.AttachmentImportDetailIns;
        instance.filePreviewInstance = SysApp.Cmn.FilePreviewIns;
        instance.init();
    }

    FileImportList${clientID}_Page_Load();
    //]]>
</script>
