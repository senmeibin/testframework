<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/resourcesdockingrecord/ResourcesDockingRecordInput.js?${version}"></script>

<title>入孵企业资源对接记录编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height">
        <div class="panel-heading ResourcesDockingRecordInput-MainContent">
            <em></em>
            <div class="panel-title" id="ctlTitle">入孵企业资源对接记录编辑</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <%@ include file="/WEB-INF/views/demo/companyinformation/CompanyInformationInnerDetail.jsp" %>
            <div class="ResourcesDockingRecordInput-MainContent margin-top-space">
                <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
                <form id="MainForm" class="form-horizontal">
                    <input id="uid" type="hidden">
                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">对接时间</label>
                        <div class="col-lg-4">
                            <ctag:CalendarSelect id="dockingDate" title="对接时间" showValidateError="true" width="181px" required="required"></ctag:CalendarSelect>
                        </div>

                        <label class="col-lg-1 control-label">数量</label>
                        <div class="col-lg-4">
                            <input id="quantity" type="text" class="form-control" data-title="数量" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                            <label id="quantity_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">对接人</label>
                        <div class="col-lg-4">
                            <ctag:ComboTree valueDomId="dockingPersonUid" textDomId="dockingPersonName" parentInstance="SysApp.Demo.ResourcesDockingRecordInputIns" dataTitle="对接人" required="required"
                                            idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dispName="deptName" style="width:181px" dataUrl="${ctx}/demo/user/getAllUserTree"/>
                        </div>
                        <label class="col-lg-1 control-label">对接机构</label>
                        <div class="col-lg-4 select2-required">
                            <ctag:Select2 selectId="thirdPartyServiceUid" dataTitle="对接机构" required="required" style="width:220px"/>
                            <label id="thirdPartyServiceUid_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">对接机构联系人</label>
                        <div class="col-lg-4">
                            <ctag:ComboTree valueDomId="thirdPartyServiceContactsUid" textDomId="thirdPartyServiceName" parentInstance="SysApp.Demo.ResourcesDockingRecordInputIns" dataTitle="对接机构联系人" parentDomId="thirdPartyServiceUid" parentDataTitle="对接机构"
                                            idKey="uid" parentIdKey="thirdPartyServiceUid" nodeName="contactName" dispName="contactName" style="width:181px" dataUrl="${ctx}/demo/thirdpartyservicecontacts/getContactsTree"/>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">对接内容</label>
                        <div class="col-lg-8">
                            <textarea id="dockingContent" rows="5" class="form-control" data-title="对接内容" data-rangelength="[0,512]" style="width: 98%;"></textarea>
                            <label id="dockingContent_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">跟踪</label>
                        <div class="col-lg-8">
                            <textarea id="following" rows="5" class="form-control" data-title="跟踪" data-rangelength="[0,512]" style="width: 98%;"></textarea>
                            <label id="following_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">备注</label>
                        <div class="col-lg-8">
                            <textarea id="remark" rows="5" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 98%;"></textarea>
                            <label id="remark_Error" class="validator-error"></label>
                        </div>
                    </div>
                </form>

                <div class="text-center">
                    <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                    <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                    <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
                </div>

                <%--原始对象Json字符串[实体属性校验用]--%>
                <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_ResourcesDockingRecord}"/>

                <%--下拉框选项数据集合--%>
                <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ResourcesDockingRecord}"/>
            </div>

        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ResourcesDockingRecordInput_Page_Load() {
        SysApp.Demo.ResourcesDockingRecordInputIns = new SysApp.Demo.ResourcesDockingRecordInput();
        var instance = SysApp.Demo.ResourcesDockingRecordInputIns;
        instance.selfInstance = "SysApp.Demo.ResourcesDockingRecordInputIns";
        instance.controller = "${ctx}/demo/resourcesdockingrecord/";
        instance.listUrl = "${ctx}/demo/resourcesdockingrecord/list";
        instance.clientID = "ResourcesDockingRecordInput";

        instance.init();
    }

    ResourcesDockingRecordInput_Page_Load();
    //]]>
</script>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.ResourcesDockingRecordInputIns"/>