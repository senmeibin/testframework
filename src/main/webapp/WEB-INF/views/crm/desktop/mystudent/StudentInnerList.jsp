<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/Common.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/desktop/mystudent/StudentInnerList.js?${version}"></script>
<div class="row StudentInnerList-MainContent">
    <div style="padding-right: 10px;">
        <div class="desktop-search-condition clearfix">
            <form id="MainForm" class="form-horizontal" onsubmit="return false;">
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
                <div class="col-xs-1 auto-match-position" style="padding-left: 0;width: 110px;">
                    <i class="fa fa-toggle-on have-next-followup"></i>
                    <label style="color: red;margin: 2px 0px 0px 2px;">待跟进学员</label>
                    <input id="nextFollowupDate" type="hidden" data-search-mode="=" value="IS NOT NULL"/>
                </div>
                <div class="col-xs-6" style="padding-left: 0">
                    <input type="text" class="form-control list-keyword-input" id="main.name,main.email,main.mobile,main.parentName,student_belong_consultant_user.userName" data-search-mode="OR" data-title="关键字"
                           placeholder="请输入关键字查询（如：学员姓名、家长姓名、手机号码等）" data-search-mode="$ignore_search" style="width:450px;float:left;">
                    <button type="button" class="btn btn-primary" id="btnCustomSearch">
                        <i class="fa fa-search"></i>
                    </button>

                    <button id="btnStudentAdd" type="button" class="btn btn-primary" style="margin-left: 30px;">
                        <i class="fa fa-plus"></i>新增学员
                    </button>
                </div>
            </form>
        </div>
        <!--学员列表区-->
        <div class="iscroll-wrapper desktop-inner-list" data-iscroll-id="iScrollStudentInnerList">
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
    function StudentInnerList_Page_Load() {
        SysApp.Crm.StudentInnerListIns = new SysApp.Crm.StudentInnerList();
        var instance = SysApp.Crm.StudentInnerListIns;

        instance.selfInstance = "SysApp.Crm.StudentInnerListIns";
        instance.controller = "${ctx}/crm/student/";
        instance.clientID = "StudentInnerList";
        instance.tableName = "rp_student";
        instance.desktopInstance = "SysApp.Crm.DesktopIns";
        instance.init();

        instance.bindScheduleFollowup();
    }

    $(function () {
        StudentInnerList_Page_Load();
    });
    //]]>
</script>
