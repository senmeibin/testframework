// 定义数据输入画面JS类
SysApp.Demo.CompanyIntellectualInnerInput = Class.create();

SysApp.Demo.CompanyIntellectualInnerInput.prototype = Object.extend(new SysApp.Demo.CompanyExtendedCommonInnerInput(), {
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

    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {

    },

    //验证前处理
    validateFormBefore: function () {
        return true;
    },

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "";
    },

    getInnerListInstance: function () {
        return SysApp.Demo.CompanyIntellectualInnerListIns;
    }
});