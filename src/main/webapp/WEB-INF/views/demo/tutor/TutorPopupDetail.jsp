<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content TutorDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">姓名</label>
            <label class="col-lg-4 control-label content-label" id="tutorName"></label>
            <label class="col-lg-1 control-label">单位</label>
            <label class="col-lg-4 control-label content-label" id="tutorUnit"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">职务</label>
            <label class="col-lg-4 control-label content-label" id="tutorPosition"></label>
            <label class="col-lg-1 control-label">联系方法</label>
            <label class="col-lg-4 control-label content-label" id="contactInfo"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-2 control-label">方向</label>
            <label class="col-lg-4 control-label content-label" id="direction"></label>
            <label class="col-lg-1 control-label">备注</label>
            <label class="col-lg-4 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/tutor/TutorDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function TutorDetail_Page_Load() {
        SysApp.Demo.TutorDetailIns = new SysApp.Demo.TutorDetail();
        var instance = SysApp.Demo.TutorDetailIns;
        instance.selfInstance = "SysApp.Demo.TutorDetailIns";
        instance.clientID = "TutorDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/demo/tutor/";
        instance.init();
    }

    TutorDetail_Page_Load();
    //]]>
</script>