// 定义数据输入画面JS类
SysApp.Sys.ApplicationInput = Class.create();

SysApp.Sys.ApplicationInput.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
    /**********************以下方法为可选方法**********************/
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        //应用维护设定
        this.bindEvent("mainteSetting", "click", this.mainteSetting.bind(this));
    },

    //应用维护设定
    mainteSetting: function () {
        if (this.isChecked("mainteSetting") == false) {
            this.setValue("mainteStartDate", "");
            this.setValue("mainteEndDate", "");

            $(this.getDom("mainteStartDate")).removeClass("required");
            $(this.getDom("mainteEndDate")).removeClass("required");
            $(this.getDom("mainteContent")).removeClass("required");
        }
        else {
            $(this.getDom("mainteStartDate")).addClass("required");
            $(this.getDom("mainteEndDate")).addClass("required");
            $(this.getDom("mainteContent")).addClass("required");
        }

        var thisObj = this;
        $(".app-mainte").each(function () {
            $(this).css("display", thisObj.isChecked("mainteSetting") ? "" : "none");
        });
    },

    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        if (this.isNotEmpty(this.inputEntity.mainteStartDate)) {
            this.getDom("mainteSetting").checked = true;
        }
        else {
            this.getDom("mainteSetting").checked = false;
        }
        this.mainteSetting();

        //系统登录的APP的场合，删除不可及部分信息编辑不可
        if (this.inputEntity.regSystem == 1) {
            this.displayDom("btnDelete", false);

            this.disabledDom("appCode", true);
            this.disabledDom("appName", true);
            this.disabledDom("validDate", true);

            this.displayDom("divMainteContainer", false);
        }
        var title = "应用模块编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "应用模块添加";
        }
        this.$("ctlTitle").html(title);

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        this.inputEntity.appCode = this.inputEntity.appCode.toUpperCase()
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