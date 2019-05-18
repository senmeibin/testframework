//定义数据一览画面JS类
SysApp.Cmn.RegionSetting = Class.create();

SysApp.Cmn.RegionSetting.prototype = Object.extend(new SysApp.Cmn.CommonList(), {
    //分页显示的记录数[注意：必须实现的方法]
    getPageSize: function () {
        return -1;
    },

    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        this.popupPosition = "top";

        this.autoSearch = false;

        this.searchMethod = "getRegionSettingList";

        //绑定批量修改表示顺序按钮
        this.bindEvent("btnBatchSave", "click", this.btnBatchSave.bind(this));
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {

        this.colList.push(new ColInfo("区域名称", "regionName", "530px", SORT_YES));
        this.colList.push(new ColInfo("表示顺序", "dispSeq", "180px", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("是否启用", "recordStatus", "180px", SORT_YES, ALIGN_CENTER));

        this.datagrid.cssClass = "app-table-list app-table-list-input";
        this.datagrid.useRowNo = true;

        this.datagrid.useScroll = true;

        this.datagrid.useCheckAll = false;
        this.datagrid.useCheckOne = false;
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            strHtml = "";
        }
        //表示顺序
        else if (colName == "dispSeq") {
            strHtml = "<input id='dispSeq_" + rowData["uid"] + "' type='text' class='form-control ' maxlength='3' data-rangelength='[0,3]' data-range='[0,999]' data-digits='true' style='text-align: right ;width: 150px;' value='" + rowData[colName] + "'>";
        }
        //是否启用
        else if (colName == "recordStatus") {
            strHtml = "<select id='recordStatus_" + rowData["uid"] + "' class='form-control' data-title='是否启用' style='width: 150px;float: center;' data-value = '" + rowData["recordStatus"] + "' >"
                + "<option value='1'>启用</option>"
                + "<option value='8'>停用</option>"
                + "</select>";
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    //表格创建后处理
    createListAfter: function () {
        for (var i = 0; i < this.datagrid.data.length; i++) {
            var region = $.extend(true, {}, this.getRowData(i));
            //赋值是否启用
            this.setValue("recordStatus_" + region.uid, region.recordStatus);
        }
    },

    //批量保存
    btnBatchSave: function (rows) {
        var rows = this.datagrid.data;
        for (var i = 0; i < rows.length; i++) {
            if (isNaN(this.getValue("dispSeq_" + rows[i].uid))) {
                this.showErrorMessage("必须输入数字类型的【表示顺序】，请重新输入后保存。");
                return false;
            }
            rows[i].dispSeq = this.isEmpty(this.getValue("dispSeq_" + rows[i].uid)) ? 999 : this.getValue("dispSeq_" + rows[i].uid);

            rows[i].recordStatus = this.isEmpty(this.getValue("recordStatus_" + rows[i].uid)) ? 8 : this.getValue("recordStatus_" + rows[i].uid);
        }
        var params = "regionRows=" + JsonUtility.ToJSON(rows);
        //Ajax处理前显示提示画面
        this.showProcessing(false);

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "batchSave", this.btnBatchSaveAjaxCallback.bind(this), params);
    },

    //提交处理[Callback后处理]
    btnBatchSaveAjaxCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //处理成功的场合、显示处理结果消息后刷新一览画面
        if (ajaxResult.result > 0) {
            this.showInfoMessage("记录修改成功。", this.refreshList.bind(this));
        }
        else {
            this.showErrorMessage("记录修改失败。");
        }
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