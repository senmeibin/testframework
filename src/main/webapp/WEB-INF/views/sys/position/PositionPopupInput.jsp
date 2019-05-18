<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content PositionInput-MainContent" style="width: 800px; display: none;">
    <ctag:ModalHeader modalTitle="职位编辑"></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <div class="form-group" style="display: none">
            <div>
                <input id="uid" type="hidden">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">职位名称</label>
            <div class="col-sm-9">
                <input id="positionName" type="text" class="form-control required duplication" data-title="职位名称" maxlength="32" data-rangelength="[2,32]" style="width: 350px;">
            </div>
        </div>
        <div class="form-group hidden">
            <label class="col-sm-3 control-label">职位编号</label>
            <div class="col-sm-9">
                <input id="positionCode" type="text" class="form-control duplication" data-title="职位编号" maxlength="16" data-rangelength="[0,16]" style="width: 350px;">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">职位等级</label>
            <div class="col-sm-9">
                <select id="dispSeq" class="form-control required" data-title="职位等级" style="width: 350px;">
                    <option value="">请选择</option>
                    <option value="1">一级</option>
                    <option value="2">二级</option>
                    <option value="3">三级</option>
                    <option value="4">四级</option>
                    <option value="5">五级</option>
                    <option value="6">六级</option>
                    <option value="7">七级</option>
                    <option value="8">八级</option>
                    <option value="9">九级</option>
                    <option value="10">十级</option>
                </select>
            </div>
        </div>
        <div class="form-group" id="div_record_status">
            <label class="col-sm-3 control-label">是否启用</label>
            <div class="col-sm-9">
                <input type="checkbox" id="recordStatus" class="switch"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">备注</label>
            <div class="col-sm-9">
                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 350px;"></textarea>
            </div>
        </div>
    </form>
    <%--操作按钮区域--%>
    <ctag:ModalFooter showDeleteButton="true" showSaveButton="true"></ctag:ModalFooter>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Position}"/>
</div>
<script src="${staticContentsServer}/static/js/sys/position/PositionInput.js"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function PositionInput_Page_Load() {
        SysApp.Sys.PositionInputIns = new SysApp.Sys.PositionInput();
        var instance = SysApp.Sys.PositionInputIns;

        instance.selfInstance = "SysApp.Sys.PositionInputIns";
        instance.clientID = "PositionInput";
        instance.controller = "${ctx}/sys/position/";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    PositionInput_Page_Load();
    //]]>
</script>
