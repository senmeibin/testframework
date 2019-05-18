<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script src="${staticContentsServer}/static/js/sys/position/PositionList.js"></script>

<title>系统职位一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper PositionList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">系统职位一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增职位
            </button>
            <!--有配置DATA_IMPORT_SYS_API才显示同步按钮 -->
            <c:if test="${dataImportApiExist}">
                <button id="btnSyncData" type="button" class="btn btn-danger">
                    <i class="fa fa-spinner"></i>同步数据
                </button>
            </c:if>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>职位名称</label>
                    <input id="positionName" data-alias-table="main" type="text" class="form-control" data-title="职位名称"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.PositionListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Position}"/>
            <!--上次检索条件-->
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Position}"/>
            <div class="margin-top-space" id="divList" style="width: 100%">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function PositionList_Page_Load() {
        SysApp.Sys.PositionListIns = new SysApp.Sys.PositionList();
        var instance = SysApp.Sys.PositionListIns;

        instance.selfInstance = "SysApp.Sys.PositionListIns";
        instance.controller = "${ctx}/sys/position/";
        instance.clientID = "PositionList";
        instance.tableName = "sys_position";
        instance.inputInstance = SysApp.Sys.PositionInputIns;
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        PositionList_Page_Load();
    });
    //]]>
</script>

<%--POPUP编辑控件--%>
<%@ include file="/WEB-INF/views/sys/position/PositionPopupInput.jsp" %>