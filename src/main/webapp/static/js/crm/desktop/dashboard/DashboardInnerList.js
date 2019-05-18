//定义数据一览画面JS类
SysApp.Crm.DashboardInnerList = Class.create();

SysApp.Crm.DashboardInnerList.prototype = Object.extend(new SysApp.Crm.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.autoSearch = false;

        this.initCommonCallback();

        //初始化仪表盘
        this.initDashboard();

        //每隔X分钟刷新
        setInterval(this.initDashboard.bind(this), window.desktopRefreshTime);
    },

    //初始化仪表盘
    initDashboard: function () {
        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "getDashboardData", this.getDashboardDataCallback.bind(this));
    },

    //仪表盘数据回调函数
    getDashboardDataCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);
        var dashboard = ajaxResult.content;
        //学员总数
        $(".resumeCount").html(dashboard.resumeCount);
        //等于0场合移除点击事件
        if (dashboard.resumeCount == 0) {
            $(".resumeCount").removeAttr("onclick");
        }
        //今日学员新增量
        $(".dayResumeCount").html(dashboard.dayResumeCount);
        //等于0场合移除点击事件
        if (dashboard.dayResumeCount == 0) {
            $(".dayResumeCount").removeAttr("onclick");
        }

        //今日推荐
        $(".dayRecommendCount").html(dashboard.dayRecommendCount);
        //等于0场合移除点击事件
        if (dashboard.dayRecommendCount == 0) {
            $(".dayRecommendCount").removeAttr("onclick");
        }

        //今日到访
        $(".dayVisitCount").html(dashboard.dayVisitCount);
        //等于0场合移除点击事件
        if (dashboard.dayVisitCount == 0) {
            $(".dayVisitCount").removeAttr("onclick");
        }

        //今日Offer
        $(".dayOfferCount").html(dashboard.dayOfferCount);
        if (dashboard.dayOfferCount == 0) {
            $(".dayOfferCount").removeAttr("onclick");
        }

        //今日入职
        $(".recommendEntryCount").html(dashboard.recommendEntryCount);
        if (dashboard.recommendEntryCount == 0) {
            $(".recommendEntryCount").removeAttr("onclick");
        }

        //待处理学员（待电话沟通）
        $(".waitCount").html(dashboard.waitCount);
        //等于0场合移除点击事件
        if (dashboard.waitCount == 0) {
            $(".waitCount").removeAttr("onclick");
        }

        //未加微信（总学员数量-已加微信数量）
        $(".noWechatCount").html(dashboard.resumeCount - dashboard.wechatCount);
        //等于0场合移除点击事件
        if (dashboard.noWechatCount == 0) {
            $(".noWechatCount").removeAttr("onclick");
        }

        //本月新增的学员
        $(".monthResumeCount").html(dashboard.monthResumeCount);
        //等于0场合移除点击事件
        if (dashboard.monthResumeCount == 0) {
            $(".monthResumeCount").removeAttr("onclick");
        }

        //总推荐量
        $(".recommendCount").html(dashboard.recommendCount);
        //等于0场合移除点击事件
        if (dashboard.recommendCount == 0) {
            $(".recommendCount").removeAttr("onclick");
        }
        //总到访
        $(".visitCount").html(dashboard.visitCount);
        //等于0场合移除点击事件
        if (dashboard.visitCount == 0) {
            $(".visitCount").removeAttr("onclick");
        }
        //总Offer
        $(".offerCount").html(dashboard.offerCount);
        //等于0场合移除点击事件
        if (dashboard.offerCount == 0) {
            $(".offerCount").removeAttr("onclick");
        }
        //总入职
        $(".monthEntryCount").html(dashboard.monthEntryCount);
        //等于0场合移除点击事件
        if (dashboard.monthEntryCount == 0) {
            $(".monthEntryCount").removeAttr("onclick");
        }
    },

    //
    getResumeList: function (type, name) {
        //我的学员移除active样式
        this.$("myStudentTab").removeClass("active");
        this.$("myStudentContainer").removeClass("active");

        //我的跟进添加active样式
        this.$("myFollowupTab").addClass("active");
        this.$("myFollowupContainer").addClass("active");

        //我的班级移除active样式
        this.$("myClassTab").removeClass("active");
        this.$("myClassContainer").removeClass("active");

        //香草活跃用户tab移除active样式
        this.$("xcActiveTab").removeClass("active");
        this.$("active-users").removeClass("active");

        //总学员、本月新增学员、今日新增学员、未加微信、未电话沟通 今日到访 总到访 跳转我的跟进-人才库tab
        if (type == "monthResumeCount" || type == "resumeCount" || type == "dayResumeCount" || type == "noWechatCount" || type == "waitCount" || type == "dayVisitCount" || type == "visitCount") {
            //人才库添加active样式
            this.$("resumeBelongTab").addClass("active");
            this.$("talent-pool").addClass("active");
            //已推荐tab移除active样式
            this.$("recommendResumeTab").removeClass("active");
            this.$("already-suggested").removeClass("active");

            $(".student-belong-dashboard-search").html("<span>" + name + "-条件筛选<i class='fa fa-close' onclick='SysApp.Crm.ResumeBelongInnerListIns.clearDashboard()'></i></span>");
            //显示人才库画面
            SysApp.Crm.ResumeBelongInnerListIns.showDashboardResume(type);
        }
        else {
            //人才库添加active样式
            this.$("resumeBelongTab").removeClass("active");
            this.$("talent-pool").removeClass("active");
            //已推荐tab移除active样式
            this.$("recommendResumeTab").addClass("active");
            this.$("already-suggested").addClass("active");

            $(".student-recommend-dashboard-search").html("<span>" + name + "-条件筛选<i class='fa fa-close'onclick='SysApp.Crm.ResumeRecommendInnerListIns.clearDashboard()'></i></span>");
            //显示已推荐画面
            SysApp.Crm.ResumeRecommendInnerListIns.showDashboardResume(type);
        }
    }
});