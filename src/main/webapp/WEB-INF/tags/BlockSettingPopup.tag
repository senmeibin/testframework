<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ attribute name="pageInstance" type="java.lang.String" required="true" %>
<%@ attribute name="settingType" type="java.lang.String" required="false" %>
<%@ attribute name="width" type="java.lang.String" required="false" %>
<%--画面模式，解决不同画面使用相同的js实例的业务场景--%>
<%@ attribute name="pageMode" type="java.lang.String" required="false" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content BlockSetting-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader modalTitle=""></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal" style=" height: 450px;overflow-y: auto">
        <div class="form-group form-inline">
            <input type="hidden" id="pageInstance">
            <!--内容开始-->
            <table class="table table-bordered app-table-list" id="settingTable">
            </table>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true"></ctag:ModalFooter>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function BlockSetting_Page_Load() {
        SysApp.Cmn.BlockSettingIns = new SysApp.Cmn.BlockSetting();
        var instance = SysApp.Cmn.BlockSettingIns;
        instance.selfInstance = "SysApp.Cmn.BlockSettingIns";
        instance.pageInstance = "${pageInstance}";
        instance.settingType = "${settingType}";
        instance.width = "${width}";
        instance.clientID = "BlockSetting";
        instance.controller = "${ctx}/cmn/customsetting/";
        instance.pageMode = "${pageMode}";
        instance.init();
    }
    $(function () {
        BlockSetting_Page_Load();
    });
    //]]>
</script>