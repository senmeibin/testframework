<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/urlrole/UrlRoleTreeList.js?${version}"></script>

<title>权限配置</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="section selection-urlrole">
    <div class="section-icon popovers" data-trigger="hover" data-placement="right" data-content="隐藏/显示访问路径"></div>
    <div class="section-left" style="width: 400px">
        <h3>访问路径</h3>
        <div class="section-tree" style="width: 400px">
            <ctag:TreeView treeId="urlRoleTree" parentInstance="SysApp.Sys.UrlRoleListIns" style="width:510px;"
                           idKey="urlStr" parentIdKey="parentModule" nodeName="name" dataUrl="${ctx}/sys/urlrole/getAllUrlList"/>
        </div>
        <div class="clear"></div>
    </div>
    <div class="section-right UrlRoleTreeList-MainContent">
        <div style=" height:auto; overflow:hidden; margin-top:0px;">
            <div class="wrapper">
                <div class="panel">
                    <div class="panel-heading">
                        <em></em>
                        <div class="panel-title">访问权限配置</div>
                        <ctag:PageViewIcon></ctag:PageViewIcon>
                    </div>
                    <div class="panel-body">
                        <div class="UrlRoleTreeList-MainContent">
                            <button id="btnUrlRoleList" type="button" class="btn btn-default">
                                <i class="fa fa-list"></i>访问权限平铺展示
                            </button>

                            <div class="clear-both dashed-line">
                            </div>

                            <div style="border-bottom: 1px dotted  #ccc; padding: 10px 0 7px;">
                                <label style="color: #09c; font-weight: bold;">访问路径：</label><span id="urlSpan" style='margin-right: 30px; font-weight: bold;'></span>
                                <br><label style="color: #09c; font-weight: bold;">业务描述：</label><textarea id="description" rows="5" cols="100" class="form-control" data-title="付款备注" data-rangelength="[0,256]"></textarea>
                            </div>

                            <ctag:Fold id="roleInfo" name="角色权限" marginTop="15px"/>
                            <div id="roleInfo" class="separate-block clearfix">
                                <div class="col-sm-12" style="padding: 0px 15px 0px 15px;">
                                    <div id="roles"></div>
                                </div>
                            </div>

                            <div class="text-center margin-top-space">
                                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>仅保存
                                </button>
                                <button type="button" class="btn btn-primary btn-100px" id="btnCoverSave"><i class="fa fa-save"></i>保存并被继承
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_UrlRole}"/>
    </div>
</div>
<script type="text/javascript">
    //<![CDATA[
    function UrlRoleTreeList_Page_Load() {
        SysApp.Sys.UrlRoleListIns = new SysApp.Sys.UrlRoleTreeList();
        var instance = SysApp.Sys.UrlRoleListIns;
        instance.selfInstance = "SysApp.Sys.UrlRoleListIns";
        instance.controller = "${ctx}/sys/urlrole/";
        instance.inputUrl = "${ctx}/sys/urlrole/input";
        instance.clientID = "UrlRoleTreeList";
        instance.tableName = "sys_url_role";
        instance.urlRoleListUrl = "${ctx}/sys/urlrole?entry=menu";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    $(function () {
        UrlRoleTreeList_Page_Load();
    });
    //]]>
</script>

