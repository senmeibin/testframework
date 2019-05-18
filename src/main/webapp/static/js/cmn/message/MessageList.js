//定义数据一览画面JS类
SysApp.Cmn.MessageList = Class.create();

SysApp.Cmn.MessageList.prototype = Object.extend(new SysApp.Cmn.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        //消息推送时间默认值设置 默认设置 一周
        this.setValue("messageStartDate$from_search", new Date().addDay(-3).formatYMD());
        this.setValue("messageStartDate$to_search", new Date().formatYMD());
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("消息类型", "messageTypeName", "8%", SORT_YES));
        this.colList.push(new ColInfo("消息标题", "messageTitle", "30%", SORT_YES));
        this.colList.push(new ColInfo("重要度", "importanceDegreeName", "6%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("发送区分", "sendTypeName", "8%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("消息提醒开始时间", "messageStartDate", "12%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("消息提醒结束时间", "messageEndDate", "12%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("提示总人数", "allCount", "6%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("未读总人数", "unreadCount", "6%", SORT_YES, ALIGN_RIGHT));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            if (parseInt(this.serviceTime) - new Date(rowData.messageEndDate).getTime() > 0) {
                strHtml = this.createDetailButton(rowNo);
            }
            //创建列表编辑图标
            else {
                strHtml = this.createEditButton(rowNo);
            }
            strHtml += this.createCopyButton(rowNo);

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        //POPUP详细表示
        else if (colName == "messageTitle") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        //POPUP详细表示(总人数)
        else if (colName == "allCount") {
            strHtml = this.createMessageUserLink(rowData, "all");
        }
        //POPUP详细表示(未读人数)
        else if (colName == "unreadCount") {
            strHtml = this.createMessageUserLink(rowData, "unread");
        }
        //消息提醒开始时间
        else if (colName == "messageStartDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMDHMS();
        }
        //消息提醒结束时间
        else if (colName == "messageEndDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMDHMS();
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    //创建列表复制图标
    createCopyButton: function (rowNo) {
        var html = "<button type='button'' class='btn btn-primary' onclick='" + this.selfInstance + ".copyRecord(" + rowNo + ");return false;'>";
        html += "<i class='fa fa-copy fa-width-fixed'></i>复制";
        html += "</button>";
        return html;
    },

    copyRecord: function (rowNo) {
        var json = this.getRowData(rowNo);
        this.showConfirm("您是否确定复制该消息吗？<br/>复制后即将进入编辑页面，请进行相应信息修正。", this.copyConfirm.bind(this, json.uid));
    },

    //复制处理
    copyConfirm: function (uid) {
        var params = "uid=" + uid;

        //Ajax处理前显示提示画面
        this.showProcessing(false);

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "copyMessage", this.copyConfirmCallback.bind(this), params);
    },

    //提交处理[Callback后处理]
    copyConfirmCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //处理成功的场合、显示处理结果消息后刷新一览画面
        if (ajaxResult.result > 0) {
            window.location = this.inputUrl + "?uid=" + ajaxResult.content + "&isCopy=yes";
        }
        else {
            this.showMessage(ajaxResult.message, MSG_TYPE_ERROR);
        }
    },

    createMessageUserLink: function (rowData, type) {
        if (type == "all") {
            return "<a href='#' class='detail-link' onclick='" + this.selfInstance + ".showMessageUser(\"" + rowData.uid + "\",\"" + type + "\")'>" + rowData.allCount + "</a>";
        } else if (type == "unread") {
            return "<a href='#' class='detail-link' onclick='" + this.selfInstance + ".showMessageUser(\"" + rowData.uid + "\",\"" + type + "\")'>" + rowData.unreadCount + "</a>";
        }
    },

    //显示人员信息
    showMessageUser: function (uid, type) {
        this.messageUserPopupIns = SysApp.Cmn.MessageUserPopupListIns;
        this.messageUserPopupIns.setCondition(uid, type == "unread" ? "01" : "");
        this.messageUserPopupIns.show();
        this.messageUserPopupIns.getList(true);
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