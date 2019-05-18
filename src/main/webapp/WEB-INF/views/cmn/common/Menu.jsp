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
            <shiro:hasAnyRoles name="SystemManagement,BasicDataManagement">
                <li>
                    <a href="#">
                        <i class="fa fa-caret-right"></i><span>基础数据</span>
                    </a>
                    <ul>
                        <li class="<c:if test="${uri.indexOf('cmn/maincustomer') > 0}">active</c:if>">
                            <a href="${ctx}/cmn/maincustomer?entry=menu">
                                <i class="fa fa-vcard"></i><span>主客户</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('cmn/customer') > 0}">active</c:if>">
                            <a href="${ctx}/cmn/customer?entry=menu">
                                <i class="fa fa-vcard-o"></i><span>合同客户</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('cmn/signcompany') > 0}">active</c:if>">
                            <a href="${ctx}/cmn/signcompany?entry=menu">
                                <i class="fa fa-id-card"></i><span>签单公司</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('cmn/area') > 0}">active</c:if>">
                            <a href="${ctx}/cmn/area?entry=menu">
                                <i class="fa fa-cubes"></i><span>大区信息</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('cmn/city') > 0}">active</c:if>">
                            <a href="${ctx}/cmn/city?entry=menu">
                                <i class="fa fa-building"></i><span>城市信息</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('cmn/region') > 0}">active</c:if>">
                            <a href="${ctx}/cmn/region?entry=menu">
                                <i class="fa fa-reorder"></i><span>地区信息</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('cmn/subway') > 0}">active</c:if>">
                            <a href="${ctx}/cmn/subway?entry=menu">
                                <i class="fa fa-subway"></i><span>地铁信息</span>
                            </a>
                        </li>
                    </ul>
                </li>

                <li>
                    <a href="#">
                        <i class="fa fa-caret-right"></i><span>消息管理</span>
                    </a>
                    <ul>
                        <li class="<c:if test="${uri.indexOf('cmn/message') > 0}">active</c:if>">
                            <a href="${ctx}/cmn/message?entry=menu">
                                <i class="fa fa-comments"></i><span>消息推送</span>
                            </a>
                        </li>
                    </ul>
                </li>

                <li>
                    <a href="#">
                        <i class="fa fa-caret-right"></i><span>协议管理</span>
                    </a>
                    <ul>
                        <li class="<c:if test="${uri.indexOf('cmn/protocol') > 0 && uri.indexOf('cmn/protocolagreelog') < 0}">active</c:if>">
                            <a href="${ctx}/cmn/protocol?entry=menu">
                                <i class="fa fa-file"></i><span>协议管理</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('cmn/protocolagreelog') > 0}">active</c:if>">
                            <a href="${ctx}/cmn/protocolagreelog?entry=menu">
                                <i class="fa fa-check"></i><span>同意日志</span>
                            </a>
                        </li>
                    </ul>
                </li>
            </shiro:hasAnyRoles>
        </ul>
    </div>
</div>
<%--左侧菜单鼠标提示专用--%>
<div class="sidebar-nav-prompt">
    <i class="fa fa-caret-left"></i>
    <em></em>
</div>
<input type="hidden" id="requestUri" value="${uri}"/>