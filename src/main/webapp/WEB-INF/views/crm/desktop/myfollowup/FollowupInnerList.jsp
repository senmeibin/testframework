<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/Common.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/desktop/myfollowup/FollowupInnerList.js?${version}"></script>
<div class="row FollowupInnerList-MainContent">
    <div style="padding-right: 10px;">
        <div class="desktop-search-condition clearfix">
            <form id="MainForm" class="form-horizontal" onsubmit="return false;">
                <div class="col-sm-1" style="padding-left: 0;width: 100px;">
                    <select id="followupBelongCampusUid" data-alias-table="main" class="form-control" data-blank-text="-所属校区-">
                    </select>
                </div>
                <div class="col-sm-1" style="padding-left: 0;width: 100px;">
                    <select id="followupBelongConsultantUserUid" data-alias-table="main" class="form-control" data-blank-text="-课程顾问-">
                    </select>
                </div>
                <div class="col-xs-6" style="padding-left: 5px;">
                    <input type="text" class="form-control list-keyword-input" id="main.contents,student.name,student.email,student.mobile,student.parentName,student_belong_consultant_user.userName" data-search-mode="OR" data-title="关键字"
                           placeholder="请输入关键字查询（如：跟进内容、学员姓名、家长姓名、手机号码等）" data-search-mode="$ignore_search" style="width:450px;float:left;">
                    <button type="button" class="btn btn-primary" id="btnCustomSearch">
                        <i class="fa fa-search"></i>
                    </button>
                </div>
            </form>
        </div>
        <!--学员列表区-->
        <div class="iscroll-wrapper desktop-inner-list" data-iscroll-id="iScrollFollowupInnerList">
            <div>
                <ul class="desktop-list desktop-list-active clear-both">
                    <div id="divList">
                    </div>
                </ul>
            </div>
        </div>
        <div id="divBottomPager">
        </div>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Followup}"/>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function FollowupInnerList_Page_Load() {
        SysApp.Crm.FollowupInnerListIns = new SysApp.Crm.FollowupInnerList();
        var instance = SysApp.Crm.FollowupInnerListIns;

        instance.selfInstance = "SysApp.Crm.FollowupInnerListIns";
        instance.controller = "${ctx}/crm/followup/";
        instance.clientID = "FollowupInnerList";
        instance.tableName = "rp_followup";
        instance.desktopInstance = "SysApp.Crm.DesktopIns";
        instance.inputInstance = SysApp.Crm.FollowupInputIns;
        instance.detailInstance = SysApp.Crm.FollowupDetailIns;
        instance.init();
    }

    FollowupInnerList_Page_Load();
    //]]>
</script>
