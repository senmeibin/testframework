//定义数据一览画面JS类
SysApp.Demo.CompanyCopyrightInnerList = Class.create();

SysApp.Demo.CompanyCopyrightInnerList.prototype = Object.extend(new SysApp.Demo.CompanyExtendedCommonList(), {
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
        this.colList.push(new ColInfo("企业名称", "companyName", "15%", SORT_YES));
        this.colList.push(new ColInfo("年份", "year", "8%", SORT_YES));
        this.colList.push(new ColInfo("当前承担国家级科技计划项目数", "nationalPlanningProjectNum", "15%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("当前获得省级以上奖励(万元)", "provincialLevelNum", "15%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("购买（被许可）国外专利", "foreignPatentNum", "12%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("技术合同交易数量", "technicalContractNum", "9%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("技术合同交易额(万元)", "technicalContractAmount", "9%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("研发加计备案数", "rdCasesNum", "9%", SORT_YES, ALIGN_RIGHT));

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
        else if (colName == "provincialLevelNum" || colName == "technicalContractAmount" || colName == "stateSubsidyAmount" || colName == "provincialSubsidyAmount" || colName == "municipalSubsidyAmount") {
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