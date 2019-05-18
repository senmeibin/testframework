<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CompanyDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">单位编号</label>
            <label class="col-sm-4 control-label content-label" id="code"></label>
            <label class="col-sm-2 control-label">单位名称</label>
            <label class="col-sm-4 control-label content-label" id="name"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">简称</label>
            <label class="col-sm-4 control-label content-label" id="abbreviation"></label>
            <label class="col-sm-2 control-label">性质</label>
            <label class="col-sm-4 control-label content-label" id="propertyName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">规模</label>
            <label class="col-sm-4 control-label content-label" id="scaleName"></label>
            <label class="col-sm-2 control-label">地址</label>
            <label class="col-sm-4 control-label content-label" id="address"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">邮编</label>
            <label class="col-sm-4 control-label content-label" id="zipcode"></label>
            <label class="col-sm-2 control-label">电话</label>
            <label class="col-sm-4 control-label content-label" id="telephone"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">传真</label>
            <label class="col-sm-4 control-label content-label" id="fax"></label>
            <label class="col-sm-2 control-label">邮箱</label>
            <label class="col-sm-4 control-label content-label" id="email"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">网址</label>
            <label class="col-sm-4 control-label content-label" id="url"></label>
            <label class="col-sm-2 control-label">所属部门</label>
            <label class="col-sm-4 control-label content-label" id="belongDeptName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">所属业务员</label>
            <label class="col-sm-4 control-label content-label" id="belongUserName"></label>
            <label class="col-sm-2 control-label">联系人</label>
            <label class="col-sm-4 control-label content-label" id="contactName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">附件关联ID</label>
            <label class="col-sm-4 control-label content-label" id="fileRelationUid"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/company/CompanyDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyDetail_Page_Load() {
        SysApp.Sys.CompanyDetailIns = new SysApp.Sys.CompanyDetail();
        var instance = SysApp.Sys.CompanyDetailIns;
        instance.selfInstance = "SysApp.Sys.CompanyDetailIns";
        instance.controller = "${ctx}/sys/company/";
        instance.clientID = "CompanyDetail";
        instance.isDetail = true;
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    CompanyDetail_Page_Load();
    //]]>
</script>