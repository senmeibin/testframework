<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<style type="text/css">
    .details-div {
        line-height: 24px;
        vertical-align: middle;
        cursor: pointer;
    }
</style>
<title>常见问题编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper FaqInput-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">常见问题编辑</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">
                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">应用名称</label>
                    <div class="col-lg-3">
                        <select id="appCode" class="form-control required" data-title="应用名称" style="width: 350px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="appCode_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">模块名称</label>
                    <div class="col-lg-3">
                        <input id="moduleName" type="text" class="form-control required" data-title="模块名称" maxlength="64" data-rangelength="[0,64]" style="width: 350px;"/>
                        <label id="moduleName_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">问题描述</label>
                    <div class="col-lg-9">
                        <textarea id="question" rows="3" cols="30" class="form-control required" data-title="问题描述" maxlength="256" data-rangelength="[0,256]" style="width: 80%;"></textarea>
                        <label id="question_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">问题详细</label>
                    <div class="details-div col-lg-9" domId="questionDesc"><i class="fa fa-pencil-square-o"></i>单击填写问题详细</div>
                    <div class="col-lg-9" style="display: none;">
                        <textarea id="questionDesc" data-title="问题详细" style="width: 80%;height: 500px;"></textarea>
                        <label id="questionDesc_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">问题解答</label>
                    <div class="col-lg-9">
                        <textarea id="answer" rows="3" cols="30" class="form-control required" data-title="问题解答" maxlength="256" data-rangelength="[0,256]" style="width: 80%;"></textarea>
                        <label id="answer_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2  control-label">操作说明</label>
                    <div class="details-div col-lg-9" domId="answerDesc"><i class="fa fa-pencil-square-o"></i>单击填写操作说明</div>
                    <div class="col-lg-9" style="display: none;">
                        <textarea id="answerDesc" data-title="操作说明" style="width: 80%;height: 500px;"></textarea>
                        <label id="answerDesc_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">适用角色</label>
                    <div class="col-lg-3">
                        <input id="applicableRole" type="text" class="form-control required" data-title="适用角色" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                        <label id="applicableRole_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2  control-label">责任人</label>
                    <div class="col-lg-3">
                        <input id="responsibleRole" type="text" class="form-control required" data-title="责任人" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                        <label id="responsibleRole_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">备注</label>
                    <div class="col-lg-9">
                        <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,512]" style="width: 80%"></textarea>
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
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Faq}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Faq}"/>
        </div>
    </div>
    <ctag:FileUpload clientID="FileUpload" relationUid="${uid}" appCode="CMN" moduleName="常见问题" panelTitle="附件一览"></ctag:FileUpload>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/faq/FaqInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function FaqInput_Page_Load() {
        SysApp.Cmn.FaqInputIns = new SysApp.Cmn.FaqInput();
        var instance = SysApp.Cmn.FaqInputIns;
        instance.selfInstance = "SysApp.Cmn.FaqInputIns";
        instance.controller = "${ctx}/${appCode.toLowerCase()}/faq/";
        instance.listUrl = "${ctx}/${appCode.toLowerCase()}/faq/list?appCode=${appCode}";
        instance.clientID = "FaqInput";
        instance.appCode = "${appCode}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    FaqInput_Page_Load();
    //]]>
</script>