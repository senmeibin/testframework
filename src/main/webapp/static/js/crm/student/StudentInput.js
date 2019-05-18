// 定义数据输入画面JS类
SysApp.Crm.StudentInput = Class.create();

SysApp.Crm.StudentInput.prototype = Object.extend(new SysApp.Crm.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        this.bindEvent("cardNumber", "blur", this.validCardNumber.bind(this));
    },

    /**********************以下方法为可选方法**********************/
    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //证件号码验证
    validCardNumber: function () {
        //身份证的场合
        if (this.getValue("cardTypeCd") == "01") {
            var result = this.validIDCard(this.getValue("cardNumber"));
            if (result == -1) {
                this.showErrorMessage("身份证号码不正确，请仔细核对后重新输入。")
                return false;
            }
            else {
                this.setValue("genderCd", result.gender);
                this.setValue("birthday", result.birthday.substring(0, 7));
                return true;
            }
        }
        return true;
    },

    //初始化日期控件
    initCalendar: function () {
        $(".datetime-picker", this.getMainContentClassName()).datetimepicker({
            format: "YYYY/MM",
            locale: "zh-cn",
            showTodayButton: true,
            useCurrent: false
        });
    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "学员信息编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "学员信息添加";
        }

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);

        if (this.isNotEmpty(this.inputEntity.birthday)) {
            this.setValue("birthday", this.inputEntity.birthday.formatYM());
            this.initCalendar();
        }
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        if (this.isNotEmpty(this.inputEntity.birthday)) {
            this.inputEntity.birthday = this.inputEntity.birthday + "/01";
        }
    },

    //验证前处理
    validateFormBefore: function () {
        if (!this.isMobile(this.getValue("mobile"))) {
            this.showErrorMessage("请输入11位有效手机号码(如：13661950001)。");
            return false;
        }
        return true;
    },

    //验证后处理
    validateFormAfter: function () {
        return this.validCardNumber();
    },

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "";
    }
});