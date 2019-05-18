//定义数据一览画面JS类
SysApp.Sys.LoginLogList = Class.create();

SysApp.Sys.LoginLogList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("登录日期", "insertDate", "18%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("用户名", "userCd", "20%", SORT_YES));
        this.colList.push(new ColInfo("姓名", "userName", "20%", SORT_YES));
        this.colList.push(new ColInfo("登录IP", "remoteIp", "20%", SORT_YES));
        this.colList.push(new ColInfo("登录类型", "loginTypeName", "10%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("登录结果", "loginResultName", "10%", SORT_YES, ALIGN_CENTER));

        this.datagrid.cssClass = "app-table-list";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //POPUP详细表示
        if (colName == "userCd") {
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
    initCallback: function () {
        this.initCommonCallback();

        //设置日志日期检索周期
        var start = this.getValue("insertDate$from_search");
        var end = this.getValue("insertDate$to_search");
        if (this.isEmpty(start)) {
            this.setValue("insertDate$from_search", new Date().addDay(-6).formatYMD());
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
        var start = new Date(this.getValue("insertDate$from_search"));
        var end = new Date(this.getValue("insertDate$to_search"));
        if (end.diff(start) > 90) {
            this.showErrorMessage("登录日期查询条件最多查询90天内的数据，请调整登录日期的起止查询区间。");
            return false;
        }
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