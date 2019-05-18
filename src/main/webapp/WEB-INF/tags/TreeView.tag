<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%-- Tree对应的ID--%>
<%@ attribute name="treeId" type="java.lang.String" required="true" %>
<%-- 父画面JS实例对象字符串--%>
<%@ attribute name="parentInstance" type="java.lang.String" required="true" %>
<%-- Tree数据请求URL--%>
<%@ attribute name="dataUrl" type="java.lang.String" required="true" %>

<%-- Tree描画对应数据结构的ID字段属性名--%>
<%@ attribute name="idKey" type="java.lang.String" required="true" %>
<%-- Tree描画对应数据结构的父ID字段属性名--%>
<%@ attribute name="parentIdKey" type="java.lang.String" required="true" %>
<%-- Tree节点显示字段属性名--%>
<%@ attribute name="nodeName" type="java.lang.String" required="true" %>
<%-- 检索用表别名--%>
<%@ attribute name="aliasTable" type="java.lang.String" required="false" %>

<%-- 选中后显示字段属性名--%>
<%@ attribute name="dispName" type="java.lang.String" required="false" %>
<%-- 文本样式--%>
<%@ attribute name="style" type="java.lang.String" required="false" %>

<%-- 唯一UID临时变量--%>
<c:set var="uniqueID" value="${parentInstance.replace('.', '_')}_${valueDomId}"/>
<%-- 唯一JS实例临时变量--%>
<c:set var="jsInstance" value="SysApp.Cmn.TreeIns_${uniqueID}"/>

<div style="position: relative">
    <div id="${treeId}MenuContent" class="menuContent" style="position: absolute;z-index: 999">
        <ul id="${treeId}Tree" class="ztree"></ul>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function TreeView_${uniqueID}_Page_Load() {
        //解决多TAG引入的问题，全局实例变量前追加selectId唯一关键字
        ${jsInstance} = new SysApp.Cmn.TreeView();
        var instance = ${jsInstance};
        instance.selfInstance = "${jsInstance}";
        //父画面JS实例对象字符串
        instance.parentInstanceString = "${parentInstance}";
        //树ID
        instance.treeId = "${treeId}";
        //id对应的字段属性名
        instance.idKey = "${idKey}";
        //父id对应的字段属性名
        instance.parentIdKey = "${parentIdKey}";
        //节点显示字段属性名
        instance.nodeName = "${nodeName}";
        //选中后显示用字段属性名（如果不指定用显示名）
        instance.dispName = "${empty dispName ? nodeName : dispName}";
        //树内容取得用 ajax 地址
        instance.ajaxUrl = "${dataUrl}";
        //唯一UID临时变量
        instance.uniqueID = "${uniqueID}";
        //初始化父画面JS实例对象
        instance.initParentInstance();
    }

    TreeView_${uniqueID}_Page_Load();
    //]]>
</script>