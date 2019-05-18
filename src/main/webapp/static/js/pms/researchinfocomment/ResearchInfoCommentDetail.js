// 定义数据输入画面JS类
SysApp.Pms.ResearchInfoCommentDetail = Class.create();

SysApp.Pms.ResearchInfoCommentDetail.prototype = Object.extend(new SysApp.Pms.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    //设置市场调研信息
    setResearchInfo: function (researchInfo) {
        this.setValue("researchInfoName", researchInfo.researchInfoName);
        this.setValue("researchInfoTitle", researchInfo.title);
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
        var title = "市场调研评论详细";
        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);
    }
});