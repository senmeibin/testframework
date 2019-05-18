// 定义数据输入画面JS类
SysApp.Crm.ActivityInput = Class.create();

SysApp.Crm.ActivityInput.prototype = Object.extend(new SysApp.Crm.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        this.initUEditor("contents", "100%", "300");

        setTimeout(this.initTreeCallback.bind(this), 500);
    },

    //树形加载回调函数
    initTreeCallback: function () {
        //招聘专员ComboTree回调【只需回调一次】
        if (this.isNotNull(this.campusUidsComboTree) && this.isNull(this.campusUidsComboTree.initCallbacked)) {
            this.campusUidsComboTree.initCallbacked = true;
            this.campusUidsComboTree.showMenu();
        }
    },

    /**********************以下方法为可选方法**********************/
    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "活动编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "活动添加";
        }

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        // this.inputEntity.contents = this.editor.contents.getContent();
    },

    //验证前处理
    validateFormBefore: function () {
        // if (this.isEmpty(this.editor.contents.getContent())) {
        //     this.showErrorMessage("请输入活动内容。");
        //     return false;
        // }

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