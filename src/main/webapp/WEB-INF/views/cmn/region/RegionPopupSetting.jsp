<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/region/RegionSetting.js?${version}"></script>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content RegionSetting-MainContent" style="width: 1000px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader hideMaximizationIcon="true" modalTitle="区域批量设置"></ctag:ModalHeader>

    <div class="modal-body" style="height: 450px;">
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm">
            <div class="col-md-2 form-group">
                <label>区域名称</label>
                <select id="uid" data-alias-table="main" class="form-control" data-title="区域名称" data-search-mode="=">
                    <option value="">请选择</option>
                </select>
            </div>
            <div class="col-md-2 form-group" style="width: 500px;">
                <label>
                    &nbsp;
                </label>
                <div>
                    <button id="btnSearch" type="button" class="btn btn-primary">
                        <i class="fa fa-search"></i>查询
                    </button>
                    <button id="btnBatchSave" type="button" class="btn btn-primary">
                        <i class="fa fa-save"></i>批量保存
                    </button>
                </div>
            </div>
        </form>
        <%--上次检索条件--%>
        <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Region}"/>
        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Region}"/>
        <div class="margin-top-space" id="divList" style="width: 100%; height:350px;">
        </div>
    </div>

    <div class="modal-footer">
        <button type="button" class="btn btn-default" id="btnClose"><i class="fa fa-remove"></i>关闭</button>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function RegionSetting_Page_Load() {
        SysApp.Cmn.RegionSettingIns = new SysApp.Cmn.RegionSetting();
        var instance = SysApp.Cmn.RegionSettingIns;

        instance.selfInstance = "SysApp.Cmn.RegionSettingIns";
        instance.clientID = "RegionSetting";
        instance.controller = "${ctx}/cmn/region/";

        instance.init();
    }

    $(function () {
        RegionSetting_Page_Load();
    });
    //]]>
</script>

