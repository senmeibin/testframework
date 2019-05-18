//定义数据一览画面JS类
SysApp.Sys.ApplicationList = Class.create();

SysApp.Sys.ApplicationList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("应用名称", "appName", "24%", SORT_YES));
        this.colList.push(new ColInfo("有效日期", "validDate", "8%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("状态", "recordStatus", "6%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("表示顺序", "dispSeq", "6%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("维护开始日期", "mainteStartDate", "10%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("维护结束日期", "mainteEndDate", "10%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("维护内容", "mainteContent", "22%", SORT_YES));
        this.colList.push(new ColInfo("备注", "remark", "4%", SORT_YES, ALIGN_CENTER));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
        //关闭回车检索
        this.useEnterSearch = false;
        //绑定批量保存按钮
        this.bindEvent("btnBatchSaveDispSeq", "click", this.onClick_BatchSaveDispSeq.bind(this));
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditButton(rowNo);

            //手动登录的场合
            if (rowData.regSystem == 0) {
                if (rowData.recordStatus == 1) {
                    strHtml += this.createLockButton(rowNo);
                }
                else {
                    strHtml += this.createUnlockButton(rowNo);
                }
                //创建列表删除图标
                strHtml += this.createDeleteButton(rowNo, false, "delete");
            }

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        else if (colName == "appName") {
            strHtml = this.createDetailLink(rowData, colName, rowNo) + "[" + rowData.appCode + "]";
        }
        //有效日期
        else if (colName == "validDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        //维护开始日期
        else if (colName == "mainteStartDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        //维护结束日期
        else if (colName == "mainteEndDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        else if (colName == "recordStatus") {
            strHtml = rowData[colName] == "1" ? "有效" : "<font color='red'>停用</font>";
        }
        else if (colName == "mainteContent") {
            strHtml = $M.enterToBr(rowData[colName]);
        }
        else if (colName == "remark") {
            strHtml = this.createRemarkColumn(rowData);
        }
        else if (colName == "dispSeq") {
            strHtml = "<input type='text'  id='dispSeq_" + rowNo + "' data-uid='" + rowData.uid + "' class='form-control' maxlength='4' style='text-align: right; width: 100%;' value='" + rowData.dispSeq + "'>";
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    onClick_BatchSaveDispSeq: function () {
        this.showConfirm("确定要批量设置并保存表示顺序吗？", this.batchSaveDispSeq.bind(this));
    },

    //批量保存表示顺序
    batchSaveDispSeq: function () {
        var thisObj = this;
        var itemList = [];
        var errorFlag = false;
        $("input[id^=dispSeq]").each(function (index, item) {
            var dispSeq = parseInt($(item).val(), 10);
            if (isNaN(dispSeq)) {
                errorFlag = true;
                return false;
            }

            itemList.push({
                uid: $(item).data("uid"),
                dispSeq: dispSeq
            });
        });

        if (errorFlag) {
            thisObj.showErrorMessage("请在表示顺序中输入正确的数字。");
            return;
        }

        var params = "itemJson=" + JsonUtility.ToJSON(itemList);
        //Ajax处理前显示提示画面
        this.showProcessing();

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "batchSaveDispSeq", this.batchSaveDispSeqCallback.bind(this), params);
    },

    //批量保存表示顺序[Callback后处理]
    batchSaveDispSeqCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //处理成功的场合、显示处理结果消息后刷新一览画面
        if (ajaxResult.result > 0) {
            this.showInfoMessage("表示顺序批量保存成功。", this.refreshList.bind(this));
        }
        else {
            this.showErrorMessage(ajaxResult.message);
        }
    }
});