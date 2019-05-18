<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/maincustomer/MainCustomerImport.js?${version}"></script>

<title>主客户导入</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper MainCustomerImport-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">主客户导入</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form class="form-horizontal panel-body" action="${ctx}/cmn/maincustomer/importPost" role="form" id="fileForm" method="post" enctype="multipart/form-data" onsubmit="return SysApp.Cmn.MainCustomerImportIns.checkFile()">
            <%--Flash消息提示--%>
            <%@ include file="/WEB-INF/views/cmn/FlashMessage.jsp" %>

            <div class="form-group form-inline">
                <label class="col-sm-3 control-label">主客户模板文件</label>
                <div class="col-sm-9" style="padding-top: 6px;">
                    <a href="${ctx}/template/cmn/MainCustomerTemplate.xlsx" style="color:#F00;text-decoration:underline;">主客户模板文件下载</a>
                </div>
            </div>
            <div class="form-group form-inline">
                <label class="col-sm-3 control-label">导入文件</label>
                <div class="col-sm-9">
                    <ctag:FileImport fileId="file" clientID="MainCustomerImport"></ctag:FileImport>
                </div>
            </div>

            <div class="form-group form-inline" style="margin-top: 30px;">
                <label class="col-sm-3 control-label"></label>
                <div class="col-sm-9">
                    <button type="submit" class="btn btn-primary btn-100px" id="btnUpload"><i class="fa fa-cloud-upload"></i>上传</button>
                    <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function MainCustomerImport_Page_Load() {
        SysApp.Cmn.MainCustomerImportIns = new SysApp.Cmn.MainCustomerImport();
        var instance = SysApp.Cmn.MainCustomerImportIns;
        instance.selfInstance = "SysApp.Cmn.MainCustomerImportIns";
        instance.listUrl = "${ctx}/cmn/maincustomer/";
        instance.clientID = "MainCustomerImport";
        instance.tableName = "cmn_main_customer";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    $(function () {
        MainCustomerImport_Page_Load();
    });
    //]]>
</script>

<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Cmn.MainCustomerImportIns"/>