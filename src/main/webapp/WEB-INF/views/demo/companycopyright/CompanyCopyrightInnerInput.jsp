<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/CompanyExtendedCommon.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companycopyright/CompanyCopyrightInnerInput.js?${version}"></script>


<div class="panel-body CompanyCopyrightInnerInput-MainContent" id="companyCopyrightInnerInputDiv" style="display: none;padding-top:0px;">
    <div style="border: 1px solid #09c; border-top-width: 3px; padding: 15px 15px;">
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm" class="form-horizontal">
            <input id="uid" type="hidden" data-allow-clear="true">
            <ctag:Fold id="divBaseInfo" name="基本信息"></ctag:Fold>
            <div id="divBaseInfo" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label">年份</label>
                    <div class="col-lg-3">
                        <input id="year" type="text" class="form-control required" data-title="年份" maxlength="4" data-rangelength="[0,4]" style="width: 220px;"/>
                        <label id="year_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">当前承担国家级科技计划项目数</label>
                    <div class="col-lg-3">
                        <input id="nationalPlanningProjectNum" type="text" class="form-control" data-title="当前承担国家级科技计划项目数" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;"/>
                        <label id="nationalPlanningProjectNum_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">当前获得省级以上奖励(万元)</label>
                    <div class="col-lg-3">
                        <input id="provincialLevelNum" type="text" class="form-control" data-title="当前获得省级以上奖励(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;"
                               data-money="true"/>
                        <label id="provincialLevelNum_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label">购买（被许可）国外专利</label>
                    <div class="col-lg-3">
                        <input id="foreignPatentNum" type="text" class="form-control" data-title="购买（被许可）国外专利" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="foreignPatentNum_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">技术合同交易数量</label>
                    <div class="col-lg-3">
                        <input id="technicalContractNum" type="text" class="form-control" data-title="技术合同交易数量" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="technicalContractNum_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">技术合同交易额(万元)</label>
                    <div class="col-lg-3">
                        <input id="technicalContractAmount" type="text" class="form-control" data-title="技术合同交易额(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;"
                               data-money="true"/>
                        <label id="technicalContractAmount_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label">研发加计备案数</label>
                    <div class="col-lg-3">
                        <input id="rdCasesNum" type="text" class="form-control" data-title="研发加计备案数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="rdCasesNum_Error" class="validator-error"></label>
                    </div>
                </div>
            </div>

            <ctag:Fold id="divResearchActivities" name="科研活动" marginTop="10px"></ctag:Fold>
            <div id="divResearchActivities" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label">累计工程中心数</label>
                    <div class="col-lg-3">
                        <input id="totalEngineeringNum" type="text" class="form-control" data-title="累计工程中心数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="totalEngineeringNum_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label">其中，国家级</label>
                    <div class="col-lg-3">
                        <input id="enNationalLevelNum" type="text" class="form-control" data-title="国家级" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="enNationalLevelNum_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">其中，省级</label>
                    <div class="col-lg-3">
                        <input id="enProvincialLevelNum" type="text" class="form-control" data-title="省级" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="enProvincialLevelNum_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">其中，市级</label>
                    <div class="col-lg-3">
                        <input id="enMunicipalLevelNum" type="text" class="form-control" data-title="市级" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="enMunicipalLevelNum_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label">累计项目数量</label>
                    <div class="col-lg-3">
                        <input id="totalProjectNum" type="text" class="form-control" data-title="累计项目数量" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="totalProjectNum_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label">其中，国家级</label>
                    <div class="col-lg-3">
                        <input id="projectNationalLevelNum" type="text" class="form-control" data-title="国家级" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="projectNationalLevelNum_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">其中，省级</label>
                    <div class="col-lg-3">
                        <input id="projectProvincialLevelNum" type="text" class="form-control" data-title="省级" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="projectProvincialLevelNum_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">其中，市级</label>
                    <div class="col-lg-3">
                        <input id="projectMunicipalLevelNum" type="text" class="form-control" data-title="市级" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="projectMunicipalLevelNum_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label">累计获得国家资助经费金额(万元)</label>
                    <div class="col-lg-3">
                        <input id="stateSubsidyAmount" type="text" class="form-control" data-title="累计获得国家资助经费金额(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;"
                               data-money="true"/>
                        <label id="stateSubsidyAmount_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">累计获得省级资助经费金额(万元)</label>
                    <div class="col-lg-3">
                        <input id="provincialSubsidyAmount" type="text" class="form-control" data-title="累计获得省级资助经费金额(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;"
                               data-money="true"/>
                        <label id="provincialSubsidyAmount_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">累计获得市级资助经费金额(万元)</label>
                    <div class="col-lg-3">
                        <input id="municipalSubsidyAmount" type="text" class="form-control" data-title="累计获得市级资助经费金额(万元)" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 220px;"
                               data-money="true"/>
                        <label id="municipalSubsidyAmount_Error" class="validator-error"></label>
                    </div>
                </div>

            </div>

            <ctag:Fold id="divIntellectualPropertyRight" name="知识产权" marginTop="10px"></ctag:Fold>
            <div id="divIntellectualPropertyRight" class="separate-block clearfix">

                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label">当年知识产权申请数</label>
                    <div class="col-lg-3">
                        <input id="iprApplicationsNum" type="text" class="form-control" data-title="当年知识产权申请数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="iprApplicationsNum_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">其中，当年发明专利申请数</label>
                    <div class="col-lg-3">
                        <input id="patentInventionNum" type="text" class="form-control" data-title="当年发明专利申请数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="patentInventionNum_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label">当前知识产权授权数</label>
                    <div class="col-lg-3">
                        <input id="intellectualPropertyNum" type="text" class="form-control" data-title="当前知识产权授权数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="intellectualPropertyNum_Error" class="validator-error"></label>
                    </div>

                    <label class="col-lg-1 control-label">其中，发明专利授权数</label>
                    <div class="col-lg-3">
                        <input id="inventionPatentsAuthNum" type="text" class="form-control" data-title="发明专利授权数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="inventionPatentsAuthNum_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label">拥有有效知识产权数</label>
                    <div class="col-lg-3">
                        <input id="effectiveIntellectualNum" type="text" class="form-control" data-title="拥有有效知识产权数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="effectiveIntellectualNum_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">其中，发明专利数</label>
                    <div class="col-lg-3">
                        <input id="inventionPatentsNum" type="text" class="form-control" data-title="发明专利数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="inventionPatentsNum_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">软件著作权登记数</label>
                    <div class="col-lg-3">
                        <input id="softwareCopyrightNum" type="text" class="form-control" data-title="软件著作权登记数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="softwareCopyrightNum_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label">植物新品种</label>
                    <div class="col-lg-3">
                        <input id="newPlantNum" type="text" class="form-control" data-title="植物新品种" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="newPlantNum_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">集成电路布图设计登记数</label>
                    <div class="col-lg-3">
                        <input id="icLayoutNum" type="text" class="form-control" data-title="集成电路布图设计登记数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="icLayoutNum_Error" class="validator-error"></label>
                    </div>
                    <label class="col-lg-1 control-label">其他知识产权登记数</label>
                    <div class="col-lg-3">
                        <input id="otherIntellectualNum" type="text" class="form-control" data-title="其他知识产权登记数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 220px;"/>
                        <label id="otherIntellectualNum_Error" class="validator-error"></label>
                    </div>
                </div>
            </div>

            <ctag:Fold id="divRemark" name="备注信息" marginTop="10px"></ctag:Fold>
            <div id="divRemark" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-lg-1 control-label">备注</label>
                    <div class="col-lg-8">
                        <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 98%;"></textarea>
                        <label id="remark_Error" class="validator-error"></label>
                    </div>
                </div>
            </div>
        </form>

        <div class="text-center margin-top-space">
            <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存专利版权</button>
            <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
            <button type="button" class="btn btn-default btn-100px" id="btnClose"><i class="fa fa-close"></i>关闭</button>
        </div>

        <%--原始对象Json字符串[实体属性校验用]--%>
        <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_CompanyCopyright}"/>

        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyCopyright}"/>
    </div>
</div>

<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyCopyrightInnerInput_Page_Load() {
        SysApp.Demo.CompanyCopyrightInnerInputIns = new SysApp.Demo.CompanyCopyrightInnerInput();
        var instance = SysApp.Demo.CompanyCopyrightInnerInputIns;
        instance.selfInstance = "SysApp.Demo.CompanyCopyrightInnerInputIns";
        instance.controller = "${ctx}/demo/companycopyright/";
        instance.clientID = "CompanyCopyrightInnerInput";

        instance.init();
    }

    CompanyCopyrightInnerInput_Page_Load();
    //]]>
</script>