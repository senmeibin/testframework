<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/servicetracking/ServiceTrackingInput.js?${version}"></script>

<title>企业服务跟踪编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper ServiceTrackingInput-MainContent">
    <div class="panel">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">企业服务跟踪编辑</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">走访企业</label>
                    <div class="col-lg-4 select2-required">
                        <ctag:Select2 selectId="companyUid" dataTitle="走访企业" style="width: 220px;" required="required"/>
                        <label id="companyUid_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">走访人</label>
                    <div class="col-lg-4">
                        <ctag:ComboTree valueDomId="visitUserUid" textDomId="visitUserName" parentInstance="SysApp.Demo.ServiceTrackingInputIns" dataTitle="走访人" style="width:181px;" required="required"
                                        selectParent="false" idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dispName="deptName" dataUrl="${ctx}/demo/user/getUserTreeByRole"/>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">企业拜访人</label>
                    <div class="col-lg-4">
                        <input id="visitor" type="text" class="form-control" data-title="企业拜访人" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                        <label id="visitor_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">走访时间</label>
                    <div class="col-lg-4">
                        <ctag:CalendarSelect id="visitTime" title="走访时间" showValidateError="true" width="181px" required="required"></ctag:CalendarSelect>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">下次跟踪服务人</label>
                    <div class="col-lg-4">
                        <ctag:ComboTree valueDomId="nextTrackingUserUid" textDomId="nextTrackingUserName" parentInstance="SysApp.Demo.ServiceTrackingInputIns" dataTitle="下次跟踪服务人" style="width:181px;"
                                        selectParent="false" idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dispName="deptName" dataUrl="${ctx}/demo/user/getAllUserTree"/>
                    </div>
                    <label class="col-lg-1 control-label">下次跟踪时间</label>
                    <div class="col-lg-4">
                        <ctag:CalendarSelect id="nextTrackingTime" title="下次跟踪时间" showValidateError="true" width="181px"></ctag:CalendarSelect>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">走访状态</label>
                    <div class="col-lg-4">
                        <select id="visitStatusCd" class="form-control required" data-title="走访状态" style="width: 220px;" required="required">
                            <option value="">请选择</option>
                        </select>
                        <label id="visitStatusCd_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">企业需求</label>
                    <div class="col-lg-8">
                        <textarea id="businessRequirements" rows="5" cols="30" class="form-control" data-title="企业需求" data-rangelength="[0,512]" style="width:98%;"></textarea>
                        <label id="businessRequirements_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">备注</label>
                    <div class="col-lg-8">
                        <textarea id="remark" rows="5" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width:98%;"></textarea>
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
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_ServiceTracking}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ServiceTracking}"/>
        </div>
    </div>
    <ctag:FileUpload clientID="FileUpload" relationUid="${serviceTrackingUid}" appCode="TUS" moduleName="BPMS演示" panelTitle="附件一览"></ctag:FileUpload>
</div>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/servicetracking/ServiceTrackingInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ServiceTrackingInput_Page_Load() {
        SysApp.Demo.ServiceTrackingInputIns = new SysApp.Demo.ServiceTrackingInput();
        var instance = SysApp.Demo.ServiceTrackingInputIns;
        instance.selfInstance = "SysApp.Demo.ServiceTrackingInputIns";
        instance.clientID = "ServiceTrackingInput";
        instance.controller = "${ctx}/demo/servicetracking/";
        instance.listUrl = "${ctx}/demo/servicetracking/list";

        instance.init();
    }

    ServiceTrackingInput_Page_Load();
    //]]>
</script>