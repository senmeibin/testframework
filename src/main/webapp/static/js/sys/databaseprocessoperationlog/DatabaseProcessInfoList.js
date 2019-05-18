//定义数据一览画面JS类
SysApp.Sys.DatabaseProcessInfoList = Class.create();

SysApp.Sys.DatabaseProcessInfoList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //分页显示的记录数[注意：必须实现的方法]
    getPageSize: function () {
        return -1;
    },

    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        this.searchMethod = "searchProcessInfo";
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("进程号", "processId", "4%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("数据库名@用户", "databaseName", "9%", SORT_YES));
        this.colList.push(new ColInfo("主机地址", "hostName", "12%", SORT_YES));
        this.colList.push(new ColInfo("执行类型", "executeCommand", "7%", SORT_YES));
        this.colList.push(new ColInfo("执行状态", "executeState", "10%", SORT_YES));
        this.colList.push(new ColInfo("执行信息", "executeInformation", "40%", SORT_YES));
        this.colList.push(new ColInfo("等待时间（S）", "executeWaitTime", "8%", SORT_YES, ALIGN_RIGHT));

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //清除进程绑定
    killProcess: function (rowNo) {
        this.showConfirm("确定清除此进程吗？", this.killProcessAjax.bind(this, rowNo));
    },

    //清除进程ajax
    killProcessAjax: function (rowNo) {
        var params = "inputJson=" + JsonUtility.ToJSON(this.datagrid.getRowData(rowNo));

        //处理中提示消息
        this.showProcessing(false);

        //Ajax処理（POST方式）
        AjaxIns.sendPOST(this.controller + "killProcess", this.killProcessAjaxCallback.bind(this), params);
    },

    //清除进程返回处理
    killProcessAjaxCallback: function (response) {
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);
        if (ajaxResult.result > 0) {
            this.showInfoMessage("进程清除成功。", this.refreshList.bind(this));
        }
        else {
            this.showErrorMessage(ajaxResult.message);
        }
    },

    //创建停止进程信息图标
    createAddIcon: function (rowNo) {
        return "<button type='button' class='btn btn-danger' onclick='" + this.selfInstance + ".killProcess(" + rowNo + ");return false;'><i class='fa fa-eraser fa-width-fixed'></i>清除进程</button>";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建停止进程信息的图标
            strHtml = this.createAddIcon(rowNo);
        }
        else if (colName == "databaseName") {
            strHtml = rowData[colName] + "@" + rowData.userName;
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
    }
});