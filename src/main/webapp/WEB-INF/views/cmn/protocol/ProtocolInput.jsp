<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/protocol/ProtocolInput.js?${version}"></script>

<title>协议编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper ProtocolInput-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">协议编辑</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">
                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label">协议名称</label>
                    <div class="col-lg-11">
                        <input id="name" type="text" class="form-control required" data-title="协议名称" maxlength="16" data-rangelength="[0,64]" style="width: 350px;"/>
                        <label id="name_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label">协议分类</label>
                    <div class="col-lg-11">
                        <select id="categoryCd" class="form-control required" data-title="协议分类" style="width: 350px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="categoryCd_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label">协议版本</label>
                    <div class="col-lg-11">
                        <input id="version" type="text" class="form-control required" data-title="协议版本" maxlength="16" data-rangelength="[0,16]" style="width: 350px;"/>
                        <label id="version_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label">有效日期</label>
                    <div class="col-lg-11">
                        <ctag:CalendarSelect id="startDate" title="有效开始日期" showValidateError="true" width="120px"></ctag:CalendarSelect>~
                        <ctag:CalendarSelect id="endDate" title="有效结束日期" showValidateError="true" width="120px"></ctag:CalendarSelect>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label">协议内容</label>
                    <div class="col-lg-11">
                        <div class="log-text-editor" style="padding: 0;width: 95%;">
                            <textarea id="contents" rows="10" cols="20" data-title="反馈信息" style="width: 100%;"></textarea>
                            <label id="contents_Error" class="validator-error"></label>
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
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Protocol}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Protocol}"/>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ProtocolInput_Page_Load() {
        SysApp.Cmn.ProtocolInputIns = new SysApp.Cmn.ProtocolInput();
        var instance = SysApp.Cmn.ProtocolInputIns;
        instance.selfInstance = "SysApp.Cmn.ProtocolInputIns";
        instance.controller = "${ctx}/cmn/protocol/";
        instance.listUrl = "${ctx}/cmn/protocol/list";
        instance.clientID = "ProtocolInput";
        instance.entry = "${entry}";

        instance.init();
    }

    ProtocolInput_Page_Load();
    //]]>
</script>