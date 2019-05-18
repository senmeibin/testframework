// 定义数据输入画面JS类
SysApp.Crm.StudentDetail = Class.create();

SysApp.Crm.StudentDetail.prototype = Object.extend(new SysApp.Crm.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    /**********************以下方法为可选方法**********************/
    //详细/编辑POPUP控件表示回调函数
    showCallback: function () {

    },

    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "学员信息详细";
        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);

        //家庭住址
        var address = joinStudentAddress(this.inputEntity);
        this.setValue("homeAddress", address);

        if (this.isNotEmpty(this.inputEntity.relationshipTypeName)) {
            this.setValue("parentName", this.inputEntity.parentName + "[" + this.inputEntity.relationshipTypeName + "]")
        }

        if (this.isNotEmpty(this.inputEntity.genderName)) {
            this.setValue("name", this.inputEntity.name + "[" + this.inputEntity.genderName + "]")
        }

        if (this.isNotEmpty(this.inputEntity.studentAge)) {
            this.setValue("birthday", this.inputEntity.birthday.formatYM() + "[" + this.inputEntity.studentAge + "岁]")
        }

        if (this.isNotEmpty(this.inputEntity.cardTypeName)) {
            this.setValue("cardNumber", this.inputEntity.cardNumber + "[" + this.inputEntity.cardTypeName + "]")
        }
    }
});