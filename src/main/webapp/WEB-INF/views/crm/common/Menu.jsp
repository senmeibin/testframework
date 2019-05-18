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
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>工作平台</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('crm/desktop') >= 0 }">active</c:if>">
                        <a href="${ctx}/crm/desktop?entry=menu">
                            <i class="fa fa-desktop"></i><span>我的工作台</span>
                        </a>
                    </li>
                </ul>
            </li>

            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>学员管理</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('crm/student/') >= 0}">active</c:if>">
                        <a href="${ctx}/crm/student/list?entry=menu">
                            <i class="fa fa-users"></i><span>学员管理</span>
                        </a>
                    </li>
                </ul>
            </li>

            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>跟进管理</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('crm/followup/') >= 0}">active</c:if>">
                        <a href="${ctx}/crm/followup/list?entry=menu">
                            <i class="fa fa-handshake-o"></i><span>跟进管理</span>
                        </a>
                    </li>
                </ul>
            </li>

            <shiro:hasAnyRoles name="SystemManagement">
                <li>
                    <a href="#">
                        <i class="fa fa-caret-right"></i><span>预约管理</span>
                    </a>
                    <ul>
                        <li class="<c:if test="${uri.indexOf('crm/reservation/') >= 0}">active</c:if>">
                            <a href="${ctx}/crm/reservation/list?entry=menu">
                                <i class="fa fa-calendar-check-o"></i><span>预约管理</span>
                            </a>
                        </li>
                    </ul>
                </li>
            </shiro:hasAnyRoles>

            <shiro:hasAnyRoles name="SystemManagement">
                <li>
                    <a href="#">
                        <i class="fa fa-caret-right"></i><span>报名管理</span>
                    </a>
                    <ul>
                        <li class="<c:if test="${uri.indexOf('crm/registration/') >= 0}">active</c:if>">
                            <a href="${ctx}/crm/registration/list?entry=menu">
                                <i class="fa fa-cube"></i><span>报名管理</span>
                            </a>
                        </li>
                    </ul>
                </li>
            </shiro:hasAnyRoles>

            <shiro:hasAnyRoles name="SystemManagement">
                <li>
                    <a href="#">
                        <i class="fa fa-caret-right"></i><span>活动管理</span>
                    </a>
                    <ul>
                        <li class="<c:if test="${uri.indexOf('crm/activity/list') >= 0}">active</c:if>">
                            <a href="${ctx}/crm/activity/list?entry=menu">
                                <i class="fa fa-cube"></i><span>活动管理</span>
                            </a>
                        </li>
                    </ul>
                </li>
            </shiro:hasAnyRoles>

            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>财务管理</span>
                </a>
                <ul>
                    <shiro:hasAnyRoles name="SystemManagement,GeneralIT,BranchIT">
                        <li>
                            <a href="#">
                                <i class="fa fa-angle-down"></i><span>报名收费</span>
                            </a>
                            <ol>
                                <li class="<c:if test="${uri.indexOf('crm/payment/') >= 0 and mode == 1}">active</c:if>">
                                    <a href="${ctx}/crm/payment/list?entry=menu">
                                        <i class="fa fa-ticket"></i><span>缴费管理</span>
                                    </a>
                                </li>
                                <li class="<c:if test="${uri.indexOf('crm/storeplan/storecaigoulist') >= 0 and mode == 1}">active</c:if>">
                                    <a href="${ctx}/crm/default/doing?entry=menu">
                                        <i class="fa fa-ticket"></i><span>退费管理</span>
                                    </a>
                                </li>
                            </ol>
                        </li>
                    </shiro:hasAnyRoles>

                    <shiro:hasAnyRoles name="SystemManagement,SupplierIT">
                        <li>
                            <a href="#">
                                <i class="fa fa-angle-down"></i><span>费用统计</span>
                            </a>
                            <ol>
                                <li class="<c:if test="${uri.indexOf('crm/storeplan/storecaigouconfirm2') >= 0}">active</c:if>">
                                    <a href="${ctx}/crm/default/doing?entry=menu">
                                        <i class="fa fa-cube"></i><span>收费统计</span>
                                    </a>
                                </li>
                                <li class="<c:if test="${uri.indexOf('crm/storeplan/storecaigouconfirm2') >= 0}">active</c:if>">
                                    <a href="${ctx}/crm/default/doing?entry=menu">
                                        <i class="fa fa-cube"></i><span>退费统计</span>
                                    </a>
                                </li>
                            </ol>
                        </li>
                    </shiro:hasAnyRoles>
                </ul>
            </li>

            <shiro:hasAnyRoles name="SystemManagement">
                <li>
                    <a href="#">
                        <i class="fa fa-caret-right"></i><span>教务管理</span>
                    </a>
                    <ul>
                        <li class="<c:if test="${uri.indexOf('crm/campus/') >= 0}">active</c:if>">
                            <a href="${ctx}/crm/campus/list?entry=menu">
                                <i class="fa fa-cube"></i><span>校区管理</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('crm/campusclass/') >= 0}">active</c:if>">
                            <a href="${ctx}/crm/campusclass/list?entry=menu">
                                <i class="fa fa-cube"></i><span>班级管理</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('crm/doing') >= 0}">active</c:if>">
                            <a href="${ctx}/crm/default/doing?entry=menu">
                                <i class="fa fa-cube"></i><span>课程管理</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('crm/doing') >= 0}">active</c:if>">
                            <a href="${ctx}/crm/default/doing?entry=menu">
                                <i class="fa fa-cube"></i><span>签到管理</span>
                            </a>
                        </li>
                    </ul>
                </li>
            </shiro:hasAnyRoles>

            <!--
            二期功能菜单暂不开发
            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>报表管理</span>
                </a>
                <ul>
                </ul>
            </li>

            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>网站管理</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('crm/doing') >= 0}">active</c:if>">
                        <a href="${ctx}/crm/default/doing?entry=menu">
                            <i class="fa fa-cube"></i><span>关于我们</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('crm/doing') >= 0}">active</c:if>">
                        <a href="${ctx}/crm/default/doing?entry=menu">
                            <i class="fa fa-cube"></i><span>我要学围棋</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('crm/doing') >= 0}">active</c:if>">
                        <a href="${ctx}/crm/default/doing?entry=menu">
                            <i class="fa fa-cube"></i><span>考级报名</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('crm/doing') >= 0}">active</c:if>">
                        <a href="${ctx}/crm/default/doing?entry=menu">
                            <i class="fa fa-cube"></i><span>视频管理</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('crm/doing') >= 0}">active</c:if>">
                        <a href="${ctx}/crm/default/doing?entry=menu">
                            <i class="fa fa-cube"></i><span>新闻活动</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('crm/doing') >= 0}">active</c:if>">
                        <a href="${ctx}/crm/default/doing?entry=menu">
                            <i class="fa fa-cube"></i><span>师资阵容</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('crm/doing') >= 0}">active</c:if>">
                        <a href="${ctx}/crm/default/doing?entry=menu">
                            <i class="fa fa-cube"></i><span>开班计划</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('crm/doing') >= 0}">active</c:if>">
                        <a href="${ctx}/crm/default/doing?entry=menu">
                            <i class="fa fa-cube"></i><span>公益课</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>赛事报名</span>
                </a>
                <ul>
                </ul>
            </li>
            -->
            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>基础设置</span>
                </a>
                <ul>
                    <shiro:hasAnyRoles name="SystemManagement,ItSupport">
                        <li class="<c:if test="${uri.indexOf('crm/wormmanual') > 0 and isReadonly == false}">active</c:if>">
                            <a href="${ctx}/crm/workmanual?entry=menu&appCode=CRM&isReadonly=false&category=CRM">
                                <i class="fa fa-upload"></i><span>工作手册上传</span>
                            </a>
                        </li>
                    </shiro:hasAnyRoles>
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