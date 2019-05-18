//定义数据一览画面JS类
SysApp.Crm.Desktop = Class.create();

SysApp.Crm.Desktop.prototype = Object.extend(new SysApp.Crm.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        //打开学员实体集合
        this.studentInfoMap = {};

        this.bindBeforeUnload();

        //根据手机号快速检索
        this.bindEvent("btnMobileSearch", 'click', this.getStudentInfoForMobileSearch.bind(this));

        //我的学员tab点击事件绑定
        this.bindEvent("myStudentTab", "click", this.onClick_MyStudentTab.bind(this));
        //我的跟进tab切换事件绑定
        this.bindEvent("myFollowupTab", 'click', this.onClick_MyFollowupTab.bind(this));
        //我的预约tab切换事件绑定
        this.bindEvent("myReservationTab", 'click', this.onClick_MyReservationTab.bind(this));
        //我的任务tab切换事件绑定
        this.bindEvent("myTaskTab", 'click', this.onClick_MyTaskTab.bind(this));
        //我的班级tab切换事件绑定
        this.bindEvent("myClassTab", 'click', this.onClick_MyClassTab.bind(this));
        //课程表tab切换事件绑定
        this.bindEvent("myCourseScheduleTab", 'click', this.onClick_MyCourseScheduleTab.bind(this));

        //我的日程tab切换事件绑定
        this.bindEvent("mySchedulerTab", 'click', this.onClick_SchedulerTab.bind(this));
        //工作手册tab切换事件绑定
        this.bindEvent("workManualTab", 'click', this.onClick_WorkManualTab.bind(this));

        //从cookie中读取学员详情
        this.initStudentDetailByCookie();

        //iScroll初期化
        this.initIScroll();

        //初始化仪表盘
        this.initDashboard();
    },

    //取得当前学员Tab的学员信息
    getCurrentStudent: function () {
        return this.studentInfoMap[this.currentStudentUid];
    },

    //初始化仪表盘
    initDashboard: function () {
        //鼠标经过展开
        $(".right-side-fold,.DashboardInnerList-MainContent").mouseenter(function () {
            $(".DashboardInnerList-MainContent").show();
        });
        //鼠标离开隐藏
        $(".right-side-fold,.DashboardInnerList-MainContent").mouseleave(function () {
            $(".DashboardInnerList-MainContent").hide();
        });
    },

    //我的学员tab点击事件
    onClick_MyStudentTab: function () {
        var hasData = SysApp.Crm.StudentInnerListIns.datagrid.hasData();
        //列表有数据，刷新当前页，列表无数据，重新检索
        SysApp.Crm.StudentInnerListIns.getList(!hasData);
    },

    //我的跟进tab点击事件
    onClick_MyFollowupTab: function () {
        var hasData = SysApp.Crm.FollowupInnerListIns.datagrid.hasData();
        //列表有数据，刷新当前页，列表无数据，重新检索
        SysApp.Crm.FollowupInnerListIns.getList(!hasData);
    },

    //我的预约tab点击事件
    onClick_MyReservationTab: function () {
        var hasData = SysApp.Crm.ReservationInnerListIns.datagrid.hasData();
        //列表有数据，刷新当前页，列表无数据，重新检索
        SysApp.Crm.ReservationInnerListIns.getList(!hasData);
    },

    //我的任务tab点击事件
    onClick_MyTaskTab: function () {
        var hasData = SysApp.Crm.TaskInnerListIns.datagrid.hasData();
        //列表有数据，刷新当前页，列表无数据，重新检索
        SysApp.Crm.TaskInnerListIns.getList(!hasData);
    },

    //我的班级tab点击事件
    onClick_MyClassTab: function () {
        var hasData = SysApp.Crm.ClassInnerListIns.datagrid.hasData();
        //列表有数据，刷新当前页，列表无数据，重新检索
        SysApp.Crm.ClassInnerListIns.getList(!hasData);
    },

    //课程表tab点击事件
    onClick_MyCourseScheduleTab: function () {
        var hasData = SysApp.Crm.CourseScheduleInnerListIns.datagrid.hasData();
        //列表有数据，刷新当前页，列表无数据，重新检索
        SysApp.Crm.CourseScheduleInnerListIns.getList(!hasData);
    },

    //我的日程tab点击事件
    onClick_SchedulerTab: function () {
        SysApp.Crm.SchedulerInnerListIns.initScheduler();
    },

    //工作手册tab点击事件
    onClick_WorkManualTab: function () {
        SysApp.Crm.WorkManualInnerListIns.getList(true);
    },

    //根据学员UID取得学员相关的所有信息
    getStudentInfo: function (studentUid) {
        //已经存在学员详情场合
        if (this.isNotEmpty(this.studentInfoMap[studentUid])) {
            this.setStudentInfo(this.studentInfoMap[studentUid]);
            return false;
        }

        //最大显示学员详情
        if (Object.keys(this.studentInfoMap).length >= window.studentOpenMax) {
            this.showErrorMessage("最多开启" + window.studentOpenMax + "个学员详情标签，请关闭多余标签后重试。");
            return false;
        }

        var params = "studentUid=" + studentUid;

        //Ajax处理前显示提示画面
        this.showProcessing();

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.studentController + "getStudentInfo", this.getStudentInfoCallback.bind(this), params);
    },

    //根据手机号获取学员详情
    getStudentInfoForMobileSearch: function () {
        //最大显示学员详情
        if (Object.keys(this.studentInfoMap).length >= window.studentOpenMax) {
            this.showErrorMessage("最多开启" + window.studentOpenMax + "个学员详情标签，请关闭多余标签后重试。");
            return false;
        }

        var mobile = this.getValue("desktopSearchMobile");

        //手机号码校验通过场景
        if (!this.isMobile(mobile)) {
            this.showErrorMessage("请输入有效的手机号码。");
            return false;
        }

        //遍历已打开学员
        for (var key in this.studentInfoMap) {
            //学员已存在场合
            if (this.studentInfoMap[key].mobile == mobile) {
                this.setStudentInfo(this.studentInfoMap[key]);
                return false;
            }
        }

        var params = "mobile=" + mobile;

        //Ajax处理前显示提示画面
        this.showProcessing();

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.studentController + "getStudentInfoByMobile", this.getStudentInfoCallback.bind(this), params);
    },

    //学员详情回调
    getStudentInfoCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);
        console.log(ajaxResult.message);

        //处理成功的场合、显示处理结果消息后刷新一览画面
        if (ajaxResult.result > 0) {
            this.hideMessage();
            //学员信息
            var student = ajaxResult.content;
            this.setStudentInfo(student);
        }
        else {
            this.showErrorMessage(ajaxResult.message);
        }
    },

    //设置学员全部详情
    setStudentInfo: function (student) {
        this.currentStudentUid = student.uid;

        //设置详情标题
        this.setStudentInfoTab(student);

        //创建基本信息
        SysApp.Crm.StudentBaseInnerDetailIns.createStudentDetail(student);

        //创建跟进记录
        SysApp.Crm.StudentFollowupInnerListIns.createFollowupList(student);

        //创建预约记录
        SysApp.Crm.StudentReservationInnerListIns.createReservationList(student);

        //创建报名记录
        SysApp.Crm.StudentRegistrationInnerListIns.createRegistrationList(student);

        //创建缴费记录
        SysApp.Crm.StudentPaymentInnerListIns.createPaymentList(student);
    },

    //设置学员详情标题
    setStudentInfoTab: function (student) {
        //已经在显示tab 场合
        if (this.isNotEmpty(this.studentInfoMap[student.uid])) {
            //tab表示名称与学员姓名保持一致
            $(".tab-name-span", "#studentInfoUid_" + student.uid).html(student.name);

            $(".desktop-tab").find("li").removeClass("active");
            $(".desktop-content").children("div").removeClass("active");

            //判断已存在的标签存放的位置
            if (this.$("studentInfoUid_" + student.uid).parents("ul").hasClass("desktop-tab")) {
                //存放tabs内，直接切换
                this.$("studentInfoUid_" + student.uid).addClass("active");
            }
            else {
                //存放在下拉里面
                var existHtmlCopy = this.$("studentInfoUid_" + student.uid).html();
                //从下拉里面 -- 移到 -- tab里面第一个位置
                this.$("studentInfoUid_" + student.uid).remove();
                $(".desktop-tab li:eq(7)").after("<li class='active' id='studentInfoUid_" + student.uid + "'>" + existHtmlCopy + "</li>");
                //tab里面最后一个标签 -- 移到 -- 下拉里面第一个位置
                $(".desktop-tab-select ul").prepend("<li id='" + $(".desktop-tab li:last").attr("id") + "'>" + $(".desktop-tab li:last").html() + "</li>");
                $(".desktop-tab li:last").remove();
            }

            this.$("studentInfoContainer").addClass("active");
            //进入操作台-仪表盘隐藏(列表进入、学员已经存在)
            this.$("divDashboardContainer").hide();
            return false;
        }
        else {
            this.studentInfoMap[student.uid] = student;
            $.cookie(window.loginUserUid + "_studentInfoOpenUids", this.getStudentInfoUidStr());
        }

        var studentInfoTabHtml = "<li class='active' id='studentInfoUid_" + student.uid + "'>" +
            "   <a href='#studentInfoContainer' data-toggle='tab' onclick='" + this.selfInstance + ".selectStudentDetailTab(this,\"" + student.uid + "\") ;return false;' >" +
            "       <span class='tab-name-span'>" + student.name + "</span>" +
            "   </a>" +
            "   <em class='fa fa-times-circle' onclick='" + this.selfInstance + ".closeStudentDetailTab(this,\"" + student.uid + "\") ;return false;'></em>" +
            "</li>";

        //去掉所有标签的当前状态
        $(".desktop-tab").find("li").removeClass("active");
        //当前已有-标签数 与 最多可放-标签数 --- 相等时
        var desktopTab = $(".desktop-tab");
        var tabSelect = $(".desktop-tab-select");
        if (desktopTab.find("li").length - 5 == parseInt((desktopTab.width() - 650) / 85)) {
            //下拉按钮显示
            tabSelect.show();
            //最后一个标签，移动到下拉里面
            tabSelect.children("ul").prepend("<li id='" + desktopTab.find("li:last").attr("id") + "'>" + desktopTab.find("li:last").html() + "</li>");
            desktopTab.find("li:last").remove();
            //更新-数量-到下拉按钮
            tabSelect.children("button").find("em").text(tabSelect.find("li").length);
        }
        //添加新标签到最前面
        $(".desktop-tab li:eq(7)").after(studentInfoTabHtml);

        $(".desktop-content").children("div").removeClass("active");
        this.$("studentInfoContainer").addClass("active");
        //进入操作台-仪表盘隐藏（列表进入、新建学员）
        this.$("divDashboardContainer").hide()
    },

    //切换学员详情tab
    selectStudentDetailTab: function (thisObj, studentUid) {
        //标签为当前显示学员场合 不重新加载
        if ($(thisObj).parents("li").hasClass("active")) {
            return false;
        }

        //当前所在是tab，还是下拉
        var currentPosition = this.$(thisObj).parents("div.dropdown").hasClass("desktop-tab-select");
        if (currentPosition) {
            //当前标签移到tab内
            $(".desktop-tab li:eq(7)").after("<li id='" + $(thisObj).parents("li").attr("id") + "'>" + $(thisObj).parents("li").html() + "</li>");
            $(thisObj).parents("li").remove();
            //tab最后一个标签 --- 移到 --- 下拉第一个位置
            $(".desktop-tab-select ul").prepend("<li id='" + $(".desktop-tab li:last").attr("id") + "'>" + $(".desktop-tab li:last").html() + "</li>");
            $(".desktop-tab li:last").remove();
        }

        this.setStudentInfo(this.studentInfoMap[studentUid]);
        //进入操作台-仪表盘隐藏（通过tabs切换、进入学员）
        this.$("divDashboardContainer").hide();
    },

    //关闭学员详情tab
    closeStudentDetailTab: function (thisObj, studentUid) {
        //当前学员详情页打开场合
        if (this.$("studentInfoUid_" + studentUid).hasClass("active")) {
            $(".desktop-tab").find("li").removeClass("active");
            $(".desktop-content").children("div").removeClass("active");
            //显示 我的跟进
            this.$("myFollowupTab").addClass("active");
            this.$("myFollowupContainer").addClass("active");
            //重置当前学员UID
            this.currentStudentUid = "";
            //关闭的标签，处于当前状态，仪表盘复原
            this.$("divDashboardContainer").show();
        }

        //当前所在是tab，还是下拉
        var currentPosition = this.$(thisObj).parents("ul").hasClass("desktop-tab");
        //移除标签tab
        this.$("studentInfoUid_" + studentUid).remove();
        var desktopTab = $(".desktop-tab");
        var tabSelect = $(".desktop-tab-select");
        //下拉菜单内，存在标签
        if (tabSelect.find("li").length >= 1) {
            if (currentPosition) {
                //下拉菜单内，第一个标签，移动到tab标签最后一个
                desktopTab.find("li:last").after("<li id='" + tabSelect.find("li:first").attr("id") + "'>" + tabSelect.find("li:first").html() + "</li>");
                tabSelect.find("li:first").remove();
            } else {
                //删除下拉内的标签
                this.$(thisObj).parent("li").remove();
            }

            //更新-数量-到下拉按钮
            tabSelect.children("button").find("em").text(tabSelect.find("li").length);
        }

        //如果下拉菜单内，只有一个标签，隐藏下拉按钮
        if (tabSelect.find("li").length == 0) {
            tabSelect.hide();
        }

        //移除学员信息
        delete this.studentInfoMap[studentUid];
        $.cookie(window.loginUserUid + "_studentInfoOpenUids", this.getStudentInfoUidStr());
    },

    //从cookie中读取学员详情
    initStudentDetailByCookie: function () {
        if (this.isNotEmpty($.cookie(window.loginUserUid + "_studentInfoOpenUids"))) {
            if (confirm("您上次离开工作台时有未关闭的学员，是否需要默认打开这些学员信息？")) {
                this.initLastTimeStudentDetail();
            }
            else {
                $.cookie(window.loginUserUid + "_studentInfoOpenUids", "");
            }
        }
    },

    //加载上次cookie中的学员详情
    initLastTimeStudentDetail: function () {
        var studentUids = $.cookie(window.loginUserUid + "_studentInfoOpenUids").split(",");
        for (var i = 0; i < studentUids.length; i++) {
            this.getStudentInfo(studentUids[i]);
        }
    },

    //获取已打开学员详情的学员UID
    getStudentInfoUidStr: function () {
        var studentUids = "";
        //遍历已打开学员
        for (var key in this.studentInfoMap) {
            studentUids += key + ",";
        }
        return studentUids.substr(0, studentUids.length - 1);
    },

    //绑定beforeunload事件
    bindBeforeUnload: function () {
        $(window).bind('beforeunload', function () {
            return "您将离开工作台，确定离开此页面吗？";
        });
    }
});