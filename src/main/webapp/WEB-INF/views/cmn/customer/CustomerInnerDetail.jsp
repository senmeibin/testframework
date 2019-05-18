<%@ page language="java" pageEncoding="UTF-8" %>

<div class="CustomerInnerDetail-MainContent">
    <ctag:Fold id="divCustomerInfo" name="客户信息"></ctag:Fold>
    <div id="divCustomerInfo" class="separate-block clearfix">
        <%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
        <div class="form-horizontal">
            <%--POPUP控件Body--%>
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">客户名称</label>
                <label class="col-lg-2 control-label content-label" id="customerName"></label>
                <label class="col-lg-1 control-label">客户简称</label>
                <label class="col-lg-2 control-label content-label" id="customerAbbreviation"></label>
                <label class="col-lg-1 control-label">开票抬头</label>
                <label class="col-lg-2 control-label content-label" id="invoiceTitle"></label>
                <label class="col-lg-1 control-label">所属行业</label>
                <label class="col-lg-2 control-label content-label" id="industryName"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">开票注册地址</label>
                <label class="col-lg-2 control-label content-label" id="invoiceRegisteredAddress"></label>
                <label class="col-lg-1 control-label">开票电话</label>
                <label class="col-lg-2 control-label content-label" id="invoiceTelephone"></label>
                <label class="col-lg-1 control-label">开票开户行</label>
                <label class="col-lg-2 control-label content-label" id="invoiceBank"></label>
                <label class="col-lg-1 control-label">开票帐号</label>
                <label class="col-lg-2 control-label content-label" id="invoiceAccountNo"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">开票税号</label>
                <label class="col-lg-2 control-label content-label" id="invoiceTaxNo"></label>
                <label class="col-lg-1 control-label">备注</label>
                <label class="col-lg-2 control-label content-label" id="remark" data-textarea="true"></label>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/cmn/customer/CustomerInnerDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CustomerInnerDetail_Page_Load() {
        SysApp.Cmn.CustomerInnerDetailIns = new SysApp.Cmn.CustomerInnerDetail();
        var instance = SysApp.Cmn.CustomerInnerDetailIns;
        instance.selfInstance = "SysApp.Cmn.CustomerInnerDetailIns";
        instance.clientID = "CustomerInnerDetail";
        instance.isDetail = true;
        instance.controller = "${ctx}/cmn/customer/";
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    CustomerInnerDetail_Page_Load();
    //]]>
</script>