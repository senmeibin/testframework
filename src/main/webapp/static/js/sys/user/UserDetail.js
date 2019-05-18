// 定义数据输入画面JS类
SysApp.Sys.UserDetail = Class.create();

SysApp.Sys.UserDetail.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
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
        var title = "用户详细";
        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.setValue("accountLock", this.inputEntity.accountLock == "0" ? "账号有效" : "账号锁定");

        this.$("ctlTitle").html(title);

        //用户名去掉部分字符
        if (this.isNotEmpty(this.inputEntity.userCd)) {
            this.setValue("userCd", this.inputEntity.userCd.split("^")[0]);
        }

        //初始化调用角色列表
        this.setRole();
        this.setCustomer();
    },

    getDetail: function (uid) {
        if (this.isEmpty(uid)) return;
        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "getDetail", this.getDetailCallback.bind(this), "uid=" + uid);
    },

    getDetailCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }
        this.inputEntity = JsonUtility.Parse(response.responseText);

        this.initInputForm(this.inputEntity);
        this.setRole();
        this.setCustomer();
        this.show();
    },

    /**
     * 角色列表生成
     */
    setRole: function () {
        //角色拥有角色集合
        var selectedRoleList = this.inputEntity.roleList || [];
        var roles = $(this.getDom("roles"));
        roles.empty();
        $.each(selectedRoleList, function (index, item) {
            roles.append('<div style="width:155px;float: left;text-overflow:ellipsis;overflow: hidden;">' + item.roleName + '</div>');
        });
    },

    /**
     * 合同客户列表生成
     */
    setCustomer: function () {
        //合同客户集合
        var selectedCustomerList = this.inputEntity.customerList || [];
        var customerList = $(this.getDom("customerList"));
        customerList.empty();
        $.each(selectedCustomerList, function (index, item) {
            customerList.append('<div style="width:155px;float: left;text-overflow:ellipsis;overflow: hidden;">' + item.customerName + '</div>');
        });
    }
});