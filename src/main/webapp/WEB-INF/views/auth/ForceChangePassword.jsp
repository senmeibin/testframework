<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<script type="text/javascript">
    //<![CDATA[
    window.contextPath = "${ctx}";
    window.imagesPath = "${staticContentsServer}/static/images/";
    //]]>
</script>
<head>
    <title>${systemName}-强制密码修改</title>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/bootstrap/css/bootstrap.min.css?${version}"/>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/font-awesome/css/font-awesome.min.css?${version}"/>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/base/main.css?${version}"/>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/auth/auth.css?${version}"/>

    <script type="text/javascript" src="${staticContentsServer}/static/js/Namespace.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/jquery.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/prototype.js?${version}"></script>

    <script type="text/javascript" src="${staticContentsServer}/static/plugins/jquery-validation/jquery.validate.min.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/plugins/jquery-validation/localization/messages_zh.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/plugins/bootstrap/js/bootstrap.min.js?${version}"></script>

    <script type="text/javascript" src="${staticContentsServer}/static/js/MainScript.min.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/Core.min.js?${version}"></script>
</head>

<body class="full-width">
<%@ include file="/WEB-INF/views/cmn/CmnMessage.jsp" %>

<%--画面加载中提示--%>
<%@ include file="/WEB-INF/views/cmn/Loading.jsp" %>

<%@ include file="/WEB-INF/views/auth/common/Common.jsp" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/auth/forcechangepassword/ForceChangePassword.js?${version}"></script>

<div class="auth-center-header"></div>
<!--logo区开始-->
<div class="auth-center-logo"><i class="fa fa-windows"></i>&nbsp;${systemName}<span>安全中心</span></div>
<!--内容区开始-->
<div class="force-change-password-content">
    <div class="force-change-password-left"><img src="${staticContentsServer}/static/images/auth/forced_left_img.jpg"></div>
    <div class="force-change-password-right">
        <h3>初次登录请修改密码</h3>
        <div class="panel-body ForceChangePassword-MainContent">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label">原密码</label>
                    <div class="col-sm-9">
                        <input id="oldPwd" type="password" class="form-control required" data-title="原密码" placeholder="******" maxlength="16" data-rangelength="[6,16]" style="width: 240px;"/>
                        <label id="oldPwd_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label">新密码</label>
                    <div class="col-sm-9">
                        <input id="newPwd" type="password" class="form-control required" data-title="新密码" data-regex="/^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]*$/" data-regex-message="请在{0}中输入至少6位英数组合的字符。"
                               placeholder="******" maxlength="16" data-rangelength="[6,16]" style="width: 240px;"/>
                        <label id="newPwd_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label">确认密码</label>
                    <div class="col-sm-9">
                        <input id="confirmPwd" type="password" class="form-control" data-title="确认密码" placeholder="******" maxlength="16" data-rangelength="[6,16]"
                               style="width: 240px;"/>
                        <label id="confirmPwd_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label"></label>
                    <div class="col-sm-9">
                        <button type="button" class="btn btn-primary" style="width: 240px;margin-top: 20px;font-size: 14px;height: 32px;" id="btnSave"><i class="fa fa-user-secret fa-lg"></i>修改密码
                        </button>
                    </div>
                </div>
            </form>
        </div>
        <div class="clearfix"></div>
    </div>
</div>
<div class="auth-center-footer">2016 © ${companyName}-${systemName}</div>
</body>
</html>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ForceChangePassword_Page_Load() {
        SysApp.Auth.ForceChangePasswordIns = new SysApp.Auth.ForceChangePassword();
        var instance = SysApp.Auth.ForceChangePasswordIns;
        instance.selfInstance = "SysApp.Portal.ForceChangePasswordIns";
        instance.clientID = "ForceChangePassword";
        instance.controller = "${ctx}/auth/forcechangepassword/";
        instance.listUrl = "${ctx}/portal/dashboard";
        instance.init();
    }

    ForceChangePassword_Page_Load();
    //]]>
</script>