<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/region/RegionList.js?${version}"></script>

<title>地区信息一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper RegionList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">地区信息一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增地区信息
            </button>
            <!--有配置DATA_IMPORT_CMN_API才显示同步按钮 -->
            <c:if test="${dataImportApiExist}">
                <button id="btnApiImport" type="button" class="btn btn-danger">
                    <i class="fa fa-spinner"></i>同步数据
                </button>
            </c:if>
            <button id="btnRegionSetting" type="button" class="btn btn-primary">
                <i class="fa fa-wrench"></i>区域批量设置
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-12 form-group form-inline">
                    <label>区域名称</label>
                    <select id="uid" data-alias-table="main" class="form-control required" style="width:100px;" data-title="区域名称" data-search-mode="ignore_search">
                        <option value="">请选择</option>
                    </select>（注：必须先选择 <b>顶级区域</b> 后，才能检索数据并进行相关编辑操作）
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
                    <button id="btnLock" type="button" class="btn btn-danger">
                        <i class="fa fa-lock"></i>停用区域
                    </button>
                    <button id="btnUnLock" type="button" class="btn btn-danger">
                        <i class="fa fa-unlock"></i>启用区域
                    </button>
                </div>
            </form>

            <input type="hidden" id="jsonListData" value="${jsonDataList_Region}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Region}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Region}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function RegionList_Page_Load() {
        SysApp.Cmn.RegionListIns = new SysApp.Cmn.RegionList();
        var instance = SysApp.Cmn.RegionListIns;

        instance.selfInstance = "SysApp.Cmn.RegionListIns";
        instance.controller = "${ctx}/cmn/region/";
        instance.clientID = "RegionList";
        instance.tableName = "cmn_region";
        instance.inputInstance = SysApp.Cmn.RegionInputIns;
        instance.detailInstance = SysApp.Cmn.RegionDetailIns;
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    $(function () {
        RegionList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑控件--%>
<%@ include file="/WEB-INF/views/cmn/region/RegionPopupInput.jsp" %>
<%--POPUP详细控件--%>
<%@ include file="/WEB-INF/views/cmn/region/RegionPopupDetail.jsp" %>

<%--区域批量设置控件--%>
<%@ include file="/WEB-INF/views/cmn/region/RegionPopupSetting.jsp" %>