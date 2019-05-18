<%@ page language="java" pageEncoding="UTF-8" %>

<div id="MultiTabView" class="margin-top-space">
    <ul id="MultiTabView_Tabs" class="tmv-customize">
        <div id="MultiTabView_Tabs_LiContainer">
            <li id="MultiTabView_Tab0" class="active"><span>附加信息</span></li>
            <li id="MultiTabView_Tab1" class="enableout"><span>股权信息</span></li>
            <li id="MultiTabView_Tab2" class="enableout"><span>人员结构</span></li>
            <li id="MultiTabView_Tab3" class="enableout"><span>核心团队人员</span></li>
            <li id="MultiTabView_Tab4" class="enableout"><span>财务信息</span></li>
            <li id="MultiTabView_Tab5" class="enableout"><span>专利版权</span></li>
            <li id="MultiTabView_Tab6" class="enableout"><span>投融资</span></li>
            <li id="MultiTabView_Tab7" class="enableout"><span>知识产权</span></li>
            <li id="MultiTabView_Tab8" class="enableout"><span>企业资质</span></li>
            <li id="MultiTabView_Tab9" class="enableout"><span>机构信息</span></li>
            <em></em>
        </div>
    </ul>
    <div id="MultiTabView_Views" class="tmv-content">
        <!--附加信息-->
        <div id="MultiTabView_View0">
            <%@ include file="/WEB-INF/views/demo/companyadditional/CompanyAdditionalInnerList.jsp" %>
        </div>
        <!--股权信息-->
        <div id="MultiTabView_View1">
            <%@ include file="/WEB-INF/views/demo/companystock/CompanyStockInnerList.jsp" %>
        </div>
        <!--人员结构-->
        <div id="MultiTabView_View2">
            <%@ include file="/WEB-INF/views/demo/companypersonnel/CompanyPersonnelInnerList.jsp" %>
        </div>
        <!--核心团队人员-->
        <div id="MultiTabView_View3">
            <%@ include file="/WEB-INF/views/demo/companyteam/CompanyTeamInnerList.jsp" %>
        </div>
        <!--财务信息-->
        <div id="MultiTabView_View4">
            <%@ include file="/WEB-INF/views/demo/companyfinancial/CompanyFinancialInnerList.jsp" %>
        </div>
        <!--专利版权-->
        <div id="MultiTabView_View5">
            <%@ include file="/WEB-INF/views/demo/companycopyright/CompanyCopyrightInnerList.jsp" %>
        </div>
        <!--投融资-->
        <div id="MultiTabView_View6">
            <%@ include file="/WEB-INF/views/demo/companyinvestment/CompanyInvestmentInnerList.jsp" %>
        </div>
        <!--知识产权-->
        <div id="MultiTabView_View7">
            <%@ include file="/WEB-INF/views/demo/companyintellectual/CompanyIntellectualInnerList.jsp" %>
        </div>
        <!--企业资质-->
        <div id="MultiTabView_View8">
            <%@ include file="/WEB-INF/views/demo/companyqualification/CompanyQualificationInnerList.jsp" %>
        </div>
        <!--机构信息-->
        <div id="MultiTabView_View9">
            <%@ include file="/WEB-INF/views/demo/companyinstitutional/CompanyInstitutionalInnerList.jsp" %>
        </div>
    </div>
</div>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyinformation/CompanyExtendedTabs.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyExtendedTabs_Page_Load() {
        SysApp.Demo.CompanyExtendedTabsIns = new SysApp.Demo.CompanyExtendedTabs();
        var instance = SysApp.Demo.CompanyExtendedTabsIns;
        instance.selfInstance = "SysApp.Demo.CompanyExtendedTabsIns";
        instance.clientID = "CompanyExtendedTabs";
        instance.companyUid = "${companyInformationUid}";

        instance.init();
    }

    CompanyExtendedTabs_Page_Load();
    //]]>
</script>