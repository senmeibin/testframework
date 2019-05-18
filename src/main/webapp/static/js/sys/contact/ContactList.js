//定义数据一览画面JS类
SysApp.Sys.ContactList = Class.create();

SysApp.Sys.ContactList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("来往单位", "companyName", "14%", SORT_YES));
        this.colList.push(new ColInfo("姓名", "name", "8%", SORT_YES));
        this.colList.push(new ColInfo("部门", "department", "8%", SORT_YES));
        this.colList.push(new ColInfo("职务", "post", "8%", SORT_YES));
        this.colList.push(new ColInfo("主要职责", "responsibility", "8%", SORT_YES));
        this.colList.push(new ColInfo("电话", "telephone", "10%", SORT_YES));
        this.colList.push(new ColInfo("传真", "fax", "10%", SORT_YES));
        this.colList.push(new ColInfo("邮箱", "email", "12%", SORT_YES));
        this.colList.push(new ColInfo("手机", "mobile", "10%", SORT_YES));

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

            //创建列表删除图标
            strHtml += this.createDeleteButton(rowNo, false, "delete");

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        //POPUP详细表示
        else if (colName == "name") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        //生日
        else if (colName == "birthday") {
            if (this.isNotEmpty(rowData[colName])) {
                strHtml = rowData[colName].formatYMD();
            }
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