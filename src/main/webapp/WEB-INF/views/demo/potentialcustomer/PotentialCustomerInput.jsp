<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/potentialcustomer/PotentialCustomerInput.js?${version}"></script>

<title>招商潜在投资企业编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper PotentialCustomerInput-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">招商潜在投资企业编辑</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">
                <ctag:Fold id="divBaseInfo" name="基本信息"></ctag:Fold>
                <div id="divBaseInfo" class="separate-block clearfix">
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">企业名称</label>
                        <div class="col-lg-3">
                            <input id="companyName" type="text" class="form-control required" data-title="企业名称" maxlength="64" data-rangelength="[0,64]" style="width: 200px;"/>
                            <label id="companyName_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">组织机构代码</label>
                        <div class="col-lg-3">
                            <input id="organizationNo" type="text" class="form-control" data-title="组织机构代码" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="organizationNo_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">成立时间</label>
                        <div class="col-lg-3">
                            <ctag:CalendarSelect id="establishmentDate" title="成立时间" showValidateError="true" width="161px"></ctag:CalendarSelect>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">注册地区（省）</label>
                        <div class="col-lg-3">
                            <ctag:ComboTree valueDomId="registeredProvinceUid" textDomId="registeredProvinceName" parentInstance="SysApp.Demo.PotentialCustomerInputIns" dataTitle="注册地区（省）" style="width: 161px;"
                                            nodeName="regionName" idKey="uid" parentIdKey="parentUid" dataUrl="${ctx}/cmn/region/getAllRegion/1" selectParent="false"/>
                        </div>
                        <label class="col-lg-1 control-label">注册地区（市）</label>
                        <div class="col-lg-3">
                            <ctag:ComboTree valueDomId="registeredCityUid" textDomId="registeredCityName" parentInstance="SysApp.Demo.PotentialCustomerInputIns" dataTitle="注册地区（市）" style="width: 161px;" parentDomId="registeredProvinceUid"
                                            parentDataTitle="注册地区（省）" nodeName="regionName" idKey="uid" parentIdKey="parentUid" dataUrl="${ctx}/cmn/region/getAllRegion/2" selectParent="false"/>
                        </div>
                        <label class="col-lg-1 control-label">注册地区（区/县）</label>
                        <div class="col-lg-3">
                            <ctag:ComboTree valueDomId="registeredAreaUid" textDomId="registeredAreaName" parentInstance="SysApp.Demo.PotentialCustomerInputIns" dataTitle="注册地区（区/县）" style="width: 161px;" parentDomId="registeredCityUid"
                                            parentDataTitle="注册地区（市）" nodeName="regionName" idKey="uid" parentIdKey="parentUid" dataUrl="${ctx}/cmn/region/getAllRegion/3" selectParent="false"/>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">注册地址</label>
                        <div class="col-lg-3">
                            <input id="registeredAddress" type="text" class="form-control" data-title="注册地址" maxlength="64" data-rangelength="[0,64]" style="width: 200px;"/>
                            <label id="registeredAddress_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">注册资金（万元）</label>
                        <div class="col-lg-3">
                            <input id="registeredCapital" type="text" class="form-control" data-title="注册资金" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 200px;"/>
                            <label id="registeredCapital_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">所属基地</label>
                        <div class="col-lg-3 select2-required">
                            <ctag:Select2 selectId="baseUid" dataTitle="所属基地" style="width:200px" required="required"/>
                            <label id="baseUid_Error" class="validator-error"></label>
                        </div>
                    </div>
                </div>

                <ctag:Fold id="divConcatInfo" name="联系方式" marginTop="10px"></ctag:Fold>
                <div id="divConcatInfo" class="separate-block clearfix">
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">实际负责人</label>
                        <div class="col-lg-3">
                            <input id="companyPrincipal" type="text" class="form-control" data-title="实际负责人" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="companyPrincipal_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">负责人电话</label>
                        <div class="col-lg-3">
                            <input id="principalPhone" type="text" class="form-control" data-title="负责人电话" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="principalPhone_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">负责人邮箱</label>
                        <div class="col-lg-3">
                            <input id="principalMail" type="text" class="form-control" data-title="负责人邮箱" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="principalMail_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">联系人</label>
                        <div class="col-lg-3">
                            <input id="contactPerson" type="text" class="form-control" data-title="联系人" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="contactPerson_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">联系人电话</label>
                        <div class="col-lg-3">
                            <input id="contactPhone" type="text" class="form-control" data-title="联系人电话" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="contactPhone_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">联系人邮箱</label>
                        <div class="col-lg-3">
                            <input id="contactMail" type="text" class="form-control" data-title="联系人邮箱" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="contactMail_Error" class="validator-error"></label>
                        </div>
                    </div>
                </div>

                <ctag:Fold id="divFollowUpInfo" name="对接信息" marginTop="10px"></ctag:Fold>
                <div id="divFollowUpInfo" class="separate-block clearfix">
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">负责人</label>
                        <div class="col-lg-3">
                            <input id="principal" type="text" class="form-control" data-title="负责人" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="principal_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">企业类型</label>
                        <div class="col-lg-3">
                            <select id="companyTypeCd" class="form-control required" data-title="企业类型" style="width: 200px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="companyTypeCd_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">入驻概率</label>
                        <div class="col-lg-3">
                            <select id="entryProbabilityCd" class="form-control" data-title="入驻概率" style="width: 200px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="entryProbabilityCd_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">招商渠道</label>
                        <div class="col-lg-3">
                            <select id="investmentChannelsCd" class="form-control" data-title="招商渠道" style="width: 200px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="investmentChannelsCd_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">联络内容</label>
                        <div class="col-lg-3">
                            <textarea id="contactContent" rows="3" cols="30" class="form-control" data-title="联络内容" data-rangelength="[0,512]" style="width: 200px;"></textarea>
                            <label id="contactContent_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">后续对接方案</label>
                        <div class="col-lg-3">
                            <textarea id="followUpScheme" rows="3" cols="30" class="form-control" data-title="后续对接方案" data-rangelength="[0,512]" style="width: 200px;"></textarea>
                            <label id="followUpScheme_Error" class="validator-error"></label>
                        </div>
                    </div>
                </div>

                <ctag:Fold id="divOtherInfo" name="其他信息" marginTop="10px"></ctag:Fold>
                <div id="divOtherInfo" class="separate-block clearfix">
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">办公地址</label>
                        <div class="col-lg-3">
                            <input id="officeAddress" type="text" class="form-control" data-title="办公地址" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                            <label id="officeAddress_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">办公地邮政编码</label>
                        <div class="col-lg-3">
                            <input id="officePostalCode" type="text" class="form-control" data-title="办公地邮政编码" maxlength="8" data-rangelength="[0,8]" style="width: 200px;"/>
                            <label id="officePostalCode_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">登记注册类型</label>
                        <div class="col-lg-3">
                            <select id="registrationTypeCd" class="form-control" data-title="登记注册类型" style="width: 200px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="registrationTypeCd_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">高新技术分类</label>
                        <div class="col-lg-3">
                            <input id="highTechType" type="text" class="form-control" data-title="高新技术分类" maxlength="64" data-rangelength="[0,64]" style="width: 200px;"/>
                            <label id="highTechType_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">行业分类</label>
                        <div class="col-lg-3">
                            <input id="industryCategory" type="text" class="form-control" data-title="行业分类" maxlength="64" data-rangelength="[0,64]" style="width: 200px;"/>
                            <label id="industryCategory_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">专业技术方向</label>
                        <div class="col-lg-3">
                            <select id="professionalTechnicalCd" class="form-control" data-title="专业技术方向" style="width: 200px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="professionalTechnicalCd_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-1 control-label">网站/APP</label>
                        <div class="col-lg-3">
                            <input id="website" type="text" class="form-control" data-title="网站/APP" maxlength="128" data-rangelength="[0,128]" style="width: 200px;"/>
                            <label id="website_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">企业简介</label>
                        <div class="col-lg-3">
                            <textarea id="companyProfile" rows="3" cols="30" class="form-control" data-title="企业简介" data-rangelength="[0,512]" style="width: 200px;"></textarea>
                            <label id="companyProfile_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">备注</label>
                        <div class="col-lg-3">
                            <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 200px;"></textarea>
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
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_PotentialCustomer}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_PotentialCustomer}"/>
        </div>
    </div>
    <ctag:FileUpload clientID="FileUpload" relationUid="${potentialCustomerUid}" appCode="TUS" moduleName="BPMS演示" panelTitle="附件一览"></ctag:FileUpload>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function PotentialCustomerInput_Page_Load() {
        SysApp.Demo.PotentialCustomerInputIns = new SysApp.Demo.PotentialCustomerInput();
        var instance = SysApp.Demo.PotentialCustomerInputIns;
        instance.selfInstance = "SysApp.Demo.PotentialCustomerInputIns";
        instance.controller = "${ctx}/demo/potentialcustomer/";
        instance.listUrl = "${ctx}/demo/potentialcustomer/list";
        instance.clientID = "PotentialCustomerInput";

        instance.init();
    }

    PotentialCustomerInput_Page_Load();
    //]]>
</script>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.PotentialCustomerInputIns"/>