<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/crm/payment/PaymentList.js?${version}"></script>

<title>缴费记录一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper PaymentList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">缴费记录一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:SearchSettingIcon tips="检索条件自定义设定"></ctag:SearchSettingIcon>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary" style="display: none;">
                <i class="fa fa-plus"></i>新增缴费记录
            </button>
            <div class="clear-both dashed-line" style="display: none;">
            </div>
            <div class="fast-search">
                <label>快速查询</label>
                <a onclick="SysApp.Crm.PaymentListIns.fastSearch(1, this)"><i class="fa fa-calendar"></i>当日缴费<i class="fa fa-check"></i></a>
                <a onclick="SysApp.Crm.PaymentListIns.fastSearch(2, this)"><i class="fa fa-calendar"></i>最近三日缴费<i class="fa fa-check"></i></a>
                <a onclick="SysApp.Crm.PaymentListIns.fastSearch(3, this)"><i class="fa fa-calendar"></i>最近一周缴费<i class="fa fa-check"></i></a>
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>学员姓名/家长姓名/手机号码</label>
                    <input id="student.name,student.parentName,student.mobile" type="text" data-search-mode="OR" maxlength="32" class="form-control" data-title="学员姓名/家长姓名/手机号码"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>所属校区</label>
                    <select id="paymentBelongCampusUid" data-alias-table="main" class="form-control" data-title="所属校区">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>课程顾问</label>
                    <select id="paymentBelongConsultantUserUid" data-alias-table="main" class="form-control" data-title="课程顾问">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>缴费日期(起)</label>
                    <ctag:CalendarSelect id="paymentDate$from_search" title="缴费日期(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>缴费日期(止)</label>
                    <ctag:CalendarSelect id="paymentDate$to_search" title="缴费日期(止)"></ctag:CalendarSelect>
                </div>

                <ctag:Fold id="divOtherSearchCondition" name="其他查询条件" foldIconClass="fa-caret-up"></ctag:Fold>
                <div id="divOtherSearchCondition" class="separate-block clearfix" style="display: none;" data-force-fold="true">
                    <div class="col-md-2 form-group">
                        <label>收据序号</label>
                        <input id="receiptNumber" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="收据序号"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>付款人</label>
                        <input id="payer" data-camel-field="true" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="付款人"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>付款方式</label>
                        <select id="paymentMethodCd" data-alias-table="main" class="form-control" data-title="付款方式">
                            <option value="">请选择</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>费用科目</label>
                        <select id="itemTypeCd" data-alias-table="main" class="form-control" data-title="费用科目">
                            <option value="">请选择</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>收费情况</label>
                        <input id="paymentDetails" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="收费情况"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>收款人</label>
                        <input id="payeeUserUid" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="收款人"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>退款原因</label>
                        <input id="refundReason" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="退款原因"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>退款人</label>
                        <input id="refundUserName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="退款人"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>备注</label>
                        <input id="remark" data-camel-field="true" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="备注"/>
                    </div>
                </div>

                <div class="clear-both dashed-line">
                </div>
                <div>
                    <button id="btnSearch" type="button" class="btn btn-primary">
                        <i class="fa fa-search"></i>查询
                    </button>
                    <shiro:hasRole name="DataExport">
                        <button id="btnExport" type="button" class="btn btn-primary">
                            <i class="fa fa-cloud-download"></i>导出
                        </button>
                    </shiro:hasRole>
                    <button id="btnClear" type="button" class="btn btn-default">
                        <i class="fa fa-eraser"></i>清空
                    </button>
                    <ctag:PagerSettingIcon pageInstance="SysApp.Crm.PaymentListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Payment}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Payment}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Payment}"/>
            <%--自定义检索条件设置--%>
            <input type="hidden" id="jsonSearchSetting" value="${jsonSearchSetting_Payment}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_Payment}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function PaymentList_Page_Load() {
        SysApp.Crm.PaymentListIns = new SysApp.Crm.PaymentList();
        var instance = SysApp.Crm.PaymentListIns;

        instance.selfInstance = "SysApp.Crm.PaymentListIns";
        instance.controller = "${ctx}/crm/payment/";
        instance.clientID = "PaymentList";
        instance.tableName = "crm_payment";
        instance.entry = "${entry}";
        instance.pageSize = "${pageSize}";
        instance.inputInstance = SysApp.Crm.PaymentInputIns;
        instance.detailInstance = SysApp.Crm.PaymentDetailIns;

        instance.init();
    }

    $(function () {
        PaymentList_Page_Load();
    });
    //]]>
</script>
<%--POPUP编辑--%>
<%@ include file="/WEB-INF/views/crm/payment/PaymentPopupInput.jsp" %>
<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/crm/payment/PaymentPopupDetail.jsp" %>
<ctag:SearchSettingPopup pageInstance="SysApp.Crm.PaymentListIns" width="1200px"/>
<ctag:ColumnSettingPopup pageInstance="SysApp.Crm.PaymentListIns" width="1200px"/>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Crm.PaymentListIns"/>