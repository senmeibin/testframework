// 定义数据输入画面JS类
SysApp.Pms.ProjectDetail = Class.create();

SysApp.Pms.ProjectDetail.prototype = Object.extend(new SysApp.Pms.CommonInput(), {
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
        var title = "项目详细";
        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);
    }
});