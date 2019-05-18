<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/CompanyExtendedCommon.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyintellectual/CompanyIntellectualInput.js?${version}"></script>

<title>知识产权编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height">
        <div class="panel-heading CompanyIntellectualInput-MainContent">
            <em></em>
            <div class="panel-title" id="ctlTitle">知识产权编辑</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <div class=" CompanyIntellectualInput-MainContent">
                <%@ include file="/WEB-INF/views/demo/companyinformation/CompanyInformationInnerDetail.jsp" %>
                <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
                <form id="MainForm" class="form-horizontal margin-top-space">
                    <input id="uid" type="hidden">
                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">知识产权类型</label>
                        <div class="col-lg-4">
                            <select id="intellectualPropertyCd" class="form-control" data-title="知识产权类型" style="width: 220px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="intellectualPropertyCd_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">发生时间</label>
                        <div class="col-lg-4">
                            <ctag:CalendarSelect id="occurrenceDate" title="发生时间" showValidateError="true" width="181px"></ctag:CalendarSelect>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">名称</label>
                        <div class="col-lg-4">
                            <input id="name" type="text" class="form-control" data-title="名称" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                            <label id="name_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">编号</label>
                        <div class="col-lg-4">
                            <input id="serialNumber" type="text" class="form-control" data-title="编号" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                            <label id="serialNumber_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">状态</label>
                        <div class="col-lg-4">
                            <select id="stateCd" class="form-control" data-title="状态" style="width: 220px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="stateCd_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">是否有效</label>
                        <div class="col-lg-4">
                            <select id="effectiveCd" class="form-control" data-title="是否有效" style="width: 220px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="effectiveCd_Error" class="validator-error"></label>
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

                <div class="text-center margin-top-space">
                    <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                    <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                    <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
                </div>

                <%--原始对象Json字符串[实体属性校验用]--%>
                <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_CompanyIntellectual}"/>

                <%--下拉框选项数据集合--%>
                <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyIntellectual}"/>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyIntellectualInput_Page_Load() {
        SysApp.Demo.CompanyIntellectualInputIns = new SysApp.Demo.CompanyIntellectualInput();
        var instance = SysApp.Demo.CompanyIntellectualInputIns;
        instance.selfInstance = "SysApp.Demo.CompanyIntellectualInputIns";
        instance.controller = "${ctx}/demo/companyintellectual/";
        instance.listUrl = "${ctx}/demo/companyintellectual/list";
        instance.clientID = "CompanyIntellectualInput";

        instance.init();
    }

    CompanyIntellectualInput_Page_Load();
    //]]>
</script>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.CompanyIntellectualInputIns"/>