<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/thirdpartyservicecontact/ThirdPartyServiceContactInput.js?${version}"></script>

<title>第三方服务编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">第三方服务编辑</div>
        </div>
        <div class="panel-body">
            <div class="ThirdPartyServiceContactInput-MainContent">
                <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
                <form id="MainForm" class="form-horizontal">
                    <input id="uid" type="hidden">
                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">所属基地</label>
                        <div class="col-lg-4 select2-required">
                            <ctag:Select2 selectId="baseUid" dataTitle="所属基地" style="width:220px" required="required"/>
                            <label id="baseUid_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">企业名称</label>
                        <div class="col-lg-4">
                            <input id="companyName" type="text" class="form-control required" data-title="企业名称" maxlength="64" data-rangelength="[0,64]" style="width: 220px;"/>
                            <label id="companyName_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">服务时间</label>
                        <div class="col-lg-4">
                            <ctag:CalendarSelect id="serviceDate" title="服务时间" showValidateError="true" width="181px"></ctag:CalendarSelect>
                        </div>
                        <label class="col-lg-1 control-label">服务类别</label>
                        <div class="col-lg-4">
                            <input id="serviceType" type="text" class="form-control" data-title="服务类别" maxlength="64" data-rangelength="[0,64]" style="width: 220px;"/>
                            <label id="serviceType_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">服务内容</label>
                        <div class="col-lg-8">
                            <textarea id="serviceContent" rows="3" cols="30" class="form-control" data-title="服务内容" data-rangelength="[0,256]" style="width: 98%;"></textarea>
                            <label id="serviceContent_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">标签</label>
                        <div class="col-lg-8">
                            <input id="thirdPartyServiceTag" type="text" class="form-control" data-title="标签" maxlength="512" data-rangelength="[0,512]" style="width: 98%;"/>
                            <label id="thirdPartyServiceTag_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">评价</label>
                        <div class="col-lg-8">
                            <textarea id="evaluate" rows="3" cols="30" class="form-control" data-title="评价" data-rangelength="[0,256]" style="width: 98%;"></textarea>
                            <label id="evaluate_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">备注</label>
                        <div class="col-lg-8">
                            <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 98%;"></textarea>
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
                <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_ThirdPartyServiceContact}"/>

                <%--下拉框选项数据集合--%>
                <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ThirdPartyServiceContact}"/>
            </div>
            <%@ include file="/WEB-INF/views/demo/thirdpartyservicecontact/thirdpartyservicecontacts/ThirdPartyServiceContactsInnerList.jsp" %>
        </div>
    </div>
    <ctag:FileUpload clientID="FileUpload" relationUid="${thirdPartyServiceContactUid}" appCode="TUS" moduleName="BPMS演示" panelTitle="附件一览"></ctag:FileUpload>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/thirdpartyservicecontact/ThirdPartyServiceContactInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ThirdPartyServiceContactInput_Page_Load() {
        SysApp.Demo.ThirdPartyServiceContactInputIns = new SysApp.Demo.ThirdPartyServiceContactInput();
        var instance = SysApp.Demo.ThirdPartyServiceContactInputIns;
        instance.selfInstance = "SysApp.Demo.ThirdPartyServiceContactInputIns";
        instance.clientID = "ThirdPartyServiceContactInput";
        instance.controller = "${ctx}/demo/thirdpartyservicecontact/";
        instance.listUrl = "${ctx}/demo/thirdpartyservicecontact/list";
        instance.init();
    }

    ThirdPartyServiceContactInput_Page_Load();
    //]]>
</script>