// 定义数据输入画面JS类
SysApp.Sys.DesSetting = Class.create();

SysApp.Sys.DesSetting.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
    /**********************以下方法为可选方法**********************/
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        //绑定加密和解密button的click事件
        this.bindEvent("btnEncrypt", "click", this.encryptContent.bind(this));
        this.bindEvent("btnDecrypt", "click", this.decryptContent.bind(this));
    },

    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {

    },

    //加密
    encryptContent: function () {
        if (this.isEmpty(this.getValue("encryptStr"))) {
            this.showWarningMessage("请输入需要加密的字符串。")
            return;
        }
        //处理特殊字符 + 和 &
        var encryptStr = $M.encodeValue(this.getValue("encryptStr"));
        var params = "encryptStr=" + encryptStr + "&type=01";
        //Ajax处理前显示提示画面
        this.showProcessing(false);
        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "encryptOrDecryptOperation", this.encryptContentCallback.bind(this), params);
    },

    //解密
    decryptContent: function () {
        if (this.isEmpty(this.getValue("decryptStr"))) {
            this.showWarningMessage("请输入需要解密的字符串。")
            return;
        }
        //处理特殊字符 + 和 &
        var decryptStr = $M.encodeValue(this.getValue("decryptStr"));
        var params = "decryptStr=" + decryptStr + "&type=02";
        //Ajax处理前显示提示画面
        this.showProcessing(false);
        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "encryptOrDecryptOperation", this.decryptContentCallback.bind(this), params);
    },

    //加密处理后[Callback后处理]
    encryptContentCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);
        var result = ajaxResult.result;
        if (result == 1) {
            //处理成功场合、显示处理结果消息后，继续显示本页面
            this.setValue("encryptResult", ajaxResult.content);
            this.showInfoMessage(ajaxResult.message);
        }
        else {
            //处理成功失败的场合、显示处理结果消息后，继续显示本页面
            this.showErrorMessage(ajaxResult.message);
        }
    },

    //解密处理后[Callback后处理]
    decryptContentCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);
        var result = ajaxResult.result;
        if (result == 1) {
            //处理成功场合、显示处理结果消息后，继续显示本页面
            this.setValue("decryptResult", ajaxResult.content);
            this.showInfoMessage(ajaxResult.message);
        }
        else {
            //处理成功失败的场合、显示处理结果消息后，继续显示本页面
            this.showErrorMessage(ajaxResult.message);
        }
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