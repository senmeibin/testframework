<%@ page language="java" pageEncoding="UTF-8" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<c:if test="${messageNotificationEnabled == 'true'}">
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/interface/MessagePushService.js"></script>
</c:if>

<div class="navbar-header">
    <div class="brand">
        <a href="${ctx}/portal/dashboard" class="logo" style="font-size: 20px;color: #FFF;padding-top: 10px;">
            <i class="fa fa-windows"></i>&nbsp;${systemName}
            <%--<img src="${staticContentsServer}/static/images/base/logo.png?${version}" alt=""/>--%>
        </a>
    </div>

    <div class="top-nav">
        <ul class="nav pull-right top-menu">
            <%--消息提示--%>
            <c:if test="${messageNotificationEnabled == 'true'}">
                <li>
                    <div class="header-notice-message">
                        <a href="#">
                            <i class="fa fa-bell-o"></i>
                            <span class="badge bg-important" id="spanUnreadCounts" style="width: 37px;"></span>
                        </a>
                    </div>
                </li>
            </c:if>
            <%--系统首页--%>
            <li>
                <a href="${ctx}/portal/dashboard"><i class="fa fa-home fa-lg"></i>系统首页</a>
            </li>

            <c:if test="${helpServiceBbsURL != '未开通'}">
                <li>
                    <a href="${helpServiceBbsURL}" target="_blank"><i class="fa fa-wechat"></i>公司社区</a>
                </li>
            </c:if>

            <c:if test="${helpServiceQQGroupQR != '未开通' && helpServiceQQGroupNO != '未开通' && helpServiceQQGroupURL != '未开通'}">
                <%--QQ帮助群--%>
                <div class="popovers" data-trigger="hover" data-placement="bottom" style="float: left;"
                     data-content="<img style='width:100%;border-radius:0px;' src='${ctx}/static/images/base/${helpServiceQQGroupQR}'><div style='text-align:center;'>扫码加入QQ帮助群(${helpServiceQQGroupNO})</div>">
                    <li>
                        <a class="header-help-service" target="qqGroupFrame" href="${helpServiceQQGroupURL}">
                            <i class="fa fa-qq"></i>QQ帮助群
                        </a>
                    </li>
                </div>
            </c:if>

            <%--服务热线--%>
            <c:if test="${helpServiceHotline != '未开通'}">
                <div class="popovers" data-trigger="hover" data-placement="bottom" style="float: left;"
                     data-content="服务热线：<div style='padding-left:25px;'>${helpServiceHotline}</div>
                 服务时间：<br/><div style='padding-left:25px;'>上午 09：00-12：00</div><div style='padding-left:25px;'>下午 13：00-18：00</div>
                 非工作时间问题反馈请使用QQ帮助群">
                    <li>
                        <a class="header-help-service" href="#"><i class="fa fa-phone fa-lg"></i>服务热线</a>
                    </li>
                </div>
            </c:if>

            <%--基础数据--%>
            <shiro:hasAnyRoles name="SystemManagement,BasicDataManagement">
                <li>
                    <a href="${ctx}/cmn/customer/"><i class="fa fa-database"></i>基础数据</a>
                </li>
            </shiro:hasAnyRoles>

            <%--系统管理--%>
            <shiro:hasAnyRoles name="SystemManagement,ItSupport">
                <li>
                    <a href="${ctx}/sys/user/"><i class="fa fa-cog fa-lg"></i>系统管理</a>
                </li>
            </shiro:hasAnyRoles>

            <li class="dropdown" style="min-width: 120px;text-align: center;">
                <a data-toggle="dropdown" class="dropdown-toggle top-main-menu" href="#" sub_menu=".personal-setting">
                    <span class="username"><shiro:principal/></span>
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu extended logout personal-setting">
                    <li>
                        <a href="${ctx}/portal/user/profileSetting">个人设置</a>
                    </li>
                    <li>
                        <a href="${ctx}/portal/user/themeSetting">主题设置</a>
                    </li>
                    <li>
                        <a href="${ctx}/portal/user/passwordChange">修改密码</a>
                    </li>
                    <li>
                        <a href="${ctx}/logout">退出系统</a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>

<%--弹层提示框--%>
<div class="popup-prompt">
    <div class="popup-prompt-head">
        <strong class="popup-prompt-title">消息提醒</strong>
        <i class="fa fa-close"></i>
    </div>
    <div class="popup-prompt-content">
    </div>
</div>

<%--QQ帮助群专用iframe--%>
<iframe id="qqGroupFrame" name="qqGroupFrame" style="display: none;"></iframe>

<script type="text/javascript">
    function Header_Page_Load() {
        SysApp.Cmn.HeaderIns = new SysApp.Cmn.Header();
        var instance = SysApp.Cmn.HeaderIns;

        instance.selfInstance = "SysApp.Cmn.HeaderIns";
        instance.clientID = "Header";
        instance.controller = "";
        instance.userUid = "${loginUser.userUid}";
        instance.getUnreadCountController = "${ctx}/cmn/message/getUnreadCount/";
        instance.init();
    }

    $(function () {
        Header_Page_Load();

        <c:if test="${messageNotificationEnabled == 'true'}">
        //重写DWR默认错误句柄
        dwr.engine._errorHandler = function (message, ex) {
            SysCmn.CmnMsg.showMessage("服务端通讯中断，请稍后重试。", MSG_TYPE_ERROR);
            dwr.engine._debug("Error: " + ex.name + ", " + ex.message, true);
        };
        </c:if>
    });
</script>
<c:if test="${messageNotificationEnabled == 'true'}">
    <%@ include file="/WEB-INF/views/cmn/message/messagedetail/MessageDetailPopupList.jsp" %>
</c:if>