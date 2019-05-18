<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content ResearchInfoCommentInput-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <input id="uid" type="hidden">
        <input id="researchInfoUid" type="hidden"/>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">调研类别</label>
            <div class="col-sm-9">
                <input id="researchInfoName" type="text" class="form-control" data-title="市场调研类别" readonly style="width: 500px;"/>
            </div>
            <label class="col-sm-3 control-label">调研标题</label>
            <div class="col-sm-9">
                <input id="researchInfoTitle" type="text" class="form-control" data-title="市场调研标题" readonly style="width: 500px;"/>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">评论内容</label>
            <div class="col-sm-9">
                <textarea id="content" rows="5" cols="30" class="form-control required" data-title="内容"  style="width: 500px;"></textarea>
                <label id="content_Error" class="validator-error"></label>
            </div>
        </div>
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <div class="col-sm-9">
                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 500px;"></textarea>
                <label id="remark_Error" class="validator-error"></label>
            </div>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_ResearchInfoComment}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ResearchInfoComment}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/pms/researchinfocomment/ResearchInfoCommentInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ResearchInfoCommentInput_Page_Load() {
        SysApp.Pms.ResearchInfoCommentInputIns = new SysApp.Pms.ResearchInfoCommentInput();
        var instance = SysApp.Pms.ResearchInfoCommentInputIns;
        instance.selfInstance = "SysApp.Pms.ResearchInfoCommentInputIns";
        instance.clientID = "ResearchInfoCommentInput";
        instance.entry = "${entry}";
        instance.controller = "${ctx}/pms/researchinfocomment/";
        
        instance.init();
    }

    ResearchInfoCommentInput_Page_Load();
    //]]>
</script>