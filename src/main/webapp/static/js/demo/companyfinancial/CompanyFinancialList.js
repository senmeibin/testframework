//定义数据一览画面JS类
SysApp.Demo.CompanyFinancialList = Class.create();

SysApp.Demo.CompanyFinancialList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
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

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [

            new ColInfo("操作", "operation", "100px", SORT_NO, ALIGN_CENTER),
            new ColInfo("企业名称", "companyName", "100px", SORT_YES),
            new ColInfo("年末总资产(万元)", "totalAssets", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("年末负债总额(万元)", "totalLiabilities", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("年末固定资产总额(万元)", "totalFixedAssets", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("年末完成固定资产投资额(万元)", "totalCompletedAssets", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("年份", "particularYear", "100px", SORT_YES),
            new ColInfo("营业收入(万元)", "businessIncome", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("主营业收入(万元)", "mainBusinessIncome", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("高新技术产品销售收入(万元)", "highTechIncome", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("软件产品及服务收入(万元)", "softwareServicesIncome", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("工业总产值(万元)", "grossOutput", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("高新技术产品产值(万元)", "highTechOutput", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("自主知识产权产品产值(万元)", "independentOutput", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("增加值(万元)", "increaseValue", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("出口创汇(万元)", "exportEarnings", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("当年R&D投入(万元)", "rdInvestment", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("高新技术产品出口额(万元)", "highTechProductsOutlet", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("税后利润(万元)", "afterTaxProfits", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("实际上缴税费(万元)", "totalTax", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("增值税(万元)", "addedValueTax", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("营业税(万元)", "businessTax", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("所得税(万元)", "incomeTax", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("个人所得税(万元)", "personalIncomeTax", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("其他税(万元)", "otherTax", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("实际减免税费总额(万元)", "totalDeductionsTax", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("减免增值税(万元)", "deductionsAddedValueTax", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("减免营业额(万元)", "deductionsBusinessTax", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("减免所得税(万元)", "deductionsIncomeTax", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("研发加计扣除减免(万元)", "deductionsRdTax", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("高新技术企业专项减免(万元)", "deductionsHighTechTax", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("备注", "remark", "100px", SORT_YES)
        ];
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