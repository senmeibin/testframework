//定义数据一览画面JS类
SysApp.Sys.OperationSummaryList = Class.create();

SysApp.Sys.OperationSummaryList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "15%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("路径", "url", "30%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("访问次数", "count", "10%", SORT_YES, ALIGN_RIGHT, false, CAMEL_NO));
        this.colList.push(new ColInfo("首次访问人", "insertUserName", "8%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("首次访问时间", "insertDate", "12%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("最后访问人", "updateUserName", "8%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("最后访问时间", "updateDate", "12%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            if (rowData["isIgnore"] == 1) {
                strHtml = this.createOperateIgnorePathButton(rowNo, 0);
            } else {
                strHtml = this.createOperateIgnorePathButton(rowNo, 1);
            }
        }
        //POPUP详细表示
        else if (colName == "") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }

        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    //创建列表复制图标
    createOperateIgnorePathButton: function (rowNo, mode) {
        if (mode == 1) {
            return "<button type='button' class='btn btn-primary' onclick='" + this.selfInstance + ".operateIgnorePath(" + rowNo + "," + mode + ");return false;'>" + "<i class='fa fa-plus fa-width-fixed'></i>添加为忽略路径</button>";

        }
        else if (mode == 0) {
            return "<button type='button' class='btn btn-warning' onclick='" + this.selfInstance + ".operateIgnorePath(" + rowNo + "," + mode + ");return false;'>" + "<i class='fa fa-trash fa-width-fixed'></i>从忽略路径删除</button>";
        }
    },

    /**
     * 操作忽略路径
     * @param rowNo
     * @param mode
     */
    operateIgnorePath: function (rowNo, mode) {
        var rowData = this.getRowData(rowNo);
        var thisObj = this;
        var method = "";
        if (mode == 0) {
            method = "removeIgnoreUrl";
        } else if (mode == 1) {
            method = "addIgnoreUrl";
        }
        AjaxIns.sendPOST(this.controller + method, function (response) {
            if ($M.isAjaxFail(response.responseText)) {
                return;
            }
            if (response.status === 200) {
                var responseJSON = response.responseJSON;
                if (responseJSON.result === 1) {
                    thisObj.getList(true);
                } else {
                    thisObj.showErrorMessage(response.responseJSON.message);
                }
            } else {
                thisObj.showErrorMessage(response.statusText);
            }
        }, "url=" + rowData["url"]);
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