// 定义数据输入画面JS类
SysApp.Sys.CompanyInput = Class.create();

SysApp.Sys.CompanyInput.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
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
        var title = "来往单位编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "来往单位添加";
        }
        this.$("ctlTitle").html(title);

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        var detailList = SysApp.Sys.ContactBatchInputIns.getDetailList();

        this.inputEntity.contactList = detailList;
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
        var detailList = SysApp.Sys.ContactBatchInputIns.getDetailList();
        if (this.isNull(detailList)) {
            return "";
        }
        return "detailList=" + JsonUtility.ToJSON(detailList);
    }
});