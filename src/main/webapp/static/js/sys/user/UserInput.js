// 定义数据输入画面JS类
SysApp.Sys.UserInput = Class.create();

SysApp.Sys.UserInput.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
    /**********************以下方法为可选方法**********************/
    //初期化回调处理
    initCallback: function () {
        this.deptUidComboTree = null;

        this.initCommonCallback();
    },

    //组织架构TreeView初期化回调方法
    initTreeCallback: function () {
        if (this.isNotEmpty(this.inputEntity.deptUid)) {
            //设置组织架构的默认选择节点
            this.deptUidComboTree.selectNodeItem(this.inputEntity.deptUid);
        }
        //部门页面添加用户
        else if (this.isAddMode() && this.entry == "dept") {
            this.deptUidComboTree.selectNodeItem(this.deptUid);
            //设定为readonly
            this.deptUidComboTree.setReadOnly(true);
        }

        //不是UDC服务器 下拉树readonly
        if (!SysApp.Cmn.isUdcServer) {
            this.deptUidComboTree.setReadOnly(true);
            //管辖分公司由各系统自行设置
            //this.deptManageUidsComboTree.setReadOnly(true);
            //重写同步提醒消息
            this.setSyncNoticeMessage("用户基础数据自动从UDC服务器同步，您只能进行用户权限维护。");
        }
    },

    //返回处理
    onClick_Back: function () {
        //跳转到部门列表
        if (this.entry == "dept") {
            window.location = this.deptTreeListUrl;
        }
        else {
            window.location = this.listUrl;
        }
    },

    //刷新一览
    refreshList: function () {
        SysCmn.CmnMsg.hide(true);
        //跳转到部门列表
        if (this.entry == "dept") {
            window.location = this.deptTreeListUrl;
        }
        //画面模式
        else {
            window.location = this.listUrl;
        }
    },

    //画面DOM初期化前处理
    initInputFormBefore: function () {
        //新增时强制密码变更
        if (this.isAddMode()) {
            this.inputEntity.forceChangePswd = 1;
        }
    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "用户编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "用户添加";
        }

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }
        this.$("ctlTitle").html(title);

        //初始化调用角色列表
        this.setRole();

        //添加的场合
        if (this.isEmpty(this.inputEntity.userCd)) {
            this.$("plainPassword").addClass("required");
            this.$("confirmPassword").addClass("required");
        }
        else {
            if (this.inputEntity.regSystem == 1) {
                this.displayDom("btnDelete", false);
            }
        }

        //系统注册用户的场合
        if (this.inputEntity.regSystem == 1) {
            this.$("divRegSystemTips").show();

            this.disabledDom("userCd", true);
        }

        //不是UDC服务器
        if (!SysApp.Cmn.isUdcServer) {
            //修改标题
            this.$("ctlTitle").html("权限维护");
            //禁用用户基本信息
            this.disableInput(".baseInfo-block", true);
            //禁用用户扩展信息
            this.disableInput(".extraInfo-block", true);
            //启用保存
            this.disabledDom("btnSave", false);
            //隐藏删除按钮
            this.displayDom("btnDelete", false);

            //禁用密码框
            var thisObj = this;
            $("input[type='password']").each(function () {
                thisObj.disabledDom(this, true);
            });

            //处理手机号码（隐藏后四位）
            this.hiddenUserPhoneSuffix();
        }
        //新增时强制密码变更
        if (this.isAddMode()) {
            this.disabledDom("forceChangePswd", true);
        }

        //ITSupport不可设置系统管理相关角色
        if (!SysApp.Sys.isSystemManagement && SysApp.Sys.isItSupport) {
            this.disableInput(".APP-SYS", true);
            this.displayDomByClass(".APP-SYS", false);
        }
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
        function hasRole(uid, list) {
            var result = "";
            if (list) {
                //有角色的场合
                $.each(list, function (index, item) {
                    if (item.uid == uid) {
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
            var checked = hasRole(item.uid, selectedRoleList);

            if (preAppName != item.appName) {
                if (item.appCode == "DATA") {
                    $roleList.append("<div class='clear-both'></div>" +
                        "<div style='margin-top: " + marginTop + "; border-bottom: dotted 1px #0099CC; line-height: 20px;'>" +
                        "<label style='font-weight: bold;'>" + item.appName + "</label> 的角色列表</div>");
                }
                else {
                    $roleList.append("<div class='clear-both'></div>" +
                        "<div class='appName APP-" + item.appCode + "' data-app-code='" + item.appCode + "' style='margin-top: " + marginTop + "; border-bottom: dotted 1px #0099CC; line-height: 20px;'>" +
                        "<label style='font-weight: bold;'>" + item.appName + "</label> 的角色列表</div>");
                }
                preAppName = item.appName;
                marginTop = "10px";
            }

            //数据范围管理的场合，权限只能单选
            if (item.appCode == "DATA") {
                $roleList.append('<div class="APP-' + item.appCode + '" style="width:200px;float: left;text-overflow:ellipsis;overflow: hidden;">' +
                    '<input id="' + item.uid + '" ' + checked + ' type="radio" name="DATA" data-app-code="' + item.appCode + '"/>' +
                    '&nbsp;<label for="' + item.uid + '">' + item.roleName + '</label></div>');
            }
            else {
                $roleList.append('<div class="APP-' + item.appCode + '" style="width:200px;float: left;text-overflow:ellipsis;overflow: hidden;">' +
                    '<input class="role-list" id="' + item.uid + '" ' + checked + ' type="checkbox" data-app-code="' + item.appCode + '"/>' +
                    '&nbsp;<label for="' + item.uid + '">' + item.roleName + '</label></div>');
            }
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
                if (this.disabled != true) {
                    this.checked = checked;
                }
            });
        });

        //应用模块点击
        $(".appName").click(function () {
            var appCode = $(this).data("app-code");
            var checked = this.checked = !this.checked;
            $("input[data-app-code=" + appCode + "]").each(function () {
                if (this.disabled != true) {
                    this.checked = checked;
                }
            });
        });
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        //遍历所有被选中的角色
        var $checkedRoleList = $(this.getDom("roles")).find("input:checked");

        console.log($checkedRoleList);
        //角色ID集合
        var roleUids = [];
        $.each($checkedRoleList, function (index, item) {
            roleUids.push({uid: $(item).attr("id")});
        });

        //设置角色列表
        this.inputEntity.roleList = roleUids;

        //不是UDC服务器还原手机号
        if (!SysApp.Cmn.isUdcServer) {
            //手机号码
            this.inputEntity.userPhone = this.$("userPhone").attr("data-phone");
        }
    },

    //验证前处理
    validateFormBefore: function () {
        if (this.getValue("plainPassword") != this.getValue("confirmPassword")) {
            this.showErrorMessage("确认密码与初始密码不一致。");
            return false;
        }

        //UDC服务器才校验
        if (SysApp.Cmn.isUdcServer) {
            var phoneRegex = /^1\d{10,10}$/;
            if (!phoneRegex.test(this.getValue("userPhone"))) {
                this.showErrorMessage("请输入11位有效的手机号码(如：13661950001)。");
                return false;
            }
        }
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

    onComboSelected: function (selectedValue, selectedText, treeNode) {
        if (this.isEmpty(selectedValue) || this.isNull(treeNode)) {
            return;
        }
        var deptClass = treeNode["deptClass"];
        //3：部门 4:团队
        if (this.inputEntity.regSystem == 0 && deptClass != 3 && deptClass != 4) {
            this.showErrorMessage("人员只能添加到具体部门下，请重新选择。<br/>(不能直接将人员添加到集团、大区、分公司下)");
            this.setValue("deptUid", this.inputEntity.deptUid);
            this.setValue("deptName", this.inputEntity.deptName);
        }
    },

    //隐藏手机号码后4位
    hiddenUserPhoneSuffix: function () {
        var phone = this.getValue("userPhone");
        //缓存原始手机号
        this.$("userPhone").attr("data-phone", phone);
        if (phone.length > 4) {
            this.setValue("userPhone", phone.substr(0, phone.length - 4) + "****");
        }
    }
});