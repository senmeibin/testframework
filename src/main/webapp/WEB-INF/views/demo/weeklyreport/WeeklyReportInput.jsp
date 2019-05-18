<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/weeklyreport/WeeklyReportInput.js?${version}"></script>

<title>周报编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height">
        <div class="panel-heading WeeklyReportInput-MainContent">
            <em></em>
            <div class="panel-title" id="ctlTitle">周报编辑</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <div class="WeeklyReportInput-MainContent">
                <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
                <form id="MainForm" class="form-horizontal">
                    <input id="uid" type="hidden">
                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">所属基地</label>
                        <div class="col-lg-4 select2-required">
                            <ctag:Select2 selectId="baseUid" dataTitle="所属基地" required="required" style="width: 220px;"/>
                            <label id="baseUid_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">人员</label>
                        <div class="col-lg-4">
                            <ctag:ComboTree valueDomId="userUid" textDomId="userName" parentInstance="SysApp.Demo.WeeklyReportInputIns" dataTitle="人员" required="required"
                                            idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dispName="deptName" style="width:181px" dataUrl="${ctx}/demo/user/getAllUserTree"/>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">填写时间</label>
                        <div class="col-lg-4">
                            <ctag:CalendarSelect id="fillTime" title="填写时间" showValidateError="true" width="181px" required="required" maxlength="23"></ctag:CalendarSelect>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">本周总结</label>
                        <div class="col-lg-8">
                            <textarea id="summary" rows="5" class="form-control" data-title="本周总结" data-rangelength="[0,512]" style="width: 98%;"></textarea>
                            <label id="summary_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">备注</label>
                        <div class="col-lg-8">
                            <textarea id="remark" rows="3" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 98%;"></textarea>
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
                <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_WeeklyReport}"/>

                <%--下拉框选项数据集合--%>
                <input type="hidden" id="jsonOptionList" value="${jsonOptionList_WeeklyReport}"/>
            </div>
            <%@ include file="/WEB-INF/views/demo/weeklyreport/weeklyreportdetail/WeeklyReportDetailInnerList.jsp" %>
        </div>
    </div>
    <ctag:FileUpload clientID="FileUpload" relationUid="${weeklyReportUid}" appCode="TUS" moduleName="BPMS演示" panelTitle="附件一览"></ctag:FileUpload>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function WeeklyReportInput_Page_Load() {
        SysApp.Demo.WeeklyReportInputIns = new SysApp.Demo.WeeklyReportInput();
        var instance = SysApp.Demo.WeeklyReportInputIns;
        instance.selfInstance = "SysApp.Demo.WeeklyReportInputIns";
        instance.controller = "${ctx}/demo/weeklyreport/";
        instance.listUrl = "${ctx}/demo/weeklyreport/list";
        instance.clientID = "WeeklyReportInput";
        instance.currentUser = "${loginUser.userUid}";

        instance.init();
    }

    WeeklyReportInput_Page_Load();
    //]]>
</script>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.WeeklyReportInputIns"/>