//定义数据一览画面JS类
SysApp.Cmn.MessageUserPopupList = Class.create();

SysApp.Cmn.MessageUserPopupList.prototype = Object.extend(new SysApp.Cmn.CommonList(), {

    //设置查询条件
    setCondition: function (messageUid, readTypeCd) {
        //主键UID
        this.setValue("messageUid", messageUid);
        //阅读状态
        this.setValue("readTypeCd", readTypeCd);
    },

    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        this.autoSearch = false;
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        this.colList = new Array();

        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("用户名", "userCd", "12%", SORT_YES, ALIGN_LEFT));
        this.colList.push(new ColInfo("姓名", "userName", "15%", SORT_YES, ALIGN_LEFT));
        this.colList.push(new ColInfo("部门名称", "deptName", "20%", SORT_YES));
        this.colList.push(new ColInfo("职位名称", "positionName", "15%", SORT_YES));
        this.colList.push(new ColInfo("邮箱地址", "userMail", "20%", SORT_YES));
        this.colList.push(new ColInfo("手机号码", "userPhone", "15%", SORT_YES));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        if (colName == "entryDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    }
});