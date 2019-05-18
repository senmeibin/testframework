//定义数据一览画面JS类
SysApp.Sys.UrlRoleList = Class.create();

SysApp.Sys.UrlRoleList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        //访问权限树形展示
        this.bindEvent("btnUrlRoleTreeList", 'click', this.onClick_UrlRoleTreeList.bind(this));
    },

    //访问权限平铺展示
    onClick_UrlRoleTreeList: function () {
        window.location = this.urlRoleTreeListUrl;
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "12%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("访问路径", "url", "28%", SORT_YES));
        this.colList.push(new ColInfo("角色集合", "roles", "28%", SORT_YES));
        this.colList.push(new ColInfo("描述", "description", "30%", SORT_YES));

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
            strHtml += this.createDeleteButton(rowNo);
        }
        //POPUP详细表示
        else if (colName == "url") {
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