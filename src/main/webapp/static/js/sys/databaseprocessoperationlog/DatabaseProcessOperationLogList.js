//定义数据一览画面JS类
SysApp.Sys.DatabaseProcessOperationLogList = Class.create();

SysApp.Sys.DatabaseProcessOperationLogList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("主机地址", "hostName", "12%", SORT_YES));
        this.colList.push(new ColInfo("数据库名@用户", "databaseName", "9%", SORT_YES));
        this.colList.push(new ColInfo("等待时间(S)", "executeWaitTime", "7%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("执行信息", "executeInformation", "52%", SORT_YES));
        this.colList.push(new ColInfo("操作人", "insertUserName", "6%", SORT_YES));
        this.colList.push(new ColInfo("操作时间", "insertDate", "12%", SORT_YES, ALIGN_CENTER));

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditIcon(rowNo);

            //创建列表删除图标
            strHtml += this.createDeleteIcon(rowNo);
        }
        else if (colName == "databaseName") {
            strHtml = rowData[colName] + "@" + rowData.userName;
        }
        //操作时间
        else if (colName == "insertDate") {
            strHtml = rowData[colName].formatYMDHM();
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