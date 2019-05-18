//定义数据一览画面JS类
SysApp.Demo.CompanyAdditionalInnerList = Class.create();

SysApp.Demo.CompanyAdditionalInnerList.prototype = Object.extend(new SysApp.Demo.CompanyExtendedCommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        this.autoSearch = false;
        this.searchMethod = "searchNoPage";
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "6%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("企业", "companyName", "20%", SORT_YES));
        this.colList.push(new ColInfo("证券代码", "stockCode", "10%", SORT_YES));
        this.colList.push(new ColInfo("上市时间", "listedTime", "10%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("上市市场", "listedTypeName", "10%", SORT_YES));
        this.colList.push(new ColInfo("总股本", "totalShare", "10%", SORT_YES));
        this.colList.push(new ColInfo("年底市值(万元)", "yearEndMarketValue", "10%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("退市时间", "delistingDate", "10%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("备注", "remark", "12%", SORT_YES));

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
        //上市时间
        else if (colName == "listedTime" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        //年底市值
        else if (colName == "yearEndMarketValue") {
            strHtml = this.formatCny(rowData[colName]);
        }
        //退市时间
        else if (colName == "delistingDate" && this.isNotEmpty(rowData[colName])) {
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