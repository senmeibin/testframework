//定义数据一览画面JS类
SysApp.Cmn.MessageDetailPopupList = Class.create();

SysApp.Cmn.MessageDetailPopupList.prototype = Object.extend(new SysApp.Cmn.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        this.autoSearch = false;
        this.popupPosition = "firstTop";

        //绑定批量更新已读
        this.bindEvent("batchSetReadBtn", "click", this.setRead.bind(this, ""));
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "3%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("消息标题", "messageTitle", "18%", SORT_YES));
        this.colList.push(new ColInfo("消息内容", "messageContent", "60%", SORT_YES));
        this.colList.push(new ColInfo("重要度", "importanceDegreeName", "4%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("消息接收时间", "insertDate", "9%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("是否已读", "readTypeName", "4%", SORT_YES, ALIGN_CENTER));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;
        this.datagrid.useCheckAll = true;
        this.datagrid.useCheckOne = true;

        this.datagrid.createCheckboxDisabledCallback = this.createCheckboxDisabledCallback.bind(this);

        this.datagrid.cssClass = "app-table-list app-table-list-input";
        this.datagrid.showTopPager = false;
    },

    // checkbox按钮创建回调函数
    createCheckboxDisabledCallback: function (rowData) {
        // 已读场合 禁用复选框
        if (rowData.readTypeCd == "02") {
            return true;
        }
        return false;
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建已读图标
            strHtml = this.createReadIcon(rowData);
        }
        //POPUP详细表示
        else if (colName == "messageTitle") {
            strHtml = this.createMessageDetailLink(rowData);
        }
        //消息接收时间
        else if (colName == "insertDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMDHM();
        }
        else if (colName == "messageContent") {
            //非富文本消息
            if (rowData["isRichMessage"] == 0) {
                strHtml = $M.enterToBr(rowData[colName]);
            }
            //富文本消息
            else {
                strHtml = "";
            }
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }
        return strHtml;
    },

    //当为01：未读 时，创建已读按钮
    createReadIcon: function (rowData) {
        if (rowData.readTypeCd == "01") {
            var html = "<span class='popovers' data-trigger='hover' data-placement='top' data-content='标记为已读'>";
            html += "<i onclick='" + this.selfInstance + ".setRead(\"" + rowData.uid + "\");return false;' class='fa fa-check fa-lg fa-blue'></i>";
            html += "</span>";
            return html;
        }
        return "";
    },

    //已读操作
    setRead: function (uids) {
        //批量标记已读场合
        if (this.isEmpty(uids)) {
            if (this.isEmpty(this.getSelectedIds(this.datagrid, this.getPrimaryKey()))) {
                this.showErrorMessage("未选择待标记为已读的记录，请选择记录后操作。");
                return false;
            }

            uids = this.getSelectedIds(this.datagrid, this.getPrimaryKey());
        }

        var params = "uids=" + uids;
        //Ajax处理前显示提示画面
        this.showProcessing(false);
        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "setMessageReaded", this.readDataCallback.bind(this), params);
    },

    //已读回调操作
    readDataCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //处理成功的场合、显示处理结果消息后刷新一览画面
        if (ajaxResult.result > 0) {
            SysApp.Cmn.HeaderIns.setUnreadCount(ajaxResult.content);

            //刷新列表
            this.refreshList();
        }
        else {
            this.showMessage(ajaxResult.message, MSG_TYPE_ERROR);
        }
    },

    //已读操作
    readMessage: function (uid) {
        var params = "uids=" + uid;
        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "setMessageReaded", this.readMessageCallback.bind(this), params);
    },

    //已读回调操作
    readMessageCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //处理成功的场合、显示处理结果消息后刷新一览画面
        if (ajaxResult.result > 0) {
            SysApp.Cmn.HeaderIns.setUnreadCount(ajaxResult.content);
        }
        else {
            this.showMessage(ajaxResult.message, MSG_TYPE_ERROR);
        }
    },

    //创建详情链接
    createMessageDetailLink: function (rowData) {
        return "<a href='#' class='detail-link' onclick='" + this.selfInstance + ".showMessageDetail(\"" + rowData.messageUid + "\")'>" + rowData.messageTitle + "</a>";
    },

    showMessageDetail: function (uid) {
        this.detailInstance.getDetail(uid);
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