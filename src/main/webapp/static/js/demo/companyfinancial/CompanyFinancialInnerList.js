//定义数据一览画面JS类
SysApp.Demo.CompanyFinancialInnerList = Class.create();

SysApp.Demo.CompanyFinancialInnerList.prototype = Object.extend(new SysApp.Demo.CompanyExtendedCommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "6%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("企业名称", "companyName", "20%", SORT_YES));
        this.colList.push(new ColInfo("年份", "particularYear", "8%", SORT_YES));
        this.colList.push(new ColInfo("年末总资产(万元)", "totalAssets", "10%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("年末负债总额(万元)", "totalLiabilities", "10%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("年末固定资产总额(万元)", "totalFixedAssets", "12%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("年末完成固定资产投资额(万元)", "totalCompletedAssets", "12%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("营业收入(万元)", "businessIncome", "10%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("工业总产值(万元)", "grossOutput", "10%", SORT_YES, ALIGN_RIGHT));

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
        else if (colName == "particularYear" || colName == "remark") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }
        // 字符串的场合
        else {
            // HTML特殊字符转换处理
            strHtml = this.formatCny(rowData[colName]);
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