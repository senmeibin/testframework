<%@ page language="java" pageEncoding="UTF-8" %>
<div id="ctlCmnMessageFrame" class="modal-content" style="display: none;">
    <div class="modal-header" id="ctlCmnMessageTitle">
        <button class="close" type="button" id="btnTopCloseForCmnMessage">
            ×
        </button>
        <h4 class="modal-title" id="ctlCmnMessageTitleName">${systemName}</h4>
    </div>
    <div class="modal-body">
        <table border="0" cellpadding="0" cellspacing="0" align="center" style="padding: 5px 10px 15px 10px;">
            <tr>
                <td style="height: 35px; padding-right: 15px;" id="tdCmnMessageType">
                    <i class="cmn-message-icon fa fa-spinner fa-spin fa-3x fa-fw fa-blue"></i>
                    <i class="cmn-message-icon fa fa-warning fa-3x fa-fw fa-red fa-red"></i>
                    <i class="cmn-message-icon fa fa-question-circle-o fa-3x fa-fw fa-blue"></i>
                    <i class="cmn-message-icon fa fa-info-circle fa-3x fa-fw fa-blue"></i>
                </td>
                <td>
                    <div id="divCmnMessageMessage" class="cmn-message-message">
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <div class="modal-footer" id="divCmnMessageFooter">
        <button type="button" class="btn btn-primary" id="btnYesForCmnMessage">
            <i class="fa fa-check"></i>确定
        </button>
        <button type="button" class="btn btn-default" id="btnNoForCmnMessage">
            <i class="fa fa-remove"></i>关闭
        </button>
        <button type="button" class="btn btn-default" id="btnCancelForCmnMessage">
            <i class="fa fa-remove"></i>关闭
        </button>
        <button type="button" class="btn btn-default" id="btnCloseForCmnMessage">
            <i class="fa fa-remove"></i>关闭
        </button>
    </div>
</div>
<script type="text/javascript">
    //<![CDATA[
    try {
        SysCmn.CmnMsg.init();
    } catch (e) {
    }
    //]]>
</script>
