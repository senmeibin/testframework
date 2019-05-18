//定义数据一览画面JS类
SysApp.Demo.ResourcesDockingRecordList = Class.create();

SysApp.Demo.ResourcesDockingRecordList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        //绑定新增按钮
        this.bindEvent("btnAddDock", "click", this.showCompanyInformationList.bind(this));
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "6%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("入孵企业", "companyName", "20%", SORT_YES));
        this.colList.push(new ColInfo("对接内容", "dockingContent", "20%", SORT_YES));
        this.colList.push(new ColInfo("对接时间", "dockingDate", "8%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("数量", "quantity", "8%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("对接人", "dockingPersonName", "8%", SORT_YES));
        this.colList.push(new ColInfo("对接机构", "thirdPartyServiceName", "8%", SORT_YES));
        this.colList.push(new ColInfo("对接联系人", "thirdPartyServiceContactsName", "10%", SORT_YES));
        this.colList.push(new ColInfo("跟踪", "following", "10%", SORT_YES));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [
            new ColInfo("操作", "operation", "100px", SORT_NO, ALIGN_CENTER),
            new ColInfo("入孵企业", "companyName", "200px", SORT_YES),
            new ColInfo("对接内容", "dockingContent", "200px", SORT_YES),
            new ColInfo("对接时间", "dockingDate", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("数量", "quantity", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("对接人", "dockingPersonName", "100px", SORT_YES),
            new ColInfo("对接机构", "thirdPartyServiceName", "100px", SORT_YES),
            new ColInfo("对接联系人", "thirdPartyServiceContactsName", "150px", SORT_YES),
            new ColInfo("跟踪", "following", "200px", SORT_YES),
            new ColInfo("备注", "remark", "200px", SORT_YES)
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
        //POPUP详细表示
        else if (colName == "dockingContent") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        //对接时间
        else if (colName == "dockingDate" && this.isNotEmpty(rowData[colName])) {
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
    },

    showCompanyInformationList: function () {
        var popupListIns = SysApp.Demo.CompanyInformationPopupListIns;
        popupListIns.show();
    },

    showCompanyDetail: function (companyUid) {
        this.companyDetailInstance.getDetail(companyUid);
    }
});