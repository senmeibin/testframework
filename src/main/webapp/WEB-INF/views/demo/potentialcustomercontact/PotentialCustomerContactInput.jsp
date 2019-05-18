<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/potentialcustomercontact/PotentialCustomerContactInput.js?${version}"></script>

<title>招商客户联络情况编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper PotentialCustomerContactInput-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">招商客户联络情况编辑</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">客户</label>
                    <div class="col-lg-4 select2-required">
                        <ctag:Select2 selectId="companyUid" dataTitle="客户" required="required" style="width:220px;"/>
                        <label id="companyUid_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">联络人（职位）</label>
                    <div class="col-lg-4">
                        <input id="contactPerson" type="text" class="form-control" data-title="联络人（职位）" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                        <label id="contactPerson_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">联络方式</label>
                    <div class="col-lg-4">
                        <input id="contactInfo" type="text" class="form-control" data-title="联络方式" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                        <label id="contactInfo_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">联络时间</label>
                    <div class="col-lg-4">
                        <ctag:CalendarSelect id="contactTime" title="联络时间" showValidateError="true" width="181px"></ctag:CalendarSelect>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">填表人</label>
                    <div class="col-lg-4">
                        <ctag:ComboTree valueDomId="fillFormUserUid" textDomId="fillFormUserName" parentInstance="SysApp.Demo.PotentialCustomerContactInputIns" dataTitle="填表人" style="width:181px;" required="required"
                                        selectParent="false" idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dispName="deptName" dataUrl="${ctx}/demo/user/getAllUserTree"/>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">项目简介</label>
                    <div class="col-lg-8">
                        <textarea id="projectDescription" rows="5" cols="30" class="form-control" data-title="项目简介" data-rangelength="[0,512]" style="width:92%;"></textarea>
                        <label id="projectDescription_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">公司需求</label>
                    <div class="col-lg-8">
                        <textarea id="companyDemand" rows="5" cols="30" class="form-control" data-title="公司需求" data-rangelength="[0,512]" style="width:92%;"></textarea>
                        <label id="companyDemand_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">建议及判断</label>
                    <div class="col-lg-8">
                        <textarea id="recommendationsJudgments" rows="5" cols="30" class="form-control" data-title="建议及判断" data-rangelength="[0,512]" style="width:92%;"></textarea>
                        <label id="recommendationsJudgments_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">项目时间节点规划</label>
                    <div class="col-lg-8">
                        <textarea id="projectTimeNode" rows="5" cols="30" class="form-control" data-title="项目时间节点规划" data-rangelength="[0,512]" style="width:92%;"></textarea>
                        <label id="projectTimeNode_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">计划</label>
                    <div class="col-lg-8">
                        <textarea id="plan" rows="5" cols="30" class="form-control" data-title="计划" data-rangelength="[0,512]" style="width:92%;"></textarea>
                        <label id="plan_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">结果</label>
                    <div class="col-lg-8">
                        <textarea id="result" rows="5" cols="30" class="form-control" data-title="结果" data-rangelength="[0,512]" style="width:92%;"></textarea>
                        <label id="result_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">备注</label>
                    <div class="col-lg-8">
                        <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width:92%;"></textarea>
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
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_PotentialCustomerContact}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_PotentialCustomerContact}"/>
        </div>
    </div>
    <ctag:FileUpload clientID="FileUpload" relationUid="${potentialCustomerUid}" appCode="TUS" moduleName="BPMS演示" panelTitle="附件一览"></ctag:FileUpload>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function PotentialCustomerContactInput_Page_Load() {
        SysApp.Demo.PotentialCustomerContactInputIns = new SysApp.Demo.PotentialCustomerContactInput();
        var instance = SysApp.Demo.PotentialCustomerContactInputIns;
        instance.selfInstance = "SysApp.Demo.PotentialCustomerContactInputIns";
        instance.controller = "${ctx}/demo/potentialcustomercontact/";
        instance.listUrl = "${ctx}/demo/potentialcustomercontact/list";
        instance.clientID = "PotentialCustomerContactInput";

        instance.init();
    }

    PotentialCustomerContactInput_Page_Load();
    //]]>
</script>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.PotentialCustomerContactInputIns"/>