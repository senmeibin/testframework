<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/Common.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/desktop/mytask/TaskInnerList.js?${version}"></script>
<div class="row TaskInnerList-MainContent">
    <div style="padding-right: 10px;">
        <div class="desktop-search-condition clearfix">
            <form id="MainForm" class="form-horizontal" onsubmit="return false;">
                <input type="hidden" id="nextFollowupDate$=" data-alias-table="followup" value="IS NOT NULL">
                <div class="col-sm-1" style="padding-left: 0;width: 100px;">
                    <select id="studentBelongCampusUid" data-alias-table="main" class="form-control" data-blank-text="-所属校区-">
                    </select>
                </div>
                <div class="col-sm-1" style="padding-left: 0;width: 100px;">
                    <select id="studentBelongConsultantUserUid" data-alias-table="main" class="form-control" data-blank-text="-课程顾问-">
                    </select>
                </div>
                <div class="col-sm-1" style="padding-left: 0;width: 100px;">
                    <select id="consultMethodCd" data-alias-table="main" class="form-control" data-blank-text="-咨询方式-">
                    </select>
                </div>
                <div class="col-sm-1" style="padding-left: 0;width: 100px;">
                    <select id="studentStatusCd" data-alias-table="main" class="form-control" data-blank-text="-学员状态-">
                    </select>
                </div>
                <div class="col-xs-6" style="padding-left: 5px;">
                    <input type="text" class="form-control list-keyword-input" id="main.name,main.email,main.mobile,main.parentName,student_belong_consultant_user.userName" data-search-mode="OR" data-title="关键字"
                           placeholder="请输入关键字查询（如：学员姓名、家长姓名、手机号码等）" data-search-mode="$ignore_search" style="width:450px;float:left;">
                    <button type="button" class="btn btn-primary" id="btnCustomSearch">
                        <i class="fa fa-search"></i>
                    </button>
                </div>
            </form>
        </div>
        <!--学员列表区-->
        <div class="iscroll-wrapper desktop-inner-list" data-iscroll-id="iScrollTaskInnerList">
            <div>
                <ul class="desktop-list desktop-list-active clear-both">
                    <div id="divList">
                    </div>
                </ul>
            </div>
        </div>
        <div id="divBottomPager">
        </div>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Student}"/>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function TaskInnerList_Page_Load() {
        SysApp.Crm.TaskInnerListIns = new SysApp.Crm.TaskInnerList();
        var instance = SysApp.Crm.TaskInnerListIns;

        instance.selfInstance = "SysApp.Crm.TaskInnerListIns";
        instance.controller = "${ctx}/crm/student/";
        instance.clientID = "TaskInnerList";
        instance.tableName = "rp_student";
        instance.desktopInstance = "SysApp.Crm.DesktopIns";
        instance.init();
    }

    $(function () {
        TaskInnerList_Page_Load();
    });
    //]]>
</script>
