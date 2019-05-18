<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/Common.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/desktop/myreservation/ReservationInnerList.js?${version}"></script>
<div class="row ReservationInnerList-MainContent">
    <div style="padding-right: 10px;">
        <div class="desktop-search-condition clearfix">
            <form id="MainForm" class="form-horizontal" onsubmit="return false;">
                <div class="col-sm-1" style="padding-left: 0;width: 100px;">
                    <select id="reservationCampusUid" data-alias-table="main" class="form-control" data-blank-text="-预约校区-">
                    </select>
                </div>
                <div class="col-sm-1" style="padding-left: 0;width: 100px;">
                    <select id="reservationCampusClassUid" data-alias-table="main" class="form-control" data-blank-text="-预约班级-">
                    </select>
                </div>
                <div class="col-xs-6" style="padding-left: 5px;">
                    <input type="text" class="form-control list-keyword-input" id="main.contents,student.name,student.email,student.mobile,student.parentName,student_belong_consultant_user.userName" data-search-mode="OR" data-title="关键字"
                           placeholder="请输入关键字查询（如：预约内容、学员姓名、家长姓名、手机号码等）" data-search-mode="$ignore_search" style="width:450px;float:left;">
                    <button type="button" class="btn btn-primary" id="btnCustomSearch">
                        <i class="fa fa-search"></i>
                    </button>
                </div>
            </form>
        </div>
        <!--学员列表区-->
        <div class="iscroll-wrapper desktop-inner-list" data-iscroll-id="iScrollReservationInnerList">
            <div>
                <ul class="desktop-list desktop-list-active clear-both">
                    <div id="divList">
                    </div>
                </ul>
            </div>
        </div>
        <div id="divBottomPager">
        </div>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Reservation}"/>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ReservationInnerList_Page_Load() {
        SysApp.Crm.ReservationInnerListIns = new SysApp.Crm.ReservationInnerList();
        var instance = SysApp.Crm.ReservationInnerListIns;

        instance.selfInstance = "SysApp.Crm.ReservationInnerListIns";
        instance.controller = "${ctx}/crm/reservation/";
        instance.clientID = "ReservationInnerList";
        instance.tableName = "rp_reservation";
        instance.desktopInstance = "SysApp.Crm.DesktopIns";
        instance.inputInstance = SysApp.Crm.ReservationInputIns;
        instance.detailInstance = SysApp.Crm.ReservationDetailIns;
        instance.init();
    }

    $(function () {
        ReservationInnerList_Page_Load();
    });
    //]]>
</script>
