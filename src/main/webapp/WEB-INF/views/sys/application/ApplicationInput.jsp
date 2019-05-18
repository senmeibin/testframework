<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/sys/application/ApplicationInput.js?${version}"></script>

<title>应用模块编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height ApplicationInput-MainContent">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">应用模块编辑</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <div class="form-group" style="display: none">
                    <div>
                        <input id="uid" type="hidden">
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label">应用编号</label>
                    <div class="col-sm-9">
                        <input id="appCode" type="text" class="form-control required duplication" data-title="应用编号" maxlength="16" data-rangelength="[0,16]" data-regex="/[a-zA-Z]+/"
                               data-regex-message="请在{0}中输入A-Z的字符。" style="width: 300px; text-transform: uppercase;"/>（如：CM/CRM/CC）
                        <label id="appCode_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label">应用名称</label>
                    <div class="col-sm-9">
                        <input id="appName" type="text" class="form-control required duplication" data-title="应用名称" maxlength="32" data-rangelength="[0,32]" style="width: 300px;"/>（如：合同管理/呼叫中心）
                        <label id="appName_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label">有效日期</label>
                    <div class="col-sm-9">
                        <ctag:CalendarSelect id="validDate" title="有效日期" showValidateError="true" width="120px" required="required"></ctag:CalendarSelect>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label">表示顺序</label>
                    <div class="col-sm-9">
                        <input id="dispSeq" type="text" class="form-control" data-title="表示顺序" maxlength="5" data-rangelength="[0,5]" data-range="[0,99999]" data-digits="true" style="width: 300px;"/>
                        <label id="dispSeq_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label">应用版本</label>
                    <div class="col-sm-9">
                        <input id="version" type="text" class="form-control" data-title="应用版本" maxlength="16" data-rangelength="[0,16]" style="width: 300px;"/>
                        <label id="version_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label">备注</label>
                    <div class="col-sm-9">
                        <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 550px;"></textarea>
                        <label id="remark_Error" class="validator-error"></label>
                    </div>
                </div>

                <div id="divMainteContainer">
                    <div class="form-group form-inline">
                        <label class="col-sm-3 control-label">应用维护设定</label>
                        <div class="col-sm-9 mainte-setting">
                            <input id="mainteSetting" type="checkbox"/><label for="mainteSetting">启用应用维护设定</label>
                        </div>
                    </div>

                    <div class="form-group form-inline app-mainte">
                        <label class="col-sm-3 control-label">维护开始日期</label>
                        <div class="col-sm-9">
                            <div class="input-group datetime-picker">
                                <input id="mainteStartDate" type="text" class="form-control" data-title="维护开始日期" maxlength="10" style="width: 120px;"/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                            <label id="mainteStartDate_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline app-mainte">
                        <label class="col-sm-3 control-label">维护结束日期</label>
                        <div class="col-sm-9">
                            <div class="input-group datetime-picker">
                                <input id="mainteEndDate" type="text" class="form-control" data-title="维护结束日期" maxlength="10" style="width: 120px;"/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                            <label id="mainteEndDate_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline app-mainte">
                        <label class="col-sm-3 control-label">维护内容</label>
                        <div class="col-sm-9"><textarea id="mainteContent" rows="3" cols="30" class="form-control" data-title="维护内容" data-rangelength="[0,512]" style="width: 550px;"></textarea>
                            <label id="mainteContent_Error" class="validator-error"></label>
                        </div>
                    </div>
                </div>
            </form>

            <div class="text-center">
                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
            </div>

            <%--原始对象Json字符串[实体属性校验用]--%>
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Application}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Application}"/>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ApplicationInput_Page_Load() {
        SysApp.Sys.ApplicationInputIns = new SysApp.Sys.ApplicationInput();
        var instance = SysApp.Sys.ApplicationInputIns;
        instance.selfInstance = "SysApp.Sys.ApplicationInputIns";
        instance.controller = "${ctx}/sys/application/";
        instance.listUrl = "${ctx}/sys/application/list";
        instance.clientID = "ApplicationInput";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    ApplicationInput_Page_Load();
    //]]>
</script>