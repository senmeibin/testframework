// 定义数据输入画面JS类
SysApp.Crm.CampusInput = Class.create();

SysApp.Crm.CampusInput.prototype = Object.extend(new SysApp.Crm.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        this.initUEditor("introduction", "100%", "200");

        this.bindEvent("deptUid", "change", this.changeDept.bind(this));

        setTimeout(this.initTreeCallback.bind(this), 500);
    },

    //树形加载回调函数
    initTreeCallback: function () {
        //课程顾问ComboTree回调【只需回调一次】
        if (this.isNotNull(this.consultantUserUidsComboTree) && this.isNull(this.consultantUserUidsComboTree.initCallbacked)) {
            this.consultantUserUidsComboTree.initCallbacked = true;
            this.consultantUserUidsComboTree.showMenu();
        }
    },

    changeDept: function () {
        this.setValue("name", this.getText("deptUid"));
    },

    /**********************以下方法为可选方法**********************/
    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "校区编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "校区添加";
        }
        else {
            this.disabledDom("deptUid", true);
        }

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        this.inputEntity.introduction = this.editor.introduction.getContent();
    },

    //验证前处理
    validateFormBefore: function () {
        if (this.isEmpty(this.editor.introduction.getContent())) {
            this.showErrorMessage("请输入校区介绍。");
            return false;
        }

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