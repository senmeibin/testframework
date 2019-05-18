<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/desktop/studentinfo/StudentInfoCommon.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/desktop/studentinfo/StudentBaseInnerDetail.js?${version}"></script>
<div class="student-detail iscroll-wrapper StudentBaseInnerDetail-MainContent" data-iscroll-id="iScrollStudentInfo">
    <div class="desktop-left">
        <!--学员基本资料-->
        <div class="user-content student-edit" id="studentBaseInfo">

        </div>

        <!--学员划转记录 -->
        <div class="student-edit student-assign-log">
            <h5 class="content-folding student-assign-log-evt">
                <i class="fa fa-exchange"></i><strong>划转记录</strong>
                <em class="fa fa-angle-right"></em>
            </h5>

            <div>
                <ul id="studentAssignLogList" style="min-height: 24px;">
                </ul>
            </div>
        </div>
        <br/>
    </div>
</div>
<script type="text/javascript">
    //<![CDATA[
    function StudentBaseInnerDetail_Page_Load() {
        SysApp.Crm.StudentBaseInnerDetailIns = new SysApp.Crm.StudentBaseInnerDetail();
        var instance = SysApp.Crm.StudentBaseInnerDetailIns;
        instance.desktopInstance = "SysApp.Crm.DesktopIns";
        instance.selfInstance = "SysApp.Crm.StudentBaseInnerDetailIns";
        instance.controller = "${ctx}/crm/student/";
        instance.studentAssignLogController = "${ctx}/crm/studentassignlog/";
        instance.clientID = "StudentBaseInnerDetail";
        instance.init();
    }

    $(function () {
        StudentBaseInnerDetail_Page_Load();
    });
    //]]>
</script>