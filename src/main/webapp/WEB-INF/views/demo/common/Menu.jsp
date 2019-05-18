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
                    <i class="fa fa-caret-right"></i><span>企业信息管理</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('demo/companyinformation') > 0}">active</c:if>">
                        <a href="${ctx}/demo/companyinformation?entry=menu">
                            <i class="fa fa-cube"></i><span>企业基本信息</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('demo/servicetracking') > 0}">active</c:if>">
                        <a href="${ctx}/demo/servicetracking?entry=menu">
                            <i class="fa fa-handshake-o"></i><span>企业服务跟踪</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('demo/chargesinformation') > 0}">active</c:if>">
                        <a href="${ctx}/demo/chargesinformation?entry=menu">
                            <i class="fa fa-cny"></i><span>企业收费信息</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('demo/resourcesdockingrecord') > 0}">active</c:if>">
                        <a href="${ctx}/demo/resourcesdockingrecord?entry=menu">
                            <i class="fa fa-creative-commons"></i><span>企业资源对接</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>企业扩展信息</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('demo/companyadditional') > 0}">active</c:if>">
                        <a href="${ctx}/demo/companyadditional/list?entry=menu">
                            <i class="fa fa-cubes"></i><span>附加信息</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('demo/companystock') > 0}">active</c:if>">
                        <a href="${ctx}/demo/companystock/list?entry=menu">
                            <i class="fa fa-line-chart"></i><span>股权信息</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('demo/companypersonnel') > 0}">active</c:if>">
                        <a href="${ctx}/demo/companypersonnel/list?entry=menu">
                            <i class="fa fa-users"></i><span>人员结构</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('demo/companyteam') > 0}">active</c:if>">
                        <a href="${ctx}/demo/companyteam/list?entry=menu">
                            <i class="fa fa-user-circle-o"></i><span>核心团队人员</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('demo/companyfinancial') > 0}">active</c:if>">
                        <a href="${ctx}/demo/companyfinancial/list?entry=menu">
                            <i class="fa fa-money"></i><span>财务信息</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('demo/companycopyright') > 0}">active</c:if>">
                        <a href="${ctx}/demo/companycopyright/list?entry=menu">
                            <i class="fa fa-copyright"></i><span>专利版权</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('demo/companyinvestment') > 0}">active</c:if>">
                        <a href="${ctx}/demo/companyinvestment/list?entry=menu">
                            <i class="fa fa-credit-card"></i><span>投融资</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('demo/companyintellectual') > 0}">active</c:if>">
                        <a href="${ctx}/demo/companyintellectual/list?entry=menu">
                            <i class="fa fa-lightbulb-o"></i><span>知识产权</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('demo/companyqualification') > 0}">active</c:if>">
                        <a href="${ctx}/demo/companyqualification/list?entry=menu">
                            <i class="fa fa-registered"></i><span>企业资质</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('demo/companyinstitutional') > 0}">active</c:if>">
                        <a href="${ctx}/demo/companyinstitutional/list?entry=menu">
                            <i class="fa fa-sitemap"></i><span>机构信息</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>招商潜在客户</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('demo/potentialcustomer/list') > 0 || uri.indexOf('demo/potentialcustomer/input') > 0}">active</c:if>">
                        <a href="${ctx}/demo/potentialcustomer/list?entry=menu">
                            <i class="fa fa-cubes"></i><span>招商潜在投资企业</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('demo/potentialcustomercontact') > 0}">active</c:if>">
                        <a href="${ctx}/demo/potentialcustomercontact/list?entry=menu">
                            <i class="fa fa-volume-control-phone"></i><span>招商客户联络情况</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>员工信息</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('demo/employee') >= 0}">active</c:if>">
                        <a href="${ctx}/demo/employee/list?entry=menu">
                            <i class="fa fa-user"></i><span>员工信息</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>创业活动</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('demo/entrepreneurialactivity') > 0}">active</c:if>">
                        <a href="${ctx}/demo/entrepreneurialactivity?entry=menu">
                            <i class="fa fa-trophy"></i><span>创业活动</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>企业内控</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('demo/weeklyreport') > 0}">active</c:if>">
                        <a href="${ctx}/demo/weeklyreport?entry=menu">
                            <i class="fa fa-book"></i><span>周报管理</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('demo/visitorrecords') > 0}">active</c:if>">
                        <a href="${ctx}/demo/visitorrecords?entry=menu">
                            <i class="fa fa-suitcase"></i><span>来访管理</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('demo/meetingrecord') > 0}">active</c:if>">
                        <a href="${ctx}/demo/meetingrecord?entry=menu">
                            <i class="fa fa-comments-o"></i><span>会议记录</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>第三方服务</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('demo/thirdpartyservicecontact') > 0}">active</c:if>">
                        <a href="${ctx}/demo/thirdpartyservicecontact?entry=menu">
                            <i class="fa fa-tty"></i><span>第三方服务</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('demo/tutor') > 0}">active</c:if>">
                        <a href="${ctx}/demo/tutor?entry=menu">
                            <i class="fa fa-graduation-cap"></i><span>导师信息</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>基础信息</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('demo/baseinfo') > 0}">active</c:if>">
                        <a href="${ctx}/demo/baseinfo?entry=menu">
                            <i class="fa fa-bank"></i><span>基地信息</span>
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