<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/student/StudentPopupImport.js?${version}"></script>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content StudentPopupImport-MainContent" style="width: 1200px; display: none;">
    <ctag:ModalHeader modalTitle="学员信息批量导入"></ctag:ModalHeader>

    <div class="modal-body">
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm" class="form-horizontal">
            <div class="form-group form-inline">
                <label class="col-sm-3 control-label">模板文件</label>
                <div class="col-sm-9" style="padding-top: 6px;">
                    <a href="${ctx}/template/crm/StudentTemplate.xlsx" style="color:#F00;text-decoration:underline;">学员信息模板文件下载</a>
                </div>
            </div>
            <div class="form-group form-inline">
                <label class="col-sm-3 control-label">导入文件</label>
                <div class="col-sm-9">
                    <ctag:FileImport fileId="file" clientID="StudentPopupImport"></ctag:FileImport>
                </div>
            </div>
        </form>
        <div style="max-height: 450px; overflow: auto">
            <%--导入结果汇总--%>
            <div id="divSummary"></div>
            <%--导入结果列表--%>
            <div id="divList"></div>
        </div>
        <div class="help-info prompt-open" id="promptFold">
            <label><i class="fa fa-hand-pointer-o"></i>操作说明</label>
            <ol>
                <li class="weight">
                    模板中必须数据列（红色列头）只要有一列为空，本行记录将被忽略不会被导入。
                </li>
            </ol>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <div class="modal-footer" onselectstart="return false;">
        <button type="button" class="btn btn-primary" id="btnSave"><i class="fa fa-upload"></i>导入</button>
        <button type="button" class="btn btn-default" id="btnClose"><i class="fa fa-remove"></i>关闭</button>
    </div>
</div>

<script type="text/javascript" language="javascript">
    //<![CDATA[
    function StudentPopupImport_Page_Load() {
        SysApp.Crm.StudentPopupImportIns = new SysApp.Crm.StudentPopupImport();
        var instance = SysApp.Crm.StudentPopupImportIns;
        instance.selfInstance = "SysApp.Crm.StudentPopupImportIns";
        instance.clientID = "StudentPopupImport";
        instance.popupPosition = "top";
        instance.controller = "${ctx}/crm/student/";
        instance.saveMethod = "${ctx}/crm/student/importStudent";
        instance.init();
    }

    StudentPopupImport_Page_Load();
    //]]>
</script>