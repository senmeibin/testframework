<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content RegionInput-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <input id="uid" type="hidden">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">区域名称</label>
            <div class="col-sm-9">
                <input id="regionName" type="text" class="form-control required" data-title="区域名称" maxlength="60" data-rangelength="[0,60]" style="width: 350px;"/>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">上级地区</label>
            <div class="col-sm-9">
                <ctag:Select2 selectId="parentUid" dataTitle="上级地区" style="width:350px"/>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">地区层级</label>
            <div class="col-sm-9">
                <input id="regionGrade" type="text" class="form-control" data-title="区域层级" maxlength="5" data-rangelength="[0,5]" data-range="[0,99999999]" data-digits="true" style="width: 350px;"/>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">表示顺序</label>
            <div class="col-sm-9">
                <input id="dispSeq" type="text" class="form-control required" data-title="表示顺序" maxlength="5" data-rangelength="[0,5]" data-range="[0,99999999]" data-digits="true" style="width: 350px;"/>
            </div>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Region}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Region}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/region/RegionInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function RegionInput_Page_Load() {
        SysApp.Cmn.RegionInputIns = new SysApp.Cmn.RegionInput();
        var instance = SysApp.Cmn.RegionInputIns;
        instance.selfInstance = "SysApp.Cmn.RegionInputIns";
        instance.clientID = "RegionInput";
        instance.controller = "${ctx}/cmn/region/";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    RegionInput_Page_Load();
    //]]>
</script>