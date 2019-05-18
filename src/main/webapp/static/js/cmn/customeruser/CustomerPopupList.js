//定义数据一览画面JS类
SysApp.Cmn.CustomerPopupList = Class.create();

SysApp.Cmn.CustomerPopupList.prototype = Object.extend(new SysApp.Cmn.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.searchMethod = "searchNoCustomerUser";
        this.autoSearch = false;
        this.initCommonCallback();

        //批量添加客户按钮事件
        this.bindEvent("btnBatchAddCustomerUser", 'click', this.onClick_BatchAddCustomerUser.bind(this));
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();
        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("客户名称", "customerName", "16%", SORT_YES));
        this.colList.push(new ColInfo("地址", "address", "12%", SORT_YES));
        this.colList.push(new ColInfo("电话", "telephone", "10%", SORT_YES));
        this.colList.push(new ColInfo("主客户名称", "mainCustomerName", "10%", SORT_YES));
        this.colList.push(new ColInfo("开票注册地址", "invoiceRegisteredAddress", "10%", SORT_YES));
        this.colList.push(new ColInfo("开票电话", "invoiceTelephone", "8%", SORT_YES));
        this.colList.push(new ColInfo("开票开户行", "invoiceBank", "8%", SORT_YES));
        this.colList.push(new ColInfo("开票帐号", "invoiceAccountNo", "8%", SORT_YES));
        this.colList.push(new ColInfo("开票税号", "invoiceTaxNo", "8%", SORT_YES));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;
        this.datagrid.useCheckOne = true;
        this.datagrid.showTopPager = false;
        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            if (SysApp.Cmn.isUdcServer) {
                //创建添加按钮
                strHtml = this.addCustomerUserButton(rowData["uid"]);
            }
        }
        //POPUP详细表示
        else if (colName == "enterpriseUid") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }

        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    /** ********************以下方法为可选方法********************* */
    //画面Dom值自定义设置[检索条件Entity取得之前]
    customDomValue: function () {

    },

    //检索条件Entity自定义设置[检索条件Entity取得之后]
    customSearchEntity: function () {

    },

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "";
    },

    //创建添加按钮
    addCustomerUserButton: function (uid) {
        return "<button type='button' class='btn btn-primary' onclick='" + this.selfInstance + ".onClick_addCustomerUser(\"" + uid + "\") ;return false;' ><i class='fa fa-check fa-width-fixed'></i>选择</button>";
    },

    //单个添加客户所属用户
    onClick_addCustomerUser: function (uid) {
        this.showConfirm("确定将选择的合同客户添加到用户可查阅客户列表中吗？", this.addCustomerUser.bind(this, uid));
    },

    //批量添加客户所属用户
    onClick_BatchAddCustomerUser: function () {
        //校验是否有选择合同客户
        if (this.datagrid.selectedIndexs.length == 0 && this.datagrid.selectedIndex == -1) {
            this.showMessage("请选择合同客户。", MSG_TYPE_WARNING);
            return;
        }
        this.showConfirm("确定将选择的合同客户批量添加到用户可查阅客户列表中吗？", this.addCustomerUser.bind(this, this.getSelectedIds(this.datagrid, this.getPrimaryKey())));
    },

    //添加客户所属用户
    addCustomerUser: function (uids) {
        var params = "uids=" + uids + "&userUid=" + this.getValue("userUid");
        //Ajax处理前显示提示画面
        this.showProcessing(false);

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.customerusercontroller + "addCustomerUserByUser", this.addCustomerUserCallback.bind(this), params);
    },

    //选择回调函数
    addCustomerUserCallback: function (response) {
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);
        if (ajaxResult.result > 0) {
            this.showInfoMessage(ajaxResult.message, this.refreshList.bind(this));
        }
        else {
            this.showErrorMessage(ajaxResult.message);
        }
    },

    //刷新列表
    refreshList: function () {
        this.hide();
        SysApp.Cmn.CustomerUserByUserInnerListIns.getList(true);
    },

    //设置用户UID
    setUserUid: function (uid) {
        this.setValue("userUid", uid);
    }
});