<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/CompanyExtendedCommon.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyteam/CompanyTeamInnerInput.js?${version}"></script>

<div class="panel-body CompanyTeamInnerInput-MainContent" id="companyTeamInnerInputDiv" style="display: none;padding-top:0px;">
    <div style="border: 1px solid #09c; border-top-width: 3px; padding: 15px 0;">
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm" class="form-horizontal">
            <input id="uid" type="hidden" data-allow-clear="true">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">姓名</label>
                <div class="col-lg-3">
                    <input id="name" type="text" class="form-control required" data-title="姓名" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                    <label id="name_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">出生日期</label>
                <div class="col-lg-3">
                    <ctag:CalendarSelect id="birthDate" title="出生日期" showValidateError="true" width="181px"></ctag:CalendarSelect>
                </div>
                <label class="col-lg-1 control-label">部门</label>
                <div class="col-lg-3">
                    <input id="dept" type="text" class="form-control" data-title="部门" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                    <label id="dept_Error" class="validator-error"></label>
                </div>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">职务</label>
                <div class="col-lg-3">
                    <input id="position" type="text" class="form-control" data-title="职务" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                    <label id="position_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">电话</label>
                <div class="col-lg-3">
                    <input id="phone" type="text" class="form-control" data-title="电话" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                    <label id="phone_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">传真</label>
                <div class="col-lg-3">
                    <input id="fax" type="text" class="form-control" data-title="传真" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                    <label id="fax_Error" class="validator-error"></label>
                </div>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">手机</label>
                <div class="col-lg-3">
                    <input id="mobile" type="text" class="form-control" data-title="手机" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                    <label id="mobile_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">证件类型</label>
                <div class="col-lg-3">
                    <select id="certificateTypeCd" class="form-control" data-title="证件类型" style="width: 220px;">
                        <option value="">请选择</option>
                    </select>
                    <label id="certificateTypeCd_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">证件号码</label>
                <div class="col-lg-3">
                    <input id="certificateNo" type="text" class="form-control" data-title="证件号码" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                    <label id="certificateNo_Error" class="validator-error"></label>
                </div>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">电子邮件</label>
                <div class="col-lg-3">
                    <input id="mail" type="text" class="form-control" data-title="电子邮件" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                    <label id="mail_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">学历</label>
                <div class="col-lg-3">
                    <select id="educationCd" class="form-control" data-title="学历" style="width: 220px;">
                        <option value="">请选择</option>
                    </select>
                    <label id="educationCd_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">目前最高职称</label>
                <div class="col-lg-3">
                    <input id="highestJobTitle" type="text" class="form-control" data-title="目前最高职称" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                    <label id="highestJobTitle_Error" class="validator-error"></label>
                </div>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">初次工作年份</label>
                <div class="col-lg-3">
                    <input id="firstWorkingYear" type="text" class="form-control" data-title="初次工作年份" maxlength="4" data-rangelength="[0,4]" style="width: 220px;"/>
                    <label id="firstWorkingYear_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">本公司股东</label>
                <div class="col-lg-3">
                    <select id="shareholdersCd" class="form-control" data-title="本公司股东" style="width: 220px;">
                        <option value="">请选择</option>
                    </select>
                    <label id="shareholdersCd_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">实际负责人</label>
                <div class="col-lg-3">
                    <select id="actualPersonCd" class="form-control" data-title="实际负责人" style="width: 220px;">
                        <option value="">请选择</option>
                    </select>
                    <label id="actualPersonCd_Error" class="validator-error"></label>
                </div>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">连续创业</label>
                <div class="col-lg-3">
                    <select id="continuousBusinessCd" class="form-control" data-title="连续创业" style="width: 220px;">
                        <option value="">请选择</option>
                    </select>
                    <label id="continuousBusinessCd_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">创业者特征</label>
                <div class="col-lg-3">
                    <input id="entrepreneurFeature" type="text" class="form-control" data-title="创业者特征" maxlength="64" data-rangelength="[0,64]" style="width: 220px;"/>
                    <label id="entrepreneurFeature_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">千人计划</label>
                <div class="col-lg-3">
                    <select id="thousandsPlansCd" class="form-control" data-title="千人计划" style="width: 220px;">
                        <option value="">请选择</option>
                    </select>
                    <label id="thousandsPlansCd_Error" class="validator-error"></label>
                </div>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">千人计划批次号</label>
                <div class="col-lg-3">
                    <input id="thousandsPlansNo" type="text" class="form-control" data-title="千人计划批次号" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                    <label id="thousandsPlansNo_Error" class="validator-error"></label>
                </div>
                <label class="col-lg-1 control-label">大学生科技企业</label>
                <div class="col-lg-3">
                    <select id="collegeTechnologyEnterprisesCd" class="form-control" data-title="大学生科技企业" style="width: 220px;">
                        <option value="">请选择</option>
                    </select>
                    <label id="collegeTechnologyEnterprisesCd_Error" class="validator-error"></label>
                </div>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">备注</label>
                <div class="col-lg-8">
                    <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 98%;"></textarea>
                    <label id="remark_Error" class="validator-error"></label>
                </div>
            </div>
        </form>

        <div class="text-center margin-top-space">
            <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存核心人员</button>
            <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
            <button type="button" class="btn btn-default btn-100px" id="btnClose"><i class="fa fa-close"></i>关闭</button>
        </div>

        <%--原始对象Json字符串[实体属性校验用]--%>
        <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_CompanyTeam}"/>

        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyTeam}"/>
    </div>
</div>

<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyTeamInnerInput_Page_Load() {
        SysApp.Demo.CompanyTeamInnerInputIns = new SysApp.Demo.CompanyTeamInnerInput();
        var instance = SysApp.Demo.CompanyTeamInnerInputIns;
        instance.selfInstance = "SysApp.Demo.CompanyTeamInnerInputIns";
        instance.controller = "${ctx}/demo/companyteam/";
        instance.clientID = "CompanyTeamInnerInput";

        instance.init();
    }

    CompanyTeamInnerInput_Page_Load();
    //]]>
</script>