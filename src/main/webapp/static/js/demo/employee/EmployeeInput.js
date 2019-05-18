// 定义数据输入画面JS类
SysApp.Demo.EmployeeInput = Class.create();

SysApp.Demo.EmployeeInput.prototype = Object.extend(new SysApp.Demo.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    /**********************以下方法为可选方法**********************/
    //画面DOM初期化前处理
    initInputFormBefore: function () {

        //办公所在地
        this.locationUidComboTree = null;
    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "人员信息编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "人员信息添加";
        }

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);

        this.disabledDom("seniority", true);
        this.disabledDom("age", true);
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
    },

    onComboSelected: function (selectedValue, selectedText, treeNode) {
        //部门
        if (treeNode.tId.indexOf("deptUid") != -1) {
            //将办公地点默认为当前部门所在分公司
            this.locationUidComboTree.selectNodeItem(this.getCompanyUid(treeNode));
        }
        //所在省变化，清空市、区
        else if (treeNode.tId.indexOf("contactProvinceUid") != -1) {
            this.setValue("contactCityName", "");
            this.setValue("contactCityUid", "");
            this.setValue("contactCountyName", "");
            this.setValue("contactCountyUid", "");
        }
        //所在市变化，清空区
        else if (treeNode.tId.indexOf("contactCityUid") != -1) {
            this.setValue("contactCountyName", "");
            this.setValue("contactCountyUid", "");
        }
    },

    //获取分公司UID
    getCompanyUid: function (treeNode) {
        var node = treeNode.getParentNode();
        if (node.deptClass == 2) {
            return node.uid;
        }
        return this.getCompanyUid(node);
    }
});