<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/iscroll/iscroll.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/desktop/studentinfo/StudentInfoCommon.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/desktop/studentinfo/StudentPopupDetail.js?${version}"></script>

<style>
    .iscroll-wrapper {
        height: 480px;
    }
</style>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content StudentPopupDetail-MainContent" style="width: 1100px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader>学员详情</ctag:ModalHeader>

    <div class="modal-body clearfix" style="padding: 0;">
        <div class="col-xs-6 student-detail iscroll-wrapper" data-iscroll-id="iScrollStudentInfo" style="border-right: 1px solid #E6E6E6;padding-left: 10px;">
            <!-- 学员基础信息 -->
            <%@ include file="/WEB-INF/views/crm/desktop/studentinfo/popupdetail/StudentBaseInnerDetail.jsp" %>
        </div>

        <div class="col-xs-6 iscroll-wrapper" data-iscroll-id="iScrollStudentInfoRight" style="padding: 0;">
        </div>
    </div>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>

<script type="text/javascript">
    //<![CDATA[
    function StudentPopupDetail_Page_Load() {
        SysApp.Crm.StudentPopupDetailIns = new SysApp.Crm.StudentPopupDetail();
        var instance = SysApp.Crm.StudentPopupDetailIns;
        instance.selfInstance = "SysApp.Crm.StudentPopupDetailIns";
        instance.studentController = "${ctx}/crm/student/";
        instance.studentAssignLogController = "${ctx}/crm/studentassignlog/";
        instance.clientID = "StudentPopupDetail";
        instance.init();
    }

    $(function () {
        StudentPopupDetail_Page_Load();

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
                    SysApp.Crm.StudentPopupDetailIns.getStudentAssignLogListAjax(SysApp.Crm.StudentPopupDetailIns);
                }
            }

            //人才库学员详情右侧滚动
            window.iScrollStudentInfoRight.refresh();

            //操作台-左侧-滚动条-刷新
            window.iScrollStudentInfo.refresh();
        });
    });
    //]]>
</script>