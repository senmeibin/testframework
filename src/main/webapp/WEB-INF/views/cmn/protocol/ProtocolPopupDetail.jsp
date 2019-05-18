<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content ProtocolDetail-MainContent" style="width: 1200px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-lg-1 control-label">协议名称</label>
            <label class="col-lg-10 control-label content-label" id="name"></label>
        </div>
        <div class="form-group form-inline">
            <label class="col-lg-1 control-label">协议分类</label>
            <label class="col-lg-10 control-label content-label" id="categoryName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-1 control-label">协议版本</label>
            <label class="col-lg-10 control-label content-label" id="version"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-1 control-label">使用状态</label>
            <label class="col-lg-10 control-label content-label" id="useStatusName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-1 control-label">有效日期</label>
            <label class="col-lg-10 control-label content-label"><span id="startDate" data-date="true"></span>
                ~<span id="endDate" data-date="true"></span>
            </label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-1 control-label">协议内容</label>
            <div class="col-lg-11">
                <iframe id="contentIframe" style="width: 100%;height: 460px;border: none;"></iframe>
            </div>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/protocol/ProtocolDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ProtocolDetail_Page_Load() {
        SysApp.Cmn.ProtocolDetailIns = new SysApp.Cmn.ProtocolDetail();
        var instance = SysApp.Cmn.ProtocolDetailIns;
        instance.selfInstance = "SysApp.Cmn.ProtocolDetailIns";
        instance.clientID = "ProtocolDetail";
        instance.isDetail = true;
        instance.entry = "${entry}";
        instance.controller = "${ctx}/cmn/protocol/";
        instance.init();
    }

    ProtocolDetail_Page_Load();
    //]]>
</script>