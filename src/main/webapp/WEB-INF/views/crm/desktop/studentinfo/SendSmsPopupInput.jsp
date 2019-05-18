<%@ page language="java" pageEncoding="UTF-8" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content SendSmsPopupInput-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">短信内容</label>
            <div class="col-sm-9">
                <textarea id="message" rows="5" cols="30" class="form-control" data-title="短信模板" data-rangelength="[0,256]" style="width: 400px;"></textarea>
            </div>
        </div>

        <div class="help-info prompt-open" id="promptFold">
            <label><i class="fa fa-hand-pointer-o"></i>温馨提示</label>
            <ol>
                <li>
                    请勿更改短信内容中的签名；
                </li>
                <li class="weight" style="color: red">
                    短信内容必须合法、合规，任何发送的短信内容系统都将自动监控，一旦发现有任何违规短信内容，公司将作出相应处罚；
                </li>
            </ol>
        </div>
    </form>

    <%--添加按钮--%>
    <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="btnSave"><i class="fa fa-send"></i>发送</button>
        <button type="button" class="btn btn-default" id="btnClose"><i class="fa fa-remove"></i>关闭</button>
    </div>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_TsSmsInfo}"/>

</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/desktop/studentinfo/SendSmsPopupInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function SendSmsPopupInput_Page_Load() {
        SysApp.Crm.SendSmsPopupInputIns = new SysApp.Crm.SendSmsPopupInput();
        var instance = SysApp.Crm.SendSmsPopupInputIns;
        instance.selfInstance = "SysApp.Crm.SendSmsPopupInputIns";
        instance.clientID = "SendSmsPopupInput";
        instance.controller = "${ctx}/crm/resumebelong/";

        instance.init();
    }

    SendSmsPopupInput_Page_Load();
    //]]>
</script>