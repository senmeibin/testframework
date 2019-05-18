//定义数据一览画面JS类
SysApp.Cmn.FilePreviewList = Class.create();

SysApp.Cmn.FilePreviewList.prototype = Object.extend(new SysApp.Cmn.CommonList(), {
    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();
        this.colList = new Array();

        this.colList.push(new ColInfo("文件名称", "fileName", "48%", SORT_YES));
        this.colList.push(new ColInfo("上传日期", "insertDate", "17%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("文件大小", "fileSize", "10%", SORT_YES, ALIGN_RIGHT));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;
        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    createListBefore: function () {
        $(".panel-body", this.getMainContentClassName()).show();
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];
        //POPUP详细表示
        if (colName == "fileName") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        else if (colName == "appName") {
            strHtml = rowData.appName + "[" + rowData.appCode + "]";
        }
        else if (colName == "insertDate") {
            strHtml = rowData[colName].formatYMDHMS();
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    download: function (uid) {
        window.open(this.controller + "download?uid=" + uid);
    },

    //创建详细POPUP表示的超链接
    createDetailLink: function (rowData, colName, rowNo) {
        //附件文件名称支持下载功能
        return "<a target='_blank' href='" + this.controller + "download?uid=" + rowData.uid + "' class='detail-link'><i class='fa fa-download fa-lg fa-blue'></i>" + rowData[colName].escapeHTML() + "</a>";
    },

    //表格创建后处理
    createListAfter: function () {
    },

    initCallback: function () {
        this.initCommonCallback();
    }
});