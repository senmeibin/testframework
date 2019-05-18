<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/pms/project/ProjectInput.js?${version}"></script>
	
<title>项目编辑</title>
<nav class="right-side-quick-nav">
    <ul class="nav">
        <li class="active"><a href="#divBaseInfoAnchor">基本信息</a></li>
        <li><a href="#divCapabilityInfoAnchor">实施能力</a></li>
        <li><a href="#divBudgetaryAnchor">预算价格</a></li>
        <li><a href="#divEvaluationAnchor">价格评估</a></li>
    </ul>
</nav>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper ProjectInput-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">项目编辑</div>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">名称</label>
            <div class="col-sm-9">
                <input id="name" type="text" class="form-control required" data-title="名称" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                <label id="name_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">地址</label>
            <div class="col-sm-9">
                <input id="address" type="text" class="form-control required" data-title="地址" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                <label id="address_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">报名开始时间</label>
            <div class="col-sm-9">
                <ctag:CalendarSelect id="entryStartDate" title="报名开始时间" showValidateError="true" width="120px"></ctag:CalendarSelect>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">报名终了时间</label>
            <div class="col-sm-9">
                <ctag:CalendarSelect id="entryEndDate" title="报名终了时间" showValidateError="true" width="120px"></ctag:CalendarSelect>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">踏勘开始时间</label>
            <div class="col-sm-9">
                <ctag:CalendarSelect id="explorationStartDate" title="踏勘开始时间" showValidateError="true" width="120px"></ctag:CalendarSelect>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">开标时间</label>
            <div class="col-sm-9">
                <ctag:CalendarSelect id="bidDate" title="开标时间" showValidateError="true" width="120px"></ctag:CalendarSelect>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">进场时间</label>
            <div class="col-sm-9">
                <ctag:CalendarSelect id="projectStartDate" title="进场时间" required="required" showValidateError="true" width="120px"></ctag:CalendarSelect>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">实施能力</label>
            <div class="col-sm-9">
                <textarea id="exeCapability" rows="3" cols="30" class="form-control" data-title="实施能力" data-rangelength="[0,0]" style="width: 350px;"></textarea>
                <label id="exeCapability_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">预算价格</label>
            <div class="col-sm-9">
                <textarea id="budgetaryPrice" rows="3" cols="30" class="form-control" data-title="预算价格" data-rangelength="[0,0]" style="width: 350px;"></textarea>
                <label id="budgetaryPrice_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">价格评估</label>
            <div class="col-sm-9">
                <textarea id="priceEvaluation" rows="3" cols="30" class="form-control" data-title="价格评估" data-rangelength="[0,0]" style="width: 350px;"></textarea>
                <label id="priceEvaluation_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <div class="col-sm-9">
                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 350px;"></textarea>
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
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Project}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Project}"/>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ProjectInput_Page_Load() {
        SysApp.Pms.ProjectInputIns = new SysApp.Pms.ProjectInput();
        var instance = SysApp.Pms.ProjectInputIns;
        instance.selfInstance = "SysApp.Pms.ProjectInputIns";
        instance.controller = "${ctx}/pms/project/";
        instance.listUrl = "${ctx}/pms/project/list";
        instance.clientID = "ProjectInput";
        instance.entry = "${entry}";
        
        instance.init();
    }

    ProjectInput_Page_Load();
    //]]>
</script>