<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<script type="text/javascript" src="${staticContentsServer}/static/js/portal/user/PasswordChange.js?${version}"></script>

<title>密码修改</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">密码修改</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body PasswordChange-MainContent">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label">原密码</label>
                    <div class="col-sm-9">
                        <input id="oldPwd" type="password" class="form-control required" data-title="原密码" placeholder="******" maxlength="16" data-rangelength="[6,16]" style="width: 350px;"/>
                        <label id="oldPwd_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label">新密码</label>
                    <div class="col-sm-9">
                        <input id="newPwd" type="password" class="form-control required" data-title="新密码" data-regex="/^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]*$/" data-regex-message="请在{0}中输入至少6位英数组合的字符。"
                               placeholder="******" maxlength="16" data-rangelength="[6,16]" style="width: 350px;"/>
                        <label id="newPwd_Error" class="validator-error"></label>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label">确认密码</label>
                    <div class="col-sm-9">
                        <input id="confirmPwd" type="password" class="form-control" data-title="确认密码" placeholder="******" maxlength="16" data-rangelength="[6,16]"
                               style="width: 350px;"/>
                        <label id="confirmPwd_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="text-center">
                    <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存个人密码
                    </button>
                </div>
            </form>

            <%--原始对象Json字符串[实体属性校验用]--%>
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_User}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_User}"/>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function PasswordChange_Page_Load() {
        SysApp.Portal.PasswordChangeIns = new SysApp.Portal.PasswordChange();
        var instance = SysApp.Portal.PasswordChangeIns;
        instance.selfInstance = "SysApp.Portal.PasswordChangeIns";
        instance.clientID = "PasswordChange";
        instance.controller = "${ctx}/portal/user/";

        instance.init();
    }

    PasswordChange_Page_Load();
    //]]>
</script>