// 定义数据输入画面JS类
SysApp.Demo.WeeklyReportInput = Class.create();

SysApp.Demo.WeeklyReportInput.prototype = Object.extend(new SysApp.Demo.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    //日历控件初期化
    initCalendar: function () {
        var thisObj = this;
        try {
            $(".datetime-picker", this.getMainContentClassName()).datetimepicker({
                format: thisObj.calendarFormat,
                locale: "zh-cn",
                showTodayButton: false,
                useCurrent: false,
                calendarWeeks: true
            });

            $(".datetime-picker", this.getMainContentClassName()).on('dp.change', function (e) {
                var value = thisObj.getValue("fillTime");
                var firstDate = moment(value, "YYYY/MM/DD").day(1).format("YYYY/MM/DD");
                var lastDate = moment(value, "YYYY/MM/DD").day(7).format("YYYY/MM/DD");
                thisObj.inputEntity.fillTimeStart = firstDate;
                thisObj.inputEntity.fillTimeEnd = lastDate;
                thisObj.setValue("fillTime", firstDate + " 至 " + lastDate);
            });

            //默认填写时间
            this.setValue("fillTime", this.inputEntity.fillTimeStart + " 至 " + this.inputEntity.fillTimeEnd);
        } catch (e) {
        }
    },

    /**********************以下方法为可选方法**********************/
    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "周报编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "周报添加";
        }

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);

        //不是当前用户的数据将不能操作
        if (this.isEditMode() && this.inputEntity.insertUser != this.currentUser) {
            this.disableInput(this.getMainContentClassName(), true);
            //隐藏操作按钮
            this.displayDom("btnSave", false);
            this.displayDom("btnDelete", false);
        }
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        this.inputEntity.weeklyReportDetailList = SysApp.Demo.WeeklyReportDetailInnerListIns.getDetailList();
    },

    //验证前处理
    validateFormBefore: function () {
        return true;
    },

    //验证后处理
    validateFormAfter: function () {
        return true;
    },

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "";
    }
});