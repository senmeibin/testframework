<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content SubwayInput-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <input id="uid" type="hidden">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">城市</label>
            <div class="col-sm-9">
                <ctag:ComboTree valueDomId="cityUid" textDomId="cityName" showValidateError="false" parentInstance="SysApp.Cmn.SubwayInputIns" dataTitle="城市"
                                selectParent="false" nodeName="regionName" idKey="uid" parentIdKey="parentUid" style="width:310px" dataUrl="${ctx}/cmn/region/getAllRegion/2"/>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">线路号</label>
            <div class="col-sm-9">
                <input id="subwayNo" type="text" class="form-control required" data-title="线路号" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                <label id="subwayNo_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">表示顺序</label>
            <div class="col-sm-9">
                <input id="dispSeq" type="text" class="form-control required" data-title="表示顺序" maxlength="5" data-rangelength="[0,5]" data-range="[0,99999]" data-digits="true" style="width: 350px;"/>
                <label id="dispSeq_Error" class="validator-error"/>
            </div>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Subway}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Subway}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/subway/SubwayInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function SubwayInput_Page_Load() {
        SysApp.Cmn.SubwayInputIns = new SysApp.Cmn.SubwayInput();
        var instance = SysApp.Cmn.SubwayInputIns;
        instance.selfInstance = "SysApp.Cmn.SubwayInputIns";
        instance.clientID = "SubwayInput";
        instance.controller = "${ctx}/cmn/subway/";

        instance.init();
    }

    SubwayInput_Page_Load();
    //]]>
</script>