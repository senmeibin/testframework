<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content ContactDetail-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">来往单位</label>
            <label class="col-sm-4 control-label content-label" id="companyName"></label>
            <label class="col-sm-2 control-label">姓名</label>
            <label class="col-sm-4 control-label content-label" id="name"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">部门</label>
            <label class="col-sm-4 control-label content-label" id="department"></label>
            <label class="col-sm-2 control-label">职务</label>
            <label class="col-sm-4 control-label content-label" id="post"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">主要职责</label>
            <label class="col-sm-4 control-label content-label" id="responsibility"></label>
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
            <label class="col-sm-2 control-label">手机</label>
            <label class="col-sm-4 control-label content-label" id="mobile"></label>
            <label class="col-sm-2 control-label">生日</label>
            <label class="col-sm-4 control-label content-label" id="birthday" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">家庭地址</label>
            <label class="col-sm-4 control-label content-label" id="homeAddress"></label>
            <label class="col-sm-2 control-label">家庭电话</label>
            <label class="col-sm-4 control-label content-label" id="homeTelephone"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">家庭邮编</label>
            <label class="col-sm-4 control-label content-label" id="homeZipcode"></label>
            <label class="col-sm-2 control-label">爱好</label>
            <label class="col-sm-4 control-label content-label" id="interest"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">是否主要联系人</label>
            <label class="col-sm-4 control-label content-label" id="isMainContacts"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/contact/ContactDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ContactDetail_Page_Load() {
        SysApp.Sys.ContactDetailIns = new SysApp.Sys.ContactDetail();
        var instance = SysApp.Sys.ContactDetailIns;
        instance.selfInstance = "SysApp.Sys.ContactDetailIns";
        instance.controller = "${ctx}/sys/contact/";
        instance.clientID = "ContactDetail";
        instance.isDetail = true;
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    ContactDetail_Page_Load();
    //]]>
</script>