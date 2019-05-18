<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/potentialcustomer/PotentialCustomerList.js?${version}"></script>

<title>招商潜在投资企业一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper PotentialCustomerList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">招商潜在投资企业一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:SearchSettingIcon tips="检索条件自定义设定"></ctag:SearchSettingIcon>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增企业
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>所属基地</label>
                    <ctag:Select2 selectId="baseUid" dataTitle="所属基地" multiple="true"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>企业名称</label>
                    <input id="companyName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="企业名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>实际负责人</label>
                    <input id="companyPrincipal" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="实际负责人"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>联系人</label>
                    <input id="contactPerson" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="联系人"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>企业类型</label>
                    <select id="companyTypeCd" data-alias-table="main" class="form-control" data-title="企业类型">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>入驻概率</label>
                    <select id="entryProbabilityCd" data-alias-table="main" class="form-control" data-title="入驻概率">
                        <option value="">请选择</option>
                    </select>
                </div>

                <ctag:Fold id="divOtherSearch" name="其他查询" foldIconClass="fa-caret-up"></ctag:Fold>
                <div id="divOtherSearch" style="display:none;" class="separate-block clearfix">
                    <div class="col-md-2 form-group">
                        <label>登记注册类型</label>
                        <select id="registrationTypeCd" data-alias-table="main" class="form-control" data-title="登记注册类型">
                            <option value="">请选择</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>高新技术分类</label>
                        <input id="highTechType" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="高新技术分类"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>行业分类</label>
                        <input id="industryCategory" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="行业分类"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>专业技术方向</label>
                        <select id="professionalTechnicalCd" data-alias-table="main" class="form-control" data-title="专业技术方向">
                            <option value="">请选择</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>负责人</label>
                        <input id="principal" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="负责人"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>招商渠道</label>
                        <select id="investmentChannelsCd" data-alias-table="main" class="form-control" data-title="招商渠道">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>

                <div class="clear-both dashed-line">
                </div>

                <div>
                    <button id="btnSearch" type="button" class="btn btn-primary">
                        <i class="fa fa-search"></i>查询
                    </button>
                    <shiro:hasRole name="DataExport">
                        <button id="btnExport" type="button" class="btn btn-primary">
                            <i class="fa fa-cloud-download"></i>导出
                        </button>
                    </shiro:hasRole>
                    <button id="btnClear" type="button" class="btn btn-default">
                        <i class="fa fa-eraser"></i>清空
                    </button>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_PotentialCustomer}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_PotentialCustomer}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_PotentialCustomer}"/>
            <%--自定义检索条件设置--%>
            <input type="hidden" id="jsonSearchSetting" value="${jsonSearchSetting_PotentialCustomer}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_PotentialCustomer}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function PotentialCustomerList_Page_Load() {
        SysApp.Demo.PotentialCustomerListIns = new SysApp.Demo.PotentialCustomerList();
        var instance = SysApp.Demo.PotentialCustomerListIns;

        instance.selfInstance = "SysApp.Demo.PotentialCustomerListIns";
        instance.controller = "${ctx}/demo/potentialcustomer/";
        instance.inputUrl = "${ctx}/demo/potentialcustomer/input";
        instance.clientID = "PotentialCustomerList";
        instance.tableName = "demo_potential_customer";
        instance.detailInstance = SysApp.Demo.PotentialCustomerDetailIns;
        instance.companyInformationInputUrl = "${ctx}/demo/companyinformation/input";
        instance.init();
    }

    $(function () {
        PotentialCustomerList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/demo/potentialcustomer/PotentialCustomerPopupDetail.jsp" %>
<ctag:SearchSettingPopup pageInstance="SysApp.Demo.PotentialCustomerListIns" width="1000px"/>
<ctag:ColumnSettingPopup pageInstance="SysApp.Demo.PotentialCustomerListIns" width="1000px"/>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.PotentialCustomerListIns"/>