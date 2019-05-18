// 定义数据输入画面JS类
SysApp.Crm.ReservationDetail = Class.create();

SysApp.Crm.ReservationDetail.prototype = Object.extend(new SysApp.Crm.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    /**********************以下方法为可选方法**********************/
    //详细/编辑POPUP控件表示回调函数
    showCallback: function () {

    },

    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "预约记录详细";
        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);

        this.setValue("isVisited", "未到访");
        this.setValue("isRegistration", "未报名");
        if (this.inputEntity.isVisited == 1) {
            this.setValue("isVisited", "未到访");
        }
        if (this.inputEntity.isRegistration == 1) {
            this.setValue("isRegistration", "已报名");
        }
    }
});