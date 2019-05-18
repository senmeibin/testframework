// 定义数据输入画面JS类
SysApp.Demo.ThirdPartyServiceContactInput = Class.create();

SysApp.Demo.ThirdPartyServiceContactInput.prototype = Object.extend(new SysApp.Demo.CommonInput(), {
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
        var title = "第三方服务联系编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "第三方服务联系添加";
        }

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        this.inputEntity.thirdPartyServiceContactsList = SysApp.Demo.ThirdPartyServiceContactsInnerListIns.getDetailList();
    },

    //验证前处理
    validateFormBefore: function () {
        return true;
    },

    //验证后处理
    validateFormAfter: function () {
        var detailList = SysApp.Demo.ThirdPartyServiceContactsInnerListIns.getDetailList();
        if (detailList.length == 0) {
            this.showErrorMessage("联系人不能为空。");
            return false;
        }
        for (var i = 0, len = detailList.length; i < len; i++) {
            var row = detailList[i];
            if (this.isEmpty(row.contactName)) {
                this.showErrorMessage("第" + (i + 1) + "行的【联系人】不能为空。");
                return false;
            }
        }
        return true;
    },

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "";
    }
});