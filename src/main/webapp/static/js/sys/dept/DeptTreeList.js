//定义数据一览画面JS类
SysApp.Sys.DeptTreeList = Class.create();

SysApp.Sys.DeptTreeList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //分页显示的记录数[注意：必须实现的方法]
    getPageSize: function () {
        return -1;
    },

    //初期化回调处理
    initCallback: function () {
        this.autoSearch = false;

        //部门结构树对象
        this.deptStructureTree = null;

        this.initTableCommonProperty();

        this.initCommonCallback();

        //初始化页面布局
        this.initPageLayout();

        //树显示隐藏/显示控制
        $(".section-icon").click(function () {
            $(".section").toggleClass("hiding");
        });

        //未选择部门的场合，默认选择根部门
        if (this.isEmpty(this.deptUid)) {
            this.selectedDeptUid = this.rootDeptUid;
        }
        else {
            this.selectedDeptUid = this.deptUid;
        }

        //刷新页面所有列表信息
        this.refreshPageAllList(this.selectedDeptUid);

        //UDC同步时，禁用操作按钮
        if (!SysApp.Cmn.isUdcServer && this.isSyncDataPage == "true") {
            this.displayDom("btnAddUser", false);
            //修改btnDeptList样式
            $(this.getDom("btnDeptList")).css("margin-left", 0);
        }
        //绑定用户新增按钮事件
        else {
            this.bindEvent("btnAddUser", 'click', this.onClick_AddUser.bind(this));
        }

        //组织架构平铺展示
        this.bindEvent("btnDeptList", 'click', this.onClick_DeptList.bind(this));

        //企業選擇
        if (this.enterpriseUid) {
            this.setValue('treeEnterpriseUid', this.enterpriseUid);
        }
        this.bindEvent("treeEnterpriseUid", 'change', this.onChange_TreeEnterpriseUid.bind(this));

    },
    onChange_TreeEnterpriseUid: function () {
        window.location = this.deptTreeUrl + "&enterpriseUid=" + this.getValue("treeEnterpriseUid");
    },
    //组织架构TreeView初期化回调方法
    initTreeCallback: function () {
        //设置组织架构的默认选择节点
        this.deptStructureTree.selectNodeItem(this.selectedDeptUid);
    },

    //添加用户处理
    onClick_AddUser: function () {
        window.location = this.userInputUrl + "&deptUid=" + this.selectedDeptUid;
    },

    //组织架构平铺展示
    onClick_DeptList: function () {
        window.location = this.deptListUrl;
    },

    //初始化页面布局
    initPageLayout: function () {
        //页面高度控制
        var height = $(window).height();
        var topHeight = height / 2 - 25;
        var panelHeight = height - 50;
        var treeHeight = height - 82;
        $(".section-icon").css("top", topHeight);
        $(".section-tree").css("height", treeHeight);
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        this.colList.push(new ColInfo("操作", "operation", "10%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("部门名称", "deptName", "22%", SORT_YES));
        this.colList.push(new ColInfo("部门层级", "deptClassName", "8%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("部门分类", "deptCategoryName", "8%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("部门别名", "deptAliasName", "10%", SORT_YES));
        this.colList.push(new ColInfo("部门全称", "deptFullName", "36%", SORT_YES));
        this.colList.push(new ColInfo("备注", "remark", "4%", SORT_YES, ALIGN_CENTER));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //组织结构节点选择回调函数
    onClickTreeNodeCallback: function (selectedDeptUid) {
        //刷新页面所有列表信息
        this.refreshPageAllList(selectedDeptUid);
    },

    //刷新页面所有列表信息
    refreshPageAllList: function (selectedDeptUid) {
        //如果传过来为空，则取当前选中的节点
        if (this.isEmpty(selectedDeptUid)) {
            //树重新加载
            this.deptStructureTree.reAsyncChildNodes(this.selectedDeptUid);
        }
        else {
            this.selectedDeptUid = selectedDeptUid
        }

        this.setDeptUid(this.selectedDeptUid);
        this.getList(true);
        this.deptChildListInstance.setParentDeptUid(this.selectedDeptUid);
        this.deptChildListInstance.getList(true);
        this.deptUserInnerListInstance.setDeptUid(this.selectedDeptUid);
        this.deptUserInnerListInstance.getList(true);
    },

    //自定义添加函数
    customAdd: function () {
        this.addSubDept(0);
    },

    //创建列表添加图标
    createAddIcon: function (rowNo) {
        return "<button type='button' class='btn btn-primary' onclick='" + this.selfInstance + ".addSubDept(\"" + rowNo + "\");return false;'><i class='fa fa-plus fa-blue fa-width-fixed'></i>新增</button>";
    },

    //添加下级部门
    addSubDept: function (index) {
        var json = this.datagrid.data[index];
        this.inputInstance.parentDept = json;
        this.goInput();
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            strHtml = "";
            //创建列表添加下级图标
            strHtml += this.createAddIcon(rowNo);

            //创建列表编辑图标
            strHtml += this.createEditButton(rowNo);

            //停用或者启用
            if (rowData.recordStatus == 1) {
                strHtml += this.createLockButton(rowNo);
            }
            else {
                strHtml += this.createUnlockButton(rowNo);
            }

            //创建列表删除图标
            strHtml += this.createDeleteButton(rowNo, false, "delete");

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        //备注字段
        else if (colName == "remark") {
            strHtml = this.createRemarkColumn(rowData);
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    /** ********************以下方法为可选方法********************* */
    //Ajax提交前，附加客户化请求参数
    customSearchModel: function () {
        return ""
    },

    //设置当前选择部门UID
    setDeptUid: function (deptUid) {
        this.setValue("uid", deptUid);
    },

    //删除成功后回调函数
    customDeleteDataCallback: function (ajaxResult) {
        if (ajaxResult.result == -1) return;

        //树重新加载
        this.deptStructureTree.reAsyncChildNodes(this.rootDeptUid);

        this.setDeptUid(this.rootDeptUid);

        //刷新页面所有列表信息（默认根节点）
        this.refreshPageAllList(this.rootDeptUid);
    }
});