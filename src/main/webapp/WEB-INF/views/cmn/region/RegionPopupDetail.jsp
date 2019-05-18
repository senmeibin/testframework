<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content RegionDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">区域名称</label>
            <label class="col-sm-9 control-label content-label" id="regionName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">父节点</label>
            <label class="col-sm-9 control-label content-label" id="parentUid"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">区域路径</label>
            <label class="col-sm-9 control-label content-label" id="regionPath"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">区域层级</label>
            <label class="col-sm-9 control-label content-label" id="regionGrade"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">表示顺序</label>
            <label class="col-sm-9 control-label content-label" id="dispSeq"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/region/RegionDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function RegionDetail_Page_Load() {
        SysApp.Cmn.RegionDetailIns = new SysApp.Cmn.RegionDetail();
        var instance = SysApp.Cmn.RegionDetailIns;
        instance.selfInstance = "SysApp.Cmn.RegionDetailIns";
        instance.clientID = "RegionDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/cmn/region/";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    RegionDetail_Page_Load();
    //]]>
</script>