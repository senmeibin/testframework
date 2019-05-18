<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/entrepreneurialactivity/EntrepreneurialActivityInput.js?${version}"></script>

<title>创业活动编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper EntrepreneurialActivityInput-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">创业活动编辑</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">所属基地</label>
                    <div class="col-lg-4 select2-required">
                        <ctag:Select2 selectId="baseUid" dataTitle="所属基地" style="width: 220px;" required="required"/>
                        <label id="baseUid_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">活动编号</label>
                    <div class="col-lg-4">
                        <input id="activityNo" type="text" class="form-control required" data-title="活动编号" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                        <label id="activityNo_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">活动主题</label>
                    <div class="col-lg-4">
                        <input id="activityTopic" type="text" class="form-control required" data-title="活动主题" maxlength="256" data-rangelength="[0,256]" style="width: 220px;"/>
                        <label id="activityTopic_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">会务角色</label>
                    <div class="col-lg-4">
                        <select id="conferenceRoleCd" class="form-control" data-title="会务角色" style="width: 220px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="conferenceRoleCd_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">服务类型</label>
                    <div class="col-lg-4">
                        <select id="serviceTypeCd" class="form-control" data-title="服务类型" style="width: 220px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="serviceTypeCd_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">活动地点</label>
                    <div class="col-lg-4">
                        <input id="activityLocation" type="text" class="form-control" data-title="活动地点" maxlength="64" data-rangelength="[0,64]" style="width: 220px;"/>
                        <label id="activityLocation_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">活动开始时间</label>
                    <div class="col-lg-4 startTimeDiv">
                        <ctag:CalendarSelect id="startTime" title="活动开始时间" showValidateError="true" width="181px" required="required"></ctag:CalendarSelect>
                    </div>
                    <label class="col-lg-1 control-label">活动结束时间</label>
                    <div class="col-lg-4">
                        <ctag:CalendarSelect id="endTime" title="活动结束时间" showValidateError="true" width="181px" required="required"></ctag:CalendarSelect>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">周次</label>
                    <div class="col-lg-4">
                        <input id="weeks" type="text" class="form-control" data-title="周次" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="weeks_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">活动人数</label>
                    <div class="col-lg-4">
                        <input id="activityNumber" type="text" class="form-control" data-title="活动人数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="activityNumber_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">活动金额</label>
                    <div class="col-lg-4">
                        <input id="activityAmount" type="text" class="form-control" data-title="活动金额" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;" data-money="true"/>
                        <label id="activityAmount_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">负责人</label>
                    <div class="col-lg-4">
                        <ctag:ComboTree valueDomId="personInChargeUid" textDomId="personInChargeName" parentInstance="SysApp.Demo.EntrepreneurialActivityInputIns" dataTitle="负责人" style="width:181px;" required="required"
                                        selectParent="false" idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dispName="deptName" dataUrl="${ctx}/demo/user/getAllUserTree"/>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">活动类型</label>
                    <div class="col-lg-4">
                        <select id="activityTypeCd" class="form-control" data-title="活动类型" style="width: 220px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="activityTypeCd_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">服务内容</label>
                    <div class="col-lg-4">
                        <select id="serviceContentCd" class="form-control" data-title="服务内容" style="width: 220px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="serviceContentCd_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">参与导师</label>
                    <div class="col-lg-4">
                        <ctag:ComboCheckTree valueDomId="tutorUid" textDomId="tutorName" dataTitle="参与导师" parentInstance="SysApp.Demo.EntrepreneurialActivityInputIns" dispName="tutorName" nodeName="tutorName" idKey="uid"
                                             parentIdKey="baseUid" dataUrl="${ctx}/demo/tutor/getTutorTree" style="width: 181px;"/>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">活动纪要</label>
                    <div class="col-lg-8">
                        <textarea id="activitySummary" rows="3" cols="30" class="form-control" data-title="活动纪要" data-rangelength="[0,256]" style="width: 706px;"></textarea>
                        <label id="activitySummary_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">备注</label>
                    <div class="col-lg-8">
                        <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 706px;"></textarea>
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
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_EntrepreneurialActivity}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_EntrepreneurialActivity}"/>
        </div>
    </div>
    <ctag:FileUpload clientID="FileUpload" relationUid="${entrepreneurialActivityUid}" appCode="TUS" moduleName="BPMS演示" panelTitle="附件一览"></ctag:FileUpload>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/entrepreneurialactivity/EntrepreneurialActivityInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function EntrepreneurialActivityInput_Page_Load() {
        SysApp.Demo.EntrepreneurialActivityInputIns = new SysApp.Demo.EntrepreneurialActivityInput();
        var instance = SysApp.Demo.EntrepreneurialActivityInputIns;
        instance.selfInstance = "SysApp.Demo.EntrepreneurialActivityInputIns";
        instance.clientID = "EntrepreneurialActivityInput";
        instance.controller = "${ctx}/demo/entrepreneurialactivity/";
        instance.listUrl = "${ctx}/demo/entrepreneurialactivity/list";
        instance.init();
    }

    EntrepreneurialActivityInput_Page_Load();
    //]]>
</script>