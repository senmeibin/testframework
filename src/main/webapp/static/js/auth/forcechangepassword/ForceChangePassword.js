﻿// 定义数据输入画面JS类
SysApp.Auth.ForceChangePassword = Class.create();

SysApp.Auth.ForceChangePassword.prototype = Object.extend(new SysApp.Auth.CommonInput(), {
    /**********************以下方法为可选方法**********************/
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        this.saveMethod = "changePassword";

        this.saveConfirmMessage = "确定修改密码吗？";
    },

    //验证后处理
    validateFormAfter: function () {
        if (this.getValue("newPwd") != this.getValue("confirmPwd")) {
            this.showErrorMessage("确认密码与初始密码不一致。");
            return false;
        } else if (this.getValue("oldPwd") == this.getValue("newPwd")) {
            this.showErrorMessage("新旧密码不能一致。");
            return false;
        }
        return true;
    },

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "oldPwd=" + this.getValue("oldPwd") + "&newPwd=" + this.getValue("newPwd") + "&confirmPwd=" + this.getValue("confirmPwd");
    }
});