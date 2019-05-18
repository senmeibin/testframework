<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/sys/enterprise/EnterpriseInput.js?${version}"></script>

<title>企业信息编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper EnterpriseInput-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">企业信息设置</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">
                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">企业名称</label>
                    <div class="col-sm-10">
                        <input id="enterpriseName" type="text" class="form-control required" data-title="企业名称" maxlength="64" data-rangelength="[0,64]" style="width: 350px;"/>
                        <label id="enterpriseName_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">企业简称</label>
                    <div class="col-sm-10">
                        <input id="abbreviation" type="text" class="form-control" data-title="企业简称" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                        <label id="abbreviation_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">企业标签</label>
                    <div class="col-sm-10">
                        <input id="enterpriseLabel" type="text" class="form-control" data-title="企业标签" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                        <label id="enterpriseLabel_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">所属行业</label>
                    <div class="col-sm-10">
                        <select id="industryCd" class="form-control required" data-title="所属行业" style="width: 350px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="industryCd_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">企业性质</label>
                    <div class="col-sm-10">
                        <select id="propertyCd" class="form-control required" data-title="企业性质" style="width: 350px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="propertyCd_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">企业规模</label>
                    <div class="col-sm-10">
                        <select id="scaleCd" class="form-control required" data-title="企业规模" style="width: 350px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="scaleCd_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">所在地区</label>
                    <div class="col-sm-10">
                        <input id="provinceUid" type="text" class="form-control" data-title="所在地区" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                        <label id="provinceUid_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">企业地址</label>
                    <div class="col-sm-10">
                        <input id="address" type="text" class="form-control" data-title="企业地址" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                        <label id="address_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">网址</label>
                    <div class="col-sm-10">
                        <input id="url" type="text" class="form-control" data-title="网址" maxlength="256" data-rangelength="[0,256]" style="width: 350px;"/>
                        <label id="url_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">联系人</label>
                    <div class="col-sm-10">
                        <input id="contactName" type="text" class="form-control required" data-title="联系人" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                        <label id="contactName_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">联系人电话</label>
                    <div class="col-sm-10">
                        <input id="contactTelephone" type="text" class="form-control required" data-title="联系人电话" maxlength="32" data-rangelength="[0,32]" style="width: 350px;"/>
                        <label id="contactTelephone_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">企业简介</label>
                    <div class="col-sm-10">
                        <textarea id="introduction" rows="5" cols="30" class="form-control required" data-title="企业简介" data-rangelength="[0,256]" style="width: 700px;"></textarea>
                        <label id="introduction_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <ctag:FileUpload clientID="FileUpload${uid}" relationUid="${uid}" fileTypeDesc="Image Files" fileTypeExts="*.jpg; *.png; *.jpeg; *.gif; *.bmp; *.tif; *.tiff" appCode="SYS" moduleName="企业简介" panelTitle="企业简介图片"></ctag:FileUpload>
                </div>
            </form>

            <div class="text-center">
                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
            </div>

            <%--原始对象Json字符串[实体属性校验用]--%>
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Enterprise}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Enterprise}"/>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function EnterpriseInput_Page_Load() {
        SysApp.Sys.EnterpriseInputIns = new SysApp.Sys.EnterpriseInput();
        var instance = SysApp.Sys.EnterpriseInputIns;
        instance.selfInstance = "SysApp.Sys.EnterpriseInputIns";
        instance.controller = "${ctx}/sys/enterprise/";
        instance.listUrl = "${ctx}/sys/enterprise/list";
        instance.clientID = "EnterpriseInput";

        instance.init();
    }

    EnterpriseInput_Page_Load();
    //]]>
</script>