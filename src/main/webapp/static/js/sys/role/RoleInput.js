// 定义数据输入画面JS类
SysApp.Sys.RoleInput = Class.create();

SysApp.Sys.RoleInput.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
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
        var title = "角色编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "角色添加";
        }
        this.$("ctlTitle").html(title);

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        //生成权限列表
        var value = this.getValue("jsonOptionList");

        if (this.isEmpty(value)) return;

        var jsonOptionList = JsonUtility.Parse(value);

        //初始化调用权限列表
        this.setPermission();

        //app编号变更 重新刷新权限
        this.bindEvent("appCode", "change", this.setPermission.bind(this));

        //系统角色的场合，删除不可及部分信息编辑不可
        if (this.inputEntity.regSystem == 1) {
            this.displayDom("btnDelete", false);

            this.disabledDom("appCode", true);
            this.disabledDom("roleCode", true);
            this.disabledDom("roleName", true);
            this.disabledDom("description", true);
            this.disabledDom("dispSeq", true);
        }
    },

    /**
     * 权限列表生成
     */
    setPermission: function () {
        //角色拥有权限集合
        var permissionList = this.inputEntity.permissionList;

        //清空权限列表
        var permissions = $(this.getDom("permissions"));
        permissions.empty();
        //当前app编号
        var appCode = this.getValue("appCode");
        var jsonOptionList = JsonUtility.Parse(this.getValue("jsonOptionList"));

        //有此权限checkbox选中
        function hasPermission(uid, list) {
            var result = "";
            if (list) {
                //有权限的场合
                $.each(list, function (index, item) {
                    if (item.uid == uid) {
                        result = "checked"
                    }
                });
            }
            return result;
        }

        //html生成
        $.each(jsonOptionList['permissionList'], function (key, value) {
            if (appCode == key) {
                $.each(value, function (index, item) {
                    //选中权限
                    var checked = hasPermission(item.uid, permissionList);
                    permissions.append('<div style="width:200px;float: left;text-overflow:ellipsis;overflow: hidden;"><input class="permission-list" id="' + item.uid + '" ' + checked + ' type="checkbox"/>&nbsp;<label for="' + item.uid + '">' + item.permissionName + '</label></div>');
                });
                permissions.append('<div style="clear:both; width:200px;text-overflow:ellipsis;overflow: hidden;"><input id="checkAllPermission" type="checkbox"/>&nbsp;<label for="checkAllPermission">全选择/全解除</label></div>');
            }
        });

        //全选择/全解除 事件处理
        $("#checkAllPermission").click(function () {
            var checked = this.checked;
            $(".permission-list").each(function () {
                this.checked = checked;
            });
        });

        if (this.isEmpty(permissions.val())) {
            this.displayDom("divPermission", false);
        }
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        //权限ID集合
        var permissionUids = [];

        //遍历所有被选中的权限
        $(".permission-list").each(function () {
            if (this.checked) {
                permissionUids.push({uid: this.id});
            }
        });

        //设置权限列表
        this.inputEntity.permissionList = permissionUids;
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