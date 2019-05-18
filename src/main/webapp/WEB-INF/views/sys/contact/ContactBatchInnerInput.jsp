<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="panel-body ContactBatchInput-MainContent">

    <ctag:Fold id="tblContactBatchInput" name="联系人信息"></ctag:Fold>

    <table class="table table-hover separate-block" id="tblContactBatchInput">
        <thead>
        <tr>
            <th style="width: 2%;">
                序号
            </th>
            <th style="min-width: 8%;">
                姓名
            </th>
            <th style="min-width: 8%;">
                部门
            </th>
            <th style="min-width: 10%;">
                职务
            </th>
            <th style="min-width: 10%;">
                主要职责
            </th>
            <th style="min-width: 10%;">
                手机
            </th>
            <th style="min-width: 10%;">
                生日
            </th>
            <th style="min-width: 10%;">
                电话
            </th>
            <th style="min-width: 16%;">
                备注
            </th>
            <th style="min-width: 13%;">
                操作
            </th>
        </tr>
        </thead>
        <tbody class="detail-list">
        </tbody>
    </table>

    <select id="dept_list" style="display: none;">
        <option value="">请选择</option>
        <option value="财务部">财务部</option>
        <option value="人事部">人事部</option>
        <option value="开发部">开发部</option>
        <option value="销售部">销售部</option>
        <option value="行政部">行政部</option>
        <option value="设计部">设计部</option>
    </select>

    <select id="post_list" style="display: none;">
        <option value="">请选择</option>
        <option value="总经理">总经理</option>
        <option value="部门经理">部门经理</option>
        <option value="项目经理">项目经理</option>
    </select>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Contact}"/>

    <input type="hidden" id="jsonContactList" value="${contactList}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Contact}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/contact/ContactBatchInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ContactBatchInput_Page_Load() {
        SysApp.Sys.ContactBatchInputIns = new SysApp.Sys.ContactBatchInput();
        var instance = SysApp.Sys.ContactBatchInputIns;
        instance.selfInstance = "SysApp.Sys.ContactBatchInputIns";
        instance.clientID = "ContactBatchInput";
        instance.controller = "${ctx}/sys/contact/";
        instance.companyUid = "${companyUid}";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    ContactBatchInput_Page_Load();
    //]]>
</script>