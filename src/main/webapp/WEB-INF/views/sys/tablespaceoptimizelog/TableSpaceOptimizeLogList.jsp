<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/tablespaceoptimizelog/TableSpaceOptimizeLogList.js?${version}"></script>

<title>表空间优化日志一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper TableSpaceOptimizeLogList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">表空间优化日志一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>物理表名称</label>
                    <input id="physicalName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="物理表名称" data-search-mode="="/>
                </div>
                <div class="col-md-2 form-group">
                    <label>逻辑表名称</label>
                    <input id="logicName" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="逻辑表名称"/>
                </div>
                <div class="clear-both dashed-line">
                </div>

                <div>
                    <button id="btnSearch" type="button" class="btn btn-primary">
                        <i class="fa fa-search"></i>查询
                    </button>
                    <button id="btnClear" type="button" class="btn btn-default">
                        <i class="fa fa-eraser"></i>清空
                    </button>
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.TableSpaceOptimizeLogListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_TableSpaceOptimizeLog}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_TableSpaceOptimizeLog}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_TableSpaceOptimizeLog}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function TableSpaceOptimizeLogList_Page_Load() {
        SysApp.Sys.TableSpaceOptimizeLogListIns = new SysApp.Sys.TableSpaceOptimizeLogList();
        var instance = SysApp.Sys.TableSpaceOptimizeLogListIns;

        instance.selfInstance = "SysApp.Sys.TableSpaceOptimizeLogListIns";
        instance.controller = "${ctx}/sys/tablespaceoptimizelog/";
        instance.clientID = "TableSpaceOptimizeLogList";
        instance.tableName = "sys_table_space_optimize_log";
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        TableSpaceOptimizeLogList_Page_Load();
    });
    //]]>
</script>
