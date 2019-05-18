// 定义数据输入画面JS类
SysApp.Sys.RoleDetail = Class.create();

SysApp.Sys.RoleDetail.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
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
        this.$("ctlTitle").html("角色详细");

        //角色拥有权限集合
        var permissionList = this.inputEntity.permissionList;

        var permissions = $(this.getDom("permissions"));
        permissions.empty();
        if (permissionList) {
            $.each(permissionList, function (index, item) {
                permissions.append('<div style="width:200px;float: left;text-overflow:ellipsis;overflow: hidden;">' + item.permissionName + '</div>');
            });
        }

        var thisObj = this;
        var users = $(this.getDom("users"));
        users.empty();
        //拥有角色的用户
        var userRoleList = this.inputEntity.userRoleList;
        if (userRoleList){
            $.each(userRoleList, function (index, item) {
                //姓名非空场合
                if (thisObj.isNotEmpty(item.userName)) {
                    users.append(item.userName + "，");
                }
            });
        }

    }
});