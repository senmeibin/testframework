<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/counter/CounterList.js?${version}"></script>

<title>计数器管理一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper CounterList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">计数器管理一览</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>分组条件1</label>
                    <input id="groupCounter1" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="分组条件1"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>分组条件2</label>
                    <input id="groupCounter2" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="分组条件2"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>分组条件3</label>
                    <input id="groupCounter3" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="分组条件3"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>分组条件4</label>
                    <input id="groupCounter4" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="分组条件4"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>分组条件5</label>
                    <input id="groupCounter5" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="分组条件5"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>备注</label>
                    <input id="remark" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="备注"/>
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
                    <ctag:PagerSettingIcon pageInstance="SysApp.Sys.CounterListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Counter}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Counter}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Counter}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function CounterList_Page_Load() {
        SysApp.Sys.CounterListIns = new SysApp.Sys.CounterList();
        var instance = SysApp.Sys.CounterListIns;

        instance.selfInstance = "SysApp.Sys.CounterListIns";
        instance.controller = "${ctx}/sys/counter/";
        instance.clientID = "CounterList";
        instance.tableName = "sys_counter";
        instance.pageSize = "${pageSize}";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    $(function () {
        CounterList_Page_Load();
    });
    //]]>
</script>

