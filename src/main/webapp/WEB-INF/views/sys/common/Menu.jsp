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
            <shiro:hasAnyRoles name="SystemManagement">
                <li>
                    <a href="#">
                        <i class="fa fa-caret-right"></i><span>基础设置</span>
                    </a>
                    <ul>
                        <li class="<c:if test="${uri.indexOf('sys/application') > 0}">active</c:if>">
                            <a href="${ctx}/sys/application?entry=menu">
                                <i class="fa fa-cube"></i><span>应用管理</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('sys/dictionary') > 0}">active</c:if>">
                            <a href="${ctx}/sys/dictionary?entry=menu">
                                <i class="fa fa-book"></i><span>数据字典</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('sys/parameter') > 0}">active</c:if>">
                            <a href="${ctx}/sys/parameter?entry=menu">
                                <i class="fa fa-cogs"></i><span>参数配置</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('sys/mail') > 0}">active</c:if>">
                            <a href="${ctx}/sys/mailaccount/input?entry=menu">
                                <i class="fa fa-envelope"></i><span>邮件设定</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('sys/sms') > 0}">active</c:if>">
                            <a href="${ctx}/sys/smsaccount/input?entry=menu">
                                <i class="fa fa-commenting-o"></i><span>短信设定</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('sys/counter') > 0}">active</c:if>">
                            <a href="${ctx}/sys/counter?entry=menu">
                                <i class="fa fa-assistive-listening-systems"></i><span>查看计数器</span>
                            </a>
                        </li>
                    </ul>
                </li>
            </shiro:hasAnyRoles>

            <shiro:hasAnyRoles name="SystemManagement">
                <li>
                    <a href="#">
                        <i class="fa fa-caret-right"></i><span>角色权限</span>
                    </a>
                    <ul>
                        <li class="<c:if test="${uri.indexOf('sys/role') > 0}">active</c:if>">
                            <a href="${ctx}/sys/role?entry=menu">
                                <i class="fa fa-universal-access"></i><span>角色管理</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('sys/permission') > 0}">active</c:if>">
                            <a href="${ctx}/sys/permission?entry=menu">
                                <i class="fa fa-sign-language"></i><span>权限管理</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('sys/urlrole') > 0}">active</c:if>">
                            <a href="${ctx}/sys/urlrole/tree?entry=menu">
                                <i class="fa fa-map-signs"></i><span>权限配置</span>
                            </a>
                        </li>
                    </ul>
                </li>
            </shiro:hasAnyRoles>

            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>组织结构</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('sys/enterprise') > 0}">active</c:if>">
                        <a href="${ctx}/sys/enterprise?entry=menu">
                            <i class="fa fa-universal-access"></i><span>企业管理</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('sys/dept') > 0}">active</c:if>">
                        <a href="${ctx}/sys/dept/tree?entry=menu">
                            <i class="fa fa-sitemap"></i><span>部门管理</span>
                        </a>
                    </li>

                    <shiro:hasAnyRoles name="SystemManagement">
                        <li class="<c:if test="${uri.indexOf('sys/position') > 0}">active</c:if>">
                            <a href="${ctx}/sys/position?entry=menu">
                                <i class="fa fa-assistive-listening-systems"></i><span>职位管理</span>
                            </a>
                        </li>
                    </shiro:hasAnyRoles>

                    <li class="<c:if test="${uri.indexOf('sys/user') > 0}">active</c:if>">
                        <a href="${ctx}/sys/user?entry=menu">
                            <i class="fa fa-user-secret"></i><span>用户管理</span>
                        </a>
                    </li>
                </ul>
            </li>

            <shiro:hasAnyRoles name="SystemManagement">
                <li>
                    <a href="#">
                        <i class="fa fa-caret-right"></i><span>任务管理</span>
                    </a>
                    <ul>
                        <li class="<c:if test="${uri.indexOf('sys/executerecord') > 0}">active</c:if>">
                            <a href="${ctx}/sys/executerecord?entry=menu">
                                <i class="fa fa-tasks"></i><span>任务管理</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('sys/executelog') > 0}">active</c:if>">
                            <a href="${ctx}/sys/executelog?entry=menu">
                                <i class="fa fa-list-alt"></i><span>任务日志</span>
                            </a>
                        </li>
                    </ul>
                </li>
            </shiro:hasAnyRoles>

            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>登录管理</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('sys/onlineuser') > 0}">active</c:if>">
                        <a href="${ctx}/sys/onlineuser?entry=menu">
                            <i class="fa fa-users"></i><span>在线用户</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('sys/loginlog/list') > 0}">active</c:if>">
                        <a href="${ctx}/sys/loginlog/list?entry=menu">
                            <i class="fa fa-indent"></i><span>登录日志</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('sys/loginlog/report') > 0}">active</c:if>">
                        <a href="${ctx}/sys/loginlog/report?entry=menu">
                            <i class="fa fa-bar-chart"></i><span>登录统计</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('sys/user') > 0}">active</c:if>" style="display: none;">
                        <a href="${ctx}/sys/user?entry=menu">
                            <i class="fa fa-user-secret"></i><span>登录控制</span>
                        </a>
                    </li>
                </ul>
            </li>

            <shiro:hasAnyRoles name="SystemManagement">
                <li>
                    <a href="#">
                        <i class="fa fa-caret-right"></i><span>附件管理</span>
                    </a>
                    <ul>
                        <li class="<c:if test="${uri.indexOf('sys/attachment/') > 0}">active</c:if>">
                            <a href="${ctx}/sys/attachment/list?entry=menu">
                                <i class="fa fa-upload"></i><span>文件上传附件</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('sys/attachmentimport/') > 0}">active</c:if>">
                            <a href="${ctx}/sys/attachmentimport/list?entry=menu">
                                <i class="fa fa-database"></i><span>数据导入附件</span>
                            </a>
                        </li>
                    </ul>
                </li>
            </shiro:hasAnyRoles>

            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>日志管理</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('sys/smslog') > 0}">active</c:if>">
                        <a href="${ctx}/sys/smslog?entry=menu">
                            <i class="fa fa-commenting"></i><span>短信日志</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('sys/maillog') > 0}">active</c:if>">
                        <a href="${ctx}/sys/maillog?entry=menu">
                            <i class="fa fa-envelope"></i><span>邮件日志</span>
                        </a>
                    </li>

                    <shiro:hasAnyRoles name="SystemManagement">
                        <li class="<c:if test="${uri.indexOf('sys/operationlog') > 0}">active</c:if>">
                            <a href="${ctx}/sys/operationlog?entry=menu">
                                <i class="fa fa-list"></i><span>操作日志</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('sys/operationsummary') > 0}">active</c:if>">
                            <a href="${ctx}/sys/operationsummary?entry=menu">
                                <i class="fa fa-fire"></i><span>访问量统计</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('sys/sensitivedataexportlog') > 0}">active</c:if>">
                            <a href="${ctx}/sys/sensitivedataexportlog?entry=menu">
                                <i class="fa fa-list"></i><span>敏感数据导出日志</span>
                            </a>
                        </li>
                    </shiro:hasAnyRoles>
                </ul>
            </li>

            <shiro:hasAnyRoles name="SystemManagement">
                <li>
                    <a href="#">
                        <i class="fa fa-caret-right"></i><span>缓存管理</span>
                    </a>
                    <ul>
                        <li class="<c:if test="${uri.indexOf('sys/cache/rediscacheinput') > 0}">active</c:if>">
                            <a href="${ctx}/sys/cache/redisCacheInput?entry=menu">
                                <i class="fa fa-database"></i><span>Redis缓存管理</span>
                            </a>
                        </li>
                    </ul>
                </li>
            </shiro:hasAnyRoles>

            <shiro:hasAnyRoles name="SystemManagement">
                <li>
                    <a href="#">
                        <i class="fa fa-caret-right"></i><span>数据库管理</span>
                    </a>
                    <ul>
                        <li class="<c:if test="${uri.indexOf('sys/databaseprocessoperationlog') > 0}">active</c:if>">
                            <a href="${ctx}/sys/databaseprocessoperationlog?entry=menu">
                                <i class="fa fa-tasks"></i><span>数据库进程</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('sys/uselessdatatable') > 0}">active</c:if>">
                            <a href="${ctx}/sys/uselessdatatable?entry=menu">
                                <i class="fa fa-database"></i><span>数据库数据统计</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('sys/tablespaceoptimizelog') > 0}">active</c:if>">
                            <a href="${ctx}/sys/tablespaceoptimizelog?entry=menu">
                                <i class="fa fa-list-alt"></i><span>表空间优化日志</span>
                            </a>
                        </li>
                    </ul>
                </li>
                <c:if test="${isUdcServer}">
                    <li>
                        <a href="#">
                            <i class="fa fa-caret-right"></i><span>数据同步管理</span>
                        </a>
                        <ul>
                            <li class="<c:if test="${uri.indexOf('sys/syncentity') > 0}">active</c:if>">
                                <a href="${ctx}/sys/syncentity?entry=menu">
                                    <i class="fa fa-cubes"></i><span>同步对象</span>
                                </a>
                            </li>
                            <li class="<c:if test="${uri.indexOf('sys/syncapp') > 0}">active</c:if>">
                                <a href="${ctx}/sys/syncapp?entry=menu">
                                    <i class="fa fa-cloud"></i><span>同步项目</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                </c:if>
            </shiro:hasAnyRoles>

            <shiro:hasAnyRoles name="SystemManagement">
                <li>
                    <a href="#">
                        <i class="fa fa-caret-right"></i><span>演示程序</span>
                    </a>
                    <ul>
                        <li class="<c:if test="${uri.indexOf('sys/company') > 0}">active</c:if>">
                            <a href="${ctx}/sys/company?entry=menu">
                                <i class="fa fa-th-large"></i><span>来往单位</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('sys/contact') > 0}">active</c:if>">
                            <a href="${ctx}/sys/contact?entry=menu">
                                <i class="fa fa-th"></i><span>联系人列表</span>
                            </a>
                        </li>
                        <li class="<c:if test="${uri.indexOf('sys/activemq/input') > 0}">active</c:if>">
                            <a href="${ctx}/sys/activemq/input?entry=menu">
                                <i class="fa fa-list-ol"></i><span>消息队列</span>
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