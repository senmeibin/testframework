<%@ tag pageEncoding="UTF-8" %>

<%@ attribute name="clientID" type="java.lang.String" required="false" %>

<%@ attribute name="showSaveButton" type="java.lang.Boolean" required="false" %>
<%@ attribute name="showDeleteButton" type="java.lang.Boolean" required="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal-footer <c:if test="${!empty clientID}">${clientID}-MainContent</c:if>" onselectstart="return false;">
    <c:if test="${showSaveButton}">
        <button type="button" class="btn btn-primary" id="btnSave"><i class="fa fa-save"></i>保存</button>
    </c:if>

    <c:if test="${showDeleteButton}">
        <button type="button" class="btn btn-danger" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
    </c:if>

    <button type="button" class="btn btn-default" id="btnClose"><i class="fa fa-remove"></i>关闭</button>
</div>