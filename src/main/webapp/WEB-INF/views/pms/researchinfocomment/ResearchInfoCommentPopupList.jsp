<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/pms/researchinfocomment/ResearchInfoCommentPopupList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content ResearchInfoCommentPopupList-MainContent" style="width: 1200px; display: none;">
    <ctag:ModalHeader modalTitle="市场调研评论一览"></ctag:ModalHeader>
    
    <div class="modal-body">
        <button id="btnAdd" type="button" class="btn btn-primary">
            <i class="fa fa-plus"></i>新增市场调研评论
        </button>
        <div class="clear-both dashed-line">
        </div>
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <input id="researchInfoName"  data-allow-clear="false" type="hidden"/>
        <form id="MainForm">
            <input id="researchInfoUid" data-alias-table="main" data-allow-clear="false" type="hidden"/>
        </form>
        <%--学员基本信息--%>
        <i style="font-size: 18px;"></i>&nbsp;<span id="researchInfoTitle"></span>
        <%--上次检索条件--%>
        <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_ResearchInfoComment}"/>
        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ResearchInfoComment}"/>
        <div class="margin-top-space" id="divList" style="width: 100%">
        </div>
    </div>

    <ctag:ModalFooter></ctag:ModalFooter>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ResearchInfoCommentPopupList_Page_Load() {
        SysApp.Pms.ResearchInfoCommentPopupListIns = new SysApp.Pms.ResearchInfoCommentPopupList();
        var instance = SysApp.Pms.ResearchInfoCommentPopupListIns;
        
        instance.selfInstance = "SysApp.Pms.ResearchInfoCommentPopupListIns";
        instance.clientID = "ResearchInfoCommentPopupList";
        instance.tableName = "pms_research_info_comment";
        instance.inputInstance = SysApp.Pms.ResearchInfoCommentInputIns;
        instance.detailInstance = SysApp.Pms.ResearchInfoCommentDetailIns;
        instance.controller = "${ctx}/pms/researchinfocomment/";
        instance.entry = "${entry}";

        instance.init();
    }

    $(function() {
        ResearchInfoCommentPopupList_Page_Load();
    });
    //]]>
</script>

