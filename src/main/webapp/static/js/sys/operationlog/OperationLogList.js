//定义数据一览画面JS类
SysApp.Sys.OperationLogList = Class.create();

SysApp.Sys.OperationLogList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作日期", "insertDate", "13%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("操作者", "userName", "12%", SORT_YES));
        this.colList.push(new ColInfo("应用名称", "appName", "10%", SORT_YES));
        this.colList.push(new ColInfo("模块名称", "moduleName", "8%", SORT_YES));
        this.colList.push(new ColInfo("方法名称", "functionName", "8%", SORT_YES));
        this.colList.push(new ColInfo("访问路径", "url", "16%", SORT_YES));
        this.colList.push(new ColInfo("请求参数", "parameters", "33%", SORT_YES));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //POPUP详细表示
        if (colName == "insertDate") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        else if (colName == "appName") {
            strHtml = rowData.appName + "[" + rowData.appCode + "]";
        }
        else if (colName == "userName") {
            strHtml = this.createUserDetailLink(rowData);
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    //生成用户明细信息超链接
    createUserDetailLink: function (rowData) {
        return "<a href='#' class='detail-link' onclick='" + this.userDetailInstance + ".getDetail(\"" + rowData["userUid"] + "\");return false;'>" + rowData.userName + "[" + rowData.userCd + "]" + "</a>"
    },

    /** ********************以下方法为可选方法********************* */
    initCallback: function () {
        this.initCommonCallback();

        //设置日志日期检索周期
        var start = this.getValue("insertDate$from_search");
        var end = this.getValue("insertDate$to_search");
        if (this.isEmpty(start)) {
            this.setValue("insertDate$from_search", new Date().addDay(-7).formatYMD());
        }
        if (this.isEmpty(end)) {
            this.setValue("insertDate$to_search", new Date().formatYMD());
        }
        this.autoSearch = false;
    },

    //画面Dom值自定义设置[检索条件Entity取得之前]
    customDomValue: function () {

    },

    //验证后处理
    validateFormAfter: function () {
        // var start = new Date(this.getValue("insertDate$from_search"));
        // var end = new Date(this.getValue("insertDate$to_search"));
        // if (end.diff(start) > 7) {
        //     this.showErrorMessage("操作日期最长查询周期为7天，请调整操作日期的起止区间。");
        //     return false;
        // }
        return true;
    },

    //检索条件Entity自定义设置[检索条件Entity取得之后]
    customSearchEntity: function () {

    },

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "";
    }
});