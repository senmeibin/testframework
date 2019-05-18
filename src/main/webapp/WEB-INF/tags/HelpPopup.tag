<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%--调用画面JS实例字符串--%>
<%@ attribute name="pageInstance" type="java.lang.String" required="true" %>
<%--画面模式，解决不同画面使用相同的js实例的业务场景--%>
<%@ attribute name="pageMode" type="java.lang.String" required="false" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content Help-MainContent" style="width: 1300px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <%--富文本区域--%>
        <div id="divParameters"></div>
    </form>

    <%--Footer--%>
    <div class="modal-footer">
        <shiro:hasAnyRoles name="SystemManagement,ItSupport">
            <button type="button" class="btn btn-primary" id="btnEdit"><i class="fa fa-edit"></i>我要编辑</button>
            <button type="button" class="btn btn-primary" id="btnSave"><i class="fa fa-save"></i>保存</button>
        </shiro:hasAnyRoles>
        <button type="button" class="btn btn-default" id="btnClose"><i class="fa fa-remove"></i>关闭</button>
    </div>
    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Help}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Help}"/>
</div>
<shiro:hasAnyRoles name="SystemManagement,ItSupport">
    <script type="text/javascript" src="${staticContentsServer}/static/plugins/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/plugins/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
</shiro:hasAnyRoles>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function Help_Page_Load() {
        SysApp.Cmn.HelpIns = new SysApp.Cmn.Help();
        var instance = SysApp.Cmn.HelpIns;
        instance.selfInstance = "SysApp.Cmn.HelpIns";
        instance.pageInstance = "${pageInstance}";
        instance.settingType = "4";
        instance.clientID = "Help";
        instance.pageMode = "${pageMode}"
        instance.controller = "${ctx}/cmn/customsetting/";
        instance.isItSupport = ${subject.hasRole("ItSupport")};
        instance.isSystemManagement = ${subject.hasRole("SystemManagement")};
        instance.init();
    }
    $(function () {
        Help_Page_Load();
    });
    //]]>
</script>