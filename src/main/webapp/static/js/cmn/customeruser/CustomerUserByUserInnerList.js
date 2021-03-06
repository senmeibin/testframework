//定义数据一览画面JS类
SysApp.Cmn.CustomerUserByUserInnerList = Class.create();

SysApp.Cmn.CustomerUserByUserInnerList.prototype = Object.extend(new SysApp.Cmn.CommonList(), {
    //分页显示的记录数[注意：必须实现的方法]
    getPageSize: function () {
        return 100;
    },

    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        this.bindEvent("btnAdd", "click", this.onClick_Add.bind(this));
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("客户名称", "customerName", "15%", SORT_YES));
        this.colList.push(new ColInfo("主客户名称", "mainCustomerName", "15%", SORT_YES));
        this.colList.push(new ColInfo("客户简称", "customerAbbreviation", "10%", SORT_YES));
        this.colList.push(new ColInfo("地址", "address", "30%", SORT_YES));
        this.colList.push(new ColInfo("邮编", "zipcode", "10%", SORT_YES));
        this.colList.push(new ColInfo("手机号码", "telephone", "10%", SORT_YES, ALIGN_CENTER));
        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;
        this.datagrid.showTopPager = false;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表删除图标
            strHtml = this.createDeleteButton(rowNo);
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
    },

    //重写新增方法
    onClick_Add: function () {
        this.CustomerPopupListIns = SysApp.Cmn.CustomerPopupListIns;
        this.CustomerPopupListIns.setUserUid(this.userUid);
        this.CustomerPopupListIns.show();
        this.CustomerPopupListIns.getList(true);
    }
});