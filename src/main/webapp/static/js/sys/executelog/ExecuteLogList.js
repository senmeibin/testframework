//定义数据一览画面JS类
SysApp.Sys.ExecuteLogList = Class.create();

SysApp.Sys.ExecuteLogList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        this.popupPosition = "firstTop";
        this.autoSearch = false;

        //默认查询最近一周的执行记录日志
        this.setValue("insertDate$from_search", new Date().addDay(-7).formatYMD());
        this.setValue("insertDate$to_search", new Date().formatYMD());
    },

    initDefaultCondition: function () {
        //默认查询最近一周的执行记录日志
        this.setValue("insertDate$from_search", new Date().addDay(-7).formatYMD());
        this.setValue("insertDate$to_search", new Date().formatYMD());
    },

    showCallback: function () {
        this.initDefaultCondition();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();
        this.colList.push(new ColInfo("功能模块", "functionCode", "40%", SORT_YES));
        this.colList.push(new ColInfo("功能类型", "functionTypeName", "6%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("执行时间", "executeStartDate", "13%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("执行机器", "executeMachine", "8%", SORT_YES));
        this.colList.push(new ColInfo("结果编码", "resultCode", "6%", SORT_YES));
        this.colList.push(new ColInfo("结果消息", "resultMessage", "12%", SORT_YES));
        this.colList.push(new ColInfo("执行成功数", "executeRecordCount", "6%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("执行结果", "result", "5%", SORT_YES, ALIGN_CENTER));
        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;
        this.datagrid.cellClassCallback = this.cellClassCallback.bind(this);

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    cellClassCallback: function (rowData, colName, colInfo) {
        if (colName == "result" && rowData.result != 1) {
            return "warning-red-cell";
        }
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        // 执行结果转换
        if (colName == "result") {
            if (strHtml == "1") {
                strHtml = "成功";
            } else {
                strHtml = "失败";
            }
        }
        //执行时间
        else if (colName == "executeStartDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMDHMS() + "<br/>" + rowData.executeEndDate.formatYMDHMS();
        }
        else if (colName == "sourceSystemName") {
            strHtml = rowData.sourceSystemName + "<br/>" + rowData.destSystemName;
        }
        else if (colName == "functionCode") {
            return "<a href='#' class='detail-link' onclick='" + this.selfInstance + ".showDetail(" + rowNo + ");return false;'>" + rowData.functionCode + "." + rowData.functionMethod + "<br/>[<b>" +
                rowData.sourceSystemName + "->" + rowData.destSystemName + "</b>]" + rowData.functionName;
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    //表格创建后处理
    createListAfter: function () {
        //绑定popovers事件
        this.bindPopoversEvent();
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

    // 取得执行记录表一览画面选中行的Uid
    setExecuteRecordUid: function (executeRecordUid) {
        this.setValue("executeRecordUid", executeRecordUid);
    }
});