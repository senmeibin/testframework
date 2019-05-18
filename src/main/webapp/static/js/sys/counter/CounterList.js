//定义数据一览画面JS类
SysApp.Sys.CounterList = Class.create();

SysApp.Sys.CounterList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("计数器类型", "typeCd", "7%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("分组条件1", "groupCounter1", "25%", SORT_YES));
        this.colList.push(new ColInfo("分组条件2", "groupCounter2", "25%", SORT_YES));
        this.colList.push(new ColInfo("分组条件3", "groupCounter3", "7%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("分组条件4", "groupCounter4", "7%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("分组条件5", "groupCounter5", "7%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("当前计数值", "counterNumber", "6%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("备注", "remark", "14%", SORT_YES));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        // 字符串的场合
        if (typeof rowData[colName] == "string") {
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