// 定义数据输入画面JS类
SysApp.Cmn.FaqDetail = Class.create();

SysApp.Cmn.FaqDetail.prototype = Object.extend(new SysApp.Cmn.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    /**********************以下方法为可选方法**********************/
    //详细/编辑POPUP控件表示回调函数
    showCallback: function () {
        SysApp.Cmn.FileUploadListIns.setReadonlyMode();
    },

    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "常见问题详细";
        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);

        SysApp.Cmn.FileUploadListIns.setReferenceUids(this.inputEntity.uid);

        SysApp.Cmn.FileUploadListIns.hidePanelHeader();

        SysApp.Cmn.FileUploadListIns.createListAfter = this.createFileUploadListAfter.bind(this);

        this.setValue("answer", $M.enterToBr(this.inputEntity.answer));
    },

    createFileUploadListAfter: function () {
        SysApp.Cmn.FileUploadListIns.hidePanelBody();
    }
});