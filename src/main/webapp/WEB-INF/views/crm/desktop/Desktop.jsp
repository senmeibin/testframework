<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<script type="text/javascript">
    //<![CDATA[
    window.desktopRefreshTime = "${desktopRefreshTime}";
    window.showCourseConsultantTree = "${showCourseConsultantTree}";
    window.studentOpenMax = "${studentOpenMax}";
</script>

<link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/crm/desktop.css?${version}"/>

<script type="text/javascript" src="${staticContentsServer}/static/plugins/iscroll/iscroll.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/desktop/Desktop.js?${version}"></script>

<title>我的工作台</title>

<%--特殊说明：由于工作台多Tab页面内多次调用，将通用JSP的include统一放入Desktop主页面中--%>
<%--跟进POPUP编辑--%>
<%@ include file="/WEB-INF/views/crm/followup/FollowupPopupInput.jsp" %>
<%--跟进POPUP编辑--%>
<%@ include file="/WEB-INF/views/crm/followup/FollowupPopupDetail.jsp" %>

<%--预约POPUP编辑--%>
<%@ include file="/WEB-INF/views/crm/reservation/ReservationPopupInput.jsp" %>
<%--预约POPUP编辑--%>
<%@ include file="/WEB-INF/views/crm/reservation/ReservationPopupDetail.jsp" %>

<%--报名POPUP编辑--%>
<%@ include file="/WEB-INF/views/crm/registration/RegistrationPopupInput.jsp" %>
<%--报名POPUP编辑--%>
<%@ include file="/WEB-INF/views/crm/registration/RegistrationPopupDetail.jsp" %>

<%--缴费POPUP编辑--%>
<%@ include file="/WEB-INF/views/crm/payment/PaymentPopupInput.jsp" %>
<%--缴费POPUP编辑--%>
<%@ include file="/WEB-INF/views/crm/payment/PaymentPopupDetail.jsp" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel clearfix" style="position: relative;">
        <!--TAB页-->
        <ul class="nav nav-pills desktop-tab">
            <li id="myStudentTab" class="active">
                <a href="#myStudentContainer" data-toggle="tab" style="padding-right: 10px;">
                    <i class="fa fa-users"></i><span>我的学员</span>
                </a>
            </li>
            <li id="myFollowupTab">
                <a href="#myFollowupContainer" data-toggle="tab" style="padding-right: 10px;">
                    <i class="fa fa-handshake-o"></i><span>我的跟进</span>
                </a>
            </li>
            <li id="myReservationTab">
                <a href="#myReservationContainer" data-toggle="tab" style="padding-right: 10px;">
                    <i class="fa fa-calendar-check-o"></i><span>我的预约</span>
                </a>
            </li>
            <li id="myRegistrationTab" style="display: none;">
                <a href="#myRegistrationContainer" data-toggle="tab" style="padding-right: 10px;">
                    <i class="fa fa-list"></i><span>我的报名</span>
                </a>
            </li>
            <li id="myTaskTab">
                <a href="#myTaskContainer" data-toggle="tab" style="padding-right: 10px;">
                    <i class="fa fa-list"></i><span>我的任务</span>
                </a>
            </li>

            <li id="mySchedulerTab" style="display: none;">
                <a href="#mySchedulerContainer" data-toggle="tab" style="padding-right: 10px;">
                    <i class="fa fa-calendar"></i><span>我的日程</span>
                </a>
            </li>
            <li id="myClassTab" style="display: none;">
                <a href="#myClassContainer" data-toggle="tab" style="padding-right: 10px;">
                    <i class="fa fa-id-card"></i><span>我的班级</span>
                </a>
            </li>

            <li id="myCourseScheduleTab">
                <a href="#myCourseScheduleContainer" data-toggle="tab" style="padding-right: 10px;">
                    <i class="fa fa-id-card"></i><span>课程表</span>
                </a>
            </li>
            <li id="workManualTab" style="display: none;">
                <a href="#workManualContainer" data-toggle="tab" style="padding-right: 10px;">
                    <i class="fa fa-inbox"></i><span>工作手册</span>
                </a>
            </li>
        </ul>
        <!--菜单tab下拉-->
        <div class="dropdown desktop-tab-select" style="position: absolute;top: 5px;right: 33px;">
            <button class="btn btn-info" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                <span class="caret"></span>
                <%--<i class="fa fa-list-ul"></i>--%>
                <em>0</em>
            </button>
            <ul class="dropdown-menu dropdown-menu-right">
            </ul>
        </div>
        <!--TAB切换内容区-->
        <div class="tab-content desktop-content col-xs-12">
            <!--我的学员-->
            <div class="tab-pane active" id="myStudentContainer">
                <%@ include file="/WEB-INF/views/crm/desktop/mystudent/StudentInnerList.jsp" %>
            </div>
            <!--我的跟进-->
            <div class="tab-pane" id="myFollowupContainer">
                <%@ include file="/WEB-INF/views/crm/desktop/myfollowup/FollowupInnerList.jsp" %>
            </div>
            <!--我的预约-->
            <div class="tab-pane" id="myReservationContainer">
                <%@ include file="/WEB-INF/views/crm/desktop/myreservation/ReservationInnerList.jsp" %>
            </div>
            <!--我的任务-->
            <div class="tab-pane" id="myTaskContainer">
                <%@ include file="/WEB-INF/views/crm/desktop/mytask/TaskInnerList.jsp" %>
            </div>

            <!--我的日程-->
            <div class="tab-pane" id="mySchedulerContainer">
                <%@ include file="/WEB-INF/views/crm/desktop/myscheduler/SchedulerInnerList.jsp" %>
            </div>
            <!--我的班级-->
            <div class="tab-pane" id="myClassContainer">
                <%@ include file="/WEB-INF/views/crm/desktop/myclass/ClassInnerList.jsp" %>
            </div>

            <!--课程表-->
            <div class="tab-pane" id="myCourseScheduleContainer">
                <%@ include file="/WEB-INF/views/crm/desktop/mycourseschedule/CourseScheduleInnerList.jsp" %>
            </div>
            <!--工作手册-->
            <div class="tab-pane" id="workManualContainer">
                <%@ include file="/WEB-INF/views/crm/desktop/workmanual/WorkManualInnerList.jsp" %>
            </div>
            <!--学员详情-->
            <div class="tab-pane" id="studentInfoContainer">
                <%@ include file="/WEB-INF/views/crm/desktop/studentinfo/StudentInnerDetail.jsp" %>
            </div>

        </div>
        <div id="divDashboardContainer" style="position: relative;">
            <!--仪表盘-折叠-->
            <div class="right-side-fold">
                <button><i class="fa fa-angle-right"></i></button>
            </div>
            <!--仪表盘-->
            <%@ include file="/WEB-INF/views/crm/desktop/dashboard/DashboardInnerList.jsp" %>
        </div>
    </div>
