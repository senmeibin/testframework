<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content ResearchInfoCommentDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">调研类别</label>
            <label class="col-sm-9 control-label content-label" id="researchInfoName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">调研标题</label>
            <label class="col-sm-9 control-label content-label" id="researchInfoTitle"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">内容</label>
            <label class="col-sm-9 control-label content-label" id="content" data-textarea="true"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <label class="col-sm-9 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>
    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/pms/researchinfocomment/ResearchInfoCommentDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
        function ResearchInfoCommentDetail_Page_Load() {
            SysApp.Pms.ResearchInfoCommentDetailIns = new SysApp.Pms.ResearchInfoCommentDetail();
            var instance = SysApp.Pms.ResearchInfoCommentDetailIns;
            instance.selfInstance = "SysApp.Pms.ResearchInfoCommentDetailIns";
            instance.clientID = "ResearchInfoCommentDetail";
            instance.isDetail = true;
            instance.entry = "${entry}";
            instance.controller = "${ctx}/pms/researchinfocomment/";
            instance.init();
        }

        ResearchInfoCommentDetail_Page_Load();
    //]]>
</script>