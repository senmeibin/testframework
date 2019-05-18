//定义数据一览画面JS类
SysApp.Demo.CompanyStockList = Class.create();

SysApp.Demo.CompanyStockList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
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
        this.colList.push(new ColInfo("股东类型", "shareholderTypeName", "8%", SORT_YES));
        this.colList.push(new ColInfo("股东名称", "shareholderName", "8%", SORT_YES));
        this.colList.push(new ColInfo("证件类型", "certificateTypeName", "8%", SORT_YES));
        this.colList.push(new ColInfo("上市公司", "listedCompanyName", "10%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("入选千人计划", "thousandsPeoplePlanName", "10%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("所占股份", "sharesProportion", "8%", SORT_YES));
        this.colList.push(new ColInfo("投资总额(万元)", "totalInvestment", "10%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("第一投资时间", "firstInvestmentDate", "10%", SORT_YES, ALIGN_CENTER));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [
            new ColInfo("企业名称", "companyName", "200px", SORT_YES),
            new ColInfo("股东类型", "shareholderTypeName", "100px", SORT_YES),
            new ColInfo("股东名称", "shareholderName", "100px", SORT_YES),
            new ColInfo("证件类型", "certificateTypeName", "100px", SORT_YES),
            new ColInfo("上市公司", "listedCompanyName", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("入选千人计划", "thousandsPeoplePlanName", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("所占股份", "sharesProportion", "100px", SORT_YES),
            new ColInfo("投资总额(万元)", "totalInvestment", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("境外公司或外籍", "overseasCompanyName", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("外资部门所占股份总和(%)", "totalShareForeignCapital", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("上市企业所占股份总和(%)", "totalSharesListedCompany", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("风险投资（基金）公司", "fundCompanyName", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("第一投资时间", "firstInvestmentDate", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("备注", "remark", "100px", SORT_YES),
            new ColInfo("操作", "operation", "100px", SORT_NO, ALIGN_CENTER)
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
        else if (colName == "totalInvestment") {
            strHtml = this.formatCny(rowData[colName]);
        }
        //第一投资时间
        else if (colName == "firstInvestmentDate" && this.isNotEmpty(rowData[colName])) {
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