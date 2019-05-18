//定义数据一览画面JS类
SysApp.Sys.TableSpaceOptimizeLogList = Class.create();

SysApp.Sys.TableSpaceOptimizeLogList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("逻辑表名称", "logicName", "25%", SORT_YES, ALIGN_LEFT));
        this.colList.push(new ColInfo("物理表名称", "physicalName", "25%", SORT_YES, ALIGN_LEFT));
        this.colList.push(new ColInfo("优化前占用大小（M）", "optimizeBeforeSize", "12%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("优化后占用大小（M）", "optimizeAfterSize", "12%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("表记录条数（条）", "recordCount", "12%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("优化时间", "insertDate", "12%", SORT_YES, ALIGN_CENTER));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.totalColList.push("optimizeBeforeSize");
        this.datagrid.totalColList.push("optimizeAfterSize");
        this.datagrid.totalColList.push("recordCount");

        this.datagrid.cssClass = "app-table-list";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {

        }
        //表空间Size大小统计字段
        else if (colName == "optimizeBeforeSize" || colName == "optimizeAfterSize") {
            strHtml = this.formatNum(rowData[colName], 2);
        }
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