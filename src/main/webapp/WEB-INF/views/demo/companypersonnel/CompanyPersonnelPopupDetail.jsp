<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CompanyPersonnelDetail-MainContent" style="width: 1200px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <ctag:Fold id="divBaseInfo" name="综合信息"></ctag:Fold>
        <div id="divBaseInfo" class="separate-block clearfix">
            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">企业名称</label>
                <label class="col-lg-4 control-label content-label" id="companyName"></label>
                <label class="col-lg-2 control-label">上年年末从业人员总数</label>
                <label class="col-lg-4 control-label content-label" id="lastYearTotalEmployees"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">博士毕业人数</label>
                <label class="col-lg-4 control-label content-label" id="numberOfDoctor"></label>
                <label class="col-lg-2 control-label">留学归国人员数</label>
                <label class="col-lg-4 control-label content-label" id="numberOfReturnedStudents"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">千人计划数</label>
                <label class="col-lg-4 control-label content-label" id="numberOfThousandsPlans"></label>
                <label class="col-lg-2 control-label">应届毕业生</label>
                <label class="col-lg-4 control-label content-label" id="numberOfGraduates"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">大专以上毕业人数</label>
                <label class="col-lg-4 control-label content-label" id="numberOfCollegeGraduates"></label>
                <label class="col-lg-2 control-label">上海户籍员工数量</label>
                <label class="col-lg-4 control-label content-label" id="numberOfShanghai"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">外籍员工数量</label>
                <label class="col-lg-4 control-label content-label" id="numberOfForeign"></label>
            </div>
        </div>

        <ctag:Fold id="divWorkProperty" name="按工作性质分" marginTop="10px"></ctag:Fold>
        <div id="divWorkProperty" class="separate-block clearfix">
            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">行政管理人员</label>
                <label class="col-lg-4 control-label content-label" id="numberOfAdministrativeStaff"></label>

                <label class="col-lg-2 control-label">市场营销人员</label>
                <label class="col-lg-4 control-label content-label" id="numberOfMarketingStaff"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">研发设计人员</label>
                <label class="col-lg-4 control-label content-label" id="numberOfResearch"></label>
                <label class="col-lg-2 control-label">领导管理人员</label>
                <label class="col-lg-4 control-label content-label" id="numberOfLead"></label>
            </div>
        </div>

        <ctag:Fold id="divEducation" name="按学历分" marginTop="10px"></ctag:Fold>
        <div id="divEducation" class="separate-block clearfix">
            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">硕士毕业人数</label>
                <label class="col-lg-4 control-label content-label" id="numberOfMaster"></label>
                <label class="col-lg-2 control-label">本科毕业人数</label>
                <label class="col-lg-4 control-label content-label" id="numberOfUndergraduate"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">中专毕业人数</label>
                <label class="col-lg-4 control-label content-label" id="numberOfSecondaryVocational "></label>

                <label class="col-lg-2 control-label">高中以下人数</label>
                <label class="col-lg-4 control-label content-label" id="numberOfHighSchool"></label>
            </div>
        </div>

        <ctag:Fold id="divTechnicalTitles" name="按技术职称分" marginTop="10px"></ctag:Fold>
        <div id="divTechnicalTitles" class="separate-block clearfix">
            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">高级职称人数</label>
                <label class="col-lg-4 control-label content-label" id="numberOfSeniorTitle"></label>
                <label class="col-lg-2 control-label">中级职称人数</label>
                <label class="col-lg-4 control-label content-label" id="numberOfIntermediateTitle"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">初级职称人数</label>
                <label class="col-lg-4 control-label content-label" id="numberOfJuniorTitle"></label>
                <label class="col-lg-2 control-label">无职称人数</label>
                <label class="col-lg-4 control-label content-label" id="numberOfNoneTitle"></label>
            </div>
        </div>

        <ctag:Fold id="divRemark" name="备注信息" marginTop="10px"></ctag:Fold>
        <div id="divRemark" class="separate-block clearfix">
            <div class="form-group form-inline">
                <label class="col-lg-2 control-label">备注</label>
                <label class="col-lg-8 control-label content-label" id="remark" data-textarea="true"></label>
            </div>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companypersonnel/CompanyPersonnelDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyPersonnelDetail_Page_Load() {
        SysApp.Demo.CompanyPersonnelDetailIns = new SysApp.Demo.CompanyPersonnelDetail();
        var instance = SysApp.Demo.CompanyPersonnelDetailIns;
        instance.selfInstance = "SysApp.Demo.CompanyPersonnelDetailIns";
        instance.clientID = "CompanyPersonnelDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/demo/companypersonnel/";
        instance.init();
    }

    CompanyPersonnelDetail_Page_Load();
    //]]>
</script>