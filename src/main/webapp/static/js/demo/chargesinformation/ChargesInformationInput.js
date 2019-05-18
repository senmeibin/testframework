// 定义数据输入画面JS类
SysApp.Demo.ChargesInformationInput = Class.create();

SysApp.Demo.ChargesInformationInput.prototype = Object.extend(new SysApp.Demo.CommonInput(), {
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
        var title = "企业收费信息编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "企业收费信息添加";
        }

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);

        //第一次收费日期
        this.disabledDom("firstChargeDate", true);
        //禁用下次收款日期
        this.disabledDom("nextReceiptDate", true);
        //加载企业信息
        SysApp.Demo.CompanyInformationInnerDetailIns.getDetail(this.inputEntity.companyUid);
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        this.inputEntity.chargesInformationDetailList = SysApp.Demo.ChargesInformationDetailInnerListIns.getDetailList();
    },

    //验证前处理
    validateFormBefore: function () {
        return true;
    },

    //验证后处理
    validateFormAfter: function () {
        var detailList = SysApp.Demo.ChargesInformationDetailInnerListIns.getDetailList();
        if (detailList.length == 0) {
            this.showErrorMessage("收费明细数据不能为空。");
            return false;
        }
        for (var i = 0, len = detailList.length; i < len; i++) {
            var row = detailList[i];
            if (this.isEmpty(row.chargesDate)) {
                this.showErrorMessage("第" + (i + 1) + "行的【收费时间】不能为空。");
                return false;
            }
            if (this.isEmpty(row.chargersName)) {
                this.showErrorMessage("第" + (i + 1) + "行的【收费人】不能为空。");
                return false;
            }
            if (this.isEmpty(row.chargesTypeCd)) {
                this.showErrorMessage("第" + (i + 1) + "行的【收费类型】不能为空。");
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