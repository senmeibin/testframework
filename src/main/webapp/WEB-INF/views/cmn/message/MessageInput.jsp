<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/message/MessageInput.js?${version}"></script>

<title>消息推送编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">消息推送编辑</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body MessageInput-MainContent">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">
                <h3 class="sub-title" style="margin-top: -10px;">
                    <span>
                        基本信息
                    </span>
                </h3>

                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label" style="width: 150px;">消息标题</label>
                    <div class="col-lg-10">
                        <input id="messageTitle" type="text" class="form-control required" data-title="消息标题" maxlength="64" data-rangelength="[0,64]" style="width: 650px;"/>
                        <label id="messageTitle_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label" style="width: 150px;">消息类型</label>
                    <div class="col-lg-10">
                        <select id="messageTypeCd" class="form-control required" data-title="消息类型" style="width: 200px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="messageTypeCd_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label" style="width: 150px;">消息提醒开始时间</label>
                    <div class="col-lg-10">
                        <div class="input-group datetime-picker">
                            <input id="messageStartDate" type="text" class="form-control required" data-title="消息提醒开始时间" maxlength="19" style="width: 161px;"/>
                            <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                        </div>
                        <label id="messageStartDate_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label" style="width: 150px;">消息提醒结束时间</label>
                    <div class="col-lg-10">
                        <div class="input-group datetime-picker">
                            <input id="messageEndDate" type="text" class="form-control required" data-title="消息提醒结束时间" maxlength="19" style="width: 161px;"/>
                            <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                        </div>
                        <label id="messageEndDate_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label" style="width: 150px;">重要度</label>
                    <div class="col-lg-10">
                        <select id="importanceDegreeCd" class="form-control required" data-title="重要度" style="width: 200px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="importanceDegreeCd_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label" style="width: 150px;">发送区分</label>
                    <div class="col-lg-10">
                        <select id="sendTypeCd" class="form-control required" data-title="发送区分" style="width: 200px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="sendTypeCd_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <div id="userComboCheckTree">
                        <label class="col-lg-1 control-label" style="width: 150px;">指定人员</label>
                        <div class="col-lg-10">
                            <ctag:ComboCheckTree valueDomId="sendUserUids" textDomId="sendUserNames" parentInstance="SysApp.Cmn.MessageInputIns" dataTitle="指定人员" searchMode="ignore_search" style="width:308px"
                                                 nodeName="userName" idKey="uid" parentIdKey="deptUid" dataUrl="${ctx}/cmn/message/getAllUserList"/>
                        </div>
                    </div>
                    <div id="deptComboCheckTree">
                        <label class="col-lg-1 control-label" style="width: 150px;">指定部门</label>
                        <div class="col-lg-10">
                            <ctag:ComboCheckTree valueDomId="sendDeptUids" textDomId="sendDeptNames" parentInstance="SysApp.Cmn.MessageInputIns" dataTitle="指定部门" searchMode="ignore_search" style="width:308px"
                                                 nodeName="deptName" idKey="uid" parentIdKey="parentDeptUid" dataUrl="${ctx}/sys/dept/getAllDeptList"/>
                        </div>
                    </div>
                    <div id="roleComboCheckTree">
                        <label class="col-lg-1 control-label" style="width: 150px;">指定角色</label>
                        <div class="col-lg-10">
                            <ctag:ComboCheckTree valueDomId="sendRoleUids" textDomId="sendRoleNames" parentInstance="SysApp.Cmn.MessageInputIns" dataTitle="指定角色" searchMode="ignore_search" style="width:308px"
                                                 nodeName="roleName" idKey="uid" parentIdKey="parentRoleUid" dataUrl="${ctx}/sys/role/getAllRoleList"/>
                        </div>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label" style="width: 150px;">通知设定</label>
                    <div class="col-lg-10">
                        <input type="checkbox" id="isMailNotice" data-title="是否邮件通知"/>
                        <label for="isMailNotice">邮件通知</label>
                        <label id="isMailNotice_Error" class="validator-error"></label>
                        <input type="checkbox" id="isSmsNotice" data-title="是否短信通知"/>
                        <label for="isSmsNotice">短信通知</label>
                        <label id="isSmsNotice_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline message-remark">
                    <label class="col-lg-1 control-label" style="width: 150px;">备注</label>
                    <div class="col-lg-10">
                        <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 100%;"></textarea>
                        <label id="remark_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label" style="width: 150px;">是否富文本消息</label>
                    <div class="col-lg-10">
                        <input type="checkbox" id="isRichMessage" data-title="富文本消息"/>
                        <label for="isRichMessage">富文本消息</label>
                        <label id="isRichMessage_Error" class="validator-error"></label>
                    </div>
                </div>
                <h3 class="sub-title">
                    <span>
                        消息内容
                    </span>
                </h3>

                <div class="form-group form-inline">
                    <div class="col-lg-10" style="margin-left: 150px;">
                        <div class="log-text-editor" style="padding: 0;width: 100%;">
                            <textarea id="messageContent" rows="10" cols="20" class="required" data-title="消息内容" style="width: 100%;"></textarea>
                            <label id="messageContent_Error" class="validator-error"></label>
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
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Message}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Message}"/>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function MessageInput_Page_Load() {
        SysApp.Cmn.MessageInputIns = new SysApp.Cmn.MessageInput();
        var instance = SysApp.Cmn.MessageInputIns;
        instance.selfInstance = "SysApp.Cmn.MessageInputIns";
        instance.controller = "${ctx}/cmn/message/";
        instance.listUrl = "${ctx}/cmn/message/list";
        instance.clientID = "MessageInput";
        instance.serviceTime = "${serviceTime}";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    $(function () {
        MessageInput_Page_Load();
    });

    //]]>
</script>