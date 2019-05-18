//定义数据一览画面JS类
SysApp.Sys.ExecuteRecordList = Class.create();

SysApp.Sys.ExecuteRecordList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        this.calendarFormat = "YYYY/MM/DD HH:mm:ss";
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("功能模块", "functionCode", "43%", SORT_YES));
        this.colList.push(new ColInfo("功能类型", "functionTypeName", "5%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("最后执行时间", "lastExecuteStartDate", "12%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("下次执行时间", "nextExecuteDate", "12%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("累计执行", "totalExecuteCount", "12%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("任务状态", "functionStatusName", "4%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("备注", "remark", "3%", SORT_YES, ALIGN_CENTER));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;
        this.datagrid.cellClassCallback = this.cellClassCallback.bind(this);

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    cellClassCallback: function (rowData, colName, colInfo) {
        if (colName == "functionStatusName" && rowData.functionStatusCd == "02") {
            return "warning-red-cell";
        }
    },

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [
            new ColInfo("操作", "operation", "80px", SORT_NO, ALIGN_CENTER),
            new ColInfo("功能编码", "functionCode", "380px", SORT_YES),
            new ColInfo("功能类型", "functionTypeName", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("任务状态", "functionStatusName", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("最后执行时间", "lastExecuteStartDate", "120px", SORT_YES, ALIGN_CENTER),
            new ColInfo("最后执行机器", "lastExecuteMachine", "100px", SORT_YES),
            new ColInfo("业务数据最后更新时间", "lastRecordUpdateDate", "150px", SORT_YES, ALIGN_CENTER),
            new ColInfo("业务数据最后更新UID", "lastRecordUpdateUid", "240px", SORT_YES, ALIGN_CENTER),
            new ColInfo("累计执行", "totalExecuteCount", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("下次执行时间", "nextExecuteDate", "120px", SORT_YES, ALIGN_CENTER),
            new ColInfo("备注", "remark", "60px", SORT_YES, ALIGN_CENTER)
        ];
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            // 创建详情按钮
            strHtml = this.createDetailButton(rowData["uid"]);

            //非接口调用的场合
            if (rowData.functionTypeCd != "03") {
                //创建执行按钮
                strHtml += this.createExecuteButton(rowNo);
            }

            //创建图标
            strHtml += this.createControlButton(rowData, rowNo);

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        else if (colName == "functionCode") {
            strHtml = rowData.functionCode.replace("com.bpms.", "") + "." + rowData.functionMethod + "<br/>[<b>" + rowData.sourceSystemName + "->" + rowData.destSystemName + "</b>]" + rowData.functionName;
        }
        else if (colName == "sourceSystemName") {
            strHtml = rowData.sourceSystemName + "<br/>" + rowData.destSystemName;
        }
        //业务数据最后更新时间
        else if (colName == "lastRecordUpdateDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData.lastRecordInsertDate.formatYMDHMS() + "<br/>" + rowData.lastRecordUpdateDate.formatYMDHMS();
        }
        //最后执行时间
        else if (colName == "lastExecuteStartDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMDHMS() + "<br/>" + rowData.lastExecuteEndDate.formatYMDHMS();
        }
        //最后执行时间
        else if (colName == "lastExecuteStartDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMDHMS() + "<br/>" + rowData.lastExecuteEndDate.formatYMDHMS();
        }
        else if (colName == "totalExecuteCount") {
            strHtml = rowData.totalExecuteTime + "秒/" + rowData.totalExecuteCount + "次";
        }
        else if (colName == "remark") {
            strHtml = this.createRemarkColumn(rowData);
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    //创建详情按钮
    createDetailButton: function (uid) {
        return "<button type='button' class='btn btn-default' onclick='" + this.selfInstance + ".getDetail(\"" + uid + "\");return false;'><i class='fa fa-list fa-width-fixed'></i>执行日志</button>";
    },

    //创建控制按钮
    createControlButton: function (rowData, rowNo) {
        var functionStatusCd = rowData["functionStatusCd"];

        var name = (functionStatusCd == '09') ? "启动" : "停止";
        var style = (functionStatusCd == '09') ? "primary" : "danger";
        var icon = (functionStatusCd == '09') ? "step-forward" : "stop";
        return "<button type='button' class='btn btn-" + style + " delete' onclick='" + this.selfInstance + ".onClick_UpdateStatus(\"" + rowNo + "\");return false;'>" +
            "<i class='fa fa-" + icon + " fa-width-fixed'></i>" + name + "</button>";
    },

    //创建执行按钮
    createExecuteButton: function (rowNo) {
        return "<button type='button' class='btn btn-primary' onclick='" + this.selfInstance + ".onClick_ManuallyExecuteTask(\"" + rowNo + "\");return false;'>" +
            "<i class='fa fa-cog fa-width-fixed'></i>手动执行</button>";
    },

    onClick_UpdateStatus: function (rowNo) {
        var rowData = this.getRowData(rowNo);
        var functionStatusCd = rowData["functionStatusCd"];

        var message = "";
        if (functionStatusCd == "01") {
            message = "确定要停止吗？";
        }
        else if (functionStatusCd == "09") {
            message = "确定要启动吗？";
        }
        else if (functionStatusCd == "02") {
            message = "<span style='color: red;'>当前任务正在执行中，确定要停止吗？</span><br/>(注：停止对本次执行中的任务无影响)";
        }
        this.showConfirm(message, this.updateTaskStatus.bind(this, rowNo));
    },

    updateTaskStatus: function (rowNo) {
        var rowData = this.getRowData(rowNo);
        var functionStatusCd = rowData["functionStatusCd"];
        this.showProcessing(false);
        AjaxIns.sendPOST(this.controller + "updateTaskStatus", this.updateTaskStatusCallback.bind(this), "uid=" + rowData.uid + "&functionStatusCd=" + ((functionStatusCd == '09') ? "01" : "09"));
    },

    updateTaskStatusCallback: function () {
        this.getList(true);
    },

    onClick_ManuallyExecuteTask: function (rowNo) {
        this.showConfirm("确定要手动执行任务吗？", this.manuallyExecuteTask.bind(this, rowNo));
    },

    manuallyExecuteTask: function (rowNo) {
        var rowData = this.getRowData(rowNo);
        this.showProcessing(false);
        AjaxIns.sendPOST(this.controller + "manuallyExecuteTask", this.manuallyExecuteTaskCallback.bind(this), "uid=" + rowData.uid);
    },

    manuallyExecuteTaskCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);
        if (ajaxResult.result > 0) {
            //处理成功的场合、显示处理结果消息后，继续显示本页面
            this.showMessage(ajaxResult.message, MSG_TYPE_INFO);
            this.refreshList();
        }
        else {
            this.showMessage(ajaxResult.message, MSG_TYPE_ERROR);
        }
    },

    //执行日志详细画面
    getDetail: function (uid) {
        //显示执行日志详细画面
        this.ExecuteLogListIns = SysApp.Sys.ExecuteLogListIns;
        this.ExecuteLogListIns.setExecuteRecordUid(uid);
        this.ExecuteLogListIns.show();
    },

    //表格创建后处理
    createListAfter: function () {
        //绑定popovers事件
        this.bindPopoversEvent();
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