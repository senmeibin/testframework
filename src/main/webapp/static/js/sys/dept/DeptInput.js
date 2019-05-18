// 定义数据输入画面JS类
SysApp.Sys.DeptInput = Class.create();

SysApp.Sys.DeptInput.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
    initCallback: function () {
        this.parentDeptUidComboTree = null;

        this.bindEvent("deptClass", "change", this.changeDeptClass.bind(this));
    },

    //部门层级与部门分类联动控制
    changeDeptClass: function () {
        //部门层级为公司、部门、团队的场合
        if (this.getValue("deptClass") >= 2) {
            $(".dept-category-row").show();
            var jsonOptionList = JsonUtility.Parse(this.getValue("jsonOptionList"));
            var deptCategoryCds = jsonOptionList["deptCategoryCd"];
            var subList = new Array();
            //分公司的场合
            if (this.getValue("deptClass") == 2) {
                $.each(deptCategoryCds, function (key, item) {
                    if (item.subCd > 20 && item.subCd < 30) {
                        subList.push(item);
                    }
                });
            }
            //部门、团队的场合
            if (this.getValue("deptClass") >= 3) {
                $.each(deptCategoryCds, function (key, item) {
                    if (item.subCd > 30 && item.subCd < 40) {
                        subList.push(item);
                    }
                });
            }

            this.createOption("deptCategoryCd", subList, "subCd", "subName", true, "请选择");
        }
        else {
            $(".dept-category-row").hide();
            this.setValue("deptCategoryCd", "");
        }
    },

    /**********************以下方法为可选方法**********************/
    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "部门编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "部门添加";

            if (this.isNull(this.parentDept)) {
                //添加模式的场合，默认为集团添加
                this.setValue("deptClass", 0);
            }
            else {
                //父部门下级部门添加
                var deptClass = this.parentDept.deptClass + 1;
                //用于判断是否追加下级部门
                this.parenetDeptClass = this.parentDept.deptClass;
                if (deptClass > 3) deptClass = 3;
                this.setValue("deptClass", deptClass);
            }
        }
        else {
            //更新前的class记录
            this.oldDeptClass = this.inputEntity.deptClass;
            this.parenetDeptClass = this.inputEntity.parentDeptClass;
        }
        this.$("ctlTitle").html(title);
        var parentDeptUid = "";
        //一级部门添加的场合
        if (this.isEmpty(this.parentDept)) {
            this.disabledDom("parentDeptUid", false)
        }
        //下级部门添加的场合
        else {
            this.setValue("parentDeptUid", this.parentDept.uid);
            parentDeptUid = this.parentDept.uid;

            this.setValue("enterpriseUid", this.parentDept.enterpriseUid);
        }

        //清空临时变量
        this.parentDept = null;

        if (this.isNotNull(this.inputEntity) && this.isNotNull(this.inputEntity.deptName)) {
            //过滤名称前特殊符号
            this.setValue("deptName", this.inputEntity.deptName.replaceAll("#", ""));
        }

        if (this.isNotNull(this.parentDeptUidComboTree)) {
            //初始化上级部门
            if (this.isEmpty(parentDeptUid)) {
                this.parentDeptUidComboTree.setReadOnly(false);
                //设定一下父部门uid
                if (this.isNotNull(this.inputEntity)) {
                    parentDeptUid = this.inputEntity.parentDeptUid;
                    this.parentDeptUidComboTree.selectNodeItem(parentDeptUid);
                }

            }
            else {
                //设定为readonly
                this.parentDeptUidComboTree.setReadOnly(true);
                //设置组织架构的默认选择节点
                this.parentDeptUidComboTree.selectNodeItem(parentDeptUid);
            }

            if (this.oldDeptClass <= 1) {
                //集团和大区不许修改上级部门
                //设定为readonly
                this.parentDeptUidComboTree.setReadOnly(true);
            }
        }

        this.changeDeptClass();
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        if (this.isEmpty(this.inputEntity.dispSeq)) {
            this.inputEntity.dispSeq = 99999;
        }
    },

    customSaveDataCallback: function (ajaxResult) {
        this.createOption("parentDeptUid", ajaxResult.content, "subCd", "subName", true, "请选择");
    },

    //刷新一览
    refreshList: function () {
        SysCmn.CmnMsg.hide(true);

        //popup页面隐藏
        this.hide();

        //组织结构TreeView模式设定的场合
        if (this.isNotNull(SysApp.Sys.DeptListIns) || this.isNotNull(SysApp.Sys.DeptListIns.deptStructureTree)) {
            //刷新部门页面所有列表信息
            SysApp.Sys.DeptListIns.refreshPageAllList();
        }
    },

    //验证前处理
    validateFormBefore: function () {
        if (this.getValue("deptClass") <= this.parenetDeptClass) {
            this.showErrorMessage("选择的部门层级不能高于或等于上级部门层级。");
            return false;
        }
        return true;
    },

    //上级部门 comboTree 回调函数
    onComboSelected: function (selectedValue, selectedText, treeNode) {
        if (this.isEmpty(selectedValue) || this.isNull(treeNode)) {
            return;
        }
        var deptClass = treeNode["deptClass"];
        if (this.isNotEmpty(deptClass)) {
            this.parenetDeptClass = deptClass;
            if (deptClass >= 3) this.showErrorMessage("部门下不能再添加下级部门，请重新选择。");
            deptClass = deptClass + 1;
            this.setValue("deptClass", deptClass);
        }
    }
});