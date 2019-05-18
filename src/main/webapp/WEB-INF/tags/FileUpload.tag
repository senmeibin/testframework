<%--文件上传标签--%>
<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="relationUid" type="java.lang.String" required="true" %>
<%--参照UID集合(查看主表数据以外的关联附件文件，关联附件文件不可编辑与删除)--%>
<%@ attribute name="referenceUids" type="java.lang.String" required="false" %>
<%@ attribute name="appCode" type="java.lang.String" required="true" %>
<%@ attribute name="moduleName" type="java.lang.String" required="true" %>
<%@ attribute name="panelTitle" type="java.lang.String" required="false" %>
<%@ attribute name="hidePanelTitle" type="java.lang.Boolean" required="false" %>
<%@ attribute name="fileTypeExts" type="java.lang.String" required="false" %>
<%@ attribute name="fileTypeDesc" type="java.lang.String" required="false" %>
<%@ attribute name="fileSizeLimit" type="java.lang.String" required="false" %>
<%@ attribute name="allowDownload" type="java.lang.Boolean" required="false" %>
<%@ attribute name="useInPopup" type="java.lang.Boolean" required="false" %>
<%-- 是否允许上传人以外编辑（删除）附件--%>
<%@ attribute name="allowOtherUserEdit" type="java.lang.String" required="false" %>
<%-- 是否允许编辑或者删除指定日以前的附件（默认不传，允许编辑， 如果传就是天数）--%>
<%@ attribute name="allowEditFileDays" type="java.lang.String" required="false" %>
<%-- 附件查询模块限定，为了不影响原有功能，不利用moduleName，如果需要指定模块查询，使用此属性--%>
<%@ attribute name="searchModuleName" type="java.lang.String" required="false" %>
<%-- 附件分类（同一个关联UID多种附件时使用，默认不传入为空）--%>
<%@ attribute name="categoryCd" type="java.lang.String" required="false" %>

<%@ attribute name="maxFileCount" type="java.lang.Integer" required="false" %>
<%@ attribute name="bigImageWidth" type="java.lang.Integer" required="false" %>
<%@ attribute name="bigImageHeight" type="java.lang.Integer" required="false" %>
<%@ attribute name="smallImageWidth" type="java.lang.Integer" required="false" %>
<%@ attribute name="smallImageHeight" type="java.lang.Integer" required="false" %>
<%@ attribute name="multiUpload" type="java.lang.Boolean" required="false" %>
<%@ attribute name="autoSearch" type="java.lang.Boolean" required="false" %>

<%@ attribute name="versionNumber" type="java.lang.String" required="false" %>
<%@ attribute name="fileTableName" type="java.lang.String" required="false" %>
<%@ attribute name="isReadonly" type="java.lang.String" required="false" %>
<%@ attribute name="showEditButton" type="java.lang.String" required="false" %>
<%@ attribute name="showDeleteButton" type="java.lang.String" required="false" %>

<%@ attribute name="enableContentMinHeight" type="java.lang.Boolean" required="false" %>
<%@ attribute name="disableMarginTopSpace" type="java.lang.Boolean" required="false" %>
<%@ attribute name="uploadButtonText" type="java.lang.String" required="false" %>
<%@ attribute name="uploadButtonWidth" type="java.lang.String" required="false" %>
<%@ attribute name="showRemarkColumn" type="java.lang.String" required="false" %>
<%@ attribute name="isThumbnail" type="java.lang.String" required="false" %>
<%--提示标题--%>
<%@ attribute name="helpInfoTitle" type="java.lang.String" required="false" %>
<%--提示信息 多条用半角分号（;）拼接--%>
<%@ attribute name="helpInfoMessages" type="java.lang.String" required="false" %>
<%--实例名称（多实例时请使用不同的实例名称）--%>
<%@ attribute name="clientID" type="java.lang.String" required="true" %>
<%--调用文件上传用tag 的js 实例名称 --%>
<%@ attribute name="callInstance" type="java.lang.String" required="false" %>
<%--加载延迟时间--%>
<%@ attribute name="loadDelayTime" type="java.lang.Integer" required="false" %>
<%--显示简单列表(true的场合【文件分类】及【文件大小】列将不显示，默认false)--%>
<%@ attribute name="showSimpleList" type="java.lang.Boolean" required="false" %>
<%@ attribute name="showPackDownload" type="java.lang.Boolean" required="false" %>
<%@ attribute name="showOperationColumn" type="java.lang.Boolean" required="false" %>
<%@ attribute name="showOperationIcon" type="java.lang.Boolean" required="false" %>
<%@ attribute name="isInitUploadify" type="java.lang.Boolean" required="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="clientIdWithCategory" value="${clientID}${categoryCd}"/>

