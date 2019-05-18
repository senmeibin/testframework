//定义数据一览画面JS类
SysApp.Cmn.CustomerUserByCustomerInnerList = Class.create();

SysApp.Cmn.CustomerUserByCustomerInnerList.prototype = Object.extend(new SysApp.Cmn.CommonList(), {
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

        this.colList.push(new ColInfo("操作", "operation", "15%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("姓名", "userName", "20%", SORT_YES));
        this.colList.push(new ColInfo("用户名", "userCd", "18%", SORT_YES));
        this.colList.push(new ColInfo("手机号码", "userPhone", "15%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("邮箱", "userMail", "15%", SORT_YES));
        this.colList.push(new ColInfo("是否启用", "userRecordStatus", "15%", SORT_YES, ALIGN_CENTER));

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
        //用户状态
        else if (colName == "userRecordStatus") {
            strHtml = rowData[colName] == "1" ? "启用" : "<span style='color: red;'>停用</span>";
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
        this.UserPopupListIns = SysApp.Cmn.UserPopupListIns;
        this.UserPopupListIns.setCustomerUid(this.customerUid);
        this.UserPopupListIns.show();
        this.UserPopupListIns.getList(true);
    }
});