<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/sys/user/UserInput.js?${version}"></script>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height">
        <div class="panel-heading UserInput-MainContent">
            <em></em>
            <div class="panel-title" id="ctlTitle">用户编辑</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <div class="UserInput-MainContent">
                <div id="divRegSystemTips" class="flash-error margin-bottom-space" style="display: none">系统登录的初始化用户，请谨慎修改（如有修改疑问，请与技术开发人员联系）。</div>
                <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
                <form id="MainForm" class="form-horizontal">
                    <div class="form-group" style="display: none">
                        <div>
                            <input id="uid" type="hidden">
                        </div>
                    </div>
                    <ctag:Fold id="baseInfo" name="基本信息" tipsContent="用户名与手机号码不能重复，必须唯一填写"/>
                    <div id="baseInfo" class="separate-block clearfix baseInfo-block">
                        <div class="col-sm-6">
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">部门</label>
                                <div class="col-sm-9">
                                    <ctag:ComboTree valueDomId="deptUid" textDomId="deptName" dataTitle="部门" searchMode="ignore_search" parentInstance="SysApp.Sys.UserInputIns" dispName="deptFullName" nodeName="deptName" idKey="uid"
                                                    parentIdKey="parentDeptUid"
                                                    dataUrl="${ctx}/sys/dept/getAllDeptList" required="required" style="width: 311px;"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">职位</label>
                                <div class="col-sm-9">
                                    <ctag:Select2 selectId="positionUid" dataTitle="职位" style="width: 350px;"/>
                                    <label id="positionUid_Error" class="validator-error"></label>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">用户名</label>
                                <div class="col-sm-9">
                                    <input id="userCd" type="text" class="form-control required duplication" data-title="用户名" maxlength="32" data-rangelength="[0,32]" data-record-status-is-active="false" style="width: 350px;"/>
                                    <label id="userCd_Error" class="validator-error"></label>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">姓名</label>
                                <div class="col-sm-9">
                                    <input id="userName" type="text" class="form-control required" data-title="姓名" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                                    <label id="userName_Error" class="validator-error"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">密码</label>
                                <div class="col-sm-9">
                                    <input id="plainPassword" type="password" class="form-control" data-title="密码" data-regex="/^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]*$/"
                                           data-regex-message="请在{0}中输入至少6位英数组合的字符。" placeholder="******" maxlength="16" data-rangelength="[6,16]"
                                           style="width: 350px;"/>
                                    <label id="plainPassword_Error" class="validator-error"></label>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">确认密码</label>
                                <div class="col-sm-9">
                                    <input id="confirmPassword" type="password" class="form-control" data-title="确认密码" placeholder="******" maxlength="16" data-rangelength="[6,16]"
                                           style="width: 350px;"/>
                                    <label id="confirmPassword_Error" class="validator-error"></label>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">管辖分公司</label>
                                <div class="col-sm-9">
                                    <ctag:ComboCheckTree valueDomId="deptManageUids" textDomId="deptManageName" parentInstance="SysApp.Sys.UserInputIns" dataTitle="管辖分公司" style="width:311px;"
                                                         idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dispName="deptFullName" dataUrl="${ctx}/sys/dept/getAllCompanyList"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">管辖签单公司</label>
                                <div class="col-sm-9">
                                    <ctag:ComboCheckTree valueDomId="signCompanyManageUids" textDomId="signCompanyName" parentInstance="SysApp.Sys.UserInputIns" dataTitle="管辖签单公司" style="width:311px;"
                                                         idKey="uid" parentIdKey="" nodeName="companyName" dispName="companyName" dataUrl="${ctx}/cmn/signcompany/getAllCompanyList"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">性别</label>
                                <div class="col-sm-9">
                                    <select id="sexCd" data-alias-table="main" class="form-control" data-title="性别" style="width: 350px;">
                                        <option value="">请选择</option>
                                    </select>
                                    <label id="sexCd_Error" class="validator-error"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">入职日期</label>
                                <div class="col-sm-9">
                                    <div class="input-group datetime-picker">
                                        <input id="entryDate" type="text" class="form-control" data-title="入职日期" maxlength="10" style="width: 311px;"/>
                                        <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                    </div>
                                    <label id="entryDate_Error" class="validator-error"/>
                                </div>
                            </div>

                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">邮箱地址</label>
                                <div class="col-sm-9">
                                    <input id="userMail" type="text" class="form-control required" data-title="邮箱地址" data-regex="/^(\w)+([\.-]?\w+)*@(\w)+((\.\w+)+)$/"
                                           data-regex-message="请输入有效的邮件地址(如：zhangsan@bpms.com)。" maxlength="64" data-rangelength="[0,64]" style="width: 350px;"/>
                                    <label id="userMail_Error" class="validator-error"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">手机号码</label>
                                <div class="col-sm-9">
                                    <input id="userPhone" type="text" class="form-control required duplication" data-title="手机号码" maxlength="11" data-rangelength="[0,11]" style="width: 350px;"/>
                                    <label id="userPhone_Error" class="validator-error"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">工号</label>
                                <div class="col-sm-9">
                                    <input id="userNumber" type="text" class="form-control required duplication" data-title="工号" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                                    <label id="userNumber_Error" class="validator-error"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">考勤号</label>
                                <div class="col-sm-9">
                                    <input id="attendanceNo" type="text" class="form-control" data-title="考勤号" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                                    <label id="attendanceNo_Error" class="validator-error"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">管辖城市</label>
                                <div class="col-sm-9">
                                    <ctag:ComboCheckTree valueDomId="cityManageUids" textDomId="cityManageName" parentInstance="SysApp.Sys.UserInputIns" dataTitle="管辖城市" style="width:311px;"
                                                         idKey="uid" parentIdKey="parentUid" nodeName="regionName" dispName="regionName" dataUrl="${ctx}/cmn/region/getAllRegion/2"/>
                                    <label id="deptManageName_Error" class="validator-error"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br/>
                    <ctag:Fold id="roleInfo" name="角色权限"/>
                    <div id="roleInfo" class="separate-block clearfix">
                        <div class="col-sm-12" style="padding: 0px 15px 0px 15px;">
                            <div id="roles"></div>
                        </div>
                    </div>
                    <br/>
                    <ctag:Fold id="securityInfo" name="安全认证"/>
                    <div id="securityInfo" class="separate-block clearfix">
                        <div class="col-lg-6">
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">可访问IP</label>
                                <div class="col-sm-9">
                                    <input id="accessIp" type="text" class="form-control" data-title="可访问IP" maxlength="512" data-rangelength="[0,512]" style="width: 350px;"/>
                                    <label id="accessIp_Error" class="validator-error"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">强制密码变更</label>
                                <div class="col-sm-9">
                                    <input type="checkbox" id="forceChangePswd" class="switch"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">登录许可开始时间</label>
                                <div class="col-sm-9">
                                    <div class="input-group datetime-picker">
                                        <input id="startLoginTime" type="text" class="form-control" data-title="登录许可开始时间" maxlength="10" style="width:311px;"/>
                                        <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                                    </div>
                                    <label id="startLoginTime_Error" class="validator-error"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">登录许可结束时间</label>
                                <div class="col-sm-9">
                                    <div class="input-group datetime-picker">
                                        <input id="endLoginTime" type="text" class="form-control" data-title="登录许可结束时间" maxlength="10" style="width:311px;"/>
                                        <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                                    </div>
                                    <label id="endLoginTime_Error" class="validator-error"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <br/>
                    <ctag:Fold id="extraInfo" name="扩展信息" foldIconClass="fa-caret-up"/>
                    <div id="extraInfo" style="display: none;" class="separate-block clearfix extraInfo-block">
                        <div class="col-lg-6">
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">QQ号</label>
                                <div class="col-sm-9">
                                    <input id="userInfo.qq" type="text" class="form-control" data-title="QQ号" maxlength="32" data-rangelength="[0,16]" style="width: 350px;"/>
                                    <label id="userInfo.qq_Error" class="validator-error"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">微信号</label>
                                <div class="col-sm-9">
                                    <input id="userInfo.weixin" type="text" class="form-control" data-title="微信号" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                                    <label id="userInfo.weixin_Error" class="validator-error"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">英文名</label>
                                <div class="col-sm-9">
                                    <input id="userEnName" type="text" class="form-control" data-title="英文名" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                                    <label id="userEnName_Error" class="validator-error"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">出生日期</label>
                                <div class="col-sm-9">
                                    <div class="input-group datetime-picker">
                                        <input id="birthday" type="text" class="form-control" data-title="出生日期" maxlength="10" style="width: 311px;"/>
                                        <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                    </div>
                                    <label id="birthday_Error" class="validator-error"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">用户坐席</label>
                                <div class="col-sm-9">
                                    <input id="userAgent" type="text" class="form-control" data-title="用户坐席" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                                    <label id="userAgent_Error" class="validator-error"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">用户坐席密码</label>
                                <div class="col-sm-9">
                                    <input id="userAgentPassword" type="password" class="form-control" data-title="用户坐席密码" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                                    <label id="userAgentPassword_Error" class="validator-error"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">分机号码</label>
                                <div class="col-sm-9">
                                    <input id="userExtensionNumber" type="text" class="form-control duplication" data-title="分机号码" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                                    <label id="userExtensionNumber_Error" class="validator-error"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">家庭地址</label>
                                <div class="col-sm-9">
                                    <input id="userAddress" type="text" class="form-control" data-title="家庭地址" maxlength="128" data-rangelength="[0,128]" style="width: 350px;"/>
                                    <label id="userAddress_Error" class="validator-error"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">传真号码</label>
                                <div class="col-sm-9">
                                    <input id="userFaxphone" type="text" class="form-control" data-title="传真号码" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                                    <label id="userFaxphone_Error" class="validator-error"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">邮政编码</label>
                                <div class="col-sm-9">
                                    <input id="userZipcode" type="text" class="form-control" data-title="邮政编码" maxlength="6" data-rangelength="[0,6]" style="width: 350px;"/>
                                    <label id="userZipcode_Error" class="validator-error"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">退职日期</label>
                                <div class="col-sm-9">
                                    <div class="input-group datetime-picker">
                                        <input id="retireDate" type="text" class="form-control" data-title="退职日期" maxlength="10" style="width:311px;"/>
                                        <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                    </div>
                                    <label id="retireDate_Error" class="validator-error"/>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">KA划入时间</label>
                                <div class="col-sm-9">
                                    <ctag:CalendarSelect id="shiftDate" title="KA划入时间" showValidateError="false" width="311px"></ctag:CalendarSelect>
                                </div>
                            </div>
                            <div class="form-group form-inline">
                                <label class="col-sm-3 control-label">备注</label>
                                <div class="col-sm-9">
                                    <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 350px;"></textarea>
                                    <label id="remark_Error" class="validator-error"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <br/>
                    <ctag:Fold id="extSystemAccentSetting" name="外部账号" foldIconClass="fa-caret-up"/>
                    <div id="extSystemAccentSetting" style="display: none;" class="separate-block clearfix extraInfo-block">
                        <div class="form-group form-inline">
                            <label class="col-lg-1 control-label">外部系统名称(一)</label>
                            <div class="col-lg-3">
                                <input id="userInfo.extSystemName1" type="text" class="form-control" data-title="外部系统名称(一)" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                                <label id="userInfo.extSystemName1_Error" class="validator-error"></label>
                            </div>

                            <label class="col-lg-1 control-label">外部系统账号(一)</label>
                            <div class="col-lg-3">
                                <input id="userInfo.extSystemAccount1" type="text" class="form-control" data-title="外部系统账号(一)" maxlength="64" data-rangelength="[0,64]" style="width: 200px;"/>
                                <label id="userInfo.extSystemAccount1_Error" class="validator-error"></label>
                            </div>
                            <label class="col-lg-1 control-label">外部系统密码(一)</label>
                            <div class="col-lg-3">
                                <input id="userInfo.extSystemPassword1" type="password" class="form-control" data-title="外部系统密码(一)" maxlength="16" data-rangelength="[0,16]" style="width: 200px;"/>
                                <label id="userInfo.extSystemPassword1_Error" class="validator-error"></label>
                            </div>
                        </div>

                        <div class="form-group form-inline">
                            <label class="col-lg-1 control-label">外部系统名称(二)</label>
                            <div class="col-lg-3">
                                <input id="userInfo.extSystemName2" type="text" class="form-control" data-title="外部系统名称(二)" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                                <label id="userInfo.extSystemName2_Error" class="validator-error"></label>
                            </div>
                            <label class="col-lg-1 control-label">外部系统账号(二)</label>
                            <div class="col-lg-3">
                                <input id="userInfo.extSystemAccount2" type="text" class="form-control" data-title="外部系统账号(二)" maxlength="64" data-rangelength="[0,64]" style="width: 200px;"/>
                                <label id="userInfo.extSystemAccount2_Error" class="validator-error"></label>
                            </div>
                            <label class="col-lg-1 control-label">外部系统密码(二)</label>
                            <div class="col-lg-3">
                                <input id="userInfo.extSystemPassword2" type="password" class="form-control" data-title="外部系统密码(二)" maxlength="16" data-rangelength="[0,16]" style="width: 200px;"/>
                                <label id="userInfo.extSystemPassword2_Error" class="validator-error"></label>
                            </div>
                        </div>

                        <div class="form-group form-inline">
                            <label class="col-lg-1 control-label">外部系统名称(三)</label>
                            <div class="col-lg-3">
                                <input id="userInfo.extSystemName3" type="text" class="form-control" data-title="外部系统名称(三)" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                                <label id="userInfo.extSystemName3_Error" class="validator-error"></label>
                            </div>
                            <label class="col-lg-1 control-label">外部系统账号(三)</label>
                            <div class="col-lg-3">
                                <input id="userInfo.extSystemAccount3" type="text" class="form-control" data-title="外部系统账号(三)" maxlength="64" data-rangelength="[0,64]" style="width: 200px;"/>
                                <label id="userInfo.extSystemAccount3_Error" class="validator-error"></label>
                            </div>
                            <label class="col-lg-1 control-label">外部系统密码(三)</label>
                            <div class="col-lg-3">
                                <input id="userInfo.extSystemPassword3" type="password" class="form-control" data-title="外部系统密码(三)" maxlength="16" data-rangelength="[0,16]" style="width: 200px;"/>
                                <label id="userInfo.extSystemPassword3_Error" class="validator-error"></label>
                            </div>
                        </div>

                        <div class="form-group form-inline">
                            <label class="col-lg-1 control-label">外部系统名称(四)</label>
                            <div class="col-lg-3">
                                <input id="userInfo.extSystemName4" type="text" class="form-control" data-title="外部系统名称(四)" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                                <label id="userInfo.extSystemName4_Error" class="validator-error"></label>
                            </div>
                            <label class="col-lg-1 control-label">外部系统账号(四)</label>
                            <div class="col-lg-3">
                                <input id="userInfo.extSystemAccount4" type="text" class="form-control" data-title="外部系统账号(四)" maxlength="64" data-rangelength="[0,64]" style="width: 200px;"/>
                                <label id="userInfo.extSystemAccount4_Error" class="validator-error"></label>
                            </div>
                            <label class="col-lg-1 control-label">外部系统密码(四)</label>
                            <div class="col-lg-3">
                                <input id="userInfo.extSystemPassword4" type="password" class="form-control" data-title="外部系统密码(四)" maxlength="16" data-rangelength="[0,16]" style="width: 200px;"/>
                                <label id="userInfo.extSystemPassword4_Error" class="validator-error"></label>
                            </div>
                        </div>

                        <div class="form-group form-inline">
                            <label class="col-lg-1 control-label">外部系统名称(五)</label>
                            <div class="col-lg-3">
                                <input id="userInfo.extSystemName5" type="text" class="form-control" data-title="外部系统名称(五)" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                                <label id="userInfo.extSystemName5_Error" class="validator-error"></label>
                            </div>
                            <label class="col-lg-1 control-label">外部系统账号(五)</label>
                            <div class="col-lg-3">
                                <input id="userInfo.extSystemAccount5" type="text" class="form-control" data-title="外部系统账号(五)" maxlength="64" data-rangelength="[0,64]" style="width: 200px;"/>
                                <label id="userInfo.extSystemAccount5_Error" class="validator-error"></label>
                            </div>
                            <label class="col-lg-1 control-label">外部系统密码(五)</label>
                            <div class="col-lg-3">
                                <input id="userInfo.extSystemPassword5" type="password" class="form-control" data-title="外部系统密码(五)" maxlength="16" data-rangelength="[0,16]" style="width: 200px;"/>
                                <label id="userInfo.extSystemPassword5_Error" class="validator-error"></label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="clear-both"></div>
            <div class="UserInput-MainContent margin-top-space">
                <div class="text-center">
                    <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存
                    </button>
                    <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除
                    </button>
                    <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览
                    </button>
                </div>

                <%--原始对象Json字符串[实体属性校验用]--%>
                <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_User}"/>

                <%--下拉框选项数据集合--%>
                <input type="hidden" id="jsonOptionList" value="${jsonOptionList_User}"/>
            </div>
            <%--合同客户列表--%>
            <%@ include file="/WEB-INF/views/cmn/customeruser/CustomerUserByUserInnerList.jsp" %>

        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function UserInput_Page_Load() {
        SysApp.Sys.UserInputIns = new SysApp.Sys.UserInput();
        var instance = SysApp.Sys.UserInputIns;
        instance.selfInstance = "SysApp.Sys.UserInputIns";
        instance.clientID = "UserInput";
        instance.controller = "${ctx}/sys/user/";
        instance.listUrl = "${ctx}/sys/user/list";
        instance.deptTreeListUrl = "${ctx}/sys/dept/tree?deptUid=${deptUid}";
        instance.deptUid = "${deptUid}";
        instance.uid = "${uid}";
        instance.entry = "${entry}";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    UserInput_Page_Load();
    //]]>
</script>