<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/company/CompanyList.js?${version}"></script>

<title>来往单位一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper CompanyList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">来往单位一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:SearchSettingIcon tips="检索条件自定义设定"></ctag:SearchSettingIcon>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增来往单位
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>单位编号</label>
                    <input id="code" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="单位编号"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>单位名称</label>
                    <input id="name" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="单位名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>简称</label>
                    <input id="abbreviation" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="简称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>性质</label>
                    <select id="propertyCd" data-alias-table="main" class="form-control" data-title="性质">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>规模</label>
                    <select id="scaleCd" data-alias-table="main" class="form-control" data-title="规模">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>地址</label>
                    <input id="address" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="地址"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>单位名称/简称/地址</label>
                    <input id="main.name,main.abbreviation,main.address" data-search-mode="or" type="text" maxlength="256" class="form-control" data-title="单位名称/简称/地址"/>
                </div>

                <ctag:Fold id="divOtherSearchCondition" name="其他查询条件" foldIconClass="fa-caret-up"></ctag:Fold>
                <div id="divOtherSearchCondition" class="separate-block clearfix" style="display: none;">
                    <div class="col-md-2 form-group">
                        <label>邮编</label>
                        <input id="zipcode" data-alias-table="main" type="text" maxlength="6" class="form-control" data-title="邮编"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>电话</label>
                        <input id="telephone" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="电话"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>传真</label>
                        <input id="fax" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="传真"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>邮箱</label>
                        <input id="email" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="邮箱"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>联系人</label>
                        <input id="contactName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="联系人"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.CompanyListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Company}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Company}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Company}"/>
            <%--自定义列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_Company}"/>
            <%--自定义检索条件设置--%>
            <input type="hidden" id="jsonSearchSetting" value="${jsonSearchSetting_Company}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>

            <button id="btnDelete" type="button" class="btn btn-default margin-top-space">
                <i class="fa fa-trash-o"></i>批量删除
            </button>
            <div id="divPager"></div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function CompanyList_Page_Load() {
        SysApp.Sys.CompanyListIns = new SysApp.Sys.CompanyList();
        var instance = SysApp.Sys.CompanyListIns;
        instance.selfInstance = "SysApp.Sys.CompanyListIns";
        instance.controller = "${ctx}/sys/company/";
        instance.inputUrl = "${ctx}/sys/company/input";
        instance.clientID = "CompanyList";
        instance.tableName = "sys_company";
        instance.pageSize = "${pageSize}";
        instance.detailInstance = SysApp.Sys.CompanyDetailIns;
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    $(function () {
        CompanyList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/sys/company/CompanyPopupDetail.jsp" %>
<ctag:ColumnSettingPopup pageInstance="SysApp.Sys.CompanyListIns" width="1000px"/>
<ctag:SearchSettingPopup pageInstance="SysApp.Sys.CompanyListIns" width="1000px"/>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Sys.CompanyListIns"/>