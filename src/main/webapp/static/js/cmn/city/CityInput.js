// 定义数据输入画面JS类
SysApp.Cmn.CityInput = Class.create();

SysApp.Cmn.CityInput.prototype = Object.extend(new SysApp.Cmn.CommonInput(), {
    /**********************以下方法为可选方法**********************/
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "城市信息编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "城市信息添加";
        }
        //详细模式的场合
        else if (this.isDetailMode()) {
            title = "城市信息详细";
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