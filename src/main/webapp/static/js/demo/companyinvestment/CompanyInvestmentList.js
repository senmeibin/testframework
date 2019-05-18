//定义数据一览画面JS类
SysApp.Demo.CompanyInvestmentList = Class.create();

SysApp.Demo.CompanyInvestmentList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "6%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("企业", "companyName", "20%", SORT_YES));
        this.colList.push(new ColInfo("接收投资时间", "investmentDate", "10%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("投资人", "investor", "10%", SORT_YES));
        this.colList.push(new ColInfo("投资金额(万元)", "investmentAmount", "10%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("占股权比例(%)", "proportionOfStock", "10%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("账面估值(万元)", "bookValuation", "10%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("属于孵化基金", "incubationFundName", "10%", SORT_YES));
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
            strHtml = this.createDetailButton(rowNo);
        }
        //POPUP详细表示
        else if (colName == "companyName") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        //接收投资时间
        else if (colName == "investmentDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        else if (colName == "investmentAmount" || colName == "bookValuation") {
            strHtml = this.formatCny(rowData[colName]);
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