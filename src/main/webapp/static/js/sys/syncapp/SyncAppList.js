//定义数据一览画面JS类
SysApp.Sys.SyncAppList = Class.create();

SysApp.Sys.SyncAppList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        this.bindEvent("btnRefresh", 'click', this.onClick_Refresh.bind(this));
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "6%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("同步项目/订阅者ID", "clientId", "10%", SORT_NO));
        this.colList.push(new ColInfo("项目名称/订阅者名称", "subscriberName", "12%", SORT_NO));
        this.colList.push(new ColInfo("订阅主题", "topicName", "10%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("状态", "onLine", "5%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("待同步数/待消费数", "pendingQueueSize", "10%", SORT_NO, ALIGN_RIGHT));
        this.colList.push(new ColInfo("已同步数/已消费数", "dequeueSize", "10%", SORT_NO, ALIGN_RIGHT));
        this.colList.push(new ColInfo("同步总数/已入队数", "enqueueSize", "10%", SORT_NO, ALIGN_RIGHT));
        this.colList.push(new ColInfo("连接者ID", "connectionId", "18%", SORT_NO));
        this.colList.push(new ColInfo("UDC服务器", "udcServer", "7%", SORT_NO, ALIGN_CENTER));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表删除图标
            if (!rowData.onLine && !rowData.udcServer) {
                strHtml = this.createDeleteButton(rowNo);
            }
        }
        else if (colName == "onLine") {
            strHtml = rowData[colName] ? "<lable style='color:#99CC00'>在线</label>" : "离线";
        }
        else if (colName == "udcServer") {
            strHtml = rowData[colName] ? "<i class='fa fa-check'></i>" : "";
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    //删除处理
    deleteData: function (keyValue) {
        var data = this.datagrid.getSelectedRow();

        var params = "clientId=" + data.clientId;
        params += "&subscriberName=" + data.subscriberName;

        //Ajax处理前显示提示画面
        this.showProcessing(false);

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "destroy", this.deleteDataCallback.bind(this), params);
    },

    onClick_Refresh: function () {
        this.refreshList();
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