<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/weeklyreport/weeklyreportdetail/WeeklyReportDetailInnerList.js?${version}"></script>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="WeeklyReportDetailInnerList-MainContent">
    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm">
        <%--企业收费信息UID--%>
        <input type="hidden" id="weeklyReportUid" data-alias-table="main" value="${weeklyReportUid}" data-search-mode="="/>
    </form>
    <%--周次--%>
    <select id="weekCd" style="display: none;"></select>
    <input type="hidden" id="jsonListData" value="${jsonDataList_WeeklyReportDetail}"/>
    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_WeeklyReportDetail}"/>
    <div class="margin-top-space" id="divList">
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function WeeklyReportDetailInnerList_Page_Load() {
        SysApp.Demo.WeeklyReportDetailInnerListIns = new SysApp.Demo.WeeklyReportDetailInnerList();
        var instance = SysApp.Demo.WeeklyReportDetailInnerListIns;

        instance.selfInstance = "SysApp.Demo.WeeklyReportDetailInnerListIns";
        instance.controller = "${ctx}/demo/weeklyreportdetail/";
        instance.inputUrl = "${ctx}/demo/weeklyreportdetail/input";
        instance.clientID = "WeeklyReportDetailInnerList";
        instance.tableName = "demo_weekly_report_detail";

        instance.init();
    }

    $(function () {
        WeeklyReportDetailInnerList_Page_Load();
    });
    //]]>
</script>

