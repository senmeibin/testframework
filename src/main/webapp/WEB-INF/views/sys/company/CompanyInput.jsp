<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/company/CompanyInput.js?${version}"></script>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height">
        <div class="panel-heading CompanyInput-MainContent">
            <em></em>
            <div class="panel-title" id="ctlTitle">来往单位编辑</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body CompanyInput-MainContent">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <div class="form-group" style="display: none">
                    <div>
                        <input id="uid" type="hidden">
                    </div>
                </div>

                <ctag:Fold id="divBaseInfo" name="基础信息"></ctag:Fold>

                <div id="divBaseInfo" class="separate-block clearfix">
                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">单位名称</label>
                        <div class="col-lg-4">
                            <input id="name" type="text" class="form-control required" data-title="单位名称" maxlength="256" data-rangelength="[0,256]" style="width: 220px;"/>
                            <label id="name_Error" class="validator-error"/>
                        </div>
                        <label class="col-lg-1 control-label">简称</label>
                        <div class="col-lg-5">
                            <input id="abbreviation" type="text" class="form-control" data-title="简称" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                            <label id="abbreviation_Error" class="validator-error"/>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">性质</label>
                        <div class="col-lg-4">
                            <select id="propertyCd" class="form-control" data-title="性质" style="width: 220px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="propertyCd_Error" class="validator-error"/>
                        </div>
                        <label class="col-lg-1 control-label">规模</label>
                        <div class="col-lg-5">
                            <select id="scaleCd" class="form-control" data-title="规模" style="width: 220px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="scaleCd_Error" class="validator-error"/>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">所属业务员</label>
                        <div class="col-lg-4">
                            <input id="belongUserUid" type="text" class="form-control" data-title="所属业务员" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                            <label id="belongUserUid_Error" class="validator-error"/>
                        </div>
                        <label class="col-lg-1 control-label">所属部门</label>
                        <div class="col-lg-5">
                            <input id="belongDeptUid" type="text" class="form-control" data-title="所属部门" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                            <label id="belongDeptUid_Error" class="validator-error"/>
                        </div>
                    </div>
                </div>

                <br/>
                <ctag:Fold id="divContactInfo" name="联系信息"></ctag:Fold>

                <div id="divContactInfo" class="separate-block clearfix">
                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">联系人</label>
                        <div class="col-lg-4">
                            <input id="contactName" type="text" class="form-control required" data-title="联系人" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                            <label id="contactName_Error" class="validator-error"/>
                        </div>
                        <label class="col-lg-1 control-label">电话</label>
                        <div class="col-lg-5">
                            <input id="telephone" type="text" class="form-control required" data-title="电话" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                            <label id="telephone_Error" class="validator-error"/>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">地址</label>
                        <div class="col-lg-4">
                            <input id="address" type="text" class="form-control required" data-title="地址" maxlength="256" data-rangelength="[0,256]" style="width: 220px;"/>
                            <label id="address_Error" class="validator-error"/>
                        </div>
                        <label class="col-lg-1 control-label">邮编</label>
                        <div class="col-lg-5">
                            <input id="zipcode" type="text" class="form-control" data-title="邮编" maxlength="6" data-rangelength="[0,6]" style="width: 220px;"/>
                            <label id="zipcode_Error" class="validator-error"/>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">传真</label>
                        <div class="col-lg-4">
                            <input id="fax" type="text" class="form-control" data-title="传真" maxlength="32" data-rangelength="[0,32]" style="width: 220px;"/>
                            <label id="fax_Error" class="validator-error"/>
                        </div>
                        <label class="col-lg-1 control-label">邮箱</label>
                        <div class="col-lg-5">
                            <input id="email" type="text" class="form-control" data-title="邮箱" maxlength="64" data-rangelength="[0,64]" style="width: 220px;"/>
                            <label id="email_Error" class="validator-error"/>
                        </div>
                    </div>
                </div>

                <br/>
                <ctag:Fold id="divOtherInfo" name="其他信息"></ctag:Fold>

                <div id="divOtherInfo" class="separate-block clearfix">
                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">网址</label>
                        <div class="col-lg-10">
                            <input id="url" type="text" class="form-control" data-title="网址" maxlength="256" data-rangelength="[0,256]" style="width: 600px;"/>
                            <label id="url_Error" class="validator-error"/>
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <label class="col-lg-2 control-label">备注</label>
                        <div class="col-lg-10">
                            <textarea id="remark" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 600px; height: 50px;"></textarea>
                            <label id="remark_Error" class="validator-error"/>
                        </div>
                    </div>
                </div>
            </form>

            <div class="text-center" style="margin-top: 30px;">
                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
            </div>

            <%--原始对象Json字符串[实体属性校验用]--%>
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Company}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Company}"/>
        </div>

        <%--包含联系人列表控件--%>
        <%@ include file="/WEB-INF/views/sys/contact/ContactBatchInnerInput.jsp" %>

        <%--包含联系人列表控件--%>
        <%@ include file="/WEB-INF/views/sys/contact/ContactPopupList.jsp" %>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CompanyInput_Page_Load() {
        SysApp.Sys.CompanyInputIns = new SysApp.Sys.CompanyInput();
        var instance = SysApp.Sys.CompanyInputIns;
        instance.selfInstance = "SysApp.Sys.CompanyInputIns";
        instance.clientID = "CompanyInput";
        instance.controller = "${ctx}/sys/company/";
        instance.listUrl = "${ctx}/sys/company/list";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    CompanyInput_Page_Load();
    //]]>
</script>
