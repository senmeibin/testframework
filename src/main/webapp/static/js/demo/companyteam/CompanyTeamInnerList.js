//定义数据一览画面JS类
SysApp.Demo.CompanyTeamInnerList = Class.create();

SysApp.Demo.CompanyTeamInnerList.prototype = Object.extend(new SysApp.Demo.CompanyExtendedCommonList, {
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
        this.colList.push(new ColInfo("姓名", "name", "9%", SORT_YES));
        this.colList.push(new ColInfo("出生日期", "birthDate", "9%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("部门", "dept", "10%", SORT_YES));
        this.colList.push(new ColInfo("职务", "position", "10%", SORT_YES));
        this.colList.push(new ColInfo("电子邮件", "mail", "10%", SORT_YES));
        this.colList.push(new ColInfo("学历", "educationName", "9%", SORT_YES));
        this.colList.push(new ColInfo("目前最高职称", "highestJobTitle", "10%", SORT_YES));
        this.colList.push(new ColInfo("初次工作年份", "firstWorkingYear", "10%", SORT_YES));

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
        //出生日期
        else if (colName == "birthDate" && this.isNotEmpty(rowData[colName])) {
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