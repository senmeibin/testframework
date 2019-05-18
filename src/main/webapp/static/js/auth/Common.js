SysApp.Auth.Common = function () {
}

/********************定义模块共通数据输入画面JS类********************/
SysApp.Auth.CommonInput = Class.create();

SysApp.Auth.CommonInput.prototype = Object.extend(new SysCmn.CoreInput(), {
    //取得表结构的主键名称[注意：必须实现的方法]
    getPrimaryKey: function () {
        return "uid";
    },

    //详细/编辑POPUP控件表示回调函数
    showCallback: function () {

    },

    //初期化回调处理
    initCommonCallback: function () {
    }
});

/********************定义模块共通数据一览画面JS类********************/
SysApp.Auth.CommonList = Class.create();

SysApp.Auth.CommonList.prototype = Object.extend(new SysCmn.CoreList(), {
    //取得表结构的主键名称[注意：必须实现的方法]
    getPrimaryKey: function () {
        return "uid";
    },

    //分页显示的记录数[注意：必须实现的方法]
    getPageSize: function () {
        return this.pageSize ? this.pageSize : 10;
    },

    //初期化回调处理
    initCommonCallback: function () {
    },

    commonCellCallback: function (listIns, rowData, colName, rowNo, useDelete) {
        var strHtml = "";
        if (this.isNull(useDelete)) {
            useDelete = true;
        }
        //操作列
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditButton(rowNo);

            //创建列表删除图标
            strHtml += this.createDeleteButton(rowNo);
        }
        //添加日期
        else if (colName == "insert_date") {
            strHtml = rowData[colName].formatYMD();
        }
        return strHtml;
    },

    //Table共通属性初期化
    initTableCommonProperty: function () {
        if (this.isNotNull(this.inputInstance)) {
            //设置一览画面JS对象实例
            this.inputInstance.listInstance = this;
        }

        this.datagrid.colList = this.colList;
        this.datagrid.useRowClickEvent = false;
        this.datagrid.useRowNo = true;
        this.datagrid.useCheckOne = false;

        //Popup列表画面的场合，无需显示头部分页Pager
        if (this.isPopupMode()) {
            this.datagrid.showTopPager = false;
        }
    }
});