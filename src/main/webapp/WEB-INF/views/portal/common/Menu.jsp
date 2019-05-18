<%@ page language="java" pageEncoding="UTF-8" %>

<script type="text/javascript" src="${staticContentsServer}/static/plugins/dcjqaccordion/js/jquery.hoverIntent.minified.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/dcjqaccordion/js/jquery.dcjqaccordion.2.9.js?${version}"></script>

<c:set var="uri" value="${pageContext.request.requestURI.toLowerCase()}"/>
<div class="sidebar">
    <div class="sidebar-border">
        <div class="sidebar-expand">
            <a href="javascript:void(0)"><i class="mini-icon-open"></i></a>
        </div>
        <ul class="sidebar-menu sidebar-sub-menu" id="nav-accordion">
            <li>
                <a href="#" class="active">
                    <i class="fa fa-caret-right"></i><span>个人设置</span>
                </a>
                <ul id="ulPersonSetting">
                    <li class="<c:if test="${uri.indexOf('portal/user/profilesetting') > 0}">active</c:if>">
                        <a href="${ctx}/portal/user/profileSetting">
                            <i class="fa fa-user"></i><span>个人信息</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('portal/user/themesetting') > 0}">active</c:if>">
                        <a href="${ctx}/portal/user/themeSetting">
                            <i class="fa fa-themeisle"></i><span>主题设置</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('portal/user/passwordchange') > 0}">active</c:if>">
                        <a href="${ctx}/portal/user/passwordChange">
                            <i class="fa fa-user-secret"></i><span>修改密码</span>
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>
<%--左侧菜单鼠标提示专用--%>
<div class="sidebar-nav-prompt">
    <i class="fa fa-caret-left"></i>
    <em></em>
</div>
<input type="hidden" id="requestUri" value="${uri}"/>