<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/contact/ContactInput.js?${version}"></script>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height">
        <div class="panel-heading ContactInput-MainContent">
            <em></em>
            <div class="panel-title" id="ctlTitle">联系人编辑</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--合同详细内嵌控件--%>
            <%@ include file="/WEB-INF/views/sys/contact/ContactInnerDetail.jsp" %>

            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <div class="ContactInput-MainContent margin-top-space">
                <form id="MainForm" class="form-horizontal">
                    <input id="uid" type="hidden">
                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">来往单位</label>
                        <div class="col-lg-4">
                            <select id="companyUid" class="form-control required" data-title="来往单位" style="width: 220px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="companyUid_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">姓名</label>
                        <div class="col-lg-4">
                            <input id="name" type="text" class="form-control required" data-title="姓名" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                            <label id="name_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">部门</label>
                        <div class="col-lg-4">
                            <input id="department" type="text" class="form-control" data-title="部门" maxlength="64" data-rangelength="[0,64]" style="width: 220px;"/>
                            <label id="department_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">职务</label>
                        <div class="col-lg-4">
                            <input id="post" type="text" class="form-control" data-title="职务" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                            <label id="post_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">主要职责</label>
                        <div class="col-lg-4">
                            <input id="responsibility" type="text" class="form-control" data-title="主要职责" maxlength="64" data-rangelength="[0,64]" style="width: 220px;"/>
                            <label id="responsibility_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">电话</label>
                        <div class="col-lg-4">
                            <input id="telephone" type="text" class="form-control" data-title="电话" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                            <label id="telephone_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">传真</label>
                        <div class="col-lg-4">
                            <input id="fax" type="text" class="form-control" data-title="传真" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                            <label id="fax_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">邮箱</label>
                        <div class="col-lg-4">
                            <input id="email" type="text" class="form-control" data-title="邮箱" maxlength="64" data-rangelength="[0,64]" style="width: 220px;"/>
                            <label id="email_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">手机</label>
                        <div class="col-lg-4">
                            <input id="mobile" type="text" class="form-control required" data-title="手机" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                            <label id="mobile_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">生日</label>
                        <div class="col-lg-4">
                            <div class="input-group datetime-picker">
                                <input id="birthday" type="text" class="form-control" data-title="生日" maxlength="10" style="width: 130px;"/>
                                <span class="input-group-addon">
                                     <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                            <label id="birthday_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">家庭地址</label>
                        <div class="col-lg-4">
                            <input id="homeAddress" type="text" class="form-control" data-title="家庭地址" maxlength="256" data-rangelength="[0,256]" style="width: 220px;"/>
                            <label id="homeAddress_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">家庭电话</label>
                        <div class="col-lg-4">
                            <input id="homeTelephone" type="text" class="form-control" data-title="家庭电话" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                            <label id="homeTelephone_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">家庭邮编</label>
                        <div class="col-lg-4">
                            <input id="homeZipcode" type="text" class="form-control" data-title="家庭邮编" maxlength="6" data-rangelength="[0,6]" style="width: 220px;"/>
                            <label id="homeZipcode_Error" class="validator-error"></label>
                        </div>
                        <label class="col-lg-1 control-label">爱好</label>
                        <div class="col-lg-4">
                            <input id="interest" type="text" class="form-control" data-title="爱好" maxlength="256" data-rangelength="[0,256]" style="width: 220px;"/>
                            <label id="interest_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">是否主要联系人</label>
                        <div class="col-lg-4">
                            <input id="isMainContacts" type="text" class="form-control" data-title="是否主要联系人" maxlength="5" data-rangelength="[0,5]" data-range="[0,99999]" data-digits="true"
                                   style="width: 220px;"/>
                            <label id="isMainContacts_Error" class="validator-error"></label>
                        </div>
                    </div>
                </form>

                <div class="text-center">
                    <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                    <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                    <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
                </div>

                <%--原始对象Json字符串[实体属性校验用]--%>
                <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Contact}"/>

                <%--下拉框选项数据集合--%>
                <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Contact}"/>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ContactInput_Page_Load() {
        SysApp.Sys.ContactInputIns = new SysApp.Sys.ContactInput();
        var instance = SysApp.Sys.ContactInputIns;
        instance.selfInstance = "SysApp.Sys.ContactInputIns";
        instance.controller = "${ctx}/sys/contact/";
        instance.listUrl = "${ctx}/sys/contact/list";
        instance.clientID = "ContactInput";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    ContactInput_Page_Load();
    //]]>
</script>