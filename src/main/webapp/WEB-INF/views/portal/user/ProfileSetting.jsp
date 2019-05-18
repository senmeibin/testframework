<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/portal/user/ProfileSetting.js?${version}"></script>

<title>个人信息</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">个人信息</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body ProfileSetting-MainContent">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <ctag:Fold id="baseInfo" name="基本信息"/>

                <div id="baseInfo" class="separate-block clearfix">
                    <div class="form-group" style="display: none">
                        <div>
                            <input id="uid" type="hidden">
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-sm-3 control-label" style="color: #333;">部门</label>
                        <label class="col-sm-9 control-label content-label" id="deptName"/>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-sm-3 control-label" style="color: #333;">职位</label>
                        <label class="col-sm-9 control-label content-label" id="positionName"/>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-sm-3 control-label" style="color: #333;">工号</label>
                        <label class="col-sm-9 control-label content-label" id="userNumber"/>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-sm-3 control-label" style="color: #333;">用户名</label>
                        <label class="col-sm-9 control-label content-label" id="userCd"/>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-sm-3 control-label" style="color: #333;">入职日期</label>
                        <label class="col-sm-9 control-label content-label" id="entryDate"></label>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-sm-3 control-label" style="color: #333;">姓名</label>
                        <div class="col-sm-9">
                            <input id="userName" type="text" class="form-control required" data-title="姓名" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                            <label id="userName_Error" class="validator-error"/>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-sm-3 control-label" style="color: #333;">性别</label>
                        <div class="col-sm-9">
                            <select id="sexCd" data-alias-table="main" class="form-control required" data-title="性别" style="width: 350px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="sexCd_Error" class="validator-error"/>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-3 control-label">邮箱地址</label>
                        <div class="col-sm-9">
                            <input id="userMail" type="text" class="form-control required" data-title="邮箱地址" maxlength="64" data-rangelength="[0,64]" style="width: 350px;"/>
                            （请输入企业个人邮箱）
                            <label id="userMail_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-sm-3 control-label">邮箱密码</label>
                        <div class="col-sm-9">
                            <input id="userInfo.mailPassword" type="password" class="form-control" data-title="邮箱密码" placeholder="******" maxlength="16" data-rangelength="[0,16]" style="width: 350px;"/>
                            （如果需要以企业个人邮箱的名义发送相关邮件，请配置邮箱系统登录密码）
                            <label id="userInfo.mailPassword_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-sm-3 control-label">手机号码</label>
                        <div class="col-sm-9">
                            <input id="userPhone" type="text" class="form-control required" data-title="手机号码" maxlength="11" data-rangelength="[0,11]" style="width: 350px;"/>
                            <label id="userPhone_Error" class="validator-error"></label>
                        </div>
                    </div>
                </div>
                <br/>
                <ctag:Fold id="extraInfo" name="扩展信息"/>

                <div id="extraInfo" class="separate-block clearfix" style="display: none;">
                    <div class="form-group form-inline">
                        <label class="col-sm-3 control-label">QQ号</label>
                        <div class="col-sm-9">
                            <input id="userInfo.qq" type="text" class="form-control" data-title="QQ号" maxlength="32" data-rangelength="[0,16]" style="width: 350px;"/>
                            <label id="userInfo.qq_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-sm-3 control-label">微信号</label>
                        <div class="col-sm-9">
                            <input id="userInfo.weixin" type="text" class="form-control" data-title="微信号" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                            <label id="userInfo.weixin_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-sm-3 control-label">英文名</label>
                        <div class="col-sm-9">
                            <input id="userEnName" type="text" class="form-control" data-title="英文名" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                            <label id="userEnName_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-sm-3 control-label">出生日期</label>
                        <div class="col-sm-3">
                            <div class="input-group datetime-picker">
                                <input id="birthday" type="text" class="form-control" data-title="出生日期" maxlength="10" style="width: 150px;"/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                        </div>
                        <div class="col-sm-6" style="padding-top: 7px;">
                            <label id="birthday_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-sm-3 control-label">传真号码</label>
                        <div class="col-sm-9">
                            <input id="userFaxphone" type="text" class="form-control" data-title="传真号码" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                            <label id="userFaxphone_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-sm-3 control-label">分机号码</label>
                        <div class="col-sm-9">
                            <input id="userExtensionNumber" type="text" class="form-control" data-title="分机号码" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                            <label id="userExtensionNumber_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-sm-3 control-label">家庭地址</label>
                        <div class="col-sm-9">
                            <input id="userAddress" type="text" class="form-control" data-title="家庭地址" maxlength="128" data-rangelength="[0,128]" style="width: 350px;"/>
                            <label id="userAddress_Error" class="validator-error"></label>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-sm-3 control-label">邮政编码</label>
                        <div class="col-sm-9">
                            <input id="userZipcode" type="text" class="form-control" data-title="邮政编码" maxlength="6" data-rangelength="[0,6]" style="width: 350px;"/>
                            <label id="userZipcode_Error" class="validator-error"></label>
                        </div>
                    </div>
                </div>
            </form>
            <br/>
            <div class="text-center">
                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存个人信息
                </button>
            </div>

            <%--原始对象Json字符串[实体属性校验用]--%>
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_User}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_User}"/>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ProfileSetting_Page_Load() {
        SysApp.Portal.ProfileSettingIns = new SysApp.Portal.ProfileSetting();
        var instance = SysApp.Portal.ProfileSettingIns;
        instance.selfInstance = "SysApp.Portal.ProfileSettingIns";
        instance.clientID = "ProfileSetting";
        instance.controller = "${ctx}/portal/user/";

        instance.init();
    }

    ProfileSetting_Page_Load();
    //]]>
</script>