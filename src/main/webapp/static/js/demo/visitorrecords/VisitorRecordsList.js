//定义数据一览画面JS类
SysApp.Demo.VisitorRecordsList = Class.create();

SysApp.Demo.VisitorRecordsList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "6%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("所属基地", "baseName", "10%", SORT_YES));
        this.colList.push(new ColInfo("来访单位", "visitingUnits", "23%", SORT_YES));
        this.colList.push(new ColInfo("来访时间", "visitingTime", "12%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("接待人", "receptionistName", "12%", SORT_YES));
        this.colList.push(new ColInfo("来访事由", "visitingReasons", "20%", SORT_YES));
        this.colList.push(new ColInfo("备注", "remark", "15%", SORT_YES));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];
        
        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditButton(rowNo);
        }
        //来访时间
        else if (colName == "visitingTime" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
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