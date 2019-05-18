<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="FilePreview_ctlFrame" class="modal-content FilePreview-MainContent" style="width: 1250px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div id="viewerPlaceHolder"></div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>

<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/filepreview/FilePreview.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function FilePreview_Page_Load() {
        SysApp.Cmn.FilePreviewIns = new SysApp.Cmn.FilePreview();
        var instance = SysApp.Cmn.FilePreviewIns;
        instance.selfInstance = "SysApp.Cmn.FilePreviewIns";
        instance.clientID = "FilePreview";
        instance.controller = "${ctx}/cmn/filepreview/";
        instance.isEnable = ("${attachmentPreviewEnable}" === "true");
        instance.officeOnlineServerDomain = "${officeOnlineServerDomain}";
        instance.init();
    }

    FilePreview_Page_Load();
    //]]>
</script>