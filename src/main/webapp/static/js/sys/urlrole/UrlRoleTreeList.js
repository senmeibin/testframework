//定义数据一览画面JS类
SysApp.Sys.UrlRoleTreeList = Class.create();

SysApp.Sys.UrlRoleTreeList.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
    //分页显示的记录数[注意：必须实现的方法]
    getPageSize: function () {
        return -1;
    },

    //初期化回调处理
    initCallback: function () {
        this.autoSearch = false;

        //初始化页面布局
        this.initPageLayout();

        //树显示隐藏/显示控制
        $(".section-icon").click(function () {
            $(".section").toggleClass("hiding");
        });

        this.urlRoleTreeTree = null;

        //初始化调用角色列表
        this.setRole();

        //访问权限平铺展示
        this.bindEvent("btnUrlRoleList", 'click', this.onClick_UrlRoleList.bind(this));

        //保存并被继承按钮绑定事件
        this.bindEvent("btnCoverSave", 'click', this.onClick_CoverSave.bind(this));
    },

    //访问权限平铺展示
    onClick_UrlRoleList: function () {
        window.location = this.urlRoleListUrl;
    },

    //TreeView初期化回调方法
    initTreeCallback: function () {
        //设置TreeView的默认选择节点
        this.urlRoleTreeTree.selectNodeItem(this.selectedDeptUid);
    },

    //初始化页面布局
    initPageLayout: function () {
        //页面高度控制
        var height = $(window).height();
        var topHeight = height / 2 - 25;
        var panelHeight = height - 50;
        var treeHeight = height - 82;
        $(".section-icon").css("top", topHeight);
        $(".section-right").css("height", panelHeight);
        $(".section-tree").css("height", treeHeight);
    },

    //节点选择回调函数
    onClickTreeNodeCallback: function (selectUrl, dispName) {
        this.inputEntity.url = selectUrl;
        this.inputEntity.remark = dispName;
        //页面展示
        this.$("urlSpan").html(dispName);
        // Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "getUrlRole", this.getUrlRoleCallback.bind(this), "url=" + selectUrl);
    },

    /**
     *获取路径访问权限回调函数
     *
     * @param response
     */
    getUrlRoleCallback: function (response) {
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        this.urlRole = JsonUtility.Parse(response.responseText);
        //编辑场合
        if (this.isNotEmpty(this.urlRole.uid)) {
            this.inputEntity.uid = this.urlRole.uid;
            this.inputEntity.roleList = this.urlRole.roles.split(",");
            this.setValue("description", this.urlRole.description);
            this.setRole();
        }
        //新增场合
        else {
            this.inputEntity.uid = null;
            this.inputEntity.roleList = [];
            this.setValue("description", "");
            this.setRole();
        }
    },

    /** ********************以下方法为可选方法********************* */
    //Ajax提交前，附加客户化请求参数
    customSearchModel: function () {
        return ""
    },

    /**
     * 角色列表生成
     */
    setRole: function () {
        //角色拥有角色集合
        var selectedRoleList = this.inputEntity.roleList || [];

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

    //验证前处理
    validateFormBefore: function () {
        if (this.isEmpty(this.inputEntity.url)) {
            this.showErrorMessage("请选择访问路径。");
            return false;
        }

        return true;
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        this.setInputEntity(false);
    },

    //覆盖保存
    onClick_CoverSave: function () {
        //数据校验通过的场合
        if (this.checkData()) {
            this.showConfirm("确定保存并被继承吗？", this.saveCoverData.bind(this));
        }
    },

    //保存并被继承函数
    saveCoverData: function () {
        this.setInputEntity(true);

        var params = "inputJson=" + JsonUtility.ToJSON(this.inputEntity) + "&editMode=" + this.editMode;

        //Ajax处理前显示提示画面
        this.showProcessing(false);

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + this.saveMethod, this.saveDataCallback.bind(this), params);
    },

    //设定inputEntity
    setInputEntity: function (isCover) {
        //遍历所有被选中的角色
        var $checkedRoleList = $(this.getDom("roles")).find("input:checked");
        //角色集合
        var roleCodes = "";
        //删除的权限集合
        var deleteRoleCodes = [];
        var chooseRoleCodes = [];
        $.each($checkedRoleList, function (index, chooseRoleCode) {
            roleCodes += $(chooseRoleCode).attr("id") + ",";
            chooseRoleCodes.push($(chooseRoleCode).attr("id"));
        });

        //覆盖场合
        if (isCover) {
            //循环处理
            for (var i = 0; i < this.inputEntity.roleList.length; i++) {
                //删除标识（true：删除，false:未删除）
                var delFlag = true;
                var oldRoleCode = this.inputEntity.roleList[i];
                //权限非空场合
                if (this.isNotEmpty(oldRoleCode)) {
                    //循环处理选中的权限
                    for (var j = 0; j < chooseRoleCodes.length; j++) {
                        var chooseRoleCode = chooseRoleCodes[j];
                        //未删除的场合
                        if (oldRoleCode == chooseRoleCode) {
                            delFlag = false;
                            break;
                        }
                    }
                    //已删除场合 放入删除集合里面
                    if (delFlag) {
                        deleteRoleCodes.push(oldRoleCode);
                    }
                }
            }
            this.inputEntity.deleteRoleCodes = deleteRoleCodes;
        }

        this.inputEntity.roleList = chooseRoleCodes;

        //设置角色列表(去掉最后面的,)
        this.inputEntity.roles = roleCodes;

        this.inputEntity.isCover = isCover;

        //获取描述
        this.inputEntity.description = this.getValue("description");
    }
});