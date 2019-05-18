SysApp.Demo.Common = function () {
}

//定义数据输入画面JS类
SysApp.Demo.CommonInput = Class.create();
SysApp.Demo.CommonInput.prototype = Object.extend(new SysCmn.CoreInput(), {
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
    },

    //初始弹出页面的附件控件
    initPopupInputFileUpload: function () {
        var thisObj = this;
        var relationUid = this.inputEntity.uid;
        if (this.isEmpty(this.getValue("uid"))) {
            AjaxIns.sendGET(this.controller + "/loadNewUid", function (response) {
                thisObj.initPopupInputFileUploadByRelationUid(response.responseJSON.message);
            });
        }
        else {
            this.initPopupInputFileUploadByRelationUid(relationUid);
        }
    },

    //根据relationUid初始弹出页面的附件控件
    initPopupInputFileUploadByRelationUid: function (relationUid) {
        this.setValue("uid", relationUid);
        SysApp.Cmn.FileUploadListIns.setValue("relationUid", relationUid);
        SysApp.Cmn.FileUploadListIns.relationUid = relationUid;
        //需要重新初期控件
        SysApp.Cmn.FileUploadListIns.initUploadify();
        SysApp.Cmn.FileUploadListIns.getList(true);
    }
});

//定义数据一览画面JS类
SysApp.Demo.CommonList = Class.create();
SysApp.Demo.CommonList.prototype = Object.extend(new SysCmn.CoreList(), {
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
    },

    //获取API同步按钮事件
    onClick_ApiImport: function () {
        this.showConfirm("确定要同步数据吗？", this.getApiImportData.bind(this, 1));
    },

    //同步数据
    getApiImportData: function () {
        //Ajax处理前显示提示画面
        this.showProcessing();

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
    },

    cellClassCommonCallback: function (rowData, colName, colInfo) {
    }
});