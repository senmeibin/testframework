// 定义数据输入画面JS类
SysApp.Cmn.MessageDetail = Class.create();

SysApp.Cmn.MessageDetail.prototype = Object.extend(new SysApp.Cmn.CommonInput(), {
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
        var title = "消息详细";
        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);

        //富文本消息
        if (this.inputEntity.isRichMessage == 1) {
            this.displayDom("richMessage", true);
            this.displayDom("normalMessage", false);
            //设置消息内容
            this.$("messageContentIframe").contents().find("body").html(this.inputEntity.messageContent);

            //设置高度
            this.$("messageContentIframe").css("height", $(window).height() - 220);
            this.$("messageTitleRich").html(this.inputEntity.messageTitle);
        }
        //普通消息
        else {
            this.displayDom("richMessage", false);
            this.displayDom("normalMessage", true);
        }
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