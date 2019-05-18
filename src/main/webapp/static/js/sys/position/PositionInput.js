//定义数据输入画面JS类
SysApp.Sys.PositionInput = Class.create();

SysApp.Sys.PositionInput.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
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
        //添加模式的场合
        if (this.isAddMode()) {
            this.displayDom("div_record_status", false);
            this.switchOn("recordStatus");
            this.getDom("ctlTitle").innerHTML = "职位添加";
        }
        else {
            this.displayDom("div_record_status", true);
            this.getDom("ctlTitle").innerHTML = "职位编辑";
            if (this.inputEntity.recordStatus == 1) {
                this.switchOn("recordStatus");
            }
            else {
                this.switchOff("recordStatus");
            }
        }
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        if (this.getDom("recordStatus").checked) {
            this.inputEntity.recordStatus = 1;
        }
        else {
            this.inputEntity.recordStatus = 8;
        }
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

