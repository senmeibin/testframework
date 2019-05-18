//定义数据一览画面JS类
SysApp.Demo.ResourcesDockingRecordInnerList = Class.create();

SysApp.Demo.ResourcesDockingRecordInnerList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
    //分页显示的记录数[注意：必须实现的方法]
    getPageSize: function () {
        return -1;
    },

    hide: function () {
        $(this.getMainContentClassName()).hide();
    },

    show: function () {
        $(this.getMainContentClassName()).show();
    },
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("入孵企业", "companyName", "20%", SORT_YES));
        this.colList.push(new ColInfo("对接内容", "dockingContent", "26%", SORT_YES));
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

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //对接时间
        if (colName == "dockingDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    setThirdPartyServiceUid: function (thirdPartyServiceUid) {
        this.setValue("thirdPartyServiceUid", thirdPartyServiceUid);
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