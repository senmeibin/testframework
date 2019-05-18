//定义数据一览画面JS类
SysApp.Demo.CompanyCopyrightList = Class.create();

SysApp.Demo.CompanyCopyrightList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
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

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [
            new ColInfo("操作", "operation", "100px", SORT_NO, ALIGN_CENTER),
            new ColInfo("企业名称", "companyName", "200px", SORT_YES),
            new ColInfo("年份", "year", "100px", SORT_YES),
            new ColInfo("当前承担国家级科技计划项目数", "nationalPlanningProjectNum", "200px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("当前获得省级以上奖励(万元)", "provincialLevelNum", "180px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("购买（被许可）国外专利", "foreignPatentNum", "180px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("技术合同交易数量", "technicalContractNum", "150px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("技术合同交易额(万元)", "technicalContractAmount", "150px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("研发加计备案数", "rdCasesNum", "150px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("累计工程中心数", "totalEngineeringNum", "150px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("国家级", "enNationalLevelNum", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("省级", "enProvincialLevelNum", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("市级", "enMunicipalLevelNum", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("累计项目数量", "totalProjectNum", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("国家级", "projectNationalLevelNum", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("省级", "projectProvincialLevelNum", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("市级", "projectMunicipalLevelNum", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("累计获得国家资助经费金额(万元)", "stateSubsidyAmount", "180px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("累计获得省级资助经费金额(万元)", "provincialSubsidyAmount", "180px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("累计获得市级资助经费金额(万元)", "municipalSubsidyAmount", "180px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("当年知识产权申请数", "iprApplicationsNum", "150px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("当年发明专利申请数", "patentInventionNum", "150px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("当前知识产权授权数", "intellectualPropertyNum", "150px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("发明专利授权数", "inventionPatentsAuthNum", "150px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("拥有有效知识产权数", "effectiveIntellectualNum", "1050px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("发明专利数", "inventionPatentsNum", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("软件著作权登记数", "softwareCopyrightNum", "150px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("植物新品种", "newPlantNum", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("集成电路布图设计登记数", "icLayoutNum", "180px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("其他知识产权登记数", "otherIntellectualNum", "180px", SORT_YES, ALIGN_RIGHT),
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