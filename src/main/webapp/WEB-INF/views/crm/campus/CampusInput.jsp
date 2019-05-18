<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/crm/campus/CampusInput.js?${version}"></script>

<title>校区编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper CampusInput-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">校区编辑</div>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">
                <ctag:Fold id="divBaseInfo" name="基本信息" foldIconClass="fa-caret-up" tipsContent="校区基本信息设置"></ctag:Fold>
                <div id="divBaseInfo" class="separate-block clearfix">
                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">所属部门</label>
                        <div class="col-sm-9">
                            <select id="deptUid" type="text" class="form-control required" data-title="所属部门" style="width: 500px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="deptUid_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">校区名称</label>
                        <div class="col-sm-9">
                            <input id="name" type="text" class="form-control required" data-title="校区名称" maxlength="64" data-rangelength="[0,64]" style="width: 500px;"/>
                            <label id="name_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">校区代号</label>
                        <div class="col-sm-9">
                            <input id="code" type="text" class="form-control required" data-title="校区代号" placeholder="如：JA01" maxlength="4" data-minlength="4" style="text-transform: uppercase;width: 150px;"/>
                            (<i class="fa fa-hand-o-left"></i>四位字母及数字组合，如JA01)
                            <label id="code_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">校区分类</label>
                        <div class="col-sm-9">
                            <select id="categoryCd" class="form-control required" data-title="校区分类" style="width: 150px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="categoryCd_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">校区介绍</label>
                        <div class="col-sm-9">
                            <textarea id="introduction" rows="10" cols="30" class="required" data-title="校区介绍" style="width: 800px;"></textarea>
                            <label id="introduction_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">校区所在省市区</label>
                        <div class="col-sm-1" style="width: 170px;">
                            <ctag:ComboTree valueDomId="provinceUid" textDomId="provinceName" parentInstance="SysApp.Crm.CampusInputIns" dataTitle="校区所在省" style="width: 120px;"
                                            nodeName="regionName" idKey="uid" parentIdKey="parentUid" dataUrl="${ctx}/cmn/region/getAllRegion/1" selectParent="false"/>
                        </div>

                        <div class="col-sm-1" style="width: 170px;">
                            <ctag:ComboTree valueDomId="cityUid" textDomId="cityName" parentInstance="SysApp.Crm.CampusInputIns" dataTitle="校区所在市" style="width: 120px;" parentDomId="provinceUid"
                                            parentDataTitle="校区省市区" nodeName="regionName" idKey="uid" parentIdKey="parentUid" dataUrl="${ctx}/cmn/region/getAllRegion/2" selectParent="false"/>
                        </div>

                        <div class="col-sm-1" style="width: 170px;">
                            <ctag:ComboTree valueDomId="regionUid" textDomId="regionName" parentInstance="SysApp.Crm.CampusInputIns" dataTitle="校区所在区" style="width: 120px;" parentDomId="cityUid"
                                            parentDataTitle="校区省市区" nodeName="regionName" idKey="uid" parentIdKey="parentUid" dataUrl="${ctx}/cmn/region/getAllRegion/3" selectParent="false"/>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">校区地址/邮编</label>
                        <div class="col-sm-9">
                            <input id="address" type="text" class="form-control required" data-title="校区地址" maxlength="256" data-rangelength="[0,256]" style="width: 312px;"/> /
                            <input id="zipCode" type="text" class="form-control" data-title="邮编" maxlength="6" data-rangelength="[0,6]" style="width: 100px;"/>
                            <label id="address_Error" class="validator-error"></label>
                            <label id="zipCode_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">联系电话/传真</label>
                        <div class="col-sm-9">
                            <input id="telephone" type="text" class="form-control" data-title="联系电话" maxlength="32" data-rangelength="[0,32]" style="width: 150px;"/> /
                            <input id="fax" type="text" class="form-control" data-title="传真" maxlength="32" data-rangelength="[0,32]" style="width: 150px;"/>
                            <label id="telephone_Error" class="validator-error"></label>
                            <label id="fax_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">邮箱</label>
                        <div class="col-sm-9">
                            <input id="email" type="text" class="form-control" data-title="邮箱" maxlength="64" data-rangelength="[0,64]" style="width: 500px;"/>
                            <label id="email_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">表示顺序</label>
                        <div class="col-sm-9">
                            <input id="dispSeq" type="text" class="form-control" data-title="表示顺序" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 150px;"/>
                            <label id="dispSeq_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">备注</label>
                        <div class="col-sm-9">
                            <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 500px;"></textarea>
                            <label id="remark_Error" class="validator-error"></label>
                        </div>
                    </div>
                </div>

                <ctag:Fold id="divBaseInfo" name="人员信息" foldIconClass="fa-caret-up" tipsContent="校区负责人及校区课程顾问等相关人员信息设置" marginTop="15px"></ctag:Fold>
                <div id="divBaseInfo" class="separate-block clearfix">
                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">门店店长</label>
                        <div class="col-sm-9">
                            <div class="select2-required" style="width: 500px;">
                                <ctag:Select2 selectId="managerUserUid" required="required" dataTitle="门店店长" style="width:100%;"/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">门店副店长</label>
                        <div class="col-sm-9">
                            <div style="width: 500px;">
                                <ctag:Select2 selectId="assistantManagerUserUid" required="required" dataTitle="门店副店长" style="width:100%;"/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">门店助理</label>
                        <div class="col-sm-9">
                            <div style="width: 500px;">
                                <ctag:Select2 selectId="assistantUserUid" required="required" dataTitle="门店助理" style="width:100%;"/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">课程顾问</label>
                        <div class="col-sm-9" style="height: 330px;">
                            <ctag:ComboCheckTree valueDomId="consultantUserUids" required="required" textDomId="consultantUserNames" parentInstance="SysApp.Crm.CampusInputIns" dataTitle="课程顾问" style="width:461px;"
                                                 idKey="uid" parentIdKey="parentDeptUid" nodeName="deptName" dispName="deptName" disabledLevel="0"
                                                 dataUrl="${ctx}/cmn/user/getUserTreeByRole?roleCode=" alwaysShowTree="true"/>
                        </div>
                    </div>
                </div>
            </form>

            <div class="text-center margin-top-space">
                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
            </div>

            <%--原始对象Json字符串[实体属性校验用]--%>
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Campus}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Campus}"/>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CampusInput_Page_Load() {
        SysApp.Crm.CampusInputIns = new SysApp.Crm.CampusInput();
        var instance = SysApp.Crm.CampusInputIns;
        instance.selfInstance = "SysApp.Crm.CampusInputIns";
        instance.controller = "${ctx}/crm/campus/";
        instance.listUrl = "${ctx}/crm/campus/list";
        instance.clientID = "CampusInput";
        instance.entry = "${entry}";

        instance.init();
    }

    CampusInput_Page_Load();
    //]]>
</script>