<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/subway/SubwayList.js?${version}"></script>

<title>地铁线路信息一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper SubwayList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">地铁线路信息一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增地铁线路信息
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>省份</label>
                    <input id="region_province.region_name" type="text" maxlength="32" class="form-control" data-title="省份"/>
                </div>

                <div class="col-md-2 form-group">
                    <label>城市</label>
                    <input id="region_city.region_name" type="text" maxlength="32" class="form-control" data-title="城市"/>
                </div>

                <div class="col-md-2 form-group">
                    <label>线路号</label>
                    <input id="subwayNo" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="线路号"/>
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

                    <shiro:hasAnyRoles name="SystemManagement,ItSupport">
                        &nbsp;&nbsp;&nbsp;
                        <button id="btnSubwayStationSync" type="button" class="btn btn-primary">
                            <i class="fa fa-download"></i>更新地铁线路信息
                        </button>
                    </shiro:hasAnyRoles>

                    <ctag:PagerSettingIcon pageInstance="SysApp.Cmn.SubwayListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Subway}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Subway}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Subway}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function SubwayList_Page_Load() {
        SysApp.Cmn.SubwayListIns = new SysApp.Cmn.SubwayList();
        var instance = SysApp.Cmn.SubwayListIns;

        instance.selfInstance = "SysApp.Cmn.SubwayListIns";
        instance.controller = "${ctx}/cmn/subway/";
        instance.clientID = "SubwayList";
        instance.tableName = "cmn_subway";
        instance.pageSize = "${pageSize}";
        instance.inputInstance = SysApp.Cmn.SubwayInputIns;
        instance.detailInstance = SysApp.Cmn.SubwayDetailIns;

        instance.init();
    }

    $(function () {
        SubwayList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑--%>
<%@ include file="/WEB-INF/views/cmn/subway/SubwayPopupInput.jsp" %>
<%--POPUP地铁站信息维护--%>
<%@ include file="/WEB-INF/views/cmn/subwaystation/SubwayStationPopupList.jsp" %>
