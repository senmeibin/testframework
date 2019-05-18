<%--文件上传标签--%>
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
<%@ attribute name="uploadButtonIcon" type="java.lang.String" required="false" %>
<%@ attribute name="uploadButtonText" type="java.lang.String" required="false" %>
<%@ attribute name="uploadButtonWidth" type="java.lang.String" required="false" %>
<%@ attribute name="uploadButtonFontSize" type="java.lang.String" required="false" %>
<%@ attribute name="base64ImageFlag" type="java.lang.Boolean" required="false" %>
<!--压缩宽度-->
<%@ attribute name="compressWidth" type="java.lang.Integer" required="false" %>
<!--自定义clientID-->
<%@ attribute name="clientID" type="java.lang.String" required="true" %>
<%--加载延迟时间--%>
<%@ attribute name="loadDelayTime" type="java.lang.Integer" required="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!--上传插件-->
<script src="${staticContentsServer}/static/plugins/localResizeIMG/dist/lrz.all.bundle.js"></script>

<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/fileupload/ImageUploadH5.js?${version}"></script>
<div class="${clientID}-MainContent">
    <a href="#" class="file btn btn-primary"><i class="fa fa-upload"></i>${empty uploadButtonText ? '上传图片' : uploadButtonText}<input type="file" accept="image/*" name="fileName" id="imageUpload" data-allow-clear="false"/></a>
    <img src="" id="${clientID}_imagePath" style="display: none;"/>
    <div style="margin-bottom: 10px; display:${showPromptMsgFlag}">建议上传${smallImageWidth}px*${smallImageHeight}px整数部放大尺寸的图片</div>
    <!--原图-->
    <input id="pictureUrl" type="hidden"/>
    <!--大图-->
    <input id="bigPictureUrl" type="hidden"/>
    <!--小图-->
    <input id="smallPictureUrl" type="hidden"/>
</div>
<script type="text/javascript">
    window.UploadifyUploader = "${ctx}/cmn/fileupload/uploadImages;jsessionid=${pageContext.session.id}";

    function ${clientID}_Page_Load() {
        SysApp.Cmn.${clientID}Ins = new SysApp.Cmn.ImageUploadH5();
        var instance = SysApp.Cmn.${clientID}Ins;
        instance.selfInstance = "SysApp.Cmn.${clientID}Ins";
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
        instance.compressWidth = "${instance}";
        instance.base64ImageFlag = "${base64ImageFlag}";
        instance.uploadButtonIcon = "${empty uploadButtonIcon ? 'fa fa-cloud-upload' : uploadButtonIcon}";
        instance.uploadButtonText = "${empty uploadButtonText ? '上传图片' : uploadButtonText}";
        instance.uploadButtonWidth = "${empty uploadButtonWidth ? '100' : uploadButtonWidth}";
        instance.loadDelayTime = ${empty loadDelayTime ? 500 : loadDelayTime};
        instance.init();
    }

    ${clientID}_Page_Load();
</script>
<style>
    .file {
        position: relative;
        display: inline-block;
        color: #fff;
        background: #09c;
        overflow: hidden;
        font-size: ${empty uploadButtonFontSize ? '12px' : uploadButtonFontSize};
        width: ${empty uploadButtonWidth ? '100' : uploadButtonWidth}px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        cursor: pointer;
        padding-top: 0px;
        padding-left: 8px;
    }

    .file input {
        position: absolute;
        right: 0;
        top: 0;
        opacity: 0;
        cursor: pointer;
    }
</style>