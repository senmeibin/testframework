//定义数据一览画面JS类
SysApp.Cmn.MainCustomerList = Class.create();

SysApp.Cmn.MainCustomerList.prototype = Object.extend(new SysApp.Cmn.CommonList(), {
    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();
        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("主客户名称", "mainCustomerName", "26%", SORT_YES));
        this.colList.push(new ColInfo("主客户简称", "mainCustomerAbbreviation", "10%", SORT_YES));
        this.colList.push(new ColInfo("联系人", "contactName", "10%", SORT_YES));
        this.colList.push(new ColInfo("联系电话", "contactTelephone", "15%", SORT_YES));
        this.colList.push(new ColInfo("客户概况", "introduction", "24%", SORT_YES));
        this.colList.push(new ColInfo("备注", "remark", "5%", SORT_YES, ALIGN_CENTER));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //数据导入
    onClick_Import: function () {
        window.location = this.importUrl;
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditButton(rowNo);

            //创建列表删除图标
            strHtml += this.createDeleteButton(rowNo, false, "delete");

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        //POPUP详细表示
        else if (colName == "mainCustomerName") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        //备注字段
        else if (colName == "remark") {
            strHtml = this.createRemarkColumn(rowData);
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    /** ********************以下方法为可选方法********************* */
    initCallback: function () {
        this.initCommonCallback();
    },

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