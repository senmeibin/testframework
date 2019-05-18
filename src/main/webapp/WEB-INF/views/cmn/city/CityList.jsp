<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/city/CityList.js?${version}"></script>

<title>城市信息一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper CityList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">城市信息一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增城市信息
            </button>
            <!--有配置DATA_IMPORT_CMN_API才显示同步按钮 -->
            <c:if test="${dataImportApiExist}">
                <button id="btnApiImport" type="button" class="btn btn-danger">
                    <i class="fa fa-spinner"></i>同步数据
                </button>
            </c:if>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>城市编号</label>
                    <input id="cityCd" data-alias-table="main" type="text" maxlength="8" class="form-control"
                           data-title="城市编号"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>城市名称</label>
                    <input id="cityName" data-alias-table="main" type="text" maxlength="32" class="form-control"
                           data-title="城市名称"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>上级城市</label>
                    <select id="parentCityUid" data-alias-table="main" class="form-control" data-title="上级城市">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>备注</label>
                    <input id="remark" data-alias-table="main" type="text" maxlength="256" class="form-control"
                           data-title="备注"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Cmn.CityListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_City}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_City}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_City}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function CityList_Page_Load() {
        SysApp.Cmn.CityListIns = new SysApp.Cmn.CityList();
        var instance = SysApp.Cmn.CityListIns;

        instance.selfInstance = "SysApp.Cmn.CityListIns";
        instance.controller = "${ctx}/cmn/city/";
        instance.clientID = "CityList";
        instance.tableName = "cmn_city";
        instance.inputInstance = SysApp.Cmn.CityInputIns;
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    $(function () {
        CityList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑控件--%>
<%@ include file="/WEB-INF/views/cmn/city/CityPopupInput.jsp" %>
