SysApp.Cmn.Common = function () {
}

//定义数据输入画面JS类
SysApp.Cmn.CommonInput = Class.create();
SysApp.Cmn.CommonInput.prototype = Object.extend(new SysCmn.CoreInput(), {
    //取得表结构的主键名称[注意：必须实现的方法]
    getPrimaryKey: function () {
        return "uid";
    },

    //详细/编辑POPUP控件表示回调函数
    showCallback: function () {

    },

    //初期化回调处理
    initCommonCallback: function () {
        this.showSaveDataSuccessMessage = false;
        //不是UDC服务器，禁用编辑功能
        if (!SysApp.Cmn.isUdcServer && this.isSyncDataPage == "true") {
            //禁止所有Form输入元素
            this.disableInput(this.getMainContentClassName(), true);
        }
    },

    //将数字转为金额格式
    convertAmount: function (obj) {
        if (Object.isUndefined(obj)) {
            return;
        }
        //非数值的场合，设置为0
        if (!this.isDouble(obj)) {
            obj.value = "0";
        } else {
            if (parseFloat(obj.value) < 0) {
                obj.value = "0";
            }
        }
        obj.value = this.formatCny(obj.value);
    },

    //设置同步提醒消息
    setSyncNoticeMessage: function (message) {
        $(".udc-sync-flash-notice").html(message);
    }
});

//定义数据导入画面JS类
SysApp.Cmn.CommonImport = Class.create();
SysApp.Cmn.CommonImport.prototype = Object.extend(new SysCmn.CoreImport(), {});

//定义数据一览画面JS类
SysApp.Cmn.CommonList = Class.create();
SysApp.Cmn.CommonList.prototype = Object.extend(new SysCmn.CoreList(), {
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
        //API同步按钮绑定事件
        this.bindEvent("btnApiImport", 'click', this.onClick_ApiImport.bind(this));

        //不是UDC服务器，禁用编辑功能
        if (!SysApp.Cmn.isUdcServer && this.isSyncDataPage == "true") {
            //添加警示栏
            if ($(".udc-sync-flash-notice").length == 0) {
                $(".panel-body").prepend("<div id=\"divSyncNoticeMessage\" class=\"flash-notice udc-sync-flash-notice\" style=\"margin-bottom: 10px;\">基础数据自动从UDC服务器同步，如需修改请与UDC数据管理专员联系。</div>");
            }

            //设为只读模式
            this.setReadonlyMode();
        }
    },

    //设为只读模式
    setReadonlyMode: function () {
        //删除操作栏
        this.removeOperationColumn();
        //隐藏保存、导入、删除、新增按钮
        this.displayDom("btnAdd", false);
        this.displayDom("btnSave", false);
        this.displayDom("btnDelete", false);
        this.displayDom("btnImport", false);
    },

    //设置同步提醒消息
    setSyncNoticeMessage: function (message) {
        $(".udc-sync-flash-notice").html(message);
    },

    removeOperationColumn: function () {
        //存储操作列的宽度
        var operationWidth = 0;
        //匹配宽度单位
        var reg = /px|%/;
        //将“操作”列隐藏
        for (var i = 0, len = this.colList.length; i < len; i++) {
            var colInfo = this.colList[i];
            if (colInfo.colName == "operation") {
                operationWidth = this.convertNum(colInfo.width);
                this.colList.splice(i, 1);
                break;
            }
        }
        //将“操作”列隐藏[备选]
        if (this.allColList) {
            for (var i = 0, len = this.allColList.length; i < len; i++) {
                var colInfo = this.allColList[i];
                if (colInfo.colName == "operation") {
                    this.allColList.splice(i, 1);
                    break;
                }
            }
        }
        //将操作列的宽度添加至第一列
        var firstColWidth = this.colList[0].width;
        var suffix = firstColWidth.match(reg)[0];
        this.colList[0].width = new String((this.convertNum(firstColWidth) + operationWidth) + suffix);
    },

    //获取API同步按钮事件
    onClick_ApiImport: function () {
        this.showConfirm("确定要同步数据吗？", this.getApiImportData.bind(this, 1));
    },

    //同步数据
    getApiImportData: function () {
        //Ajax处理前显示提示画面
        this.showProcessing(false);

        var params = "tableName=" + this.tableName;

        //Ajax处理(GET方式)
        AjaxIns.sendPOST(this.controller + "syncData", this.getApiImportDataCallback.bind(this), params);
    },

    //同步数据回调函数
    getApiImportDataCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //处理成功
        if (ajaxResult.result > 0) {
            this.showInfoMessage(ajaxResult.message, this.refreshList.bind(this));
        }
        else {
            this.showErrorMessage(ajaxResult.message);
        }
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
        this.datagrid.cellClassCallback = this.cellClassCommonCallback.bind(this);

        //Popup列表画面的场合，无需显示头部分页Pager
        if (this.isPopupMode()) {
            this.datagrid.showTopPager = false;
        }
    },

    cellClassCommonCallback: function (rowData, colName, colInfo) {
    }
});