<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyinformation/CompanyInformationInput.js?${version}"></script>

<title>企业基本信息编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height">
        <div class="panel-heading CompanyInformationInput-MainContent">
            <em></em>
            <div class="panel-title" id="ctlTitle">企业基本信息编辑</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <div class="CompanyInformationInput-MainContent">
                <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
                <form id="MainForm" class="form-horizontal">
                    <input id="uid" type="hidden">
                    <ctag:Fold id="divBaseInfo" name="基本信息"></ctag:Fold>
                    <div id="divBaseInfo" class="separate-block clearfix">
                        <div class="form-group form-inline">
                            <label class="col-lg-1 control-label">入孵编号</label>
                            <div class="col-lg-3">
                                <input id="companyNo" type="text" class="form-control required" data-title="入孵编号" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                                <label id="companyNo_Error" class="validator-error"></label>
                            </div>
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
                        </div>
                        <div class="form-group form-inline">
                            <label class="col-lg-1 control-label">注册地区（省）</label>
                            <div class="col-lg-3">
                                <ctag:ComboTree valueDomId="registeredProvinceUid" textDomId="registeredProvinceName" parentInstance="SysApp.Demo.CompanyInformationInputIns" dataTitle="注册地区（省）" style="width: 161px;"
                                                nodeName="regionName" idKey="uid" parentIdKey="parentUid" dataUrl="${ctx}/cmn/region/getAllRegion/1" selectParent="false"/>
                            </div>
                            <label class="col-lg-1 control-label">注册地区（市）</label>
                            <div class="col-lg-3">
                                <ctag:ComboTree valueDomId="registeredCityUid" textDomId="registeredCityName" parentInstance="SysApp.Demo.CompanyInformationInputIns" dataTitle="注册地区（市）" style="width: 161px;" parentDomId="registeredProvinceUid"
                                                parentDataTitle="注册地区（省）" nodeName="regionName" idKey="uid" parentIdKey="parentUid" dataUrl="${ctx}/cmn/region/getAllRegion/2" selectParent="false"/>
                            </div>
                            <label class="col-lg-1 control-label">注册地区（区/县）</label>
                            <div class="col-lg-3">
                                <ctag:ComboTree valueDomId="registeredAreaUid" textDomId="registeredAreaName" parentInstance="SysApp.Demo.CompanyInformationInputIns" dataTitle="注册地区（区/县）" style="width: 161px;" parentDomId="registeredCityUid"
                                                parentDataTitle="注册地区（市）" nodeName="regionName" idKey="uid" parentIdKey="parentUid" dataUrl="${ctx}/cmn/region/getAllRegion/3" selectParent="false"/>
                            </div>
                        </div>
                        <div class="form-group form-inline">
                            <label class="col-lg-1 control-label">注册地址</label>
                            <div class="col-lg-3">
                                <input id="registeredAddress" type="text" class="form-control" data-title="注册地址" maxlength="64" data-rangelength="[0,64]" style="width: 200px;"/>
                                <label id="registeredAddress_Error" class="validator-error"></label>
                            </div>
                            <label class="col-lg-1 control-label">成立时间</label>
                            <div class="col-lg-3">
                                <ctag:CalendarSelect id="establishmentDate" title="成立时间" showValidateError="true" width="161px"></ctag:CalendarSelect>
                            </div>
                            <label class="col-lg-1 control-label">注册资金（万元）</label>
                            <div class="col-lg-3">
                                <input id="registeredCapital" type="text" class="form-control" data-title="注册资金" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 200px;"/>
                                <label id="registeredCapital_Error" class="validator-error"></label>
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

                    <ctag:Fold id="divSettledInfo" name="入驻信息" marginTop="10px"></ctag:Fold>
                    <div id="divSettledInfo" class="separate-block clearfix">
                        <div class="form-group form-inline">
                            <label class="col-lg-1 control-label">所属基地</label>
                            <div class="col-lg-3 select2-required">
                                <ctag:Select2 selectId="baseUid" dataTitle="所属基地" style="width:200px" required="required"/>
                                <label id="baseUid_Error" class="validator-error"></label>
                            </div>
                            <label class="col-lg-1 control-label">入驻时间</label>
                            <div class="col-lg-3">
                                <ctag:CalendarSelect id="settlingDate" title="入驻时间" showValidateError="true" width="161px"></ctag:CalendarSelect>
                            </div>
                            <label class="col-lg-1 control-label">租赁面积</label>
                            <div class="col-lg-3">
                                <input id="leaseArea" type="text" class="form-control" data-title="租赁面积" maxlength="13" data-rangelength="[0,13]" data-range="[0,99999999]" data-number="true" style="width: 200px;"/>
                                <label id="leaseArea_Error" class="validator-error"></label>
                            </div>
                        </div>
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
                            <label class="col-lg-1 control-label">孵化状态</label>
                            <div class="col-lg-3">
                                <select id="incubationStateCd" class="form-control required" data-title="孵化状态" style="width: 200px;">
                                    <option value="">请选择</option>
                                </select>
                                <label id="incubationStateCd_Error" class="validator-error"></label>
                            </div>
                            <label class="col-lg-1 control-label">毕业企业编号</label>
                            <div class="col-lg-3">
                                <input id="graduationNo" type="text" class="form-control" data-title="毕业企业编号" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                                <label id="graduationNo_Error" class="validator-error"></label>
                            </div>
                        </div>
                        <div class="form-group form-inline">
                            <label class="col-lg-1 control-label">创业企业特征</label>
                            <div class="col-lg-3">
                                <ctag:ComboCheckTree valueDomId="enterpriseCharacteristics" textDomId="maritalStatusName" parentInstance="SysApp.Demo.CompanyInformationInputIns" dataTitle="创业企业特征"
                                                     nodeName="subName" idKey="subCd" parentIdKey="parentSubCd" style="width:161px"/>
                            </div>
                            <label class="col-lg-1 control-label">市场分类</label>
                            <div class="col-lg-3">
                                <ctag:ComboCheckTree valueDomId="marketClassification" textDomId="marketClassificationName" parentInstance="SysApp.Demo.CompanyInformationInputIns" dataTitle="市场分类"
                                                     nodeName="subName" idKey="subCd" parentIdKey="parentSubCd" style="width:161px"/>
                            </div>
                            <label class="col-lg-1 control-label">主营产品</label>
                            <div class="col-lg-3">
                                <input id="mainProduct" type="text" class="form-control" data-title="主营产品" maxlength="256" data-rangelength="[0,256]" style="width: 200px;"/>
                                <label id="mainProduct_Error" class="validator-error"></label>
                            </div>
                        </div>

                        <div class="form-group form-inline">
                            <label class="col-lg-1 control-label">工位数</label>
                            <div class="col-lg-3">
                                <input id="stationCounts" type="text" class="form-control" data-title="工位数" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                                <label id="stationCounts_Error" class="validator-error"></label>
                            </div>
                            <label class="col-lg-1 control-label">工位位置</label>
                            <div class="col-lg-3">
                                <input id="stationPosition" type="text" class="form-control" data-title="工位位置" maxlength="64" data-rangelength="[0,64]" style="width: 200px;"/>
                                <label id="stationPosition_Error" class="validator-error"></label>
                            </div>
                        </div>
                    </div>

                    <ctag:Fold id="divOtherInfo" name="备注信息" marginTop="10px"></ctag:Fold>
                    <div id="divOtherInfo" class="separate-block clearfix">
                        <div class="form-group form-inline">
                            <label class="col-lg-1 control-label">备注</label>
                            <div class="col-lg-10">
                                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width:98%;"></textarea>
                                <label id="remark_Error" class="validator-error"></label>
                            </div>
                        </div>
                    </div>
                </form>

                <div class="text-center margin-top-space">
                    <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存基本信息</button>
                    <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                    <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
                </div>

                <%--原始对象Json字符串[实体属性校验用]--%>
                <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_CompanyInformation}"/>

                <%--下拉框选项数据集合--%>
                <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyInformation}"/>
            </div>
            <%--企业扩展信息--%>
            <%@ include file="/WEB-INF/views/demo/companyinformation/CompanyExtendedTabs.jsp" %>
        </div>
    </div>
    <ctag:FileUpload clientID="FileUpload" relationUid="${companyInformationUid}" appCode="TUS" moduleName="BPMS演示" panelTitle="附件一览（孵化协议，品牌商标等）"></ctag:FileUpload>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyInformationInput_Page_Load() {
        SysApp.Demo.CompanyInformationInputIns = new SysApp.Demo.CompanyInformationInput();
        var instance = SysApp.Demo.CompanyInformationInputIns;
        instance.selfInstance = "SysApp.Demo.CompanyInformationInputIns";
        instance.controller = "${ctx}/demo/companyinformation/";
        instance.listUrl = "${ctx}/demo/companyinformation/list";
        instance.clientID = "CompanyInformationInput";

        instance.init();
    }

    CompanyInformationInput_Page_Load();
    //]]>
</script>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.CompanyInformationInputIns"/>