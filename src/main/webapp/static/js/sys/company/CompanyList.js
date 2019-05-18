//定义数据一览画面JS类
SysApp.Sys.CompanyList = Class.create();

SysApp.Sys.CompanyList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("单位名称", "name", "12%", SORT_YES));
        this.colList.push(new ColInfo("简称", "abbreviation", "8%", SORT_YES));
        this.colList.push(new ColInfo("性质", "propertyName", "7%", SORT_YES));
        this.colList.push(new ColInfo("规模", "scaleName", "8%", SORT_YES));
        this.colList.push(new ColInfo("地址", "address", "8%", SORT_YES));
        this.colList.push(new ColInfo("邮编", "zipcode", "8%", SORT_YES));
        this.colList.push(new ColInfo("电话", "telephone", "8%", SORT_YES));
        this.colList.push(new ColInfo("传真", "fax", "8%", SORT_YES));
        this.colList.push(new ColInfo("邮箱", "email", "10%", SORT_YES));
        this.colList.push(new ColInfo("联系人", "contactName", "10%", SORT_YES));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.divPager = this.getDom("divPager");
        this.datagrid.cssClass = "app-table-list app-table-list-input";
        this.datagrid.allowResizeColumnWidth = true;
        this.datagrid.useCheckOne = true;
    },

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [
            new ColInfo("操作", "operation", "80px", SORT_NO, ALIGN_CENTER),
            new ColInfo("单位编号", "code", "100px", SORT_YES),
            new ColInfo("单位名称", "name", "100px", SORT_YES),
            new ColInfo("简称", "abbreviation", "100px", SORT_YES),
            new ColInfo("性质", "propertyName", "100px", SORT_YES),
            new ColInfo("规模", "scaleName", "100px", SORT_YES),
            new ColInfo("地址", "address", "100px", SORT_YES),
            new ColInfo("邮编", "zipcode", "100px", SORT_YES),
            new ColInfo("电话", "telephone", "100px", SORT_YES),
            new ColInfo("传真", "fax", "100px", SORT_YES),
            new ColInfo("邮箱", "email", "100px", SORT_YES),
            new ColInfo("网址", "url", "100px", SORT_YES),
            new ColInfo("所属部门", "belongDeptName", "100px", SORT_YES),
            new ColInfo("所属业务员", "belongUserName", "100px", SORT_YES),
            new ColInfo("联系人", "contactName", "100px", SORT_YES),
            new ColInfo("备注", "remark", "100px", SORT_YES),
            new ColInfo("附件关联ID", "fileRelationUid", "100px", SORT_YES)
        ];
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