<link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/uploadify/uploadify.css?${version}"/>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/uploadify/jquery.uploadify.min.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/fileupload/FileUploadList.js?${version}"></script>

<c:if test="${empty useInPopup}">
    <%--POPUP编辑控件--%>
    <jsp:include page="/WEB-INF/views/cmn/fileupload/FileUploadPopupInput.jsp"/>
    <%--POPUP编辑控件--%>
    <jsp:include page="/WEB-INF/views/cmn/filepreview/FilePreview.jsp"/>
    <%--POPUP附件访问日志一览--%>
    <jsp:include page="/WEB-INF/views/sys/attachmentaccesslog/AttachmentAccessLogPopupList.jsp"/>
</c:if>

<div class="${clientIdWithCategory}-MainContent ${disableMarginTopSpace ? '' : 'margin-top-space'}">
    <div class="panel ${enableContentMinHeight ? 'content-min-height' : ''}">
        <div class="panel-heading" style="display: <c:if test="${hidePanelTitle}">none</c:if>;">
            <em></em>
            <div class="panel-title">${empty panelTitle ? '附件文件一览' : panelTitle}</div>
        </div>

        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="margin-top-space" style="display: none;">
                <input id="relationUid" data-alias-table="main" type="hidden" data-search-mode="in" value="${relationUid}<c:if test="${!empty referenceUids}">,${referenceUids}</c:if>" data-allow-clear="false"/>
                <input id="categoryCd" data-alias-table="main" type="hidden" data-search-mode="=" value="${categoryCd}" data-allow-clear="false"/>
                <c:if test="${not empty searchModuleName}">
                    <input id="moduleName" data-alias-table="main" type="hidden" data-search-mode="=" value="${searchModuleName}" data-allow-clear="false"/>
                </c:if>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Attachment}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Attachment}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Attachment}"/>
            <div class="attachment-control-area">
                <div style="float: left; width: 105px;" class="upload-button-container">
                    <input type="file" name="fileName" id="file_upload"/>
                </div>
                <div style="float: left; width:500px;padding-top: 5px;" class="upload-button-container">
                    <i class="fa fa-star-o fa-lg fa-red"></i>
                    <c:choose>
                        <c:when test="${not empty maxFileCount}">
                            最多上传${maxFileCount}个附件文件；
                        </c:when>
                        <c:otherwise>
                            一次可上传多个附件文件；
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${not empty fileTypeExts}">
                            仅支持${fileTypeExts}文档上传。
                        </c:when>
                        <c:otherwise>
                            支持Word、Excel、PDF、图像等文档上传。
                        </c:otherwise>
                    </c:choose>
                </div>
                <div style="float: right;margin-bottom: 10px;" id="divPackDownload">
                    <button type="button" class="btn btn-primary" id="btnPackDownload"><i class="fa fa-download"></i>打包下载</button>
                </div>
                <div style="clear: both;">
                </div>
                <div id="fileQueue" class="upload-button-container"></div>
            </div>
            <div id="divFileMaxMessage" style="display:none;" class="flash-error">您上传的文件已达到最大个数限制，禁止继续上传。</div>
            <div id="divList" style="width: 100%">
            </div>
            <!--所需附件说明-->
            <c:if test="${not empty helpInfoMessages}">
                <c:set value="${fn:split(helpInfoMessages, ';') }" var="helpInfoMessages"/>
                <div class="help-info prompt-open" id="promptFold">
                    <label><i class="fa fa-hand-pointer-o"></i><c:if test="${not empty helpInfoTitle}">${helpInfoTitle}</c:if><c:if test="${empty helpInfoTitle}">所需附件</c:if></label>
                    <ol>
                        <c:forEach items="${ helpInfoMessages }" var="helpInfoMessage">
                            <li class="weight" style="color: red;">
                                    ${helpInfoMessage}；
                            </li>
                        </c:forEach>
                    </ol>
                </div>
            </c:if>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    window.UploadifySwfPath = "${ctx}/static/plugins/uploadify/uploadify.swf";
    window.UploadifyUploader = "${ctx}/cmn/fileupload/uploading;jsessionid=${pageContext.session.id}";

    function ${clientIdWithCategory}List_Page_Load() {
        SysApp.Cmn.${clientIdWithCategory}ListIns = new SysApp.Cmn.FileUploadList();
        var instance = SysApp.Cmn.${clientIdWithCategory}ListIns;

        instance.selfInstance = "SysApp.Cmn.${clientIdWithCategory}ListIns";
        instance.controller = "${ctx}/cmn/fileupload/";
        instance.clientID = "${clientIdWithCategory}";
        instance.tableName = "sys_attachment";
        instance.showRecordInfo = false;
        instance.fileTypeExts = "${fileTypeExts}";
        instance.fileTypeDesc = "${fileTypeDesc}";
        instance.fileSizeLimit = "${fileSizeLimit}";
        instance.multiUpload = "${multiUpload}";
        instance.bigImageWidth = "${bigImageWidth}";
        instance.bigImageHeight = "${bigImageHeight}";
        instance.smallImageWidth = "${smallImageWidth}";
        instance.smallImageHeight = "${smallImageHeight}";
        instance.maxFileCount = "${maxFileCount}";
        instance.appCode = "${appCode}";
        instance.moduleName = "${moduleName}";
        instance.relationUid = "${relationUid}";
        //附件分类 （同一个关联UID 多种附件时使用， 默认不传入为空）
        instance.categoryCd = "${categoryCd}";
        instance.referenceUids = "${referenceUids}";
        instance.isReadonly = "${isReadonly}";
        instance.showEditButton = "${showEditButton}";
        instance.showDeleteButton = "${showDeleteButton}";
        instance.uploadButtonText = "${uploadButtonText}";
        instance.uploadButtonWidth = "${uploadButtonWidth}";
        //默认显示备注（如果参数传入则以传入参数为准）
        instance.showRemarkColumn = ${empty showRemarkColumn ? true : showRemarkColumn};
        instance.allowDownload = "${allowDownload}";
        instance.allowOtherUserEdit = "${allowOtherUserEdit}";
        instance.allowEditFileDays = "${allowEditFileDays}";

        instance.inputInstance = SysApp.Cmn.FileUploadInputIns;
        instance.filePreviewIns = SysApp.Cmn.FilePreviewIns;
        instance.callInstance = "${callInstance}";
        //是否显示缩略图
        instance.isThumbnail = "${isThumbnail}";
        instance.autoSearch = ${empty autoSearch ? "true" : autoSearch};
        instance.showPackDownload = ${empty showPackDownload ? "true" : showPackDownload};
        instance.showOperationColumn = ${empty showOperationColumn ? "true" : showOperationColumn};
        instance.showOperationIcon = ${empty showOperationIcon ? "false" : showOperationIcon};
        instance.loadDelayTime = ${empty loadDelayTime ? 500 : loadDelayTime};
        instance.showSimpleList = ${empty showSimpleList ? false : showSimpleList};
        instance.isInitUploadify = ${empty isInitUploadify ? true : isInitUploadify};

        instance.init();
    }

    ${clientIdWithCategory}List_Page_Load();
    //]]>
