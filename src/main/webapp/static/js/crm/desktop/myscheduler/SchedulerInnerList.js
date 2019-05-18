//定义数据一览画面JS类
SysApp.Crm.SchedulerInnerList = Class.create();

SysApp.Crm.SchedulerInnerList.prototype = Object.extend(new SysApp.Crm.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.searchMethod = "searchScheduler";
        this.initCommonCallback();
        this.autoSearch = false;
        this.weekDay = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
        this.dayOffset = 0;
        this.dayScope = 5;
        this.bindEvent("prevDays", "click", this.addDayOffset.bind(this, -this.dayScope));
        this.bindEvent("nextDays", "click", this.addDayOffset.bind(this, this.dayScope));
        this.bindEvent("today", "click", this.addDayOffset.bind(this, 0));
    },

    //修改日期偏移量
    addDayOffset: function (dayScope) {
        if (dayScope == 0) {
            this.dayOffset = 0;
        } else {
            this.dayOffset += dayScope;
        }
        this.initScheduler();
    },

    //初始化日历
    initScheduler: function () {
        var thisObj = this;
        AjaxIns.sendPOST(this.controller + this.searchMethod, function (response) {
            var scheduler = response.responseJSON.content;
            var html = "";
            var classInfo = {
                0: "iscroll-remind-one",
                1: "iscroll-remind-two",
                2: "iscroll-remind-three",
                3: "iscroll-remind-four",
                4: "iscroll-remind-five"
            }

            Object.keys(scheduler).forEach(function (day, index) {
                var date = new Date(day);
                var todayClass = day.formatYMD() == new Date().formatYMD() ? "active" : "";
                html += "<li class=\"" + todayClass + "\">" +
                    "                <h4>" + day.formatMD() + "<span>（" + thisObj.weekDay[date.getDay()] + "）</span><em>" + scheduler[day].length + "</em></h4>" +
                    "                <div class=\"iscroll-wrapper " + classInfo[index] + " remind-list-height\">" +
                    "                    <div>" +
                    "                        <ul>";
                scheduler[day].forEach(function (schedule) {
                    var relationType = schedule.relationType;

                    //是否可删除
                    var canDelete = new Date(schedule.scheduleDate).getTime() > new Date().getTime();

                    canDelete = false;
                    var deleteHtml = canDelete ? "<button data-title=\"取消预约\" type=\"button\" class=\"btn btn-link schedule-red title-prompt-box\" style='float: right;padding: 3px 0 4px 10px;' data-placement=\"top\" " +
                        "onclick=\"SysApp.Crm.SchedulerInnerListIns.onClick_DeleteScheduler(\'" + schedule.relationUid + "\')\"><i class=\"fa fa-trash\"></i></button>" : "";

                    html += "<li class=\"title-prompt-box\" data-placement=\"top\" data-title=\"" + thisObj.enterToBr(schedule.contents) + "\">" +
                        "   <a href=\"#\" class=\"hover-underline\" onclick=\"SysApp.Crm.DesktopIns.getStudentInfo('" + schedule.studentUid + "')\">" +
                        "       <span class=\"title-prompt-box\" data-placement=\"top\" data-title=\"查看学员详情\">[" + schedule.relationType + "]" + schedule.studentName + "</span>" +
                        "   </a>" +
                        deleteHtml +
                        "   <i>" + schedule.scheduleDate.formatHM() + "</i>" +
                        "   <p>" + schedule.contents + "</p>" +
                        "</li>";
                });
                html += "                        </ul>" +
                    "                    </div>" +
                    "                </div>" +
                    "            </li>";
            });
            $(".myscheduler").html(html);

            thisObj.refreshIScroll();

            //Title提示框
            thisObj.titlePromptBox();

        }, "dayOffset=" + this.dayOffset + "&dayScope=" + this.dayScope);
    },

    //取消预约确认
    onClick_DeleteScheduler: function (uid) {
        this.showConfirm("确定要取消此预约吗？", this.cancelScheduler.bind(this, uid));
    },

    //取消预约
    cancelScheduler: function (uid) {
        var thisObj = this;
        thisObj.showProcessing();
        AjaxIns.sendPOST(this.controller + "delete", function (response) {
            if ($M.isAjaxFail(response.responseText)) {
                return;
            }
            var ajaxResult = JsonUtility.Parse(response.responseText);
            //处理成功
            if (ajaxResult.result == 1) {
                thisObj.hideMessage();
                thisObj.initScheduler();
            } else {
                thisObj.showErrorMessage("取消预约失败。")
            }
        }, "uid=" + uid);
    },

    //设置滚动
    refreshIScroll: function () {
        //我的日程滚动
        var iscrollRemindOne = new IScroll('.iscroll-remind-one', IScrollUtility.getGlobalOptions());
        var iscrollRemindTwo = new IScroll('.iscroll-remind-two', IScrollUtility.getGlobalOptions());
        var iscrollRemindThree = new IScroll('.iscroll-remind-three', IScrollUtility.getGlobalOptions());
        var iscrollRemindFour = new IScroll('.iscroll-remind-four', IScrollUtility.getGlobalOptions());
        var iscrollRemindFive = new IScroll('.iscroll-remind-five', IScrollUtility.getGlobalOptions());
        $(".remind-list-height").height($(window).height() - 213);
        iscrollRemindOne.refresh();
        iscrollRemindTwo.refresh();
        iscrollRemindThree.refresh();
        iscrollRemindFour.refresh();
        iscrollRemindFive.refresh();
    }
})