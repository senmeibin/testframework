// 定义数据输入画面JS类
SysApp.Cmn.ProtocolDetail = Class.create();

SysApp.Cmn.ProtocolDetail.prototype = Object.extend(new SysApp.Cmn.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        this.popupPosition = "firstTop";
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
        var title = "协议详细";
        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);

        //设置协议内容
        this.$("contentIframe").contents().find("body").html(this.inputEntity.contents);

        //设置高度
        this.$("contentIframe").css("height", $(window).height() - 320);
    },

    getDetail: function (uid) {
        if (this.isEmpty(uid)) return;
        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "getDetail", this.getDetailCallback.bind(this), "uid=" + uid);
    },

    getDetailCallback: function (response, type) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }
        this.inputEntity = JsonUtility.Parse(response.responseText);

        this.initInputForm(this.inputEntity);

        this.show();
    }
});