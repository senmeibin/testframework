// 定义数据输入画面JS类
SysApp.Sys.SyncEntityInput = Class.create();

SysApp.Sys.SyncEntityInput.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
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
        var title = "同步对象编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "同步对象添加";
        }

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);

        //同步对象实体类 不能进行修改
        if (this.inputEntity.entityClassPath == "com.bpms.sys.entity.ext.SyncEntityExt") {
            this.disabledDom("entityClassPath", true);
            this.displayDom("btnDelete", false);
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