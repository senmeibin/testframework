<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/dessetting/DesSetting.js?${version}"></script>

<title>加密/解密</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height DesSetting-MainContent">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">字符串加密/解密</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <ctag:Fold id="encryptOrDecryptArea" name="加密/解密" tipsContent="字符串加密/解密"></ctag:Fold>
                <div id="encryptOrDecryptArea" class="separate-block clearfix">
                    <div class="form-group form-inline">
                        <div class="col-lg-1">
                            <button type="button" class="btn btn-primary btn-100px" id="btnEncrypt"><i class="fa fa-check"></i>加密
                            </button>
                        </div>
                        <label class="col-lg-1 control-label">加密字符串</label>
                        <div class="col-lg-2">
                            <input id="encryptStr" type="text" class="form-control" data-title="加密字符串" maxlength="32" style="width: 200px;"/>
                        </div>
                        <label class="col-lg-1 control-label">加密结果</label>
                        <div class="col-lg-2">
                            <input id="encryptResult" type="text" class="form-control" data-title="加密结果" style="width: 200px;"/>
                        </div>
                    </div>
                    <div class="form-group form-inline margin-top-space">
                        <div class="col-lg-1">
                            <button type="button" class="btn btn-danger btn-100px" id="btnDecrypt"><i class="fa fa-check"></i>解密
                            </button>
                        </div>
                        <label class="col-lg-1 control-label">需解密字符串</label>
                        <div class="col-lg-2">
                            <input id="decryptStr" type="text" class="form-control" data-title="需解密字符串" maxlength="64" style="width: 200px;"/>
                        </div>
                        <label class="col-lg-1 control-label">解密结果</label>
                        <div class="col-lg-2">
                            <input id="decryptResult" type="text" class="form-control" data-title="解密结果" style="width: 200px;"/>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function DesSetting_Page_Load() {
        SysApp.Sys.DesSettingIns = new SysApp.Sys.DesSetting();
        var instance = SysApp.Sys.DesSettingIns;
        instance.selfInstance = "SysApp.Sys.DesSettingIns";
        instance.controller = "${ctx}/sys/dessetting/";
        instance.clientID = "DesSetting";
        instance.isSyncDataPage = "${isSyncDataPage}";

        instance.init();
    }

    DesSetting_Page_Load();
    //]]>
</script>