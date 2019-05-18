// 定义数据输入画面JS类
SysApp.Pms.ResearchInfoCommentInput = Class.create();

SysApp.Pms.ResearchInfoCommentInput.prototype = Object.extend(new SysApp.Pms.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    //设置市场调研信息
    setResearchInfo: function (researchInfo) {
        this.setValue("researchInfoUid", researchInfo.uid);
        this.setValue("researchInfoName", researchInfo.researchInfoName);
        this.setValue("researchInfoTitle", researchInfo.title);

    },

    //数据删除后回调
    deleteDataCallback: function (response) {
        this.saveDataCallback(response);
    },

    //数据保存后回调
    saveDataCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //处理成功
        if (ajaxResult.result > 0) {
            this.hideMessage();
            this.hide();

            if (this.isNotNull(SysApp.Pms.ResearchInfoCommentPopupListIns)) {
                SysApp.Pms.ResearchInfoCommentPopupListIns.getList(true);
            }
            if (this.isNotNull(SysApp.Pms.ResearchInfoListIns)) {
                SysApp.Pms.ResearchInfoListIns.getList(true);
            }
        }
        else {
            this.showErrorMessage(ajaxResult.message);
        }
    },
    /**********************以下方法为可选方法**********************/
    //画面DOM初期化前处理
    initInputFormBefore: function () {
    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "市场调研评论编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "市场调研评论添加";
        }

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);
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