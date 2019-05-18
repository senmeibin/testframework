<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CompanyTeamDetail-MainContent" style="width: 1200px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <div class="form-group form-inline">
            <label class="col-lg-1 control-label">企业名称</label>
            <label class="col-lg-3 control-label content-label" id="companyName"></label>
            <label class="col-lg-1 control-label">姓名</label>
            <label class="col-lg-3 control-label content-label" id="name"></label>
            <label class="col-lg-1 control-label">出生日期</label>
            <label class="col-lg-3 control-label content-label" id="birthDate" data-date="true"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-1 control-label">部门</label>
            <label class="col-lg-3 control-label content-label" id="dept"></label>
            <label class="col-lg-1 control-label">职务</label>
            <label class="col-lg-3 control-label content-label" id="position"></label>
            <label class="col-lg-1 control-label">电话</label>
            <label class="col-lg-3 control-label content-label" id="phone"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-1 control-label">传真</label>
            <label class="col-lg-3 control-label content-label" id="fax"></label>
            <label class="col-lg-1 control-label">手机</label>
            <label class="col-lg-3 control-label content-label" id="mobile"></label>
            <label class="col-lg-1 control-label">证件类型</label>
            <label class="col-lg-3 control-label content-label" id="certificateTypeName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-1 control-label">证件号码</label>
            <label class="col-lg-3 control-label content-label" id="certificateNo"></label>
            <label class="col-lg-1 control-label">电子邮件</label>
            <label class="col-lg-3 control-label content-label" id="mail"></label>
            <label class="col-lg-1 control-label">学历</label>
            <label class="col-lg-3 control-label content-label" id="educationName"></label>
        </div>

        <div class="form-group form-inline">

            <label class="col-lg-1 control-label">目前最高职称</label>
            <label class="col-lg-3 control-label content-label" id="highestJobTitle"></label>
            <label class="col-lg-1 control-label">初次工作年份</label>
            <label class="col-lg-3 control-label content-label" id="firstWorkingYear"></label>
            <label class="col-lg-1 control-label">本公司股东</label>
            <label class="col-lg-3 control-label content-label" id="shareholdersName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-1 control-label">实际负责人</label>
            <label class="col-lg-3 control-label content-label" id="actualPersonName"></label>
            <label class="col-lg-1 control-label">连续创业</label>
            <label class="col-lg-3 control-label content-label" id="continuousBusinessName"></label>
            <label class="col-lg-1 control-label">创业者特征</label>
            <label class="col-lg-3 control-label content-label" id="entrepreneurFeature"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-1 control-label">千人计划</label>
            <label class="col-lg-3 control-label content-label" id="thousandsPlansName"></label>
            <label class="col-lg-1 control-label">千人计划批次号</label>
            <label class="col-lg-3 control-label content-label" id="thousandsPlansNo"></label>
            <label class="col-lg-1 control-label">大学生科技企业</label>
            <label class="col-lg-3 control-label content-label" id="collegeTechnologyEnterprisesName"></label>
        </div>

        <div class="form-group form-inline">
            <label class="col-lg-1 control-label">备注</label>
            <label class="col-lg-8 control-label content-label" id="remark" data-textarea="true"></label>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyteam/CompanyTeamDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyTeamDetail_Page_Load() {
        SysApp.Demo.CompanyTeamDetailIns = new SysApp.Demo.CompanyTeamDetail();
        var instance = SysApp.Demo.CompanyTeamDetailIns;
        instance.selfInstance = "SysApp.Demo.CompanyTeamDetailIns";
        instance.clientID = "CompanyTeamDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/demo/companyteam/";
        instance.init();
    }

    CompanyTeamDetail_Page_Load();
    //]]>
</script>