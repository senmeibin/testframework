<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%--浏览量图标--%>
<c:if test="${!empty pageViewCount}">
    <div class="access-statistics pull-right" data-page-view="${pageViewCount}" data-page-average-view="${pageViewAverageCount}" data-page-compare-view="${pageViewCompareCount}">
        <span class='popovers' data-animation="true" data-trigger='hover' data-placement='left' data-content='访问量：${pageViewCount}次'>
            <c:if test="${pageViewCount < pageViewAverageCount - pageViewCompareCount}">
                <i class="grey"></i>
            </c:if>
            <c:if test="${pageViewCount >= pageViewAverageCount - pageViewCompareCount && pageViewCount <= pageViewAverageCount + pageViewCompareCount}">
                <i class="blue"></i>
            </c:if>
            <c:if test="${pageViewCount > pageViewAverageCount + pageViewCompareCount}">
                <i class="green"></i>
            </c:if>
        </span>
    </div>
</c:if>