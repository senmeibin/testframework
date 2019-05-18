//定义数据一览画面JS类
SysApp.Demo.CompanyTeamList = Class.create();

SysApp.Demo.CompanyTeamList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
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

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [
            new ColInfo("操作", "operation", "100px", SORT_NO, ALIGN_CENTER),
            new ColInfo("企业名称", "companyName", "200px", SORT_YES),
            new ColInfo("姓名", "name", "100px", SORT_YES),
            new ColInfo("出生日期", "birthDate", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("部门", "dept", "100px", SORT_YES),
            new ColInfo("职务", "position", "100px", SORT_YES),
            new ColInfo("电话", "phone", "100px", SORT_YES),
            new ColInfo("传真", "fax", "100px", SORT_YES),
            new ColInfo("手机", "mobile", "100px", SORT_YES),
            new ColInfo("证件类型", "certificateTypeName", "100px", SORT_YES),
            new ColInfo("证件号码", "certificateNo", "100px", SORT_YES),
            new ColInfo("电子邮件", "mail", "100px", SORT_YES),
            new ColInfo("学历", "educationName", "100px", SORT_YES),
            new ColInfo("目前最高职称", "highestJobTitle", "100px", SORT_YES),
            new ColInfo("初次工作年份", "firstWorkingYear", "100px", SORT_YES),
            new ColInfo("本公司股东", "shareholdersName", "100px", SORT_YES),
            new ColInfo("实际负责人", "actualPersonName", "100px", SORT_YES),
            new ColInfo("连续创业", "continuousBusinessName", "100px", SORT_YES),
            new ColInfo("创业者特征", "entrepreneurFeature", "100px", SORT_YES),
            new ColInfo("千人计划", "thousandsPlansName", "100px", SORT_YES),
            new ColInfo("千人计划批次号", "thousandsPlansNo", "100px", SORT_YES),
            new ColInfo("大学生科技企业", "collegeTechnologyEnterprisesName", "100px", SORT_YES),
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
        else if (colName == "name") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
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