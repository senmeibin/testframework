// 定义数据输入画面JS类
SysApp.Cmn.ProtocolInput = Class.create();

SysApp.Cmn.ProtocolInput.prototype = Object.extend(new SysApp.Cmn.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        //初始化审批意见的富文本
        if (this.getDom("contents") != null) {
            this.initUEditor("contents", "100%", "500");
        }
    },

    /**********************以下方法为可选方法**********************/
    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "协议编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "协议添加";
        }

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        //反馈信息
        this.inputEntity.contents = this.editor.contents.getContent();
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