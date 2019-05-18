<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/employee/EmployeeInput.js?${version}"></script>

<title>人员信息表编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper EmployeeInput-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">人员信息表编辑</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">

                <ctag:Fold id="divBaseInfo" name="基本信息"></ctag:Fold>
                <div id="divBaseInfo" class="separate-block clearfix baseInfoClass">
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">用户名</label>
                        <div class="col-lg-3">
                            <input id="userCd" type="text" class="form-control required" data-title="登录账号" maxlength="64" data-rangelength="[0,64]" style="width: 200px;"/>
                            <label id="userCd_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">工号</label>
                        <div class="col-lg-3">
                            <input id="userNumber" type="text" class="form-control required" data-title="工号" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="userNumber_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">姓名</label>
                        <div class="col-lg-3">
                            <input id="userName" type="text" class="form-control required" data-title="姓名" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="userName_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">性别</label>
                        <div class="col-lg-3">
                            <select id="sexCd" class="form-control" data-title="性别" style="width: 200px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="sexCd_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">出生日期</label>
                        <div class="col-lg-3">
                            <ctag:CalendarSelect id="birthday" title="出生日期" showValidateError="true" width="161px"></ctag:CalendarSelect>
                        </div>
                        <label class="col-lg-1 control-label">年龄</label>
                        <div class="col-lg-3">
                            <input id="age" type="text" class="form-control" data-title="年龄" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="age_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">身份证号</label>
                        <div class="col-lg-3">
                            <input id="idCardNo" type="text" class="form-control" data-title="身份证号" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="idCardNo_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">民族</label>
                        <div class="col-lg-3">
                            <select id="ethnicityCd" class="form-control" data-title="民族" style="width: 200px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="ethnicityCd_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">政治面貌</label>
                        <div class="col-lg-3">
                            <select id="policyCd" class="form-control" data-title="政治面貌" style="width: 200px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="policyCd_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">户口类型</label>
                        <div class="col-lg-3">
                            <input id="residenceType" type="text" class="form-control" data-title="户口类型" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="residenceType_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">户口所在地</label>
                        <div class="col-lg-3">
                            <input id="registeredResidence" type="text" class="form-control" data-title="户口所在地" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="registeredResidence_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">籍贯</label>
                        <div class="col-lg-3">
                            <input id="ancestralNativePlace" type="text" class="form-control" data-title="籍贯" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="ancestralNativePlace_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">毕业院校</label>
                        <div class="col-lg-3">
                            <input id="graduateInstitutions" type="text" class="form-control" data-title="毕业院校" maxlength="128" data-rangelength="[0,128]" style="width: 200px;"/>
                            <label id="graduateInstitutions_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">所学专业</label>
                        <div class="col-lg-3">
                            <input id="major " type="text" class="form-control" data-title="所学专业" maxlength="128" data-rangelength="[0,128]" style="width: 200px;"/>
                            <label id="major _Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">学历</label>
                        <div class="col-lg-3">
                            <select id="educationCd" class="form-control" data-title="学历" style="width: 200px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="educationCd_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">学位</label>
                        <div class="col-lg-3">
                            <select id="educationalDegreeCd" class="form-control" data-title="学位" style="width: 200px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="educationalDegreeCd_Error" class="validator-error"></label>
                        </div>
                    </div>
                </div>

                <ctag:Fold id="divPositionInfo" name="工作信息" marginTop="10px"></ctag:Fold>
                <div id="divPositionInfo" class="separate-block clearfix positionInfoClass">
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">所属部门</label>
                        <div class="col-lg-3">
                            <ctag:ComboTree valueDomId="deptUid" textDomId="deptFullName" dataTitle="所属部门" parentInstance="SysApp.Demo.EmployeeInputIns" dispName="deptFullName" nodeName="deptName" idKey="uid"
                                            parentIdKey="parentDeptUid" dataUrl="${ctx}/sys/dept/getAllDeptList" required="required" style="width: 161px;" selectParent="false"/>
                        </div>
                        <label class="col-lg-1 control-label">岗位</label>
                        <div class="col-lg-3">
                            <ctag:Select2 selectId="positionUid" dataTitle="岗位" style="width: 200px;"/>
                            <label id="positionUid_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">办公地点</label>
                        <div class="col-lg-3">
                            <ctag:ComboTree valueDomId="locationUid" textDomId="locationFullName" dataTitle="办公地点" parentInstance="SysApp.Demo.EmployeeInputIns" dispName="deptFullName" nodeName="deptName" idKey="uid"
                                            parentIdKey="parentDeptUid" dataUrl="${ctx}/sys/dept/getAllCompanyList" required="required" style="width: 161px;" selectParent="false"/>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">所属基地</label>
                        <div class="col-lg-3 select2-required">
                            <ctag:Select2 selectId="baseUid" dataTitle="所属基地" style="width:200px" required="required"/>
                            <label id="baseUid_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">职等</label>
                        <div class="col-lg-3">
                            <input id="positionsGrade" type="text" class="form-control" data-title="职等" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="positionsGrade_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">用工方式</label>
                        <div class="col-lg-3">
                            <input id="employmentType" type="text" class="form-control" data-title="用工方式" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="employmentType_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">试用期结束日期</label>
                        <div class="col-lg-3">
                            <ctag:CalendarSelect id="probationEndDate" title="试用期结束日期" showValidateError="true" width="161px"></ctag:CalendarSelect>
                        </div>
                        <label class="col-lg-1 control-label">合同开始日期</label>
                        <div class="col-lg-3">
                            <ctag:CalendarSelect id="contractStartDate" title="合同开始日期" showValidateError="true" width="161px"></ctag:CalendarSelect>
                        </div>
                        <label class="col-lg-1 control-label">合同结束日期</label>
                        <div class="col-lg-3">
                            <ctag:CalendarSelect id="contractEndDate" title="合同结束日期" showValidateError="true" width="161px"></ctag:CalendarSelect>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">公积金帐号</label>
                        <div class="col-lg-3">
                            <input id="accumulationFundAccount" type="text" class="form-control" data-title="公积金帐号" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="accumulationFundAccount_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">开户银行</label>
                        <div class="col-lg-3">
                            <input id="bankName" type="text" class="form-control" data-title="开户银行" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="bankName_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">工资卡号</label>
                        <div class="col-lg-3">
                            <input id="bankAccountNumber" type="text" class="form-control" data-title="工资卡号" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="bankAccountNumber_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">人员状态</label>
                        <div class="col-lg-3">
                            <select id="statusCd" class="form-control required" data-title="人员状态" style="width: 200px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="statusCd_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">参加工作日期</label>
                        <div class="col-lg-3">
                            <ctag:CalendarSelect id="firstJobDate" title="参加工作日期" showValidateError="true" width="161px"></ctag:CalendarSelect>
                        </div>
                        <label class="col-lg-1 control-label">入职日期</label>
                        <div class="col-lg-3">
                            <ctag:CalendarSelect id="entryDate" title="入职日期" showValidateError="true" width="161px"></ctag:CalendarSelect>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">司龄</label>
                        <div class="col-lg-3">
                            <input id="seniority" type="text" class="form-control" data-title="司龄" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="seniority_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">转正日期</label>
                        <div class="col-lg-3">
                            <ctag:CalendarSelect id="positiveDate" title="转正日期" showValidateError="true" width="161px"></ctag:CalendarSelect>
                        </div>
                        <label class="col-lg-1 control-label">离职日期</label>
                        <div class="col-lg-3">
                            <ctag:CalendarSelect id="retireDate" title="离职日期" showValidateError="true" width="161px"></ctag:CalendarSelect>
                        </div>
                    </div>
                </div>
                <ctag:Fold id="divContactInfo" name="联系方式" marginTop="10px"></ctag:Fold>
                <div id="divContactInfo" class="separate-block clearfix contactInfoClass">
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">手机号码</label>
                        <div class="col-lg-3">
                            <input id="mobile" type="text" class="form-control" data-title="手机号码" data-regex="/^1\d{10,10}$/"
                                   data-regex-message="请输入11位有效的手机号码(如：13661950001)。" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="mobile_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">固定电话</label>
                        <div class="col-lg-3">
                            <input id="telephone" type="text" class="form-control" data-title="固定电话" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="telephone_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">传值</label>
                        <div class="col-lg-3">
                            <input id="fax" type="text" class="form-control" data-title="传值" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="fax_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">电子邮件</label>
                        <div class="col-lg-3">
                            <input id="email" type="text" class="form-control" data-title="电子邮件" data-regex="/^(\w)+([\.-]?\w+)*@(\w)+((\.\w+)+)$/"
                                   data-regex-message="请输入有效的邮件地址(如：zhangsan@domain.com)。" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="email_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">出生地</label>
                        <div class="col-lg-3">
                            <input id="birthPlace" type="text" class="form-control" data-title="出生地" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="birthPlace_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">家庭住址</label>
                        <div class="col-lg-3">
                            <input id="homeAddress" type="text" class="form-control" data-title="家庭住址" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="homeAddress_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">所在省</label>
                        <div class="col-lg-3">
                            <ctag:ComboTree valueDomId="contactProvinceUid" textDomId="contactProvinceName" parentInstance="SysApp.Demo.EmployeeInputIns" dataTitle="所在省" style="width: 161px;"
                                            nodeName="regionName" idKey="uid" parentIdKey="parentUid" dataUrl="${ctx}/cmn/region/getAllRegion/1" selectParent="false"/>
                        </div>
                        <label class="col-lg-1 control-label">所在市</label>
                        <div class="col-lg-3">
                            <ctag:ComboTree valueDomId="contactCityUid" textDomId="contactCityName" parentInstance="SysApp.Demo.EmployeeInputIns" dataTitle="所在市" style="width: 161px;" parentDomId="contactProvinceUid"
                                            parentDataTitle="所在省" nodeName="regionName" idKey="uid" parentIdKey="parentUid" dataUrl="${ctx}/cmn/region/getAllRegion/2" selectParent="false"/>
                        </div>
                        <label class="col-lg-1 control-label">所在区(县)</label>
                        <div class="col-lg-3">
                            <ctag:ComboTree valueDomId="contactCountyUid" textDomId="contactCountyName" parentInstance="SysApp.Demo.EmployeeInputIns" dataTitle="所在区(县)" style="width: 161px;" parentDomId="contactCityUid"
                                            parentDataTitle="所在市" nodeName="regionName" idKey="uid" parentIdKey="parentUid" dataUrl="${ctx}/cmn/region/getAllRegion/3" selectParent="false"/>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">联系地址</label>
                        <div class="col-lg-3">
                            <input id="contactAddress" type="text" class="form-control" data-title="联系地址" maxlength="128" data-rangelength="[0,128]" style="width: 200px;"/>
                            <label id="contactAddress_Error" class="validator-error"></label>
                        </div>
                    </div>
                </div>
                <ctag:Fold id="divOtherInfo" name="其他信息" marginTop="10px"></ctag:Fold>
                <div id="divOtherInfo" class="separate-block clearfix otherInfoClass">
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">国籍</label>
                        <div class="col-lg-3">
                            <input id="nationality" type="text" class="form-control" data-title="国籍" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="nationality_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">婚姻状况</label>
                        <div class="col-lg-3">
                            <select id="maritalStatusCd" class="form-control" data-title="婚姻状况" style="width: 200px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="maritalStatusCd_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">健康状况</label>
                        <div class="col-lg-3">
                            <input id="healthInfo" type="text" class="form-control" data-title="健康状况" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="healthInfo_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">身高</label>
                        <div class="col-lg-3">
                            <input id="stature" type="text" class="form-control" data-title="身高" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                            <label id="stature_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">体重</label>
                        <div class="col-lg-3">
                            <input id="weight" type="text" class="form-control" data-title="体重" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                            <label id="weight_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">血型</label>
                        <div class="col-lg-3">
                            <select id="bloodTypeCd" class="form-control" data-title="血型" style="width: 200px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="bloodTypeCd_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">星座</label>
                        <div class="col-lg-3">
                            <input id="constellation" type="text" class="form-control" data-title="星座" data-rangelength="[0,32]" style="width: 200px;"></input>
                            <label id="constellation_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">备注</label>
                        <div class="col-lg-6">
                            <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 98%;"></textarea>
                            <label id="remark_Error" class="validator-error"></label>
                        </div>
                    </div>
                </div>
            </form>

            <div class="text-center margin-top-space">
                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
            </div>

            <%--原始对象Json字符串[实体属性校验用]--%>
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Employee}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Employee}"/>
        </div>
    </div>
    <ctag:FileUpload clientID="FileUpload" relationUid="${employeeUid}" appCode="TUS" moduleName="BPMS演示" panelTitle="附件一览"></ctag:FileUpload>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function EmployeeInput_Page_Load() {
        SysApp.Demo.EmployeeInputIns = new SysApp.Demo.EmployeeInput();
        var instance = SysApp.Demo.EmployeeInputIns;
        instance.selfInstance = "SysApp.Demo.EmployeeInputIns";
        instance.controller = "${ctx}/demo/employee/";
        instance.listUrl = "${ctx}/demo/employee/list";
        instance.clientID = "EmployeeInput";

        instance.init();
    }

    EmployeeInput_Page_Load();
    //]]>
</script>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.EmployeeInputIns"/>