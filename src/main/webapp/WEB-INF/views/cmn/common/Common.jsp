<%@ page language="java" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/cmn/cmn.css?${version}"/>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/Common.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/Common.js?${version}"></script>
<script type="text/javascript">
    //是否是UDC服务器
    SysApp.Cmn.isUdcServer = ${isUdcServer};

    $(function () {
        //注意：为防止画面DOM ID未完全初始化加载完成，导致温馨提示展开/折叠功能失效
        //此处延迟100ms绑定温馨提示的展开/折叠功能
        setTimeout($M.bindPromptEvent, 150);
    });

</script>