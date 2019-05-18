<%@ page language="java" pageEncoding="UTF-8" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="MultiTabView" class="form-horizontal CompanyInformationInnerDetail-MainContent">
    <ul id="MultiTabView_Tabs" class="tmv-customize">
        <div style="width: 530px;" id="MultiTabView_Tabs_LiContainer">
            <li id="MultiTabView_Tab0" class="active"><span>基本信息</span></li>
            <li id="MultiTabView_Tab1" class="enableout"><span>联系方式</span></li>
            <li id="MultiTabView_Tab2" class="enableout"><span>入驻信息</span></li>
            <em></em>
        </div>
    </ul>

    <div id="MultiTabView_Views" class="tmv-content">
        <div id="MultiTabView_View0">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">入孵编号</label>
                <label class="col-lg-3 control-label content-label" id="companyNo"></label>
                <label class="col-lg-1 control-label">企业名称</label>
                <label class="col-lg-3 control-label content-label" id="companyName"></label>
                <label class="col-lg-1 control-label">组织机构代码</label>
                <label class="col-lg-3 control-label content-label" id="organizationNo"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">注册地区（省）</label>
                <label class="col-lg-3 control-label content-label" id="registeredProvinceName"></label>
                <label class="col-lg-1 control-label">注册地区（市）</label>
                <label class="col-lg-3 control-label content-label" id="registeredCityName"></label>
                <label class="col-lg-1 control-label">注册地区（区/县）</label>
                <label class="col-lg-3 control-label content-label" id="registeredAreaName"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">注册地址</label>
                <label class="col-lg-3 control-label content-label" id="registeredAddress"></label>
                <label class="col-lg-1 control-label">成立时间</label>
                <label class="col-lg-3 control-label content-label" id="establishmentDate" data-date="true"></label>
                <label class="col-lg-1 control-label">注册资金(万元)</label>
                <label class="col-lg-3 control-label content-label" id="registeredCapital" data-money="true"></label>
            </div>
        </div>
        <div id="MultiTabView_View1">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">实际负责人</label>
                <label class="col-lg-3 control-label content-label" id="companyPrincipal"></label>
                <label class="col-lg-1 control-label">负责人电话</label>
                <label class="col-lg-3 control-label content-label" id="principalPhone"></label>
                <label class="col-lg-1 control-label">负责人邮箱</label>
                <label class="col-lg-3 control-label content-label" id="principalMail"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">联系人</label>
                <label class="col-lg-3 control-label content-label" id="contactPerson"></label>
                <label class="col-lg-1 control-label">联系人电话</label>
                <label class="col-lg-3 control-label content-label" id="contactPhone"></label>
                <label class="col-lg-1 control-label">联系人邮箱</label>
                <label class="col-lg-3 control-label content-label" id="contactMail"></label>
            </div>
        </div>
        <div id="MultiTabView_View2">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">所属基地</label>
                <label class="col-lg-3 control-label content-label" id="baseName"></label>
                <label class="col-lg-1 control-label">入驻时间</label>
                <label class="col-lg-3 control-label content-label" id="settlingDate" data-date="true"></label>
                <label class="col-lg-1 control-label">租赁面积</label>
                <label class="col-lg-3 control-label content-label" id="leaseArea"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">办公地址</label>
                <label class="col-lg-3 control-label content-label" id="officeAddress"></label>
                <label class="col-lg-1 control-label">办公地邮政编码</label>
                <label class="col-lg-3 control-label content-label" id="officePostalCode"></label>
                <label class="col-lg-1 control-label">登记注册类型</label>
                <label class="col-lg-3 control-label content-label" id="registrationTypeName"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">高新技术分类</label>
                <label class="col-lg-3 control-label content-label" id="highTechType"></label>
                <label class="col-lg-1 control-label">行业分类</label>
                <label class="col-lg-3 control-label content-label" id="industryCategory"></label>
                <label class="col-lg-1 control-label">专业技术方向</label>
                <label class="col-lg-3 control-label content-label" id="professionalTechnicalName"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">网站/APP</label>
                <label class="col-lg-3 control-label content-label" id="website"></label>
                <label class="col-lg-1 control-label">孵化状态</label>
                <label class="col-lg-3 control-label content-label" id="incubationStateName"></label>
                <label class="col-lg-1 control-label">毕业企业编号</label>
                <label class="col-lg-3 control-label content-label" id="graduationNo"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">创业企业特征</label>
                <label class="col-lg-3 control-label content-label" id="enterpriseCharacteristicsName"></label>
                <label class="col-lg-1 control-label">市场分类</label>
                <label class="col-lg-3 control-label content-label" id="marketClassificationName"></label>
                <label class="col-lg-1 control-label">主营产品</label>
                <label class="col-lg-3 control-label content-label" id="mainProduct"></label>
            </div>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">工位数</label>
                <label class="col-lg-3 control-label content-label" id="stationCounts"></label>
                <label class="col-lg-1 control-label">工位位置</label>
                <label class="col-lg-3 control-label content-label" id="stationPosition"></label>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyinformation/CompanyInformationInnerDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyInformationInnerDetail_Page_Load() {
        SysApp.Demo.CompanyInformationInnerDetailIns = new SysApp.Demo.CompanyInformationInnerDetail();
        var instance = SysApp.Demo.CompanyInformationInnerDetailIns;
        instance.selfInstance = "SysApp.Demo.CompanyInformationInnerDetailIns";
        instance.controller = "${ctx}/demo/companyinformation/";
        instance.clientID = "CompanyInformationInnerDetail";
        instance.isDetail = true;
        instance.init();
    }

    CompanyInformationInnerDetail_Page_Load();
    //]]>
</script>