// 定义数据输入画面JS类
SysApp.Demo.TutorInput = Class.create();

SysApp.Demo.TutorInput.prototype = Object.extend(new SysApp.Demo.CommonInput(), {
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
        var title = "导师表编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "导师表添加";
        }

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);

        //初始弹出页面的附件控件
        this.initPopupInputFileUpload();

        $('select[id*=baseUid]', this.getMainContentClassName()).trigger("change");

        //处理联系方式码（隐藏后四位）
        this.hiddenContactInfoSuffix();
        //处理联系方式事件
        this.bindEvent("contactInfo", "focus", this.showContactInfo.bind(this));
        this.bindEvent("contactInfo", "blur", this.hiddenContactInfoSuffix.bind(this));
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        //联系方式
        this.inputEntity.contactInfo = this.$("contactInfo").attr("data-contactInfo");
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
    },

    //隐藏联系方式后4位
    hiddenContactInfoSuffix: function () {
        var contactInfo = this.getValue("contactInfo");
        if (contactInfo.length > 4) {
            this.$("contactInfo").val(contactInfo.substr(0, contactInfo.length - 4) + "****");
        }
        this.$("contactInfo").attr("data-contactInfo", contactInfo);
    },

    //显示全部联系方式
    showContactInfo: function () {
        this.setValue("contactInfo", this.$("contactInfo").attr("data-contactInfo"));
    }
});