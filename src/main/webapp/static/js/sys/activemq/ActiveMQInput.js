// 定义数据输入画面JS类
SysApp.Sys.ActiveMQInput = Class.create();

SysApp.Sys.ActiveMQInput.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
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
        //启用mq
        if (this.isEnable) {
            //发送邮件
            this.bindEvent("btnSendMail", "click", this.sendMail.bind(this));
            //发送短信
            this.bindEvent("btnSendSms", "click", this.sendSms.bind(this));
        }
        //未启用mq
        else {
            this.displayDom("divNoticeMessage", true);
            this.disabledDom("btnSendMail", true);
            this.disabledDom("btnSendSms", true);
        }
    },

    sendMail: function () {
        var json = {};
        json.appCode = "SYS";
        json.mailTo = this.getValue("mailTo");
        json.mailCc = this.getValue("mailCc");
        json.mailBcc = this.getValue("mailBcc");
        json.subject = this.getValue("subject");
        json.message = this.getValue("mailMessage");
        if (this.isEmpty(json.mailTo)) {
            this.showErrorMessage("请输入收件人地址。");
            return;
        }
        if (this.isEmpty(json.subject)) {
            this.showErrorMessage("请输入主题。");
            return;
        }
        if (this.isEmpty(json.message)) {
            this.showErrorMessage("请输入邮件内容。");
            return;
        }
        this.showConfirm("是否确定要发送测试邮件？", this.pushQueue.bind(this, "mail", json));
    },

    sendSms: function () {
        var json = {};
        json.mobile = this.getValue("mobile");
        json.message = this.getValue("smsMessage");
        if (this.isEmpty(json.mobile)) {
            this.showErrorMessage("请输入手机号码。");
            return;
        }
        if (this.isEmpty(json.message)) {
            this.showErrorMessage("请输入短信内容。");
            return;
        }
        this.showConfirm("是否确定要发送测试短信？", this.pushQueue.bind(this, "sms", json));
    },

    pushQueue: function (sendType, jsonObject) {
        var params = "sendType=" + sendType + "&jsonObject=" + JSON.stringify(jsonObject);
        //Ajax处理前显示提示画面
        this.showProcessing(false);
        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "pushQueue", this.pushCallback.bind(this), params);
    },

    //清除处理后[Callback后处理]
    pushCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //处理成功的场合、显示处理结果消息后，继续显示本页面
        this.showMessage(ajaxResult.content, MSG_TYPE_INFO);
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