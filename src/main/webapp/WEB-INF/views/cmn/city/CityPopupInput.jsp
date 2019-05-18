<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CityInput-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <input id="uid" type="hidden">
        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">城市编号</label>
            <div class="col-sm-9">
                <input id="cityCd" type="text" class="form-control required duplication" data-title="城市编号" maxlength="8" data-rangelength="[0,8]" style="width: 350px; text-transform: uppercase; "/>
                <label id="cityCd_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">城市名称</label>
            <div class="col-sm-9">
                <input id="cityName" type="text" class="form-control required duplication" data-title="城市名称" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                <label id="cityName_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">表示顺序</label>
            <div class="col-sm-9">
                <input id="dispSeq" type="text" class="form-control" data-title="表示顺序" maxlength="5" data-rangelength="[0,5]" data-range="[0,99999]" data-digits="true" style="width: 350px;"/>
                <label id="dispSeq_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">所属大区</label>
            <div class="col-sm-9">
                <select id="areaUid" class="form-control required" data-title="所属大区" style="width: 350px;">
                    <option value="">请选择</option>
                </select>
                <label id="areaUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">上级城市</label>
            <div class="col-sm-9">
                <select id="parentCityUid" class="form-control" data-title="上级城市" style="width: 350px;">
                    <option value="">请选择</option>
                </select>
                <label id="parentCityUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">关联分公司</label>
            <div class="col-sm-9">
                <select id="deptUid" data-alias-table="main" class="form-control required" data-title="关联分公司" style="width: 350px;">
                    <option value="">请选择</option>
                </select>
                <label id="deptUid_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-3 control-label">备注</label>
            <div class="col-sm-9">
                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 350px;"></textarea>
                <label id="remark_Error" class="validator-error"></label>
            </div>
        </div>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="true"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_City}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_City}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/city/CityInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CityInput_Page_Load() {
        SysApp.Cmn.CityInputIns = new SysApp.Cmn.CityInput();
        var instance = SysApp.Cmn.CityInputIns;
        instance.selfInstance = "SysApp.Cmn.CityInputIns";
        instance.controller = "${ctx}/cmn/city/";
        instance.clientID = "CityInput";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    CityInput_Page_Load();
    //]]>
</script>