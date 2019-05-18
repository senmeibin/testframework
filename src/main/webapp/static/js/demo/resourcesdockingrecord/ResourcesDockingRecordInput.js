// 定义数据输入画面JS类
SysApp.Demo.ResourcesDockingRecordInput = Class.create();

SysApp.Demo.ResourcesDockingRecordInput.prototype = Object.extend(new SysApp.Demo.CommonInput(), {
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
        var title = "入孵企业资源对接记录编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "入孵企业资源对接记录添加";
        }

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);
        //加载企业信息
        SysApp.Demo.CompanyInformationInnerDetailIns.getDetail(this.inputEntity.companyUid);
        //对接机构修改时，清空联系人\
        $('select[id*=thirdPartyServiceUid]').on("change", this.clearThirdPartyServiceContactsUid.bind(this));
    },

    clearThirdPartyServiceContactsUid: function () {
        this.setValue("thirdPartyServiceContactsUid", "");
        this.setValue("thirdPartyServiceName", "");
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {

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