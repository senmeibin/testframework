<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/Common.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/desktop/myclass/ClassInnerList.js?${version}"></script>
<div class="row ClassInnerList-MainContent">
    <div style="padding-right: 10px;">
        <div class="desktop-search-condition clearfix">
            <form id="MainForm" class="form-horizontal" onsubmit="return false;">
                <div class="col-sm-1" style="padding-left: 0;width: 100px;">
                    <select id="campusClassUid" data-alias-table="main" class="form-control required" data-blank-text="-我的班级-" data-title="我的班级">
                        <option value="">-我的班级-</option>
                    </select>
                </div>
                <div class="col-xs-6" style="padding-left: 5px;">
                    <input type="text" class="form-control list-keyword-input" id="main.name,main.email,main.mobile,main.parentName,student_belong_consultant_user.userName" data-search-mode="OR" data-title="关键字"
                           placeholder="请输入关键字查询（如：学员姓名、家长姓名、手机号码等）" data-search-mode="$ignore_search" style="width:450px;float:left;">

                    <button type="button" class="btn btn-primary" id="btnCustomSearch">
                        <i class="fa fa-search"></i>
                    </button>
                </div>
            </form>
        </div>
        <!--班级列表区-->
        <div class="iscroll-wrapper desktop-inner-list" data-iscroll-id="iScrollClassInnerList">
            <div>
                <ul class="desktop-list desktop-list-active clear-both">
                    <div class="text-center" style="padding-top: 80px;"><img src="${staticContentsServer}/static/images/base/doing.jpg"></div>
                    <div id="divList">
                        我的班级功能开发中...
                    </div>
                </ul>
            </div>
        </div>
        <div id="divBottomPager">
        </div>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CampusClass}"/>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function ClassInnerList_Page_Load() {
        SysApp.Crm.ClassInnerListIns = new SysApp.Crm.ClassInnerList();
        var instance = SysApp.Crm.ClassInnerListIns;

        instance.selfInstance = "SysApp.Crm.ClassInnerListIns";
        instance.controller = "${ctx}/crm/campusclass/";
        instance.clientID = "ClassInnerList";
        instance.tableName = "rp_campus_class";
        instance.desktopInstance = "SysApp.Crm.DesktopIns";
        instance.init();
    }

    $(function () {
        ClassInnerList_Page_Load();
    });
    //]]>
</script>
