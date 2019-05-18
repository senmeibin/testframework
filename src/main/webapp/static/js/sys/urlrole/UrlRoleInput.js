// 定义数据输入画面JS类
SysApp.Sys.UrlRoleInput = Class.create();

SysApp.Sys.UrlRoleInput.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    /**********************以下方法为可选方法**********************/
    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        this.disabledDom("url", true);
        //初始化调用角色列表
        this.setRole();
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        //遍历所有被选中的角色
        var $checkedRoleList = $(this.getDom("roles")).find("input:checked");

        //角色集合
        var roleUids = "";
        $.each($checkedRoleList, function (index, item) {
            roleUids += $(item).attr("id") + ",";
        });

        //设置角色列表
        this.inputEntity.roles = roleUids;
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
    },

    /**
     * 角色列表生成
     */
    setRole: function () {
        //角色拥有角色集合
        var selectedRoleList = this.inputEntity.roles.split(",") || [];

        //清空角色列表
        var $roleList = $(this.getDom("roles"));
        $roleList.empty();
        var jsonOptionList = JsonUtility.Parse(this.getValue("jsonOptionList"));

        //有此角色checkbox选中
        function hasRole(roleCode, list) {
            var result = "";
            if (list) {
                //有角色的场合
                $.each(list, function (index, item) {
                    if (item == roleCode) {
                        result = "checked"
                    }
                });
            }
            return result;
        }

        var marginTop = "5px";
        var sourceRoleList = jsonOptionList['roleList'];
        var preAppName = "";
        //html生成
        $.each(sourceRoleList, function (index, item) {
            //选中角色
            var checked = hasRole(item.roleCode, selectedRoleList);

            if (preAppName != item.appName) {
                $roleList.append("<div class='clear-both'></div>" +
                    "<div style='margin-top: " + marginTop + "; border-bottom: dotted 1px #0099CC; line-height: 20px;'>" +
                    "<label style='font-weight: bold;'>" + item.appName + "</label> 的角色列表</div>");
                preAppName = item.appName;
                marginTop = "10px";
            }

            $roleList.append('<div style="width:200px;float: left;text-overflow:ellipsis;overflow: hidden;">' +
                '<input  class="role-list" id="' + item.roleCode + '" ' + checked + ' type="checkbox"/>' +
                '&nbsp;<label for="' + item.roleCode + '">' + item.roleName + '</label></div>');

        });

        //全选择/全解除 选择
        $roleList.append('<div class="clear-both"></div>' +
            '<div style="margin: 10px auto;color: #0099CC;">' +
            '   <input id="checkAllRole" type="checkbox"/>' +
            '   <label for="checkAllRole" style="font-weight: bold;">全选择/全解除</label>' +
            '</div>');

        //全选择/全解除 事件处理
        $("#checkAllRole").click(function () {
            var checked = this.checked;
            $(".role-list").each(function () {
                this.checked = checked;
            });
        });
    },

});