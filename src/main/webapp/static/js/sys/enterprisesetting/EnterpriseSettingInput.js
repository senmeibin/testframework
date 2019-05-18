// 定义数据输入画面JS类
SysApp.Sys.EnterpriseSettingInput = Class.create();

SysApp.Sys.EnterpriseSettingInput.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    /**********************以下方法为可选方法**********************/
    //画面DOM初期化前处理
    initInputFormBefore: function () {
    },

    //设置企业名称
    setEnterpriseName: function (enterpriseName) {
        this.enterpriseName = enterpriseName;
    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "【" + this.enterpriseName + "】企业设置";
        this.$("ctlTitle").html(title);

        if (this.inputEntity.setting002 == 1) {
            this.switchOn("setting002");
        }
        else {
            this.switchOff("setting002");
        }

        if (this.inputEntity.setting004 == 1) {
            this.switchOn("setting004");
        }
        else {
            this.switchOff("setting004");
        }
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