// 定义数据输入画面JS类
SysApp.Crm.CampusClassInput = Class.create();

SysApp.Crm.CampusClassInput.prototype = Object.extend(new SysApp.Crm.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        this.bindEvent("campusUid", "change", this.createClassNumber.bind(this));
        this.bindEvent("classLevelCd", "change", this.createClassNumber.bind(this));
        this.bindEvent("classYear", "change", this.createClassNumber.bind(this));
        this.bindEvent("classMonth", "change", this.createClassNumber.bind(this));
        this.bindEvent("classSeq", "change", this.createClassNumber.bind(this));
    },

    /**********************以下方法为可选方法**********************/
    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    createClassNumber: function () {
        var campusUid = this.getValue("campusUid");
        var classLevelCd = this.getValue("classLevelCd");
        var classYear = this.getValue("classYear");
        var classMonth = this.getValue("classMonth");
        var classSeq = this.getValue("classSeq");

        if (this.isNotEmpty(campusUid) && this.isNotEmpty(classLevelCd) && this.isNotEmpty(classYear) && this.isNotEmpty(classMonth) && this.isNotEmpty(classSeq)) {
            var campusName = this.getText("campusUid");
            var code = campusName.split("(")[1];
            code = code.split(")")[0]
            this.inputEntity.classNumber = code + "-" + classLevelCd + "-"+ classYear + classMonth + "-" + classSeq;

            this.setValue("classNumber", this.inputEntity.classNumber);
        }
    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "校区班级编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "校区班级添加";
        }

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);

        //开班年月
        this.setValue("classYear", this.inputEntity.classYearMonth.substring(0, 4));
        this.setValue("classMonth", this.inputEntity.classYearMonth.substring(4));
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        this.inputEntity.classYearMonth = this.getValue("classYear") + this.getValue("classMonth");
        this.createClassNumber();
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