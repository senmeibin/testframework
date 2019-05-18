//定义数据一览画面JS类
SysApp.Sys.AttachmentAccessLogPopupList = Class.create();

SysApp.Sys.AttachmentAccessLogPopupList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //分页显示的记录数[注意：必须实现的方法]
    getPageSize: function () {
        return -1;
    },

    //初期化回调处理
    initCallback: function () {
        this.popupPosition = "top";
        this.autoSearch = false;
        this.initCommonCallback();
    },

    //显示附件访问日志
    showAccessLogList: function (attachmentUid, accessType) {
        this.setValue("attachmentUid", attachmentUid);
        this.setValue("accessType", accessType);
        this.show();
        this.getList(true);
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("用户名", "userName", "280", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("预览/下载时间", "insertDate", "300", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("访问区分", "accessType", "300", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;
        this.datagrid.showTopPager = false;
        this.datagrid.useScroll = true;
        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        if (colName == "accessType") {
            strHtml = rowData[colName] == 0 ? "预览" : "下载";
        }
        else if (colName == "insertDate") {
            strHtml = rowData[colName].formatYMDHM();
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