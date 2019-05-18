// 定义数据输入画面JS类
SysApp.Demo.CompanyInstitutionalInput = Class.create();

SysApp.Demo.CompanyInstitutionalInput.prototype = Object.extend(new SysApp.Demo.CompanyExtendedCommonInput(), {
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
        var title = "机构信息编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "机构信息添加";
        }

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);

        this.showCompanyInformation();
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        var list = [];
        var detailList = SysApp.Demo.CompanyInstitutionalDetailInnerListIns.getDetailList();
        for (var i = 0, len = detailList.length; i < len; i++) {
            var row = detailList[i];
            if (this.isNotEmpty(row.name)) {
                list.push(row);
            }
        }
        this.inputEntity.companyInstitutionalDetailList = list;
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