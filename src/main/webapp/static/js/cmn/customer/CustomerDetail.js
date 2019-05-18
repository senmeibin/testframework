// 定义数据输入画面JS类
SysApp.Cmn.CustomerDetail = Class.create();

SysApp.Cmn.CustomerDetail.prototype = Object.extend(new SysApp.Cmn.CommonInput(), {
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
        var title = "合同客户详细";
        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);

        //初始化用户列表
        this.setUser();
    },

    /**
     * 用户列表
     */
    setUser: function () {
        //用户集合
        var selectedUserList = this.inputEntity.userList || [];
        var userList = $(this.getDom("userList"));
        userList.empty();
        $.each(selectedUserList, function (index, item) {
            userList.append('<div style="width:155px;float: left;text-overflow:ellipsis;overflow: hidden;">' + item.userName + '</div>');
        });
    }
});