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
<div id="ctlFrame" class="modal-content ColumnSetting-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader modalTitle=""></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <input id="uid" type="hidden">

        <div class="form-group form-inline">
            <input type="hidden" id="pageInstance">
            <div id="columnSettingList" style="padding-left: 15px;padding-top: 5px;">
            </div>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_ColumnSetting}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_ColumnSetting}"/>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ColumnSetting_Page_Load() {
        SysApp.Cmn.ColumnSettingIns = new SysApp.Cmn.ColumnSetting();
        var instance = SysApp.Cmn.ColumnSettingIns;
        instance.selfInstance = "SysApp.Cmn.ColumnSettingIns";
        instance.pageInstance = "${pageInstance}";
        instance.settingType = "${settingType}";
        instance.width = "${width}";
        instance.clientID = "ColumnSetting";
        instance.controller = "${ctx}/cmn/columnsetting/";
        instance.pageMode = "${pageMode}"
        instance.init();
    }

    $(function () {
        ColumnSetting_Page_Load();
    });
    //]]>
</script>