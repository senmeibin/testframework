//定义数据一览画面JS类
SysApp.Cmn.UserPopupList = Class.create();

SysApp.Cmn.UserPopupList.prototype = Object.extend(new SysApp.Cmn.CommonList(), {
    //初期化回调函数
    initCallback: function () {
        this.autoSearch = false;

        this.popupPosition = "firstTop";

        this.initCommonCallback();

        this.searchMethod = "searchNoCustomerUser";

        //批量添加用户按钮事件
        this.bindEvent("btnBatchAddCustomerUser", 'click', this.onClick_BatchAddCustomerUser.bind(this));
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "15%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("用户名", "userCd", "20%", SORT_YES));
        this.colList.push(new ColInfo("姓名", "userName", "18%", SORT_YES));
        this.colList.push(new ColInfo("手机号码", "userPhone", "15%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("邮箱地址", "userMail", "15%", SORT_YES));
        this.colList.push(new ColInfo("是否启用", "recordStatus", "15%", SORT_YES, ALIGN_CENTER));

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
        else if (colName == "userCd") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        else if (colName == "recordStatus") {
            strHtml = rowData[colName] == "1" ? "启用" : "<font color='red'>停用</font>";
        }
        //日期字段
        else if (colName == "entryDate" || colName == "birthday") {
            strHtml = rowData[colName].formatYMD();
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    //创建添加按钮
    addCustomerUserButton: function (uid) {
        return "<button type='button' class='btn btn-primary' onclick='" + this.selfInstance + ".onClick_addCustomerUser(\"" + uid + "\") ;return false;' ><i class='fa fa-check fa-width-fixed'></i>选择</button>";
    },

    //单个添加客户所属用户
    onClick_addCustomerUser: function (uid) {
        this.showConfirm("确定将该用户加入到客户所属用户中吗？", this.addCustomerUser.bind(this, uid));
    },

    //批量添加客户所属用户
    onClick_BatchAddCustomerUser: function () {
        //校验是否有选择用户
        if (this.datagrid.selectedIndexs.length == 0 && this.datagrid.selectedIndex == -1) {
            this.showMessage("请选择用户。", MSG_TYPE_WARNING);
            return;
        }
        this.showConfirm("确定将选择用户加入到客户所属用户中吗？", this.addCustomerUser.bind(this, this.getSelectedIds(this.datagrid, this.getPrimaryKey())));
    },

    //添加客户所属用户
    addCustomerUser: function (uids) {
        var params = "uids=" + uids + "&customerUid=" + this.getValue("customerUid");
        //Ajax处理前显示提示画面
        this.showProcessing(false);

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "addCustomerUserByCustomer", this.addCustomerUserCallback.bind(this), params);
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
        SysApp.Cmn.CustomerUserByCustomerInnerListIns.getList(true);
    },

    //设定客户UID
    setCustomerUid: function (customerUid) {
        this.setValue("customerUid", customerUid);
    }
});