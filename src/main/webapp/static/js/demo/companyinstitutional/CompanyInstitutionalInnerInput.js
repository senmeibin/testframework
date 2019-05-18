// 定义数据输入画面JS类
SysApp.Demo.CompanyInstitutionalInnerInput = Class.create();

SysApp.Demo.CompanyInstitutionalInnerInput.prototype = Object.extend(new SysApp.Demo.CompanyExtendedCommonInnerInput(), {
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

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "";
    },

    getInnerListInstance: function () {
        return SysApp.Demo.CompanyInstitutionalInnerListIns;
    },

    //显示编辑form
    show: function () {
        //显示编辑form
        this.displayDom(this.getClientIdDivId(), true);
        //显示删除
        this.displayDom(this.dom.btnDelete, this.isNotEmpty(this.inputEntity.uid));
        //清空form表单
        this.clearForm();
        this.initInputForm(this.inputEntity);
        SysApp.Demo.CompanyInstitutionalDetailInnerListIns.setValue("institutionalUid", this.isEmpty(this.inputEntity.uid) ? -99999999 : this.inputEntity.uid);
        SysApp.Demo.CompanyInstitutionalDetailInnerListIns.getList(true);
    }
});