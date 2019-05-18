<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/desktop/workmanual/WorkManualInnerList.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/clipboard/clipboard.min.js"></script>
<div class="row WorkManualInnerList-MainContent">
    <div style="padding-right: 10px;">
        <div class="desktop-search-condition clearfix">
            <form id="MainForm" class="form-horizontal" onsubmit="return false;">
                <input type="hidden" id="uid" data-search-mode="$ignore_search">
                <input type="hidden" id="relationUid" value="${empty workManualRelationUid ? '20180101010000000111000000000001':workManualRelationUid}">
                <div class="col-xs-4" style="padding-left: 0px;">
                    <input type="text" class="form-control" id="fileName" data-title="关键字" placeholder="请输入文件名称" style="width:300px;float:left;">

                    <button type="button" class="btn btn-primary" id="btnSearchFileName">
                        <i class="fa fa-search"></i>
                    </button>
                </div>
            </form>
        </div>
        <div class="iscroll-wrapper desktop-inner-list" data-iscroll-id="iScrollWorkManualInnerList">
            <div>
                <ul class="desktop-list desktop-list-active clear-both">
                    <div id="divList">
                    </div>
                </ul>
            </div>
        </div>
        <div id="divBottomPager">
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function WorkManualInnerList_Page_Load() {
        SysApp.Crm.WorkManualInnerListIns = new SysApp.Crm.WorkManualInnerList();
        var instance = SysApp.Crm.WorkManualInnerListIns;

        instance.selfInstance = "SysApp.Crm.WorkManualInnerListIns";
        instance.controller = "${ctx}/crm/workmanual/";
        instance.clientID = "WorkManualInnerList";
        instance.pageSize = "100";
        instance.init();
    }

    $(function () {
        WorkManualInnerList_Page_Load();
    });
    //]]>
</script>

<%--POPUP文件预览控件--%>
<jsp:include page="/WEB-INF/views/cmn/filepreview/FilePreview.jsp"/>
<%--POPUP附件访问日志一览--%>
<jsp:include page="/WEB-INF/views/sys/attachmentaccesslog/AttachmentAccessLogPopupList.jsp"/>