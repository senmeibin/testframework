<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/message/messagedetail/MessageDetailPopupList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content MessageDetailPopupList-MainContent" style="width: 1300px;display: none;">
    <ctag:ModalHeader modalTitle="我的消息"></ctag:ModalHeader>

    <div class="modal-body">
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm">
            <%-- 当前系统用户UID --%>
            <input id="userUid" data-alias-table="main" type="hidden" value="${loginUser.userUid}" data-search-mode="="/>

            <div class="col-md-2 form-group">
                <label>消息标题</label>
                <input id="messageTitle" data-alias-table="message" type="text" maxlength="64" class="form-control" data-title="消息标题"/>
            </div>
            <div class="col-md-2 form-group">
                <label>消息内容</label>
                <input id="messageContent" data-alias-table="message" type="text" maxlength="64" class="form-control" data-title="消息内容"/>
            </div>
            <div class="col-md-2 form-group">
                <label>重要度</label>
                <select id="importanceDegreeCd" data-alias-table="message" class="form-control" data-title="重要度">
                    <option value="">-全部-</option>
                    <option value="01">普通</option>
                    <option value="02">重要</option>
                    <option value="03">紧急</option>
                </select>
            </div>
            <div class="col-md-2 form-group">
                <label>是否已读</label>
                <select id="readTypeCd" data-alias-table="main" class="form-control" data-title="是否已读">
                    <option value="">-全部-</option>
                    <option value="01" selected="selected">未读</option>
                    <option value="02">已读</option>
                </select>
            </div>
            <div class="col-md-2 form-group">
                <label>消息接收时间(起)</label>
                <div class='input-group datetime-picker'>
                    <input id="insertDate$from_search" data-alias-table="main" type='text' class="form-control" data-title="消息接收时间(起)"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
            </div>
            <div class="col-md-2 form-group">
                <label>消息接收时间(止)</label>
                <div class='input-group datetime-picker'>
                    <input id="insertDate$to_search" data-alias-table="main" type='text' class="form-control" data-title="消息接收时间(止)"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
            </div>
            <div class="clear-both dashed-line">
            </div>

            <div>
                <button id="btnSearch" type="button" class="btn btn-primary">
                    <i class="fa fa-search"></i>查询
                </button>
                <button id="btnClear" type="button" class="btn btn-default">
                    <i class="fa fa-eraser"></i>清空
                </button>
                <button id="batchSetReadBtn" type="button" class="btn btn-primary">
                    <i class="fa fa-check"></i>批量标记已读
                </button>
            </div>
        </form>
        <input type="hidden" id="jsonListData" value="${jsonDataList_MessageDetail}"/>
        <%--上次检索条件--%>
        <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_MessageDetail}"/>
        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_MessageDetail}"/>
        <div class="margin-top-space" id="divList" style="width: 100%;height: 380px;overflow-x: auto;">
        </div>
    </div>

    <ctag:ModalFooter></ctag:ModalFooter>
</div>

<script type="text/javascript">
    //<![CDATA[
    function MessageDetailPopupList_Page_Load() {
        SysApp.Cmn.MessageDetailPopupListIns = new SysApp.Cmn.MessageDetailPopupList();
        var instance = SysApp.Cmn.MessageDetailPopupListIns;
        instance.selfInstance = "SysApp.Cmn.MessageDetailPopupListIns";
        instance.controller = "${ctx}/cmn/messagedetail/";
        instance.clientID = "MessageDetailPopupList";
        instance.tableName = "cmn_message_detail";
        instance.detailInstance = SysApp.Cmn.MessageDetailIns;
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.pageSize = "100";

        instance.init();
    }

    $(function () {
        MessageDetailPopupList_Page_Load();
    });
    //]]>
</script>
<%@ include file="/WEB-INF/views/cmn/message/MessagePopupDetail.jsp" %>