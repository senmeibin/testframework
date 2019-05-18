<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/sys/cache/RedisCacheInput.js?${version}"></script>

<title>Redis缓存管理</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper">
    <div class="panel content-min-height RedisCacheInput-MainContent">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">Redis缓存管理</div>
            <ctag:PageViewIcon></ctag:PageViewIcon>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">

                <div class="flash-notice margin-bottom-space">
                    Redis服务共缓存 <b style="font-size:20px;color: #0099CC;">${redisKeyCount}</b> 个键值对，如需清除相关缓存数据，请使用具体清除操作按钮进行相关键值对的清除操作。
                </div>

                <div class="form-group form-inline">
                    <div class="col-lg-1">
                    </div>
                    <div class="col-lg-2">
                        <button type="button" class="btn btn-danger" style="width: 120px;" id="btnDeleteParameter"><i class="fa fa-eraser"></i>清除参数缓存
                        </button>
                    </div>
                    <div class="col-lg-9">
                        <label style="padding-top: 6px;">Redis缓存中的系统参数（对应数据库中的系统参数表）</label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <div class="col-lg-1">
                    </div>
                    <div class="col-lg-2">
                        <button type="button" class="btn btn-danger" style="width: 120px;" id="btnDeleteDictionary"><i class="fa fa-eraser"></i>清除字典缓存
                        </button>
                    </div>
                    <div class="col-lg-9">
                        <label style="padding-top: 6px;">Redis缓存中的字典参数（对应数据库中的数据字典表）</label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <div class="col-lg-1">
                    </div>
                    <div class="col-lg-2">
                        <button type="button" class="btn btn-danger" style="width: 120px;" id="btnDeleteDSQL"><i class="fa fa-eraser"></i>清除DSQL缓存
                        </button>
                    </div>
                    <div class="col-lg-9">
                        <label style="padding-top: 6px;">Redis缓存中的DSQL内容（对应DSQL文件中的内容）</label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <div class="col-lg-1">
                    </div>
                    <div class="col-lg-2">
                        <button type="button" class="btn btn-danger" style="width: 120px;" id="btnDeleteSpecial"><i class="fa fa-eraser"></i>清除指定缓存
                        </button>
                    </div>
                    <div class="col-lg-9">
                        <input id="redisKey" type="text" class="form-control required" data-title="缓存key" maxlength="1024" data-rangelength="[0,1024]" style="width: 250px;"/>
                        <label id="redisKey_Error" class="validator-error"></label>
                        <label class="checkbox">
                            <input name="isLike" type="checkbox" value="1" id="isLike" name="isLike" style="margin-top: 2px"/>模糊匹配
                        </label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <div class="col-lg-1">
                    </div>
                    <div class="col-lg-2">
                        <button type="button" class="btn btn-danger" style="width: 120px;" id="btnDeleteAll"><i class="fa fa-eraser"></i>清除系统缓存
                        </button>
                    </div>
                    <div class="col-lg-9">
                        <label style="padding-top: 6px;">Redis缓存中的所有系统参数</label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <div class="col-lg-1">
                    </div>
                    <div class="col-lg-2">
                        <button type="button" class="btn btn-primary" style="width: 120px;" id="btnReadRedis"><i class="fa fa-spinner"></i>读取缓存数据
                        </button>
                    </div>
                    <div class="col-lg-9">
                        <input id="readRedisKey" type="text" class="form-control required" data-title="缓存key" maxlength="1024" data-rangelength="[0,1024]" style="width: 250px;"/>
                        <label id="readRedisKey_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline" id="divReadRedisList" style="display: none">
                    <div class="col-lg-1">
                    </div>
                    <div class="col-lg-8" id="readList" style="line-height: 24px;margin-left: 15px;border: 1px solid #DDDDDD ;height: 250px;overflow: auto;"></div>
                </div>
                <c:if test="${!empty redisKeyListHtml}">
                    <div class="form-group form-inline margin-top-space">
                        <div class="col-lg-1">
                        </div>
                        <div class="col-lg-2">
                            Redis缓存列表
                        </div>
                    </div>
                    <div class="form-group form-inline">
                        <div class="col-lg-1">
                        </div>
                        <div class="col-lg-8" id="divList" style="line-height: 24px;margin-left: 15px;border: 1px solid #DDDDDD ;height: 250px;overflow: auto;">${redisKeyListHtml}</div>
                    </div>
                </c:if>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function RoleInput_Page_Load() {
        SysApp.Sys.RedisCacheInputIns = new SysApp.Sys.RedisCacheInput();
        var instance = SysApp.Sys.RedisCacheInputIns;
        instance.selfInstance = "SysApp.Sys.RedisCacheInputIns";
        instance.controller = "${ctx}/sys/cache/";
        instance.clientID = "RedisCacheInput";

        instance.init();
    }

    RoleInput_Page_Load();
    //]]>
</script>