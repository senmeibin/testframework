<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/CompanyExtendedCommon.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companypersonnel/CompanyPersonnelInnerInput.js?${version}"></script>

<div class="panel-body CompanyPersonnelInnerInput-MainContent" id="companyPersonnelInnerInputDiv" style="display: none;padding-top:0px;">
    <div style="border: 1px solid #09c; border-top-width: 3px; padding: 15px 15px;">
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm" class="form-horizontal">
            <input id="uid" type="hidden" data-allow-clear="true">
            <ctag:Fold id="divBaseInfo" name="综合信息"></ctag:Fold>
            <div id="divBaseInfo" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">上年年末从业人员总数</label>
                    <div class="col-lg-4">
                        <input id="lastYearTotalEmployees" type="text" class="form-control" data-title="上年年末从业人员总数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="lastYearTotalEmployees_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">博士毕业人数</label>
                    <div class="col-lg-4">
                        <input id="numberOfDoctor" type="text" class="form-control" data-title="博士毕业人数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="numberOfDoctor_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">留学归国人员数</label>
                    <div class="col-lg-4">
                        <input id="numberOfReturnedStudents" type="text" class="form-control" data-title="留学归国人员数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="numberOfReturnedStudents_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">千人计划数</label>
                    <div class="col-lg-4">
                        <input id="numberOfThousandsPlans" type="text" class="form-control" data-title="千人计划数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="numberOfThousandsPlans_Error" class="validator-error"></label>
                    </div>

                </div>

                <div class="form-group form-inline" marginTop="10px">
                    <label class="col-lg-2 control-label">应届毕业生</label>
                    <div class="col-lg-4">
                        <input id="numberOfGraduates" type="text" class="form-control" data-title="应届毕业生" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="numberOfGraduates_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">大专以上毕业人数</label>
                    <div class="col-lg-4">
                        <input id="numberOfCollegeGraduates" type="text" class="form-control" data-title="大专以上毕业人数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="numberOfCollegeGraduates_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">上海户籍员工数量</label>
                    <div class="col-lg-4">
                        <input id="numberOfShanghai" type="text" class="form-control" data-title="上海户籍员工数量" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="numberOfShanghai_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">外籍员工数量</label>
                    <div class="col-lg-4">
                        <input id="numberOfForeign" type="text" class="form-control" data-title="外籍员工数量" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="numberOfForeign_Error" class="validator-error"></label>
                    </div>
                </div>
            </div>

            <ctag:Fold id="divWorkProperty" name="按工作性质分" marginTop="10px"></ctag:Fold>
            <div id="divWorkProperty" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">行政管理人员</label>
                    <div class="col-lg-4">
                        <input id="numberOfAdministrativeStaff" type="text" class="form-control" data-title="行政管理人员" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="numberOfAdministrativeStaff_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">市场营销人员</label>
                    <div class="col-lg-4">
                        <input id="numberOfMarketingStaff" type="text" class="form-control" data-title="市场营销人员" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="numberOfMarketingStaff_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">研发设计人员</label>
                    <div class="col-lg-4">
                        <input id="numberOfResearch" type="text" class="form-control" data-title="研发设计人员" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="numberOfResearch_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">领导管理人员</label>
                    <div class="col-lg-4">
                        <input id="numberOfLead" type="text" class="form-control" data-title="领导管理人员" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="numberOfLead_Error" class="validator-error"></label>
                    </div>
                </div>
            </div>

            <ctag:Fold id="divEducation" name="按学历分" marginTop="10px"></ctag:Fold>
            <div id="divEducation" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">硕士毕业人数</label>
                    <div class="col-lg-4">
                        <input id="numberOfMaster" type="text" class="form-control" data-title="硕士毕业人数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="numberOfMaster_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">本科毕业人数</label>
                    <div class="col-lg-4">
                        <input id="numberOfUndergraduate" type="text" class="form-control" data-title="本科毕业人数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="numberOfUndergraduate_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">

                    <label class="col-lg-2 control-label">中专毕业人数</label>
                    <div class="col-lg-4">
                        <input id="numberOfSecondaryVocational " type="text" class="form-control" data-title="中专毕业人数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="numberOfSecondaryVocational _Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">高中以下人数</label>
                    <div class="col-lg-4">
                        <input id="numberOfHighSchool" type="text" class="form-control" data-title="高中以下人数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="numberOfHighSchool_Error" class="validator-error"></label>
                    </div>
                </div>
            </div>

            <ctag:Fold id="divTechnicalTitles" name="按技术职称分" marginTop="10px"></ctag:Fold>
            <div id="divTechnicalTitles" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">高级职称人数</label>
                    <div class="col-lg-4">
                        <input id="numberOfSeniorTitle" type="text" class="form-control" data-title="高级职称人数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="numberOfSeniorTitle_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">中级职称人数</label>
                    <div class="col-lg-4">
                        <input id="numberOfIntermediateTitle" type="text" class="form-control" data-title="中级职称人数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="numberOfIntermediateTitle_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">初级职称人数</label>
                    <div class="col-lg-4">
                        <input id="numberOfJuniorTitle" type="text" class="form-control" data-title="初级职称人数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="numberOfJuniorTitle_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">无职称人数</label>
                    <div class="col-lg-4">
                        <input id="numberOfNoneTitle" type="text" class="form-control" data-title="无职称人数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="numberOfNoneTitle_Error" class="validator-error"></label>
                    </div>
                </div>
            </div>

            <ctag:Fold id="divRemark" name="备注信息" marginTop="10px"></ctag:Fold>
            <div id="divRemark" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-lg-2 control-label">备注</label>
                    <div class="col-lg-8">
                        <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 98%;"></textarea>
                        <label id="remark_Error" class="validator-error"></label>
                    </div>
                </div>
            </div>
        </form>

        <div class="text-center margin-top-space">
            <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存人员结构</button>
            <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
            <button type="button" class="btn btn-default btn-100px" id="btnClose"><i class="fa fa-close"></i>关闭</button>
        </div>

        <%--原始对象Json字符串[实体属性校验用]--%>
        <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_CompanyPersonnel}"/>

        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyPersonnel}"/>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyPersonnelInnerInput_Page_Load() {
        SysApp.Demo.CompanyPersonnelInnerInputIns = new SysApp.Demo.CompanyPersonnelInnerInput();
        var instance = SysApp.Demo.CompanyPersonnelInnerInputIns;
        instance.selfInstance = "SysApp.Demo.CompanyPersonnelInnerInputIns";
        instance.controller = "${ctx}/demo/companypersonnel/";
        instance.clientID = "CompanyPersonnelInnerInput";

        instance.init();
    }

    CompanyPersonnelInnerInput_Page_Load();
    //]]>
</script>