<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/dictionary/DictionaryImport.js?${version}"></script>

<title>数据字典导入</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper DictionaryImport-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">数据字典导入</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form class="form-horizontal" action="${ctx}/sys/dictionary/importPost" role="form" id="fileForm" method="post" enctype="multipart/form-data" onsubmit="return SysApp.Sys.DictionaryImportIns.checkFile()">
                <%--Flash消息提示--%>
                <%@ include file="/WEB-INF/views/cmn/FlashMessage.jsp" %>

                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label">模板文件</label>
                    <div class="col-sm-9" style="padding-top: 6px;">
                        <a href="${ctx}/template/sys/DictionaryTemplate.xlsx" style="color:#F00;text-decoration:underline;">数据字典模板文件下载</a>
                    </div>
                </div>
                <div class="form-group form-inline">
                    <label class="col-sm-3 control-label">导入文件</label>
                    <div class="col-sm-9">
                        <ctag:FileImport fileId="file" clientID="DictionaryImport"></ctag:FileImport>
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

            <%--FileImportList禁止放在form tag内--%>
            <ctag:FileImportList appCode="SYS" moduleName="数据字典导入" clientID="DictionaryImport"></ctag:FileImportList>

            <div class="help-info prompt-open" id="promptFold">
                <label><i class="fa fa-hand-pointer-o"></i>操作说明</label>
                <ol>
                    <li>
                        请先下载数据字典模板文件；
                    </li>
                    <li>
                        根据模板文件的格式要求，线下整理数据字典文件；
                    </li>
                    <li>
                        将整理好的数据字典文件导入到系统中，如果导入成功将在一览画面可查看到具体信息；
                    </li>
                </ol>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function DictionaryImport_Page_Load() {
        SysApp.Sys.DictionaryImportIns = new SysApp.Sys.DictionaryImport();
        var instance = SysApp.Sys.DictionaryImportIns;
        instance.selfInstance = "SysApp.Sys.DictionaryImportIns";
        instance.listUrl = "${ctx}/sys/dictionary/";
        instance.clientID = "DictionaryImport";
        instance.tableName = "sys_dictionary";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    $(function () {
        DictionaryImport_Page_Load();
    });
    //]]>
</script>
