<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content ContactInput-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <div class="form-group" style="display: none">
            <div>
                <input id="uid" type="hidden">
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">姓名</label>
            <div class="col-sm-3">
                <input id="name" type="text" class="form-control required" data-title="姓名" maxlength="32" data-rangelength="[0,32]" style="width: 250px;"/>
            </div>
            <label class="col-sm-2 control-label">手机</label>
            <div class="col-sm-3">
                <input id="mobile" type="text" class="form-control required" data-title="手机" maxlength="32" data-rangelength="[0,32]" style="width: 250px;"/>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">部门</label>
            <div class="col-sm-3">
                <input id="department" type="text" class="form-control" data-title="部门" maxlength="64" data-rangelength="[0,64]" style="width: 250px;"/>
            </div>
            <label class="col-sm-2 control-label">职务</label>
            <div class="col-sm-3">
                <input id="post" type="text" class="form-control" data-title="职务" maxlength="32" data-rangelength="[0,32]" style="width: 250px;"/>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">主要职责</label>
            <div class="col-sm-3">
                <input id="responsibility" type="text" class="form-control" data-title="主要职责" maxlength="64" data-rangelength="[0,64]" style="width: 250px;"/>
            </div>
            <label class="col-sm-2 control-label">邮箱</label>
            <div class="col-sm-3">
                <input id="email" type="text" class="form-control" data-title="邮箱" maxlength="64" data-rangelength="[0,64]" style="width: 250px;"/>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">电话</label>
            <div class="col-sm-3">
                <input id="telephone" type="text" class="form-control" data-title="电话" maxlength="32" data-rangelength="[0,32]" style="width: 250px;"/>
            </div>
            <label class="col-sm-2 control-label">生日</label>
            <div class="col-sm-3">
                <div class="input-group datetime-picker">
                    <input id="birthday" type="text" class="form-control" data-title="生日" maxlength="10" style="width: 150px;"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">家庭地址</label>
            <div class="col-sm-3">
                <input id="homeAddress" type="text" class="form-control" data-title="家庭地址" maxlength="256" data-rangelength="[0,256]" style="width: 250px;"/>
            </div>
            <label class="col-sm-2 control-label">家庭电话</label>
            <div class="col-sm-3">
                <input id="homeTelephone" type="text" class="form-control" data-title="家庭电话" maxlength="32" data-rangelength="[0,32]" style="width: 250px;"/>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">家庭邮编</label>
            <div class="col-sm-3">
                <input id="homeZipcode" type="text" class="form-control" data-title="家庭邮编" maxlength="6" data-rangelength="[0,6]" style="width: 250px;"/>
            </div>
            <label class="col-sm-2 control-label">爱好</label>
            <div class="col-sm-3">
                <input id="interest" type="text" class="form-control" data-title="爱好" maxlength="256" data-rangelength="[0,256]" style="width: 250px;"/>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-2 control-label">是否主要联系人</label>
            <div class="col-sm-3">
                <input id="isMainContacts" type="text" class="form-control" data-title="是否主要联系人" maxlength="5" data-rangelength="[0,5]" data-range="[0,99999]" data-digits="true"
                       style="width: 250px;"/>
            </div>
            <label class="col-sm-2 control-label">备注</label>
            <div class="col-sm-3">
                <textarea id="remark" rows="2" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 250px;"></textarea>
            </div>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Contact}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Contact}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/contact/ContactInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ContactInput_Page_Load() {
        SysApp.Sys.ContactInputIns = new SysApp.Sys.ContactInput();
        var instance = SysApp.Sys.ContactInputIns;
        instance.selfInstance = "SysApp.Sys.ContactInputIns";
        instance.clientID = "ContactInput";
        instance.controller = "${ctx}/sys/contact/";
        instance.companyUid = "${companyUid}";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    ContactInput_Page_Load();
    //]]>
</script>