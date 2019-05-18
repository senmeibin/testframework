<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/tutor/TutorList.js?${version}"></script>

<title>导师表一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper TutorList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">导师表一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增导师表
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>所属基地</label>
                    <input id="baseName" data-alias-table="base" type="text" maxlength="32" class="form-control" data-title="所属基地"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>姓名</label>
                    <input id="tutorName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="姓名"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>单位</label>
                    <input id="tutorUnit" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="单位"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>职务</label>
                    <input id="tutorPosition" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="职务"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>方向</label>
                    <input id="direction" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="方向"/>
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
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Tutor}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Tutor}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Tutor}"/>
            <div class="margin-top-space" id="divList">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function TutorList_Page_Load() {
        SysApp.Demo.TutorListIns = new SysApp.Demo.TutorList();
        var instance = SysApp.Demo.TutorListIns;

        instance.selfInstance = "SysApp.Demo.TutorListIns";
        instance.controller = "${ctx}/demo/tutor/";
        instance.clientID = "TutorList";
        instance.tableName = "demo_tutor";
        instance.inputInstance = SysApp.Demo.TutorInputIns;
        instance.detailInstance = SysApp.Demo.TutorDetailIns;

        instance.init();
    }

    $(function () {
        TutorList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑--%>
<%@ include file="/WEB-INF/views/demo/tutor/TutorPopupInput.jsp" %>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/demo/tutor/TutorPopupDetail.jsp" %>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.TutorListIns"/>