<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/pms/companyinfo/CompanyInfoInput.js?${version}"></script>
	
<title>公司资讯编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper CompanyInfoInput-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">公司资讯编辑</div>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">类别</label>
            <div class="col-sm-9">
                <select id="companyInfoCd" class="form-control required" data-title="类别" style="width: 350px;" >
                    <option value="">请选择</option>
                </select>
                <label id="companyInfoCd_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">标题</label>
            <div class="col-sm-9">
                <input id="title" type="text" class="form-control required" data-title="标题" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                <label id="title_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">内容</label>
            <div class="col-sm-9">
                <textarea id="content" rows="3" cols="30" class="form-control" data-title="内容" style="width: 350px;"></textarea>
                <label id="content_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">有效开始日期</label>
            <div class="col-sm-9">
                <ctag:CalendarSelect id="effectStartDate" title="有效开始日期" required="required" showValidateError="true" width="120px"></ctag:CalendarSelect>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">有效终了日期</label>
            <div class="col-sm-9">
                <ctag:CalendarSelect id="effectEndDate" title="有效终了日期" showValidateError="true" width="120px"></ctag:CalendarSelect>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">负责人</label>
            <div class="col-sm-9">
                <%--
                <input id="chargeUserUid" type="text" class="form-control" data-title="负责人" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                --%>
                <select id="chargeUserUid" class="form-control required" data-title="负责人" style="width: 350px;" >
                    <option value="">请选择</option>
                </select>
                <label id="chargeUserUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">负责部门</label>
            <div class="col-sm-9">
                <%--
                <input id="chargeDeptUid" type="text" class="form-control" data-title="负责部门" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                --%>
                <select id="chargeDeptUid" class="form-control" data-title="负责部门" style="width: 350px;" >
                    <option value="">请选择</option>
                </select>
                <label id="chargeDeptUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <div class="col-sm-9">
                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注"  style="width: 350px;"></textarea>
                <label id="remark_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <ctag:FileUpload clientID="FileUpload${uid}" relationUid="${uid}" appCode="PMS" moduleName="市场推广" panelTitle="附件一览"></ctag:FileUpload>
        </div>

            </form>

            <div class="text-center">
                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
            </div>

            <%--原始对象Json字符串[实体属性校验用]--%>
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_CompanyInfo}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyInfo}"/>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyInfoInput_Page_Load() {
        SysApp.Pms.CompanyInfoInputIns = new SysApp.Pms.CompanyInfoInput();
        var instance = SysApp.Pms.CompanyInfoInputIns;
        instance.selfInstance = "SysApp.Pms.CompanyInfoInputIns";
        instance.controller = "${ctx}/pms/companyinfo/";
        instance.listUrl = "${ctx}/pms/companyinfo/list";
        instance.clientID = "CompanyInfoInput";
        instance.entry = "${entry}";
        
        instance.init();
    }

    CompanyInfoInput_Page_Load();
    //]]>
</script>