//定义数据一览画面JS类
SysApp.Sys.OnlineUserList = Class.create();

SysApp.Sys.OnlineUserList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //分页显示的记录数[注意：必须实现的方法]
    getPageSize: function () {
        return 100;
    },

    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        //强制退出
        this.bindEvent("btnForceLogout", "click", this.onClick_ForceLogout.bind(this, null));
    },

    onClick_ForceLogout: function (rowNo) {
        if (this.isNull(rowNo) && this.datagrid.selectedIndexs.length == 0 && this.datagrid.selectedIndex == -1) {
            this.showMessage("请从在线用户列表中选择需要强制退出的数据。", MSG_TYPE_WARNING);
        }
        else {
            this.showConfirm("对选择的用户执行强制退出处理后，需要用户重新登录系统，<br />确定要强制退出吗？", this.forceLogout.bind(this, rowNo));
        }
    },

    //强制退出
    forceLogout: function (rowNo) {
        var params;

        //批量强制退出处理
        if (this.isNull(rowNo)) {
            params = "uid=" + this.getSelectedIds(this.datagrid, this.getPrimaryKey());
        }
        //单个用户强制退出处理
        else {
            params = "uid=" + this.datagrid.data[rowNo].uid;
        }

        //Ajax处理前显示提示画面
        this.showProcessing(false);

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "forceLogout", this.forceLogoutCallback.bind(this), params);
    },

    //强制退出[Callback后处理]
    forceLogoutCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //处理成功的场合、显示处理结果消息后刷新一览画面
        if (ajaxResult.result > 0) {
            this.showMessage(ajaxResult.message, MSG_TYPE_INFO, this.refreshList.bind(this));
        }
        else {
            this.showMessage(ajaxResult.message, MSG_TYPE_ERROR);
        }
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "10%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("用户名", "userCd", "20%", SORT_YES));
        this.colList.push(new ColInfo("姓名", "userName", "18%", SORT_YES));
        this.colList.push(new ColInfo("登录IP", "remoteIp", "18%", SORT_YES));
        this.colList.push(new ColInfo("登录时间", "insertDate", "17%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("最后访问时间", "updateDate", "17%", SORT_YES, ALIGN_CENTER));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.useCheckAll = true;
        this.datagrid.useCheckOne = true;
        this.datagrid.cssClass = "app-table-list app-table-list-input";
        this.datagrid.divPager = this.getDom("divPager");

        this.datagrid.createCheckboxDisabledCallback = this.createCheckboxDisabledCallback.bind(this);
    },

    createCheckboxDisabledCallback: function (rowData) {
        //登录者本人的场合
        if (rowData.uid == window.loginUserUid) {
            return true;
        }
        return false;
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            if (rowData.uid != window.loginUserUid) {
                //创建列表编辑图标
                strHtml = this.createLogoutIcon(rowNo);
            }
            else {
                strHtml = "&nbsp;";
            }
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    //创建强制退出图标
    createLogoutIcon: function (rowNo) {
        return "<button type='button' class='btn btn-danger' onclick='" + this.selfInstance + ".onClick_ForceLogout(\"" + rowNo + "\");return false;'><i class='fa fa-mars-double fa-width-fixed'></i>强制退出</button>";
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
    }
});