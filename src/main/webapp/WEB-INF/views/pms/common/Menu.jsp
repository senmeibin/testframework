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
                    <i class="fa fa-caret-right"></i><span>工作台</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('pms/doing') >= 0}">active</c:if>">
                        <a href="${ctx}/pms/default/doing?entry=menu">
                            <i class="fa fa-cube"></i><span>我的工作台</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>市场推广</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('pms/companyinfo') >= 0 }">active</c:if>">
                        <a href="${ctx}/pms/companyinfo?entry=menu">
                            <i class="fa fa-desktop"></i><span>公司资讯</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>市场调研</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('pms/researchinfo') >= 0 }">active</c:if>">
                        <a href="${ctx}/pms/researchinfo?entry=menu">
                            <i class="fa fa-desktop"></i><span>信息登录</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>项目</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('pms/project') >= 0}">active</c:if>">
                        <a href="${ctx}/pms/project?entry=menu">
                            <i class="fa fa-cube"></i><span>项目一览</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('pms/doing') >= 0}">active</c:if>">
                        <a href="${ctx}/pms/default/doing?entry=menu">
                            <i class="fa fa-cube"></i><span>项目客户一览</span>
                        </a>
                    </li>
                    <li class="<c:if test="${uri.indexOf('pms/doing') >= 0}">active</c:if>">
                        <a href="${ctx}/pms/default/doing?entry=menu">
                            <i class="fa fa-cube"></i><span>项目踏勘</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>投标管理</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('pms/doing') >= 0}">active</c:if>">
                        <a href="${ctx}/pms/default/doing?entry=menu">
                            <i class="fa fa-cube"></i><span>投标管理</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="#">
                    <i class="fa fa-caret-right"></i><span>合同管理</span>
                </a>
                <ul>
                    <li class="<c:if test="${uri.indexOf('pms/doing') >= 0}">active</c:if>">
                        <a href="${ctx}/pms/default/doing?entry=menu">
                            <i class="fa fa-cube"></i><span>合同管理</span>
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