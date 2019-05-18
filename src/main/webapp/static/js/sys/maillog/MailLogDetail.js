// 定义数据输入画面JS类
SysApp.Sys.MailLogDetail = Class.create();

SysApp.Sys.MailLogDetail.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
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
        var title = "邮件日志详细";
        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);

        this.setValue("sendResult", this.inputEntity.sendResult == 1 ? "成功" : "《失败》");

        this.$("sendResult").removeClass("warning-red-cell");
        if (this.inputEntity.sendResult != 1) {
            this.$("sendResult").addClass("warning-red-cell");
        }

        //向iframe中写入邮件内容
        this.writeMailContent();
    },

    writeMailContent: function () {
        var iframe = this.getDom("iframeMessage");
        var doc = document.all ? iframe.contentWindow.document : iframe.contentDocument;
        doc.open();
        doc.write(this.inputEntity.message);
        doc.close();
    }
});