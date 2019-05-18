<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content EmployeeDetail-MainContent" style="width: 1200px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--POPUP控件Body--%>
    <div class="modal-body form-horizontal">
        <ctag:Fold id="divBaseInfo" name="基本信息"></ctag:Fold>
        <div id="divBaseInfo" class="separate-block clearfix baseInfoClass">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">用户名</label>
                <label class="col-lg-3 control-label content-label" id="userCd"></label>
                <label class="col-lg-1 control-label">工号</label>
                <label class="col-lg-3 control-label content-label" id="userNumber"></label>
                <label class="col-lg-1 control-label">姓名</label>
                <label class="col-lg-3 control-label content-label" id="userName"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">性别</label>
                <label class="col-lg-3 control-label content-label" id="sexName"></label>
                <label class="col-lg-1 control-label">出生日期</label>
                <label class="col-lg-3 control-label content-label" id="birthday" data-date="true"></label>
                <label class="col-lg-1 control-label">年龄</label>
                <label class="col-lg-3 control-label content-label" id="age"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">身份证号</label>
                <label class="col-lg-3 control-label content-label" id="idCardNo"></label>
                <label class="col-lg-1 control-label">民族</label>
                <label class="col-lg-3 control-label content-label" id="ethnicityName"></label>
                <label class="col-lg-1 control-label">政治面貌</label>
                <label class="col-lg-3 control-label content-label" id="policyName"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">户口类型</label>
                <label class="col-lg-3 control-label content-label" id="residenceType"></label>
                <label class="col-lg-1 control-label">户口所在地</label>
                <label class="col-lg-3 control-label content-label" id="registeredResidence"></label>
                <label class="col-lg-1 control-label">籍贯</label>
                <label class="col-lg-3 control-label content-label" id="ancestralNativePlace"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">学历</label>
                <label class="col-lg-3 control-label content-label" id="educationName"></label>
                <label class="col-lg-1 control-label">学位</label>
                <label class="col-lg-3 control-label content-label" id="educationalDegreeName"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">毕业院校</label>
                <label class="col-lg-3 control-label content-label" id="graduateInstitutions"></label>
                <label class="col-lg-1 control-label">所学专业</label>
                <label class="col-lg-3 control-label content-label" id="major "></label>
            </div>
        </div>

        <ctag:Fold id="divPositionInfo" name="工作信息" marginTop="10px"></ctag:Fold>
        <div id="divPositionInfo" class="separate-block clearfix positionInfoClass">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">所属部门</label>
                <label class="col-lg-3 control-label content-label" id="deptFullName"></label>
                <label class="col-lg-1 control-label">岗位</label>
                <label class="col-lg-3 control-label content-label" id="positionName"></label>
                <label class="col-lg-1 control-label">办公地点</label>
                <label class="col-lg-3 control-label content-label" id="locationName"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">所属基地</label>
                <label class="col-lg-3 control-label content-label" id="baseName"></label>
                <label class="col-lg-1 control-label">用工方式</label>
                <label class="col-lg-3 control-label content-label" id="employmentType"></label>
                <label class="col-lg-1 control-label">职等</label>
                <label class="col-lg-3 control-label content-label" id="positionsGrade"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">试用期结束日期</label>
                <label class="col-lg-3 control-label content-label" id="probationEndDate" data-date="true"></label>
                <label class="col-lg-1 control-label">合同开始日期</label>
                <label class="col-lg-3 control-label content-label" id="contractStartDate" data-date="true"></label>
                <label class="col-lg-1 control-label">合同结束日期</label>
                <label class="col-lg-3 control-label content-label" id="contractEndDate" data-date="true"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">公积金帐号</label>
                <label class="col-lg-3 control-label content-label" id="accumulationFundAccount"></label>
                <label class="col-lg-1 control-label">开户银行</label>
                <label class="col-lg-3 control-label content-label" id="bankName"></label>
                <label class="col-lg-1 control-label">工资卡号</label>
                <label class="col-lg-3 control-label content-label" id="bankAccountNumber"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">人员状态</label>
                <label class="col-lg-3 control-label content-label" id="statusName"></label>
                <label class="col-lg-1 control-label">参加工作日期</label>
                <label class="col-lg-3 control-label content-label" id="firstJobDate" data-date="true"></label>
                <label class="col-lg-1 control-label">入职日期</label>
                <label class="col-lg-3 control-label content-label" id="entryDate" data-date="true"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">司龄</label>
                <label class="col-lg-3 control-label content-label" id="seniority"></label>
                <label class="col-lg-1 control-label">转正日期</label>
                <label class="col-lg-3 control-label content-label" id="positiveDate" data-date="true"></label>
                <label class="col-lg-1 control-label">离职日期</label>
                <label class="col-lg-3 control-label content-label" id="retireDate" data-date="true"></label>
            </div>
        </div>
        <ctag:Fold id="divContactInfo" name="联系方式" marginTop="10px"></ctag:Fold>
        <div id="divContactInfo" class="separate-block clearfix contactInfoClass">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">手机</label>
                <label class="col-lg-3 control-label content-label" id="mobile"></label>
                <label class="col-lg-1 control-label">固定电话</label>
                <label class="col-lg-3 control-label content-label" id="telephone"></label>
                <label class="col-lg-1 control-label">传值</label>
                <label class="col-lg-3 control-label content-label" id="fax"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">电子邮件</label>
                <label class="col-lg-3 control-label content-label" id="email"></label>
                <label class="col-lg-1 control-label">出生地</label>
                <label class="col-lg-3 control-label content-label" id="birthPlace"></label>
                <label class="col-lg-1 control-label">家庭住址</label>
                <label class="col-lg-3 control-label content-label" id="homeAddress"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">所在省</label>
                <label class="col-lg-3 control-label content-label" id="contactProvinceName"></label>
                <label class="col-lg-1 control-label">所在市</label>
                <label class="col-lg-3 control-label content-label" id="contactCityName"></label>
                <label class="col-lg-1 control-label">所在区县</label>
                <label class="col-lg-3 control-label content-label" id="contactCountyName"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">联系地址</label>
                <label class="col-lg-3 control-label content-label" id="contactAddress"></label>
            </div>
        </div>
        <ctag:Fold id="divOtherInfo" name="其他信息" marginTop="10px"></ctag:Fold>
        <div id="divOtherInfo" class="separate-block clearfix otherInfoClass">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">国籍</label>
                <label class="col-lg-3 control-label content-label" id="nationality"></label>
                <label class="col-lg-1 control-label">健康状况</label>
                <label class="col-lg-3 control-label content-label" id="healthInfo"></label>
                <label class="col-lg-1 control-label">婚姻状况</label>
                <label class="col-lg-3 control-label content-label" id="maritalStatusName"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">身高</label>
                <label class="col-lg-3 control-label content-label" id="stature"></label>
                <label class="col-lg-1 control-label">体重</label>
                <label class="col-lg-3 control-label content-label" id="weight"></label>
                <label class="col-lg-1 control-label">血型</label>
                <label class="col-lg-3 control-label content-label" id="bloodTypeName"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">星座</label>
                <label class="col-lg-3 control-label content-label" id="constellation"></label>
                <label class="col-lg-1 control-label">在职状态</label>
                <label class="col-lg-3 control-label content-label" id="inServiceName"></label>
                <label class="col-lg-1 control-label">备注</label>
                <label class="col-lg-3 control-label content-label" id="remark" data-textarea="true"></label>
            </div>
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/employee/EmployeeDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function EmployeeDetail_Page_Load() {
        SysApp.Demo.EmployeeDetailIns = new SysApp.Demo.EmployeeDetail();
        var instance = SysApp.Demo.EmployeeDetailIns;
        instance.selfInstance = "SysApp.Demo.EmployeeDetailIns";
        instance.clientID = "EmployeeDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/demo/employee/";
        instance.init();
    }

    EmployeeDetail_Page_Load();
    //]]>
</script>