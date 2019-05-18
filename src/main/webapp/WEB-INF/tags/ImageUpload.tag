<%--文件上传标签 用户同一个form中引用多个上传控件--%>
<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="relationUid" type="java.lang.String" required="true" %>
<%@ attribute name="parentInstance" type="java.lang.String" required="true" %>
<%@ attribute name="appCode" type="java.lang.String" required="false" %>
<%@ attribute name="moduleName" type="java.lang.String" required="false" %>
<%@ attribute name="fileTypeExts" type="java.lang.String" required="false" %>
<%@ attribute name="fileTypeDesc" type="java.lang.String" required="false" %>
<%@ attribute name="fileSizeLimit" type="java.lang.String" required="false" %>
<%@ attribute name="bigImageWidth" type="java.lang.Integer" required="false" %>
<%@ attribute name="bigImageHeight" type="java.lang.Integer" required="false" %>
<%@ attribute name="smallImageWidth" type="java.lang.Integer" required="false" %>
<%@ attribute name="smallImageHeight" type="java.lang.Integer" required="false" %>
<%@ attribute name="multiUpload" type="java.lang.Boolean" required="false" %>
<%@ attribute name="showPromptMsgFlag" type="java.lang.String" required="false" %>
<%@ attribute name="previewFlag" type="java.lang.Boolean" required="false" %>
<%@ attribute name="clientID" type="java.lang.String" required="true" %>
<%@ attribute name="uploadButtonIcon" type="java.lang.String" required="false" %>
<%@ attribute name="uploadButtonText" type="java.lang.String" required="false" %>
<%@ attribute name="uploadButtonWidth" type="java.lang.String" required="false" %>
<%@ attribute name="uploadButtonFontSize" type="java.lang.String" required="false" %>
<%--加载延迟时间--%>
<%@ attribute name="loadDelayTime" type="java.lang.Integer" required="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/uploadify/uploadify.css?${version}"/>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/uploadify/jquery.uploadify.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/fileupload/ImageUpload.js?${version}"></script>

<div class="${clientID}-MainContent">
    <input type="file" name="fileName" id="file_upload"/>

    <div id="fileQueue"></div>
    <img src="" id="${clientID}_imagePath" style="display: none;"/>
    <div style="margin-bottom: 10px; display:${showPromptMsgFlag}">建议上传${smallImageWidth}px*${smallImageHeight}px整数部放大尺寸的图片</div>
    <!--原图-->
    <input id="${clientID}_pictureUrl" type="hidden"/>
    <!--大图-->
    <input id="${clientID}_bigPictureUrl" type="hidden"/>
    <!--小图-->
    <input id="${clientID}_smallPictureUrl" type="hidden"/>
</div>

<script type="text/javascript">
    window.UploadifySwfPath = "${ctx}/static/plugins/uploadify/uploadify.swf";
    window.UploadifyUploader = "${ctx}/cmn/fileupload/uploadImages;jsessionid=${pageContext.session.id}";

    function ${clientID}_Page_Load() {
        SysApp.Cmn.${clientID}SpecialIns = new SysApp.Cmn.ImageUpload();
        var instance = SysApp.Cmn.${clientID}SpecialIns;
        instance.selfInstance = "SysApp.Cmn.${clientID}SpecialIns";
        instance.parentInstance = "${parentInstance}";
        instance.clientID = "${clientID}";
        instance.fileTypeExts = "${fileTypeExts}";
        instance.fileTypeDesc = "${fileTypeDesc}";
        instance.fileSizeLimit = "${fileSizeLimit}";
        instance.bigImageWidth = "${bigImageWidth}";
        instance.bigImageHeight = "${bigImageHeight}";
        instance.smallImageWidth = "${smallImageWidth}";
        instance.smallImageHeight = "${smallImageHeight}";
        instance.multiUpload = "${multiUpload}";
        instance.appCode = "${appCode}";
        instance.moduleName = "${moduleName}";
        instance.relationUid = "${relationUid}";
        instance.previewFlag = "${previewFlag}";
        instance.uploadButtonIcon = "${empty uploadButtonIcon ? 'fa fa-cloud-upload' : uploadButtonIcon}";
        instance.uploadButtonText = "${empty uploadButtonText ? '上传图片' : uploadButtonText}";
        instance.uploadButtonWidth = "${empty uploadButtonWidth ? '100' : uploadButtonWidth}";
        instance.loadDelayTime = ${empty loadDelayTime ? 500 : loadDelayTime};
        instance.init();
    }

    ${clientID}_Page_Load();
</script>

<style>
    .upload-image-button {
        margin-top: 10px;
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
        font: ${empty uploadButtonFontSize ? '14px' : uploadButtonFontSize} "Microsoft YaHei", sans-serif;
        text-align: center;
        text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
        width: 100%;
    }

    .uploadify:hover .upload-image-button {
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