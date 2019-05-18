//定义数据一览画面JS类
SysApp.Demo.ChargesInformationList = Class.create();

SysApp.Demo.ChargesInformationList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        //绑定新增按钮
        this.bindEvent("btnAddCharges", "click", this.showCompanyInformationList.bind(this));
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "7%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("所属基地", "baseName", "10%", SORT_YES));
        this.colList.push(new ColInfo("企业", "companyName", "20%", SORT_YES));
        this.colList.push(new ColInfo("单位", "unitName", "8%", SORT_YES));
        this.colList.push(new ColInfo("数量", "quantity", "8%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("企业押金", "businessDeposit", "12%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("收费方式", "chargesTypeName", "8%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("第一次收费日期", "firstChargeDate", "9%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("每期收费金额", "eachChargeAmount", "8%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("下次收款日期", "nextReceiptDate", "8%", SORT_YES, ALIGN_CENTER));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [
            new ColInfo("企业", "companyName", "200px", SORT_YES),
            new ColInfo("所属基地", "baseName", "100px", SORT_YES),
            new ColInfo("单位", "unitName", "100px", SORT_YES),
            new ColInfo("数量", "quantity", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("工位位置", "stationPosition", "200px", SORT_YES),
            new ColInfo("工位租金", "stationRent", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("收租方式", "rentWayName", "100px", SORT_YES),
            new ColInfo("押金方式", "depositMethodName", "100px", SORT_YES),
            new ColInfo("企业押金", "businessDeposit", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("收费方式", "chargesTypeName", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("第一次收费日期", "firstChargeDate", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("每期收费金额", "eachChargeAmount", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("下次收款日期", "nextReceiptDate", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("备注", "remark", "200px", SORT_YES),
            new ColInfo("操作", "operation", "100px", SORT_NO, ALIGN_CENTER)
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
            strHtml = "<a href='#' class='detail-link' onclick='" + this.selfInstance + ".showCompanyDetail(\"" + rowData.companyUid + "\");return false;'>" + rowData[colName].escapeHTML() + "</a>";
        }
        //第一次收费日期
        else if (colName == "firstChargeDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        //下次收款日期
        else if (colName == "nextReceiptDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        else if (colName == "businessDeposit" || colName == "eachChargeAmount" || colName == "stationRent") {
            strHtml = this.formatCny(rowData[colName]);
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    showCompanyDetail: function (companyUid) {
        this.companyDetailInstance.getDetail(companyUid);
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
    },

    showCompanyInformationList: function () {
        var popupListIns = SysApp.Demo.CompanyInformationPopupListIns;
        popupListIns.show();
    }
});