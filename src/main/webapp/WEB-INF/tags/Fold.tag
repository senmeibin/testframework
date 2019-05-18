<%--样式布局标签--%>
<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="id" type="java.lang.String" required="true" %>
<%@ attribute name="name" type="java.lang.String" required="true" %>
<%@ attribute name="foldIconClass" type="java.lang.String" required="false" %>
<%@ attribute name="foldWidth" type="java.lang.String" required="false" %>
<%@ attribute name="foldHeight" type="java.lang.String" required="false" %>
<%@ attribute name="marginTop" type="java.lang.String" required="false" %>
<%@ attribute name="tipsContent" type="java.lang.String" required="false" %>

<%@ attribute name="showRightAddButton" type="java.lang.Boolean" required="false" %>
<%@ attribute name="clientInstance" type="java.lang.String" required="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="form-group-fold" style="margin-top: ${marginTop}">
    <div class="popovers" data-trigger="hover" data-placement="right" data-content="展开/收缩内容区域" style="float: left;">
        <button type="button" class="btn btn-primary" data-allow-disabled="false" data-container="${id}"
                style="min-width: ${empty foldWidth ? '120px' : foldWidth};height: ${foldHeight};line-height: ${foldHeight};">
            ${name}&nbsp;
            <c:if test="${foldIconClass != null}">
                <i class="form-group-fold-icon fa ${foldIconClass}"></i>
            </c:if>
            <c:if test="${foldIconClass == null}">
                <i class="form-group-fold-icon fa fa-caret-down"></i>
            </c:if>
        </button>
    </div>

    <c:if test="${showRightAddButton}">
        <button id="btnAdd" onclick="${clientInstance}.onClick_Add();" type="button" style="float: right;border: 0;background: #3CC457;color: #fff;min-width: 70px;font-size: 12px;height: 24px;">
            <i class="fa fa-plus"></i> 新增
        </button>
    </c:if>

    <c:if test="${tipsContent != null}">
        <label class="control-label">（<b>Tips：</b>${tipsContent}）</label>
    </c:if>
</div>
<div class="clear-both"></div>