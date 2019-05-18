<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/desktop/dashboard/DashboardInnerList.js?${version}"></script>
<div class="col-xs-2 dashboard-container DashboardInnerList-MainContent">
    <div class="dashboard-inner-list iscroll-wrapper" data-iscroll-id="iScrollDashboardInnerList">
        <ul class="clearfix">

            <li>
                <em><a href="#" onclick="SysApp.Crm.DashboardInnerListIns.getResumeList('dayResumeCount','今日新增学员')" class="dayResumeCount"></a></em>
                <span></span>
                <em><a href="#" onclick="SysApp.Crm.DashboardInnerListIns.getResumeList('resumeCount','总学员')" class="resumeCount"></a></em>
                <em class="title">本月新增学员</em>
                <em class="title">总学员</em>
            </li>

            <li>
                <em><a href="#" onclick="SysApp.Crm.DashboardInnerListIns.getResumeList('recommendEntryCount','本月推荐入职')" class="recommendEntryCount"></a></em>
                <span></span>
                <em><a href="#" onclick="SysApp.Crm.DashboardInnerListIns.getResumeList('monthEntryCount','本月总入职')" class="monthEntryCount"></a></em>
                <em class="title">本月跟进数</em>
                <em class="title">总跟进数</em>
            </li>

            <li>
                <em><a href="#" onclick="SysApp.Crm.DashboardInnerListIns.getResumeList('recommendEntryCount','本月推荐入职')" class="recommendEntryCount"></a></em>
                <span></span>
                <em><a href="#" onclick="SysApp.Crm.DashboardInnerListIns.getResumeList('monthEntryCount','本月总入职')" class="monthEntryCount"></a></em>
                <em class="title">本月预约数</em>
                <em class="title">总预约数</em>
            </li>

            <li>
                <em><a href="#" onclick="SysApp.Crm.DashboardInnerListIns.getResumeList('dayRecommendCount','今日推荐')" class="dayRecommendCount"></a></em>
                <span></span>
                <em><a href="#" onclick="SysApp.Crm.DashboardInnerListIns.getResumeList('recommendCount','总推荐')" class="recommendCount"></a></em>
                <em class="title">本月报名数</em>
                <em class="title">总报名数</em>
            </li>

            <li>
                <em><a href="#" onclick="SysApp.Crm.DashboardInnerListIns.getResumeList('dayRecommendCount','今日推荐')" class="dayRecommendCount"></a></em>
                <span></span>
                <em><a href="#" onclick="SysApp.Crm.DashboardInnerListIns.getResumeList('recommendCount','总推荐')" class="recommendCount"></a></em>
                <em class="title">本月缴费数</em>
                <em class="title">总缴费数</em>
            </li>

            <li>
                <em><a href="#" class="dayVisitCount" onclick="SysApp.Crm.DashboardInnerListIns.getResumeList('dayVisitCount','今日到访')"></a></em>
                <span></span>
                <em><a href="#" class="visitCount" onclick="SysApp.Crm.DashboardInnerListIns.getResumeList('visitCount','总到访')"></a></em>
                <em class="title">本月缴费金额</em>
                <em class="title">总缴费金额</em>
            </li>
        </ul>
    </div>
</div>


<script type="text/javascript">
    //<![CDATA[
    function DashboardInnerList_Page_Load() {
        SysApp.Crm.DashboardInnerListIns = new SysApp.Crm.DashboardInnerList();
        var instance = SysApp.Crm.DashboardInnerListIns;

        instance.selfInstance = "SysApp.Crm.DashboardInnerListIns";
        instance.controller = "${ctx}/crm/desktop/";
        instance.clientID = "DashboardInnerList";

        instance.init();
    }

    DashboardInnerList_Page_Load();
    //]]>
</script>