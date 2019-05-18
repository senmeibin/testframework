<%@ page language="java" pageEncoding="UTF-8" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="MultiTabView" class="form-horizontal ContactInnerDetail-MainContent">
    <ul id="MultiTabView_Tabs" class="tmv-customize">
        <div style="width: 530px;" id="MultiTabView_Tabs_LiContainer">
            <li id="MultiTabView_Tab0" class="active"><span>基础信息</span></li>
            <li id="MultiTabView_Tab1" class="enableout"><span>联系方式</span></li>
            <li id="MultiTabView_Tab2" class="enableout"><span>家庭信息</span></li>
            <em></em>
        </div>
    </ul>

    <div id="MultiTabView_Views" class="tmv-content">
        <div id="MultiTabView_View0">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">来往单位</label>
                <label class="col-lg-3 control-label content-label" id="companyName"></label>
                <label class="col-lg-1 control-label">姓名</label>
                <label class="col-lg-3 control-label content-label" id="name"></label>
                <label class="col-lg-1 control-label">生日</label>
                <label class="col-lg-3 control-label content-label" id="birthday" data-date="true"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">部门</label>
                <label class="col-lg-3 control-label content-label" id="department"></label>
                <label class="col-lg-1 control-label">职务</label>
                <label class="col-lg-3 control-label content-label" id="post"></label>
                <label class="col-lg-1 control-label">主要职责</label>
                <label class="col-lg-3 control-label content-label" id="responsibility"></label>
            </div>
        </div>
        <div id="MultiTabView_View1">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">电话</label>
                <label class="col-lg-3 control-label content-label" id="telephone"></label>
                <label class="col-lg-1 control-label">传真</label>
                <label class="col-lg-3 control-label content-label" id="fax"></label>
                <label class="col-lg-1 control-label">邮箱</label>
                <label class="col-lg-3 control-label content-label" id="email"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">手机</label>
                <label class="col-lg-3 control-label content-label" id="mobile"></label>
            </div>
        </div>
        <div id="MultiTabView_View2">
            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">家庭地址</label>
                <label class="col-lg-3 control-label content-label" id="homeAddress"></label>
                <label class="col-lg-1 control-label">家庭电话</label>
                <label class="col-lg-3 control-label content-label" id="homeTelephone"></label>
                <label class="col-lg-1 control-label">家庭邮编</label>
                <label class="col-lg-3 control-label content-label" id="homeZipcode"></label>
            </div>

            <div class="form-group form-inline">
                <label class="col-lg-1 control-label">爱好</label>
                <label class="col-lg-3 control-label content-label" id="interest"></label>
                <label class="col-lg-1 control-label">是否主要联系人</label>
                <label class="col-lg-3 control-label content-label" id="isMainContacts"></label>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/contact/ContactInnerDetail.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ContactInnerDetail_Page_Load() {
        SysApp.Sys.ContactInnerDetailIns = new SysApp.Sys.ContactInnerDetail();
        var instance = SysApp.Sys.ContactInnerDetailIns;
        instance.selfInstance = "SysApp.Sys.ContactInnerDetailIns";
        instance.controller = "${ctx}/sys/contact/";
        instance.clientID = "ContactInnerDetail";
        instance.isDetail = true;
        instance.isSyncDataPage = "${isSyncDataPage}";
        instance.init();
    }

    ContactInnerDetail_Page_Load();
    //]]>
</script>