//定义数据一览画面JS类
SysApp.Demo.PotentialCustomerContactList = Class.create();

SysApp.Demo.PotentialCustomerContactList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "6%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("客户", "companyName", "14%", SORT_YES));
        this.colList.push(new ColInfo("联络人（职位）", "contactPerson", "10%", SORT_YES));
        this.colList.push(new ColInfo("联络方式", "contactInfo", "12%", SORT_YES));
        this.colList.push(new ColInfo("项目简介", "projectDescription", "20%", SORT_YES));
        this.colList.push(new ColInfo("联络时间", "contactTime", "8%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("填表人", "fillFormUserName", "8%", SORT_YES));
        this.colList.push(new ColInfo("结果", "result", "20%", SORT_YES));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [
            new ColInfo("操作", "operation", "100px", SORT_NO, ALIGN_CENTER),
            new ColInfo("客户", "companyName", "180px", SORT_YES),
            new ColInfo("联络人（职位）", "contactPerson", "150px", SORT_YES),
            new ColInfo("联络方式", "contactInfo", "100px", SORT_YES),
            new ColInfo("项目简介", "projectDescription", "300px", SORT_YES),
            new ColInfo("公司需求", "companyDemand", "300px", SORT_YES),
            new ColInfo("建议及判断", "recommendationsJudgments", "200px", SORT_YES),
            new ColInfo("项目时间节点规划", "projectTimeNode", "200px", SORT_YES),
            new ColInfo("计划", "plan", "200px", SORT_YES),
            new ColInfo("联络时间", "contactTime", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("填表人", "fillFormUserName", "100px", SORT_YES),
            new ColInfo("结果", "result", "300px", SORT_YES),
            new ColInfo("备注", "remark", "150px", SORT_YES)
        ];
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditButton(rowNo);
        }
        //POPUP详细表示
        else if (colName == "companyName") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        //联络时间
        else if (colName == "contactTime" && this.isNotEmpty(rowData[colName])) {
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