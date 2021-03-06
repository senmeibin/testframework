// 定义数据输入画面JS类
SysApp.Pms.ResearchInfoInput = Class.create();

SysApp.Pms.ResearchInfoInput.prototype = Object.extend(new SysApp.Pms.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    /**********************以下方法为可选方法**********************/
    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "市场调研编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "市场调研添加";

            //有效开始日期 默认为系统时间
            this.inputEntity.effectStartDate = new Date().formatYMD();
            this.setValue("effectStartDate", this.inputEntity.effectStartDate.formatYMD());
        }
        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {

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