</div>

<%--手机号快捷搜索--%>
<div class="phone-number-search">
    <!-- 隐藏手机号快捷搜索 -->
    <%--<div class="pull-left">--%>
    <%--<input type="text" placeholder="输入手机号码" maxlength="11" id="desktopSearchMobile">--%>
    <%--<button type="button" id="btnMobileSearch"><i class="fa fa-search"></i></button>--%>
    <%--</div>--%>
    <div class="pull-left Desktop-MainContent" style="padding-top: 5px;">
        <ctag:HelpIcon tips="操作说明"/>
    </div>
</div>

<%--POPUP编辑 学员信息--%>
<%@ include file="/WEB-INF/views/crm/desktop/studentinfo/input/StudentPopupInput.jsp" %>

<%--POPUP 发送短信--%>
<%@ include file="/WEB-INF/views/crm/desktop/studentinfo/SendSmsPopupInput.jsp" %>
<script type="text/javascript">
    //<![CDATA[
    function Desktop_Page_Load() {
        SysApp.Crm.DesktopIns = new SysApp.Crm.Desktop();
        var instance = SysApp.Crm.DesktopIns;

        instance.selfInstance = "SysApp.Crm.DesktopIns";
        instance.studentController = "${ctx}/crm/student/";
        instance.controller = "${ctx}/crm/desktop/";
        instance.clientID = "Desktop";
        instance.init();
    }

    Desktop_Page_Load();

    $(function () {
        //学员列表-高度
        $(".desktop-inner-list").css("max-height", $(window).height() - 225);

        //Dashboard区域高度
        $(".dashboard-inner-list,.desktop-content,.student-detail").css("height", $(window).height() - 120);
        $(".dashboard-inner-list,.desktop-content,.student-relation-list").css("height", $(window).height() - 120);

        //操作台-学员详情-各个分类块-折叠展开
        $(".content-folding").click(function () {
            if ($(this).children("em").hasClass("fa-angle-down")) {
                $(this).children("em").removeClass("fa-angle-down").addClass("fa-angle-right");
                $(this).next().removeClass("active");
            } else {
                $(this).children("em").addClass("fa-angle-down").removeClass("fa-angle-right");
                $(this).next().addClass("active");
                //加载划转记录
                if ($(this).hasClass("student-assign-log-evt")) {
                    SysApp.Crm.StudentBaseInnerDetailIns.getStudentAssignLogListAjax(SysApp.Crm.DesktopIns);
                }
            }

            //操作台-左侧-滚动条-刷新
            window.iScrollStudentInfo.refresh();
        });

        //仪表盘-滚动条-刷新
        window.iScrollDashboardInnerList.refresh();
    });

    $('.desktop-tab > li > a').on('shown.bs.tab', function (e) {
        if ($("#myStudentTab,#myFollowupTab,#myReservationTab,#myRegistrationTab,#myTaskTab,#mySchedulerTab,#myClassTab,#myCourseScheduleTab").hasClass("active")) {
            //返回列表页-仪表盘复原(tabs切换)
            $("#divDashboardContainer").show();
        }
    });
</script>
<ctag:HelpPopup pageInstance="SysApp.Crm.DesktopIns"/>