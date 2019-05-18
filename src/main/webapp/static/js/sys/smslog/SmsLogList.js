//定义数据一览画面JS类
SysApp.Sys.SmsLogList = Class.create();

SysApp.Sys.SmsLogList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        //设置日志日期检索周期
        var start = this.getValue("sendDate$from_search");
        var end = this.getValue("sendDate$to_search");
        if (this.isEmpty(start)) {
            this.setValue("sendDate$from_search", new Date().addDay(-30).formatYMD());
        }
        if (this.isEmpty(end)) {
            this.setValue("sendDate$to_search", new Date().formatYMD());
        }
        this.autoSearch = false;
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("应用名称", "appName", "12%", SORT_YES));
        this.colList.push(new ColInfo("短信内容", "message", "32%", SORT_YES));
        this.colList.push(new ColInfo("短信供应商", "vendor", "8%", SORT_YES));
        this.colList.push(new ColInfo("手机号码", "mobile", "10%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("发送时间", "sendDate", "12%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("发送结果", "sendResult", "8%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("错误消息", "errorMessage", "18%", SORT_YES));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cellClassCallback = this.cellClassCallback.bind(this);

        this.datagrid.cssClass = "app-table-list";
    },

    cellClassCallback: function (rowData, colName, colInfo) {
        if (colName == "sendResult" && rowData[colName] != 1) {
            return "warning-red-cell";
        }
        return "";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //POPUP详细表示
        if (colName == "message") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        else if (colName == "appName") {
            strHtml = rowData.appName + "[" + rowData.appCode + "]";
        }
        //发送时间
        else if (colName == "sendDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMDHMS();
        }
        else if (colName == "sendResult") {
            strHtml = rowData[colName] == 1 ? "成功" : "失败";
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    //验证后处理
    validateFormAfter: function () {
        // var start = new Date(this.getValue("sendDate$from_search"));
        // var end = new Date(this.getValue("sendDate$to_search"));
        // if (end.diff(start) > 7) {
        //     this.showErrorMessage("短信日志最长查询周期为7天，请调整短信发送日期的起止区间。");
        //     return false;
        // }
        return true;
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