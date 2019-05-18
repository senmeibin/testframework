// 定义数据输入画面JS类
SysApp.Sys.ContactInput = Class.create();

SysApp.Sys.ContactInput.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
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
        var title = "联系人编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "联系人添加";
        }
        this.$("ctlTitle").html(title);
        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        if (this.isEditMode()) {
            SysApp.Sys.ContactInnerDetailIns.getDetail(this.inputEntity.uid);
        }
        else {
            if (this.isNotNull(SysApp.Sys.ContactInnerDetailIns)) {
                SysApp.Sys.ContactInnerDetailIns.hide();
            }
        }
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        if (this.isNotNull(this.companyUid)) {
            //设置外键关联UID
            this.inputEntity.companyUid = this.companyUid;
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