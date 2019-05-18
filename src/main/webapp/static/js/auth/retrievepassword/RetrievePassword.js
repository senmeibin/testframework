// 定义数据输入画面JS类
SysApp.Auth.RetrievePassword = Class.create();

SysApp.Auth.RetrievePassword.prototype = Object.extend(new SysApp.Auth.CommonInput(), {
    /**********************以下方法为可选方法**********************/
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        this.saveConfirmMessage = "您的密码将会被重置，确定要重置密码吗？";

        this.saveMethod = "resetPassword";
    },

    //客户化自定义参数
    customRequestParams: function () {
        return "userCd=" + this.getValue("userCd") + "&userMail=" + this.getValue("userMail");
    },

    goLogin: function () {
        window.location = "/login";
    },

    //画面内容保存[Callback后处理]
    saveDataCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //处理成功
        if (ajaxResult.result > 0) {
            this.showInfoMessage(ajaxResult.message, this.goLogin.bind(this));
        }
        else {
            this.showWarningMessage(ajaxResult.message);
        }
    }
});