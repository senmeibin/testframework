<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/customer/CustomerImport.js?${version}"></script>

<title>合同客户导入</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper CustomerImport-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">合同客户导入</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form class="form-horizontal panel-body" action="${ctx}/cmn/customer/importPost" role="form" id="fileForm" method="post" enctype="multipart/form-data" onsubmit="return SysApp.Cmn.CustomerImportIns.checkFile()">
            <%--Flash消息提示--%>
            <%@ include file="/WEB-INF/views/cmn/FlashMessage.jsp" %>

            <div class="form-group form-inline">
                <label class="col-sm-3 control-label">合同客户模板文件</label>
                <div class="col-sm-9" style="padding-top: 6px;">
                    <a href="${ctx}/template/cmn/CustomerTemplate.xlsx" style="color:#F00;text-decoration:underline;">合同客户模板文件下载</a>
                </div>
            </div>
            <div class="form-group form-inline">
                <label class="col-sm-3 control-label">导入文件</label>
                <div class="col-sm-9">
                    <ctag:FileImport fileId="file" clientID="CustomerImport"></ctag:FileImport>
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
    function CustomerImport_Page_Load() {
        SysApp.Cmn.CustomerImportIns = new SysApp.Cmn.CustomerImport();
        var instance = SysApp.Cmn.CustomerImportIns;
        instance.selfInstance = "SysApp.Cmn.CustomerImportIns";
        instance.listUrl = "${ctx}/cmn/customer/";
        instance.clientID = "CustomerImport";
        instance.tableName = "cmn_customer";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    $(function () {
        CustomerImport_Page_Load();
    });
    //]]>
</script>

<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Cmn.CustomerImportIns"/>