</script>

<style>
    .upload-file-button {
        background-repeat: no-repeat;
        background-color: #337AB7;
        background-image: linear-gradient(bottom, #337AB7 0%, #337AB7 100%);
        background-image: -o-linear-gradient(bottom, #337AB7 0%, #337AB7 100%);
        background-image: -moz-linear-gradient(bottom, #337AB7 0%, #337AB7 100%);
        background-image: -webkit-linear-gradient(bottom, #337AB7 0%, #337AB7 100%);
        background-image: -ms-linear-gradient(bottom, #337AB7 0%, #337AB7 100%);
        background-image: -webkit-gradient(
                linear,
                left bottom,
                left top,
                color-stop(0, #337AB7),
                color-stop(1, #337AB7)
        );
        background-position: center bottom;
        background-repeat: no-repeat;
        -webkit-border-radius: 0px;
        -moz-border-radius: 0px;
        border-radius: 0px;
        border: 0px solid #658AA4;
        color: #FFF;
        font: 14px "Microsoft YaHei", sans-serif;
        text-align: center;
        text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
        width: 100%;
    }

    .uploadify:hover .upload-file-button {
        background-color: #658AA4;
        background-image: linear-gradient(top, #658AA4 0%, #658AA4 100%);
        background-image: -o-linear-gradient(top, #658AA4 0%, #658AA4 100%);
        background-image: -moz-linear-gradient(top, #658AA4 0%, #658AA4 100%);
        background-image: -webkit-linear-gradient(top, #658AA4 0%, #658AA4 100%);
        background-image: -ms-linear-gradient(top, #658AA4 0%, #658AA4 100%);
        background-image: -webkit-gradient(
                linear,
                left bottom,
                left top,
                color-stop(0, #658AA4),
                color-stop(1, #658AA4)
        );
        background-position: center bottom;
    }
</style>