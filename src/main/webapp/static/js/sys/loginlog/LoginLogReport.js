//定义数据一览画面JS类
SysApp.Sys.LoginLogReport = Class.create();

SysApp.Sys.LoginLogReport.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("所属大区", "areaName", "10%", SORT_YES));
        this.colList.push(new ColInfo("所属分公司", "companyName", "10%", SORT_YES));
        this.colList.push(new ColInfo("所属部门", "deptName", "10%", SORT_YES));
        this.colList.push(new ColInfo("职位", "positionName", "10%", SORT_YES));
        this.colList.push(new ColInfo("用户名", "userCd", "10%", SORT_YES));
        this.colList.push(new ColInfo("姓名", "userName", "10%", SORT_YES));
        this.colList.push(new ColInfo("手机号码", "userPhone", "10%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("登录天数", "loginDays", "9%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("登录次数", "loginTimes", "9%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("登录频率(次/天)", "loginRate", "10%", SORT_NO, ALIGN_RIGHT));

        this.datagrid.totalColList.push("loginDays");
        this.datagrid.totalColList.push("loginTimes");
        this.exportFileName = "登录统计";
        this.datagrid.cssClass = "app-table-list";
    },

    //自定义导出列
    customExportColList: function () {
        var exportColList = [];
        exportColList.push(new ColInfo("所属大区", "areaName", "60px", SORT_YES));
        exportColList.push(new ColInfo("所属分公司", "companyName", "60px", SORT_YES));
        exportColList.push(new ColInfo("所属部门", "deptName", "60px", SORT_YES));
        exportColList.push(new ColInfo("职位", "positionName", "60px", SORT_YES));
        exportColList.push(new ColInfo("用户名", "userCd", "60px", SORT_YES));
        exportColList.push(new ColInfo("姓名", "userName", "60px", SORT_YES));
        exportColList.push(new ColInfo("登录天数", "loginDays", "60px", SORT_YES, ALIGN_RIGHT));
        exportColList.push(new ColInfo("登录次数", "loginTimes", "60px", SORT_YES, ALIGN_RIGHT));
        return exportColList;
    },

    initCallback: function () {
        this.initCommonCallback();

        //设置日志日期检索周期
        var beginDate = this.getValue("beginDate");
        var endDate = this.getValue("endDate");
        if (this.isEmpty(beginDate)) {
            this.setValue("beginDate", this.getFirstDayOfCurrentMonth());
        }
        if (this.isEmpty(endDate)) {
            this.setValue("endDate", new Date().formatYMD());
        }
    },

    //查询登录次数
    showDetailListPopup: function (rowNo) {
        SysApp.Sys.LoginLogPopupListIns.searchMethod = "search";
        SysApp.Sys.LoginLogPopupListIns.parentInstance = this;
        SysApp.Sys.LoginLogPopupListIns.setValue("userUid", this.getRowData(rowNo)["userUid"]);
        SysApp.Sys.LoginLogPopupListIns.show();
        SysApp.Sys.LoginLogPopupListIns.getList(true);
    },

    //查询登录次数链接
    createDetailPopupLink: function (rowNo, value) {
        return "<a href='#' class='detail-link' onclick='" + this.selfInstance + ".showDetailListPopup(" + rowNo + ");return false;'>" + value + "</a>"
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];
        //登录次数
        if (colName == "loginTimes") {
            strHtml = this.createDetailPopupLink(rowNo, rowData[colName]);
        }
        else
        //登录日期
        if (colName == "insertDate") {
            strHtml = rowData[colName].formatYMD();
        }
        else if (colName == "loginRate") {
            if (rowData['loginDays'] > 0) {
                strHtml = (rowData['loginTimes'] / rowData['loginDays']).toFixed(1);
            } else {
                strHtml = 0;
            }
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }
        return strHtml;
    }
});