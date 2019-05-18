<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content SignCompanyInput-MainContent" style="width: 800px; display: none;">
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
            <label class="col-sm-3 control-label">公司名称</label>
            <div class="col-sm-9">
                <input id="companyName" type="text" class="form-control required duplication" data-title="公司名称" maxlength="64" data-rangelength="[0,64]" style="width: 350px;"/>
                <label id="companyName_Error" class="validator-error"></label>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">公司地址</label>
            <div class="col-sm-9">
                <input id="companyAddr" type="text" class="form-control required" data-title="公司地址" maxlength="128" data-rangelength="[0,128]" style="width: 350px;"/>
                <label id="companyAddr_Error" class="validator-error"></label>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">公司电话</label>
            <div class="col-sm-9">
                <input id="companyTelephone" type="text" class="form-control required" data-title="公司电话" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                <label id="companyTelephone_Error" class="validator-error"></label>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">开户行</label>
            <div class="col-sm-9">
                <input id="bankName" type="text" class="form-control required" data-title="开户行" maxlength="64" data-rangelength="[0,64]" style="width: 350px;"/>
                <label id="bankName_Error" class="validator-error"></label>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">账号</label>
            <div class="col-sm-9">
                <input id="bankNumber" type="text" class="form-control required" data-title="账号" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                <label id="bankNumber_Error" class="validator-error"></label>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">纳税人识别号</label>
            <div class="col-sm-9">
                <input id="identificationNumber" type="text" class="form-control" data-title="纳税人识别号" maxlength="64" data-rangelength="[0,64]" style="width: 350px;"/>
                <label id="identificationNumber_Error" class="validator-error"></label>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">开票分公司</label>
            <div class="col-sm-9">
                <ctag:ComboTree valueDomId="invoiceCompanyUid" textDomId="invoiceCompanyName" parentInstance="SysApp.Cmn.SignCompanyInputIns" dataTitle="开票分公司" style="width:310px;"
                                idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dispName="deptFullName" required="required" dataUrl="${ctx}/sys/dept/getAllCompanyList" selectParent="false"/>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">公司编码</label>
            <div class="col-sm-9">
                <input id="companyCode" type="text" class="form-control" data-title="公司编码" maxlength="64" data-rangelength="[0,32]" style="width: 350px;"/>
                <label id="companyCode_Error" class="validator-error"></label>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">公司编号</label>
            <div class="col-sm-9">
                <input id="companyNumber" type="text" class="form-control" data-title="公司编号" maxlength="64" data-rangelength="[0,32]" style="width: 350px;"/>
                <label id="companyNumber_Error" class="validator-error"></label>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">表示顺序</label>
            <div class="col-sm-9">
                <input id="dispSeq" type="text" class="form-control" data-title="表示顺序" maxlength="5" data-rangelength="[0,5]" data-range="[0,99999]" data-digits="true" style="width: 350px;"/>
                <label id="dispSeq_Error" class="validator-error"></label>
            </div>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_SignCompany}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_SignCompany}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/signcompany/SignCompanyInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function SignCompanyInput_Page_Load() {
        SysApp.Cmn.SignCompanyInputIns = new SysApp.Cmn.SignCompanyInput();
        var instance = SysApp.Cmn.SignCompanyInputIns;
        instance.selfInstance = "SysApp.Cmn.SignCompanyInputIns";
        instance.controller = "${ctx}/cmn/signcompany/";
        instance.clientID = "SignCompanyInput";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    SignCompanyInput_Page_Load();
    //]]>
</script>