<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/demo/chargesinformation/companyinformation/CompanyInformationPopupList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content CompanyInformationPopupList-MainContent" style="width: 1200px; display: none;">
    <ctag:ModalHeader modalTitle="企业基本信息一览"></ctag:ModalHeader>

    <div class="modal-body">
        <button id="btnAdd" type="button" class="btn btn-primary">
            <i class="fa fa-plus"></i>新增企业基本信息
        </button>
        <div class="clear-both dashed-line">
        </div>
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm">
            <!--忽略已经添加的数据-->
            <input id="filterChargesInformationCompany" type="hidden" data-search-mode="ignore_search" value="TRUE"/>
            <div class="col-md-2 form-group">
                <label>企业名称</label>
                <input id="companyName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="企业名称"/>
            </div>
            <div class="col-md-2 form-group">
                <label>组织机构代码</label>
                <input id="organizationNo" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="组织机构代码"/>
            </div>
            <div class="col-md-2 form-group">
                <label>入驻时间(起)</label>
                <ctag:CalendarSelect id="settlingDate$from_search" title="入驻时间(起)"></ctag:CalendarSelect>
            </div>
            <div class="col-md-2 form-group">
                <label>入驻时间(止)</label>
                <ctag:CalendarSelect id="settlingDate$to_search" title="入驻时间(止)"></ctag:CalendarSelect>
            </div>
            <div class="col-md-2 form-group" style="width: 300px;">
                <label>
                    &nbsp;
                </label>
                <div>
                    <button id="btnSearch" type="button" class="btn btn-primary">
                        <i class="fa fa-search"></i>查询
                    </button>
                    <button id="btnClear" type="button" class="btn btn-default">
                        <i class="fa fa-eraser"></i>清空
                    </button>
                </div>
            </div>
        </form>
        <%--上次检索条件--%>
        <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_CompanyInformation}"/>
        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyInformation}"/>
        <div class="margin-top-space" id="divList" style="width: 100%">
        </div>
    </div>

    <ctag:ModalFooter></ctag:ModalFooter>
</div>

<script type="text/javascript">
    //<![CDATA[
    function CompanyInformationPopupList_Page_Load() {
        SysApp.Demo.CompanyInformationPopupListIns = new SysApp.Demo.CompanyInformationPopupList();
        var instance = SysApp.Demo.CompanyInformationPopupListIns;

        instance.selfInstance = "SysApp.Demo.CompanyInformationPopupListIns";
        instance.clientID = "CompanyInformationPopupList";
        instance.tableName = "demo_company_information";
        instance.controller = "${ctx}/demo/companyinformation/";
        instance.chargesInputUrl = "${ctx}/demo/chargesinformation/input";

        instance.init();
    }

    $(function () {
        CompanyInformationPopupList_Page_Load();
    });
    //]]>
</script>