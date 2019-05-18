<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/desktop/myscheduler/SchedulerInnerList.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/clipboard/clipboard.min.js"></script>
<div class="SchedulerInnerList-MainContent">
    <div class="make-appointment" style="padding-left: 0;">
        <div class="text-right" style="padding-bottom: 10px;">
            <button id="prevDays" class="btn btn-default" style="height: 25px;padding: 2px 10px 5px 10px;margin: 0px;"><i class="fa fa-angle-left"></i> 前五天</button>
            <button id="today" class="btn btn-default" style="height: 25px;padding: 2px 10px 5px 10px;margin: 0px;">今日预约</button>
            <button id="nextDays" class="btn btn-default" style="height: 25px;padding: 2px 10px 5px 10px;margin: 0px;">后五天 <i class="fa fa-angle-right"></i></button>
        </div>
        <ul class="clearfix myscheduler">
        </ul>
    </div>
</div>
<script type="text/javascript">
    //<![CDATA[
    function SchedulerInnerList_Page_Load() {
        SysApp.Crm.SchedulerInnerListIns = new SysApp.Crm.SchedulerInnerList();
        var instance = SysApp.Crm.SchedulerInnerListIns;

        instance.selfInstance = "SysApp.Crm.SchedulerInnerListIns";
        instance.controller = "${ctx}/crm/scheduler/";
        instance.clientID = "SchedulerInnerList";
        instance.pageSize = "${pageSize}";
        instance.init();
    }

    $(function () {
        SchedulerInnerList_Page_Load();
    });
    //]]>
</script>