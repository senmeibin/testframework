<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<div id="ctlFrame" class="modal-content BrowserVersion-MainContent" style="display: none; width: 800px;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader modalTitle="浏览器版本提示" hideMaximizationIcon="true"></ctag:ModalHeader>

    <div class="modal-body">
        <div class="alert alert-warning" style="font-size: 14px; font-weight: bold;">
            您所使用的浏览器版本太低或者浏览器兼容模式设置不匹配，请使用系统推荐的以下浏览器版本：
        </div>
        <ol style="line-height: 30px; font-size: 14px;">
            <li>
                如果你使用的是360浏览器，请切换浏览器模式为&nbsp;<label style="color: Blue;"><i class="fa fa-bolt fa-blue"></i>极速模式</label>；
            </li>
            <li>
                请使用IE10以上版本浏览器，去
                <a style="color: Blue;" target="_blank" href="http://windows.microsoft.com/zh-cn/internet-explorer/download-ie"><i class="fa fa-download"></i>下载</a>
                最新版IE浏览器；
            </li>
            <li>
                请使用最新版本的Firefox浏览器，去
                <a style="color: Blue;" target="_blank" href="http://www.firefox.com.cn/"><i class="fa fa-download"></i>下载</a>
                最新版Firefox浏览器；
            </li>
            <li>
                请使用最新版本的Chrome浏览器，去
                <a style="color: Blue;" target="_blank" href="http://rj.baidu.com/soft/detail/14744.html"><i class="fa fa-download"></i>下载</a>
                最新版Chrome浏览器；
            </li>
        </ol>
    </div>
    <%--POPUP控件Footer--%>
    <ctag:ModalFooter></ctag:ModalFooter>
</div>
<script type="text/javascript" language="javascript">
    function BrowserVersion_Page_Load() {
        SysApp.Cmn.BrowserVersionIns = new SysApp.Cmn.BrowserVersion();
        var instance = SysApp.Cmn.BrowserVersionIns;
        instance.clientID = "BrowserVersion";
        instance.init();
        instance.show();
    }
    BrowserVersion_Page_Load();
</script>
