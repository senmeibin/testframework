﻿// 定义数据输入画面JS类
SysApp.Sys.PermissionInput = Class.create();

SysApp.Sys.PermissionInput.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
    /**********************以下方法为可选方法**********************/
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "权限编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "权限添加";
        }
        this.$("ctlTitle").html(title);
        this.setValue("typeCd", "01");
        this.disabledDom(this.getDom("typeCd"), true);
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