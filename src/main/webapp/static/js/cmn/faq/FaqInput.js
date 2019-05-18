// 定义数据输入画面JS类
SysApp.Cmn.FaqInput = Class.create();

SysApp.Cmn.FaqInput.prototype = Object.extend(new SysApp.Cmn.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        //初始化百度编辑器
        this.initUEditor("questionDesc");
        this.initUEditor("answerDesc");

        var thisObj = this;
        $(".details-div").click(function () {
            $(this).hide();
            $(this).next().show();
            thisObj.editor[$(this).attr("domId")].setHeight(250);
        });
    },

    /**********************以下方法为可选方法**********************/
    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "常见问题编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "常见问题添加";

            this.setValue("appCode", this.appCode);
        }

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);
        //填写了详细说明，则显示富文本
        var thisObj = this;
        setTimeout(function () {
            if (thisObj.isEditMode() && !thisObj.isEmpty(thisObj.inputEntity.questionDesc)) {
                $(".details-div[domId='questionDesc']").click();
            }
            if (thisObj.isEditMode() && !thisObj.isEmpty(thisObj.inputEntity.answerDesc)) {
                $(".details-div[domId='answerDesc']").click();
            }
        }, 600);
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        this.inputEntity.questionDesc = this.editor.questionDesc.getContent();
        this.inputEntity.answerDesc = this.editor.answerDesc.getContent();
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