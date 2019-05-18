//定义数据一览画面JS类
SysApp.Demo.WeeklyReportList = Class.create();

SysApp.Demo.WeeklyReportList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "10%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("所属基地", "baseName", "12%", SORT_YES));
        this.colList.push(new ColInfo("人员", "userName", "13%", SORT_YES));
        this.colList.push(new ColInfo("填写时间", "fillTimeStart", "20%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("本周总结", "summary", "28%", SORT_YES));
        this.colList.push(new ColInfo("备注", "remark", "15%", SORT_YES));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            if (rowData.insertUser == this.currentUser) {
                //创建列表编辑图标
                strHtml = this.createEditButton(rowNo);
            }
            else {
                strHtml = this.createDetailButton(rowNo);
            }
            //创建列表导出图标
            strHtml += this.createExportButton(rowNo);
        }
        //POPUP详细表示
        else if (colName == "userUid") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        //填写时间
        else if (colName == "fillTimeStart" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD() + " 至 " + rowData['fillTimeEnd'].formatYMD();
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    createExportButton: function (rowNo) {
        return "<button type='button' class='btn btn-warning' onclick='" + this.selfInstance + ".onClick_exportReport(\"" + rowNo + "\");return false;'><i class='fa fa-pencil fa-cloud-download'></i>周报</button>";
    },

    onClick_exportReport: function (rowNo) {
        var data = this.datagrid.getRowData(rowNo);
        this.showConfirm("您确定要导出<b>" + data.userName + "</b>的周报数据吗？", this.exportReport.bind(this, data.uid));
    },

    exportReport: function (uid) {
        this.hideMessage();

        var form = $("<form>");
        form.attr('style', 'display:none');
        form.attr('target', '_blank');
        form.attr('method', 'post');
        form.attr('action', this.controller + this.exportReportMethod);

        //工资发放表UID
        var hiddenDom = $('<input>');
        hiddenDom.attr('type', 'hidden');
        hiddenDom.attr('name', "uid");
        hiddenDom.attr('value', uid);

        $('body').append(form);
        form.append(hiddenDom);
        form.submit();
        form.remove();
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