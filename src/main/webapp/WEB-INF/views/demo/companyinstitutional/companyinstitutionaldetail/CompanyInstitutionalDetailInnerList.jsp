<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/companyinstitutional/companyinstitutionaldetail/CompanyInstitutionalDetailInnerList.js?${version}"></script>

<title>分支机构一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="CompanyInstitutionalDetailInnerList-MainContent">
    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm">
        <input type="hidden" id="institutionalUid" data-alias-table="main" value="${companyInstitutionalUid}" data-search-mode="="/>
    </form>
    <input type="hidden" id="jsonListData" value="${jsonDataList_CompanyInstitutionalDetail}"/>
    <%--上次检索条件--%>
    <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_CompanyInstitutionalDetail}"/>
    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CompanyInstitutionalDetail}"/>
    <div class="margin-top-space" id="divList">
    </div>

</div>

<script type="text/javascript">
    //<![CDATA[
    function CompanyInstitutionalDetailInnerList_Page_Load() {
        SysApp.Demo.CompanyInstitutionalDetailInnerListIns = new SysApp.Demo.CompanyInstitutionalDetailInnerList();
        var instance = SysApp.Demo.CompanyInstitutionalDetailInnerListIns;

        instance.selfInstance = "SysApp.Demo.CompanyInstitutionalDetailInnerListIns";
        instance.controller = "${ctx}/demo/companyinstitutionaldetail/";
        instance.inputUrl = "${ctx}/demo/companyinstitutionaldetail/input";
        instance.clientID = "CompanyInstitutionalDetailInnerList";
        instance.tableName = "demo_company_institutional_detail";

        instance.init();
    }

    $(function () {
        CompanyInstitutionalDetailInnerList_Page_Load();
    });
    //]]>
</script>