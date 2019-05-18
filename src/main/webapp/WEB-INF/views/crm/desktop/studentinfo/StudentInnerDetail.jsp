<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%--注：学员详情DOM框架布局JSP文件，无需对应的JS文件--%>
<div class="clearfix">
    <div class="col-xs-3" style="border-right: 1px solid #E6E6E6;padding: 0;">
        <!-- 学员基础信息 -->
        <%@ include file="/WEB-INF/views/crm/desktop/studentinfo/StudentBaseInnerDetail.jsp" %>
    </div>

    <div class="col-xs-9" style="padding: 0px 0px 0px 10px;">
        <div class="student-relation-list iscroll-wrapper" data-iscroll-id="iScrollStudentRelationList" style="padding: 0px 10px 20px 0px;">
            <div class="desktop-right">
                <!-- 跟进记录 -->
                <h5><i class="fa fa-commenting fa-blue fa-lg"></i>跟进记录</h5>
                <i class="fa fa-plus add-button" id="btnFollowupAdd">&nbsp;新增跟进记录</i>
                <%@ include file="/WEB-INF/views/crm/desktop/studentinfo/StudentFollowupInnerList.jsp" %>

                <!-- 预约记录 -->
                <h5><i class="fa fa-list-alt fa-blue fa-lg"></i>预约记录</h5>
                <i class="fa fa-plus add-button" id="btnReservationAdd">&nbsp;新增预约记录</i>
                <%@ include file="/WEB-INF/views/crm/desktop/studentinfo/StudentReservationInnerList.jsp" %>

                <!-- 报名记录 -->
                <h5><i class="fa fa-list fa-blue fa-lg"></i>报名记录</h5>
                <i class="fa fa-plus add-button" id="btnRegistrationAdd">&nbsp;新增报名记录</i>
                <%@ include file="/WEB-INF/views/crm/desktop/studentinfo/StudentRegistrationInnerList.jsp" %>

                <!-- 缴费记录 -->
                <h5><i class="fa fa-paypal fa-blue fa-lg"></i>缴费记录</h5>
                <i class="fa fa-plus add-button" id="btnPaymentAdd">&nbsp;新增缴费记录</i>
                <%@ include file="/WEB-INF/views/crm/desktop/studentinfo/StudentPaymentInnerList.jsp" %>
                <br/>
            </div>
        </div>
    </div>
</div>
