<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%-- ComboTree对应的选择值的DOMID--%>
<%@ attribute name="valueDomId" type="java.lang.String" required="true" %>
<%-- ComboTree对应的选择文本的DOMID--%>
<%@ attribute name="textDomId" type="java.lang.String" required="true" %>
<%-- 父画面JS实例对象字符串--%>
<%@ attribute name="parentInstance" type="java.lang.String" required="true" %>
<%-- ComboTree数据请求URL--%>
<%@ attribute name="dataUrl" type="java.lang.String" required="false" %>

<%-- ComboTree描画对应数据结构的ID字段属性名--%>
<%@ attribute name="idKey" type="java.lang.String" required="true" %>
<%-- ComboTree描画对应数据结构的父ID字段属性名--%>
<%@ attribute name="parentIdKey" type="java.lang.String" required="true" %>
<%-- ComboTree节点显示字段属性名--%>
<%@ attribute name="nodeName" type="java.lang.String" required="true" %>
<%-- 文本框标题--%>
<%@ attribute name="dataTitle" type="java.lang.String" required="true" %>
<%-- 检索用表别名--%>
<%@ attribute name="aliasTable" type="java.lang.String" required="false" %>
<%-- 检索mode--%>
<%--searchMode = {"", "=", "==", "!=", ">", ">=", "<", "<=", "in_search","in", "not_eq_search", "not_equal_search", "ignore_search", "from_search", "to_search", "like_search", "partial_search",
"prefix_search", "suffix_search", "eq_search", "equal_search", "array_search", "gt_search", "gte_search", "lt_search", "lte_search"};--%>
<%@ attribute name="searchMode" type="java.lang.String" required="false" %>

<%-- 选中后显示字段属性名--%>
<%@ attribute name="dispName" type="java.lang.String" required="false" %>
<%-- 文本框是否必须 ，默认为非必需--%>
<%@ attribute name="required" type="java.lang.String" required="false" %>
<%-- 是否显示验证消息--%>
<%@ attribute name="showValidateError" type="java.lang.Boolean" required="false" %>
<%-- 重新加载标识 --%>
<%@ attribute name="reloadFlag" type="java.lang.Boolean" required="false" %>
<%-- 文本样式--%>
<%@ attribute name="style" type="java.lang.String" required="false" %>
<%-- 是否能选择父节点 （true: 可以选择， false: 不可选择 默认 true）--%>
<%@ attribute name="selectParent" type="java.lang.String" required="false" %>

<%-- 设定层级节点禁用 --%>
<%@ attribute name="disabledLevel" type="java.lang.String" required="false" %>

<%@ attribute name="parentDomId" type="java.lang.String" required="false" %>
<%@ attribute name="parentDataTitle" type="java.lang.String" required="false" %>

<%-- 唯一UID临时变量--%>
<c:set var="uniqueID" value="${parentInstance.replace('.', '_')}_${valueDomId}"/>
<%-- 唯一JS实例临时变量--%>
<c:set var="jsInstance" value="SysApp.Cmn.ComboTreeIns_${uniqueID}"/>

<div class="combo-tree-container">
    <div class='input-group combo-tree-${uniqueID}'>
        <input id="${textDomId}" style="cursor: pointer;  ${empty style ? '' : style}" type="text" readonly value="" class="form-control ${empty required ? '' : required}"
               data-required="${empty required ? 'false' : required.contains('required') ? 'true' : 'false'}"
               data-title="${dataTitle}" placeholder="请选择${dataTitle}"
               onclick="${jsInstance}.showMenu();return false;" data-ignore-search="true"/>
        <span class="input-group-addon" id="${valueDomId}MenuBtn" onclick="${jsInstance}.showMenu(); return false;">
            <span class="fa fa-chevron-down" id="${valueDomId}FaMenuBtn"></span>
        </span>
    </div>
    <% if (showValidateError == null || showValidateError == true) { %>
    <label id="${textDomId}_Error" class="validator-error" style="display: none;"></label>
    <% } %>
    <input id="${valueDomId}" data-alias-table="${empty aliasTable ? '' : aliasTable}" type="hidden" value="" data-search-mode="${empty searchMode ? '' : searchMode}" data-allow-clear="true"/>
    <div id="${valueDomId}MenuContent" class="menuContent" style="display:none; position: absolute;top:31px;left:0px;z-index: 998">
        <ul id="${valueDomId}Tree" class="ztree" style="margin-top:0; width:220px; height: 300px;"></ul>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ComboTree_${uniqueID}_Page_Load() {
        //解决多TAG引入的问题，全局实例变量前追加selectId唯一关键字
        ${jsInstance} = new SysApp.Cmn.ComboTree();
        var instance = ${jsInstance};
        instance.selfInstance = "${jsInstance}";
        //父画面JS实例对象字符串
        instance.parentInstanceString = "${parentInstance}";
        //文本框对应的hidden 域的id
        instance.valueDomId = "${valueDomId}";
        //文本框的id(兼取得名称用的key
        instance.textDomId = "${textDomId}";
        //id对应的字段属性名
        instance.idKey = "${idKey}";
        //父id对应的字段属性名
        instance.parentIdKey = "${parentIdKey}";
        //节点显示字段属性名
        instance.nodeName = "${nodeName}";
        //选中后显示用字段属性名（如果不指定用显示名）
        instance.dispName = "${empty dispName ? nodeName : dispName}";
        //父DOMID
        instance.parentDomId = "${parentDomId}";
        //父DOM数据标题
        instance.parentDataTitle = "${parentDataTitle}";
        //树内容取得用 ajax 地址
        instance.ajaxUrl = "${dataUrl}";
        //唯一UID临时变量
        instance.uniqueID = "${uniqueID}";
        //是否能选中父节点
        instance.allowSelectParent = ${empty selectParent ? "true" : selectParent};
        //重新加载标识
        instance.reloadFlag = "${reloadFlag}";
        //设定层级节点禁用
        instance.disabledLevel = "${disabledLevel}";
        //初始化父画面JS实例对象
        instance.initParentInstance();
    }

    ComboTree_${uniqueID}_Page_Load();
    //]]>
</script>