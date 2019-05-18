<%@ tag pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<%@ attribute name="modalTitle" type="java.lang.String" required="true" %>
<%@ attribute name="delayTime" type="java.lang.String" required="false" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ProgressModal_ctlFrame" class="modal-content ProgressModal-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader modalTitle="${modalTitle}" hideMaximizationIcon="true"/>
    <div class="modal-body">
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_AccountParams}"/>
        <div id="divList" style="width: 100%">
            <div class="progress">
                <div id="progressBar" class="progress-bar progress-bar-info progress-bar-striped" role="progressbar">
                </div>
            </div>
            <div id="messageContent" style="color: #008BB9;font-weight: bold;">

            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" id="btnCloseProgressModal"><i class="fa fa-remove"></i>关闭</button>
    </div>
</div>

<script type="text/javascript">
    function Progress_Page_Load() {
        SysApp.Cmn.ProgressModalIns = new SysApp.Cmn.ProgressModal();
        var instance = SysApp.Cmn.ProgressModalIns;
        instance.selfInstance = "SysApp.Cmn.ProgressModalIns";
        instance.clientID = "ProgressModal";
        instance.ajaxUrl = "${ctx}/cmn/progress/";
        instance.pauseCallback = function () {
        };
        instance.delayTime = "${delayTime}";
        instance.init();
    }

    $(function () {
        Progress_Page_Load();
    });
</script>