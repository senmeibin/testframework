// 定义数据输入画面JS类
SysApp.Cmn.MessageInput = Class.create();

SysApp.Cmn.MessageInput.prototype = Object.extend(new SysApp.Cmn.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.calendarFormat = "YYYY/MM/DD HH:mm:ss";

        this.initCommonCallback();
        //绑定是否富文本消息onchange事件
        this.bindEvent("isRichMessage", "change", this.changeRichMessage.bind(this));

        //绑定发送区分onchange事件
        this.bindEvent("sendTypeCd", "change", this.changeSendType.bind(this));
    },

    //是否富文本消息
    changeRichMessage: function () {
        if (this.getValue("isRichMessage") == 1) {
            var messageContent = this.getValue("messageContent");
            //初始化消息内容富文本
            this.initUEditor("messageContent", "100%", 500);
            this.editor.messageContent.setContext(messageContent);

        }
        else {
            var messageContent = this.editor.messageContent.getContent();
            //销毁富文本
            this.editor.messageContent.destroy();
            this.setValue("messageContent", messageContent);
        }
    },

    /**********************以下方法为可选方法**********************/
    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "消息推送编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "消息推送添加";
        }

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);

        //新增模式下，隐藏人数字段
        if (this.isAddMode()) {
            this.displayDom("divCount", false);
        }
        //编辑模式下，禁用人数输入框
        else {
            this.disabledDom("allCount", true);
            this.disabledDom("unreadCount", true);
        }

        //富文本消息
        if (this.inputEntity.isRichMessage == 1) {
            //初始化消息内容富文本
            this.initUEditor("messageContent", "100%", 500);
        }
        //初始处理
        this.showComboCheckTreeBySendTypeCd(this.inputEntity.sendTypeCd);
        //判断是否可以修改
        var treeReadOnly = false;
        if (parseInt(this.serviceTime) - new Date(this.inputEntity.messageEndDate).getTime() > 0) {
            //禁用页面输入框
            this.disableInput(this.getMainContentClassName(), true);
            //隐藏操作按钮
            this.displayDom("btnSave", false);
            this.displayDom("btnDelete", false);
            //禁用下拉树
            treeReadOnly = true;
        }

        //下拉树设置为只读
        var thisObj = this;
        this.comboTreeInterval = setInterval(function () {
            if (thisObj.sendRoleUidsComboTree) {
                thisObj.sendUserUidsComboTree.setReadOnly(treeReadOnly);
                thisObj.sendDeptUidsComboTree.setReadOnly(treeReadOnly);
                thisObj.sendRoleUidsComboTree.setReadOnly(treeReadOnly);
                clearInterval(thisObj.comboTreeInterval);
            }
        }, 10);

        if (this.isEmpty(this.inputEntity.remark)) {
            $(".message-remark").hide();
        }
        else {
            $(".message-remark").show();
        }
    },

    //发送区分
    changeSendType: function () {
        this.showComboCheckTreeBySendTypeCd(this.getValue("sendTypeCd"));
    },

    //根据发送区分显示对应的选择框
    showComboCheckTreeBySendTypeCd: function (sendTypeCd) {
        //01：全部 05：在线用户
        if ("01" == sendTypeCd || "05" == sendTypeCd || this.isEmpty(sendTypeCd)) {
            this.displayDom("userComboCheckTree", false);
            this.displayDom("deptComboCheckTree", false);
            this.displayDom("roleComboCheckTree", false);
        }
        //指定人员
        else if ("02" == sendTypeCd) {
            this.displayDom("userComboCheckTree", true);
            this.displayDom("deptComboCheckTree", false);
            this.displayDom("roleComboCheckTree", false);
        }
        //指定部门
        else if ("03" == sendTypeCd) {
            this.displayDom("userComboCheckTree", false);
            this.displayDom("deptComboCheckTree", true);
            this.displayDom("roleComboCheckTree", false);
        }
        //指定角色
        else if ("04" == sendTypeCd) {
            this.displayDom("userComboCheckTree", false);
            this.displayDom("deptComboCheckTree", false);
            this.displayDom("roleComboCheckTree", true);
        }
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        var sendTypeCd = this.inputEntity.sendTypeCd;

        if (this.inputEntity.isRichMessage == 1) {
            //消息内容
            this.inputEntity.messageContent = this.editor.messageContent.getContent();
        }

        //01：全部 05：在线用户
        if ("01" == sendTypeCd || "05" == sendTypeCd) {
            this.inputEntity.sendUserUids = "";
            this.inputEntity.sendDeptUids = "";
            this.inputEntity.sendRoleUids = "";
        }
        //指定人员
        else if ("02" == sendTypeCd) {
            this.inputEntity.sendDeptUids = "";
            this.inputEntity.sendRoleUids = "";
        }
        //指定部门
        else if ("03" == sendTypeCd) {
            this.inputEntity.sendUserUids = "";
            this.inputEntity.sendRoleUids = "";
        }
        //指定角色
        else if ("04" == sendTypeCd) {
            this.inputEntity.sendUserUids = "";
            this.inputEntity.sendDeptUids = "";
        }
    },

    customSaveConfirmMessage: function () {
        //存在已读的情况
        if (this.inputEntity.unreadCount != this.inputEntity.allCount) {
            return "该信息已被部分用户阅读，确定要重新修改吗？修改后已读用户将会重新收到该信息。";
        }
        return "确定需要保存吗？";
    },

    //验证前处理
    validateFormBefore: function () {
        return true;
    },

    //验证后处理
    validateFormAfter: function () {
        //校验消息提醒结束时间 大于 消息提醒开始时间
        var messageStartDate = this.getValue("messageStartDate");
        var messageEndDate = this.getValue("messageEndDate");
        var startTime = this.isEmpty(messageStartDate) ? 0 : new Date(messageStartDate).getTime();
        var endTime = this.isEmpty(messageEndDate) ? 0 : new Date(messageEndDate).getTime();
        if (startTime >= endTime) {
            this.showErrorMessage("消息提醒结束时间应大于消息提醒开始时间。");
            return false;
        }
        //校验是否选择消息接收对象
        var sendTypeCd = this.getValue("sendTypeCd");
        //02:指定人员;03:指定部门；04:指定角色
        if (sendTypeCd == "02" && this.isEmpty(this.getValue("sendUserUids")) ||
            sendTypeCd == "03" && this.isEmpty(this.getValue("sendDeptUids")) ||
            sendTypeCd == "04" && this.isEmpty(this.getValue("sendRoleUids"))) {
            this.showErrorMessage("请选择接收消息的" + (sendTypeCd == "02" ? "人员" : (sendTypeCd == "03" ? "部门" : "角色")) + "。");
            return false;
        }
        return true;
    },

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "";
    }
});