<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ attribute name="pageInstance" type="java.lang.String" required="true" %>
<%@ attribute name="settingType" type="java.lang.String" required="false" %>
<%@ attribute name="width" type="java.lang.String" required="false" %>
<%--画面模式，解决不同画面使用相同的js实例的业务场景--%>
<%@ attribute name="pageMode" type="java.lang.String" required="false" %>
<style>
    .screen-block {
        padding: 0 0px;
    }

    .screen-block .nav-pills > li > a {
        max-width: 190px;
        border-radius: 0;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .screen-block .nav > li > a {
        padding: 7px 15px;
        color: #333;
    }

    .screen-block .nav > li.active > a {
        color: #fff;
    }

    .screen-block .nav > li > a:hover {
        color: #fff;
        background: #337ab7;
    }

    .screen-block .nav > li {
        background: #e6e6e6;
    }

    .screen-block .screen-border {
        border: 1px solid #ccc;
        margin-top: 1px;
        padding-bottom: 15px;
    }

    .screen-block .screen-title {
        height: 32px;
        overflow: hidden;
        padding: 5px 15px 0px 15px;
    }

    .screen-block .screen-title span {
        display: block;
        border-bottom: 1px dotted #ccc;
        height: 18px;
        overflow: hidden;
    }

    .screen-block .screen-title h4 {
        font-size: 12px;
        margin: 0;
        line-height: 32px;
        font-weight: bold;
        color: #333;
        float: left;
        width: 80px;
        cursor: pointer;
    }

    .screen-block .screen-content {
        padding: 0 15px;
    }

    .screen-block .screen-name {
        border: 1px solid #ccc;
        border-top: 0;
        padding: 15px;
    }
</style>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content SearchSetting-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader modalTitle=""></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal" style="max-height: 500px; overflow: auto;">
        <div class="form-group form-inline">
            <input type="hidden" id="pageInstance">
            <!--内容开始-->
            <div class="screen-block">
                <ul id="searchConditionTitle" class="nav nav-pills">
                </ul>
                <div class="screen-border">
                    <div id="searchSettingList">

                    </div>
                    <div class="screen-content clearfix" style="margin-top: 20px;">
                        <label style="width:180px;">
                            <input id="checkAll" type="checkbox"/><label for="checkAll">全选择/全解除</label>
                        </label>
                        <label class="alert-danger">
                            <i class="fa fa-hand-pointer-o"></i>点击查询分类标题可 全选择/全解除 分类下的检索条件。
                        </label>
                    </div>
                </div>
                <div class="screen-name">
                    <label>查询条件名称：</label><input id="settingTitle" data-title="查询条件名称" type="text" size="64" data-length="64" class="form-control required"/>
                </div>
            </div>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_SearchSetting}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_SearchSetting}"/>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function SearchSetting_Page_Load() {
        SysApp.Cmn.SearchSettingIns = new SysApp.Cmn.SearchSetting();
        var instance = SysApp.Cmn.SearchSettingIns;
        instance.selfInstance = "SysApp.Cmn.SearchSettingIns";
        instance.pageInstance = "${pageInstance}";
        instance.settingType = "${settingType}";
        instance.width = "${width}";
        instance.clientID = "SearchSetting";
        instance.controller = "${ctx}/cmn/customsetting/";
        instance.pageMode = "${pageMode}"
        instance.init();
    }

    $(function () {
        SearchSetting_Page_Load();
    });
    //]]>
</script>