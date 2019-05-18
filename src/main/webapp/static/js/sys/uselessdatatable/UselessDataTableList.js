//定义数据一览画面JS类
SysApp.Sys.UselessDataTableList = Class.create();

SysApp.Sys.UselessDataTableList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //分页显示的记录数[注意：必须实现的方法]
    getPageSize: function () {
        return -1;
    },

    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        this.autoSearch = false;

        //删除处理确认消息
        this.deleteConfirmMessage = "<label style='color:#FF0000;'>将清除已逻辑删除的记录，确定要清除吗？</label>";
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("表名称", "tableName", "35%", SORT_YES));
        this.colList.push(new ColInfo("表注释", "tableComment", "35%", SORT_YES));
        this.colList.push(new ColInfo("表记录条数", "recordCount", "10%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("逻辑删除记录数", "logicDeleteRecordCount", "10%", SORT_YES, ALIGN_RIGHT));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.totalColList.push("recordCount");
        this.datagrid.totalColList.push("logicDeleteRecordCount");

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表清除图标
            strHtml = this.createClearButton(rowNo);

            //创建优化按钮
            strHtml += this.createOptimizeButton(rowNo);

            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    //创建清除按钮
    createClearButton: function (rowNo) {
        return "<button type='button' class='btn btn-danger delete' onclick='" + this.selfInstance + ".onClick_Delete(" + rowNo + ");return false;'><i class='fa fa-trash-o'></i>&nbsp;清除</button>";
    },

    //创建优化按钮
    createOptimizeButton: function (rowNo) {
        return "<button type='button' class='btn btn-danger' onclick='" + this.selfInstance + ".onClick_Optimize(" + rowNo + ");return false;'><i class='fa fa-check'></i>&nbsp;优化</button>";
    },

    //清除处理
    onClick_Delete: function (rowNo) {
        var rowData = this.getRowData(rowNo);
        this.showConfirm(this.deleteConfirmMessage, this.deleteData.bind(this, rowData.tableName));
    },

    //清除处理
    deleteData: function (tableName) {
        var params = "tableName=" + tableName;

        //Ajax处理前显示提示画面
        this.showProcessing(false);

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + this.deleteMethod, this.deleteDataCallback.bind(this), params);
    },

    //表空间优化处理
    onClick_Optimize: function (rowNo) {
        var rowData = this.getRowData(rowNo);
        this.showConfirm("<label style='color:#FF0000;'>表空间优化将锁定表并需要一定的处理时间，确定要优化吗？</label>", this.optimizeData.bind(this, rowData.tableName));
    },

    //表空间优化处理
    optimizeData: function (tableName) {
        var params = "tableName=" + tableName;

        //Ajax处理前显示提示画面
        this.showProcessing(false);

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "optimizeData", this.optimizeDataCallback.bind(this), params);
    },

    //表空间优化处理[Callback后处理]
    optimizeDataCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //处理成功的场合、显示处理结果消息后刷新一览画面
        if (ajaxResult.result > 0) {
            this.showMessage(ajaxResult.message, MSG_TYPE_INFO);
        }
        else {
            this.showMessage(ajaxResult.message, MSG_TYPE_ERROR);
        }
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